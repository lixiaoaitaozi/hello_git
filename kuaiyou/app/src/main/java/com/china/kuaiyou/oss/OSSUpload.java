package com.china.kuaiyou.oss;//package com.inno_cn.svuser.oss;
//
//        import android.util.Log;
//
//        import com.alibaba.sdk.android.oss.ClientException;
//        import com.alibaba.sdk.android.oss.ServiceException;
//        import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
//        import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
//        import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
//        import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//        import com.alibaba.sdk.android.oss.model.PutObjectResult;
//
///**
// * Created by Administrator on 2017/10/20 0020.
// */
//
//public class OSSUpload {
//    public void upload(){
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectKey>", "<uploadFilePath>");
//// 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
//            }
//        });
//        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                Log.d("PutObject", "UploadSuccess");
//                Log.d("ETag", result.getETag());
//                Log.d("RequestId", result.getRequestId());
//            }
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                // 请求异常
//                if (clientExcepion != null) {
//                    // 本地异常如网络异常等
//                    clientExcepion.printStackTrace();
//                }
//                if (serviceException != null) {
//                    // 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
//// task.cancel(); // 可以取消任务
//// task.waitUntilFinished(); // 可以等待任务完成
//        task.waitUntilFinished();
//    }
//
//}
