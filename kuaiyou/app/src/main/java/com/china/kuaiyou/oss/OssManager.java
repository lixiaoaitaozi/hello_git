package com.china.kuaiyou.oss;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadRequest;
import com.alibaba.sdk.android.oss.model.InitiateMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.ListPartsRequest;
import com.alibaba.sdk.android.oss.model.ListPartsResult;
import com.alibaba.sdk.android.oss.model.PartETag;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.alibaba.sdk.android.oss.model.UploadPartRequest;
import com.alibaba.sdk.android.oss.model.UploadPartResult;
import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.mybase.LG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/1.
 */

public class OssManager {


    private OSS oss;
    private Map<String,OSSAsyncTask>map=new ArrayMap<>();

    public static OssManager getInstance() {
        return OssInstance.instance;
    }

    private static class OssInstance {
        private static final OssManager instance = new OssManager();
    }

    private OssManager() {
    }

    private String bucketName;

    /**
     * 初始化
     **/
    public OssManager init() {
        if (oss == null) {
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSConfig.accessKeyId, OSSConfig.accessKeySecret);
            oss = new OSSClient(MyApplication.getContext(), OSSConfig.endpoint, credentialProvider);
        }
        this.bucketName = OSSConfig.bucketName;
        return OssInstance.instance;
    }

    /**
     * 普通上传，比如image
     **/


    public void upload(final String queStr, final String name, final String filePath, final OssUploadImp ossUploadImp) {
        LG.i("----", "----上传线程启动了"+(oss==null));
        new Thread(new Runnable() {
            @Override
            public void run() {

                // 构造上传请求
                PutObjectRequest put = new PutObjectRequest(bucketName, name, filePath);

                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        int download_progress = (int) (currentSize * 100 / totalSize);
                        if (ossUploadImp != null) {
                            ossUploadImp.uploadProgress(queStr, download_progress);
                        }

                        if (ossUploadImp != null && download_progress == 100) {
                            ossUploadImp.uploadOk(queStr);

                        }
                        LG.i("------", "----------" + request.getUploadFilePath() + "----" + currentSize + "----" + totalSize);
                    }
                });

                final OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        Log.d("PutObject", "UploadSuccess");
                        // 只有设置了servercallback，这个值才有数据
                        String serverCallbackReturnJson = result.getServerCallbackReturnBody();
                        Log.d("servercallback", serverCallbackReturnJson);
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        LG.i("---------", "--------" + serviceException.toString());
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            // 服务异常
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                        if (ossUploadImp != null) {
                            ossUploadImp.uploadError(queStr, filePath);
                        }
                    }
                });
                map.put(queStr,task);
                // task.cancel(); // 可以取消任务

                // task.waitUntilFinished(); // 可以等待直到任务完成
            }
        }).start();

    }


    public void cancel(String queStr){
        try {
            OSSAsyncTask task=map.get(queStr);
            if(task!=null){
                task.cancel();
            }
        }catch (Exception e){

        }
    }




    /**
     * 分片上传
     **/
    public void pullFP(String filePath, String name) throws ClientException, ServiceException {
        String uploadId;
        InitiateMultipartUploadRequest init = new InitiateMultipartUploadRequest(bucketName, name);
        InitiateMultipartUploadResult initResult = oss.initMultipartUpload(init);
        uploadId = initResult.getUploadId();
        long partSize = 128 * 1024; // 设置分片大小
        int currentIndex = 1; // 上传分片编号，从1开始

        File uploadFile = new File(filePath); // 需要分片上传的文件

        InputStream input = null;
        try {
            input = new FileInputStream(uploadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long fileLength = uploadFile.length();
        long uploadedLength = 0;
        List<PartETag> partETags = new ArrayList<PartETag>(); // 保存分片上传的结果
        while (uploadedLength < fileLength) {
            int partLength = (int) Math.min(partSize, fileLength - uploadedLength);
            byte[] partData = new byte[0]; // 按照分片大小读取文件的一段内容
            try {
                partData = IOUtils.readStreamAsBytesArray(input, partLength);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPartRequest uploadPart = new UploadPartRequest(bucketName, name, uploadId, currentIndex);
            uploadPart.setPartContent(partData); // 设置分片内容
            UploadPartResult uploadPartResult = null;
            try {
                uploadPartResult = oss.uploadPart(uploadPart);
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            partETags.add(new PartETag(currentIndex, uploadPartResult.getETag())); // 保存分片上传成功后的结果
            uploadedLength += partLength;
            currentIndex++;
            Log.d("currentIndex", currentIndex + "");
        }

        CompleteMultipartUploadRequest complete = new CompleteMultipartUploadRequest(bucketName, name, uploadId, partETags);
        final CompleteMultipartUploadResult completeResult = oss.completeMultipartUpload(complete);
        complete.setCallbackParam(new HashMap<String, String>() {
            {

                Log.d("uploadEnd", "uploadEnd");
                Log.d("multipartUpload", "multipart upload success! Location: " + completeResult.getLocation());
                put("callbackUrl", "<server address>");
                put("callbackBody", "<test>");
            }
        });

        ListPartsRequest listParts = new ListPartsRequest(bucketName, name, uploadId);

        ListPartsResult result = oss.listParts(listParts);

        for (int i = 0; i < result.getParts().size(); i++) {
            Log.d("已上传分片", "partNum: " + result.getParts().get(i).getPartNumber());
            Log.d("已上传分片", "partEtag: " + result.getParts().get(i).getETag());
            Log.d("已上传分片", "lastModified: " + result.getParts().get(i).getLastModified());
            Log.d("已上传分片", "partSize: " + result.getParts().get(i).getSize());
        }

    }

    public interface OssUploadImp {
        public void uploadOk(String queStr);

        public void uploadError(String queStr, String localUrl);

        public void uploadProgress(String queStr, int progress);
    }
}
