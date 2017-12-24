package com.china.kuaiyou.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.china.kuaiyou.appliction.MyApplication;
import com.china.kuaiyou.mybase.LG;
import com.china.kuaiyou.okhttp.OkHttpManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class MyOSS {


    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    private static String accessKeyId = "LTAIIoLe8m6UbCeW";
    private static String accessKeySecret = "ZDwtQd1MAP2nZbFhju0ECSVCo9vVvb";
    private static String bucketName = "innosmart2017";
    private final String getStsTokenUrl = "https://sts.aliyuncs.com";


    public void getOss() {
        OkHttpManager.DataCallBack dataCallBack = new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result, int netbs) throws Exception {
                LG.i("MyOSS", result);
            }
        };
        Map<String, String> map = new HashMap<>();
        map.put("Format", "JSON");
        map.put("Version", "2015-04-01");
        map.put("AccessKeyId", accessKeyId);
        map.put("Signature", "JSON");
        map.put("SignatureMethod", "HMAC-SHA1");
        map.put("SignatureVersion", "1.0");
        map.put("SignatureNonce", "123456789");
        map.put("Timestamp", getTimestamp());
        map.put("Action", "oss:*");
        LG.i("Myoss", getTimestamp());
        OkHttpManager.postAsync(getStsTokenUrl, map, dataCallBack, 1);

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, "<StsToken.SecurityToken>") {
            @Override
            public OSSFederationToken getFederationToken() {
                return super.getFederationToken();
            }
        };

    }


    public String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd?hh:mm:ss");
        Date nowDate = new Date();
        String time = sdf.format(nowDate);
        int index = time.indexOf("?");
        String time1 = time.substring(0, index);
        String time2 = time.substring(index + 1, time.length());
        StringBuilder sb = new StringBuilder();
        sb.append(time1);
        sb.append("T");
        sb.append(time2);
        sb.append("Z");
        return sb.toString();
    }

    public void add() {

        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, getStsTokenUrl) {
            @Override
            public OSSFederationToken getFederationToken() {
                return super.getFederationToken();
            }
        };
        OSS oss = new OSSClient(MyApplication.getContext(), endpoint, credentialProvider);
        String url = MyApplication.public_files + File.separator + "image" + File.separator + "public_h_test_2.jpg";
        File file = new File(url);
        LG.i("Myoss", file.exists());
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucketName, "lixiao.jpg", url);
// 异步上传时可以设置进度回调

        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                LG.i("Myoss", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LG.i("Myoss", "UploadSuccess");
                LG.i("Myoss", result.getETag());
                LG.i("Myoss", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LG.i("Myoss", serviceException.getErrorCode());
                    LG.i("Myoss", serviceException.getRequestId());
                    LG.i("Myoss", serviceException.getHostId());
                    LG.i("Myoss", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务
// task.waitUntilFinished(); // 可以等待任务完成
    }
}
