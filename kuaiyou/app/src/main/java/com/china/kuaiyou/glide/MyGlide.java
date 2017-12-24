package com.china.kuaiyou.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.china.kuaiyou.R;

import java.io.File;
import java.io.FileOutputStream;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyGlide {
    private static MyGlide myGlide;

    private MyGlide() {

    }

    public static MyGlide getInstance() {
        if (myGlide == null) {
            synchronized (MyGlide.class) {
                if (myGlide == null) {
                    myGlide = new MyGlide();
                }
            }
        }
        return myGlide;
    }

    private Drawable drawable;

    RoundedBitmapDrawable circularBitmapDrawable;

    public void setRoundedBitMap(final Context context, final ImageView imageView, int url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(url).asBitmap().dontAnimate().override(imageView.getLayoutParams().width,
                imageView.getLayoutParams().height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setBackground(circularBitmapDrawable);
            }
        });
    }

    public void setRoundedBitMap(final Context context, final ImageView imageView, String url) {
//        Glide.with(context).load(R.drawable.model_me).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(context).load(url).asBitmap().dontAnimate().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }


    public void setBitMap(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).error(R.drawable.device_bj).into(imageView);
    }

    public void setBitMap(final Context context, final ImageView imageView, final int url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).error(R.drawable.device_bj).into(imageView);
    }


    public void setBitMapGaoSiMohu(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).asBitmap().transform(new BlurTransformation(context, 14, 3)).dontAnimate().into(imageView);
    }

    public void setBitMapNuanSeQingDiao(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).asBitmap().transform(new ColorFilterTransformation(context, R.color.red)).dontAnimate().into(imageView);
    }

    public void setBitMapFenHongHuiYi(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).asBitmap().transform(new ColorFilterTransformation(context, R.color.blue)).dontAnimate().into(imageView);
    }

    public void setBitMapHeiSeJiaoCuo(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).asBitmap().transform(new GrayscaleTransformation(context)).dontAnimate().into(imageView);
    }

    public void setBitMapTianJiaWenZi(final Context context, final ImageView imageView, final String url) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(url).asBitmap().dontAnimate().override(imageView.getLayoutParams().width,
                imageView.getLayoutParams().height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                imageView.setBackground(new BitmapDrawable(createWatermark(resource, "添加文字测试")));
            }
        });
    }

    // 为图片target添加水印文字
    // Bitmap target：被添加水印的图片
    // String mark：水印文章
    private Bitmap createWatermark(Bitmap target, String mark) {
        int w = target.getWidth();
        int h = target.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        Paint p = new Paint();

        // 水印的颜色
        p.setColor(Color.RED);

        // 水印的字体大小
        p.setTextSize(50);

        p.setAntiAlias(true);// 去锯齿

        canvas.drawBitmap(target, 0, 0, p);

        // 在左边的中间位置开始添加水印
        canvas.drawText(mark, 0, h / 2, p);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bmp;
    }


    public void getBitmap(final Context context, final String url, final GetBitmapImp getBitmapImp) {
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bm = Bitmap.createScaledBitmap(resource, 150, 150, true);
                Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                        + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());


                if(getBitmapImp!=null){
                    getBitmapImp.getBitmap(bm);

                }
            }
        }); //方法中设置asBitmap可以设置回调类型
    }


    public boolean bitmapToFile(Bitmap bitmap, String fileUrl) {
        if (bitmap == null) {
            return false;
        }
        FileOutputStream fileOutStream = null;
        try {
            //通过相关方法生成一个Bitmap类型的对象
            File file = new File(fileUrl);
            fileOutStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream); // 把位图输出到指定的文件中
            fileOutStream.flush();
            fileOutStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public interface GetBitmapImp{
        public void getBitmap(Bitmap bitmap);
    }
}
