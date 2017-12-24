package com.china.kuaiyou.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类名称：${USER_NAME}
 * 类描述：<p>用于进行位图对象{@link Bitmap}的编解码、图像的读取和保存等操作的工具类。</p>
 * <p>目前支持的特性如下：</p>
 * <ul>
 * <li>支持从文件、字节数组和{@link InputStream}中读取图像</li>
 * <li>支持根据宽高或总像素数量限制位图的生成大小</li>
 * <li>提供仅用于度量位图宽高的方法</li>
 * </ul>
 * <p>该工具类已对各种可能出现的异常作了封装和保护，调用者仅需在输出为null时作处理。</p>
 *
 * @version 1.0 包含多种Bitmap读取和保存方式的工具方法
 *          创建人：万文浩
 *          创建时间：2016-05-31 14:00
 */
public final class BitmapUtils {

    private static final String TAG = "==" + BitmapUtils.class.getSimpleName();
    /**
     * 默认的位图图像解码的最大像素数限制 (2^19)，约等于720*720
     */
    public static final int DEFALUT_BITMAP_MAX_PIXELS = 524288;

    public static final int UNCONSTRAINED = -1;


    /**
     * 从指定的文件中获取位图图像
     *
     * @param filePath 图像文件的完整路径
     * @return 与原图大小相同的位图图像
     */
    public static InputStream getBitmap(String filePath) {
        if (filePath == null) {
            Log.e(TAG, "Argument 'filePath' is null at getBitmap(String)");
            return null;
        }
        Bitmap retBitmap = null;
        try {
            retBitmap = BitmapFactory.decodeFile(filePath);
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "OutOfMemoryError at getBitmap(String)", e);
            Log.e(TAG, "    filePath: " + filePath);
        } catch (Exception e) {
            Log.e(TAG, "Exception at getBitmap(String)", e);
            Log.e(TAG, "    filePath: " + filePath);
        }
        return compressImage(retBitmap);
    }

    /**
     * 质量压缩方法
     *
     * @param image 要压缩的位图
     * @return 压缩后的字节输出 流
     */
    public static InputStream compressImage(Bitmap image) {
        if (image == null) {
            throw new NullPointerException("传入的是个假的位图");
        }
        InputStream is;
        //压缩标准
        int size = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        int imageSize = baos.toByteArray().length / 1024;
        //压缩原图片的50%以上
        if (imageSize < 500)
            size = 100;// 循环判断如果压缩后图片是否大于100kb,大于继续压缩
        else if (imageSize < 1000)
            size = 300;// 循环判断如果压缩后图片是否大于500kb,大于继续压缩
        else if (imageSize < 1500)
            size = 700;// 循环判断如果压缩后图片是否大于1MB,大于继续压缩
        else if (imageSize < 2000)
            size = 1000;// 循环判断如果压缩后图片是否大于1.5MB,大于继续压缩
        else
            size = 1500;// 循环判断如果压缩后图片是否大于2MB,大于继续压缩

        while (baos.toByteArray().length / 1024 > size) {
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
//        Log.e(TAG, "compressImage减了多少: " + options + "==还有多少:" + baos.toByteArray().length);
        is = new ByteArrayInputStream(baos.toByteArray());
        try {
            baos.close();
            image.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is != null ? is : null;
    }


    /**
     * 位图 转字节流
     *
     * @param bitmap 转换目标
     * @return 转换结果
     */
    public static InputStream getInputStream(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 从指定的文件中获取位图图像
     *
     * @param filePath  图像文件的完整路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 根据指定的位图最大宽度和高度生成位图
     */
    public static Bitmap getBitmap(String filePath, int maxWidth, int maxHeight) {
        if (filePath == null) {
            Log.w(TAG, "Argument 'filePath' is null at getBitmap(String, int, int)");
            return null;
        }
        if (maxWidth <= 0) {
            Log.w(TAG, "Argument 'maxWidth' <= 0 at getBitmap(String, int, int)");
            return null;
        }
        if (maxHeight <= 0) {
            Log.w(TAG, "Argument 'maxHeight' <= 0 at getBitmap(String, int, int)");
            return null;
        }

        Bitmap retBitmap = null;

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(String, int, int)");
                return null;
            }

            // 宽度比
            int ratioWidth = (int) ((double) options.outWidth / maxWidth + 0.5);
            // 高度比
            int ratioHeight = (int) ((double) options.outHeight / maxHeight + 0.5);

            int max = ratioHeight > ratioWidth ? ratioHeight : ratioWidth;
            int sampleSize = max <= 1 ? 1 : max;

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;

            retBitmap = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(String, int, int)", e);
            Log.w(TAG, "    filePath: " + filePath);
            return null;
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(String, int, int)", e);
            Log.w(TAG, "    filePath: " + filePath);
            return null;
        }
        return retBitmap != null ? setBatmip(retBitmap, maxWidth, maxHeight) : null;
    }

    /**
     * 修改位图的大小
     *
     * @param bitmap    修改目标
     * @param newWidth  位图的宽
     * @param newHeight 位图的高
     * @return 修改结果
     */
    private static Bitmap setBatmip(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap mbitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return mbitmap;
    }

    /**
     * 从指定的文件中获取位图图像
     *
     * @param filePath       图像文件的完整路径
     * @param maxNumOfPixels 图像的最大像素数量，若原图超过该值将会自动缩小输出图像
     * @return 根据指定最大像素数量生成的位图
     */
    public static Bitmap getBitmap(String filePath, int maxNumOfPixels) {
        if (filePath == null) {
            Log.w(TAG, "Argument 'filePath' is null at getBitmap(String, int)");
            return null;
        }
        if (maxNumOfPixels <= 0) {
            Log.w(TAG, "Argument 'maxNumOfPixels' <= 0 at getBitmap(String, int)");
            return null;
        }

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(String, int)");
                return null;
            }

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = computeSampleSize(options, UNCONSTRAINED, maxNumOfPixels);

            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeFile(filePath, options);

        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(String, int)", e);
            Log.w(TAG, "    filePath: " + filePath);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(String, int)", e);
            Log.w(TAG, "    filePath: " + filePath);
        }
        return null;
    }


    /**
     * 从字节数组中获取位图图像
     *
     * @param imageBytes 位图的字节数组
     * @return 与原图大小相同的位图图像
     */
    public static Bitmap getBitmap(byte[] imageBytes) {
        if (imageBytes == null || imageBytes.length == 0) {
            Log.w(TAG, "Argument 'imageBytes' is null or empty at getBitmap(byte[])");
            return null;
        }

        Bitmap retBitmap = null;
        try {
            retBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(byte[])", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(byte[])", e);
        }
        return retBitmap;
    }

    /**
     * 从字节数组中获取位图图像
     *
     * @param imageBytes 位图的字节数组
     * @param maxWidth   最大宽度
     * @param maxHeight  最大高度
     * @return 根据指定的位图最大宽度和高度生成位图
     */
    public static Bitmap getBitmap(byte[] imageBytes, int maxWidth, int maxHeight) {
        if (imageBytes == null || imageBytes.length == 0) {
            Log.w(TAG, "Argument 'imageBytes' is null or empty at getBitmap(byte[], int, int)");
            return null;
        }
        if (maxWidth <= 0) {
            Log.w(TAG, "Argument 'maxWidth' <= 0 at getBitmap(byte[], int, int)");
            return null;
        }
        if (maxHeight <= 0) {
            Log.w(TAG, "Argument 'maxHeight' <= 0 at getBitmap(byte[], int, int)");
            return null;
        }

        Bitmap retBitmap = null;

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(byte[], int, int)");
                return null;
            }

            // 宽度比
            int ratioWidth = (int) ((double) options.outWidth / maxWidth + 0.5);
            // 高度比
            int ratioHeight = (int) ((double) options.outHeight / maxHeight + 0.5);

            int max = ratioHeight > ratioWidth ? ratioHeight : ratioWidth;

            int sampleSize = 1;
            if (max <= 1) {
                sampleSize = 1;
            } else {
                sampleSize = max;
            }

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;

            retBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(byte[], int, int)", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(byte[], int, int)", e);
        }

        return retBitmap;
    }

    /**
     * 从字节数组中获取位图图像
     *
     * @param imageBytes     位图的字节数组
     * @param maxNumOfPixels 图像的最大像素数量，若原图超过该值将会自动缩小输出图像
     * @return 根据指定最大像素数量生成的位图
     */
    public static Bitmap getBitmap(byte[] imageBytes, int maxNumOfPixels) {
        if (imageBytes == null || imageBytes.length == 0) {
            Log.w(TAG, "Argument 'imageBytes' is null or empty at getBitmap(byte[], int)");
            return null;
        }
        if (maxNumOfPixels <= 0) {
            Log.w(TAG, "Argument 'maxNumOfPixels' <= 0 at getBitmap(byte[], int)");
            return null;
        }

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(byte[], int)");
                return null;
            }

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = computeSampleSize(options, UNCONSTRAINED, maxNumOfPixels);

            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(byte[], int)", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(byte[], int)", e);
        }
        return null;
    }


    /**
     * 从数据输入流中获取位图图像
     *
     * @param is 图像文件的数据输入流
     * @return 与原图大小相同的位图图像
     */
    public static Bitmap getBitmap(InputStream is) {
        if (is == null) {
            Log.w(TAG, "Argument 'is' is null at getBitmap(InputStream)");
            return null;
        }

        Bitmap retBitmap = null;
        try {
            retBitmap = BitmapFactory.decodeStream(is);
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(InputStream)", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(InputStream)", e);
        }
        return retBitmap;
    }

    /**
     * 从数据输入流中获取位图图像
     *
     * @param is        图像文件的数据输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 根据指定的位图最大宽度和高度生成位图
     */
    public static Bitmap getBitmap(InputStream is, int maxWidth, int maxHeight) {
        if (is == null) {
            Log.w(TAG, "Argument 'is' is null at getBitmap(InputStream, int, int)");
            return null;
        }
        if (maxWidth <= 0) {
            Log.w(TAG, "Argument 'maxWidth' <= 0 at getBitmap(InputStream, int, int)");
            return null;
        }
        if (maxHeight <= 0) {
            Log.w(TAG, "Argument 'maxHeight' <= 0 at getBitmap(InputStream, int, int)");
            return null;
        }

        Bitmap retBitmap = null;

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(InputStream, int, int)");
                return null;
            }

            // 宽度比
            int ratioWidth = (int) ((double) options.outWidth / maxWidth + 0.5);
            // 高度比
            int ratioHeight = (int) ((double) options.outHeight / maxHeight + 0.5);

            int max = ratioHeight > ratioWidth ? ratioHeight : ratioWidth;

            int sampleSize = 1;
            if (max <= 1) {
                sampleSize = 1;
            } else {
                sampleSize = max;
            }

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;

            retBitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(InputStream, int, int)", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(InputStream, int, int)", e);
        }

        return retBitmap;
    }

    /**
     * 从数据输入流中获取位图图像
     *
     * @param is             图像文件的数据输入流
     * @param maxNumOfPixels 图像的最大像素数量，若原图超过该值将会自动缩小输出图像
     * @return 根据指定最大像素数量生成的位图
     */
    public static Bitmap getBitmap(InputStream is, int maxNumOfPixels) {
        if (is == null) {
            Log.w(TAG, "Argument 'is' is null at getBitmap(InputStream, int)");
            return null;
        }
        if (maxNumOfPixels <= 0) {
            Log.w(TAG, "Argument 'maxNumOfPixels' <= 0 at getBitmap(InputStream, int)");
            return null;
        }

        try {
            // 取图像sampleSize的初始值
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1) {
                Log.w(TAG, "Error on decode bounds at getBitmap(InputStream, int)");
                return null;
            }

            // 图片像素的宽和高取原来的1/sampleSize
            options.inSampleSize = computeSampleSize(options, UNCONSTRAINED, maxNumOfPixels);

            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            return BitmapFactory.decodeStream(is, null, options);

        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError at getBitmap(InputStream, int)", e);
        } catch (Exception e) {
            Log.w(TAG, "Exception at getBitmap(InputStream, int)", e);
        }
        return null;
    }


    /**
     * 根据图像的大小，使用默认图像限制（不指定变长，最大像素采用
     * {@link #DEFALUT_BITMAP_MAX_PIXELS}），计算出要缩放的图像的比例
     *
     * @param width  图像的宽度
     * @param height 图像的高度
     * @return 缩放比例（N分之一）
     */
    public static int computeSampleSize(int width, int height) {
        return computeSampleSize(width, height,
                UNCONSTRAINED, DEFALUT_BITMAP_MAX_PIXELS);
    }

    /**
     * 根据图像的属性和最大像素值，精确计算要缩放的图像的比例
     *
     * @param options        图像的设置属性（已DecodeBounds的对象）
     * @param minSideLength  最短边长，使用{@link #UNCONSTRAINED}表示不指定
     * @param maxNumOfPixels 最大像素值
     * @return 缩放比例（N分之一）
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        if (options == null) {
            Log.w(TAG, "Argument 'options' is null " +
                    "at computeSampleSize(BitmapFactory.Options, int, int)");
            return 1;
        }
        return computeSampleSize(options.outWidth, options.outHeight,
                minSideLength, maxNumOfPixels);
    }

    /**
     * <p>根据图像的属性和最大像素值，精确计算要缩放的图像的比例</p>
     * <p>说明：该段代码从系统Camera源码中提取</p>
     *
     * @param width          图像的宽度
     * @param height         图像的高度
     * @param minSideLength  最短边长，使用{@link #UNCONSTRAINED}表示不指定
     * @param maxNumOfPixels 最大像素值
     * @return 缩放比例（N分之一）
     */
    public static int computeSampleSize(int width, int height,
                                        int minSideLength, int maxNumOfPixels) {
        if (width <= 0) {
            Log.w(TAG, "Argument 'width' <= 0 at computeSampleSize()");
            return 1;
        }
        if (height <= 0) {
            Log.w(TAG, "Argument 'height' <= 0 at computeSampleSize()");
            return 1;
        }
        if (maxNumOfPixels <= 0) {
            Log.w(TAG, "Argument 'maxNumOfPixels' <= 0 at computeSampleSize()");
            return 1;
        }

        int initialSize = computeInitialSampleSize(width, height,
                minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(int width, int height,
                                                int minSideLength, int maxNumOfPixels) {
        double w = width;
        double h = height;

        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == UNCONSTRAINED) &&
                (minSideLength == UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * 根据指定的图像文件，度量图像文件的尺寸
     *
     * @param filePath 图像文件的路径
     * @param rect     存放宽度和高度数据的对象，不能为空
     */
    public static void measureImageSize(String filePath, Rect rect) {
        if (filePath == null || filePath.length() == 0) {
            Log.w(TAG, "Argument 'path' is empty at measureImageSize(String, Rect)!");
            return;
        }
        if (rect == null) {
            Log.w(TAG, "Argument 'rect' is null at measureImageSize(String, Rect)!");
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            Log.w(TAG, "Exception at measureImageSize(String, Rect)", e);
            Log.w(TAG, "    filePath: " + filePath);
        }

        rect.set(0, 0, options.outWidth, options.outHeight);
    }

    /**
     * 根据指定的图像数据，度量图像的尺寸
     *
     * @param imageBytes 图像的数据的字节数组
     * @param rect       存放宽度和高度数据的对象，不能为空
     */
    public static void measureImageSize(byte[] imageBytes, Rect rect) {
        if (imageBytes == null || imageBytes.length == 0) {
            Log.w(TAG, "Argument 'bytes' is empty at measureImageSize(byte[], Rect)!");
            return;
        }
        if (rect == null) {
            Log.w(TAG, "Argument 'rect' is null at measureImageSize(byte[], Rect)!");
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
        } catch (Exception e) {
            Log.w(TAG, "Exception at measureImageSize(byte[], Rect)", e);
        }

        rect.set(0, 0, options.outWidth, options.outHeight);
    }

    /**
     * 根据指定的图像数据，度量图像文件的尺寸
     *
     * @param is   图像文件的数据输入流
     * @param rect 存放宽度和高度数据的对象，不能为空
     */
    public static void measureImageSize(InputStream is, Rect rect) {
        if (is == null) {
            Log.w(TAG, "Argument 'is' is null at measureImageSize(InputStream, Rect)!");
            return;
        }
        if (rect == null) {
            Log.w(TAG, "Argument 'rect' is null at measureImageSize(InputStream, Rect)!");
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(is, null, options);
        } catch (Exception e) {
            Log.w(TAG, "Exception at measureImageSize(InputStream, Rect)", e);
        }

        rect.set(0, 0, options.outWidth, options.outHeight);
    }


    /**
     * 对位图对象采用<b>PNG</b>格式进行无损压缩，并保存到指定文件
     *
     * @param bitmap   需要压缩的位图对象，不能为空
     * @param filePath 要保存的图像文件的路径，不能为空。
     *                 若要让图片文件可见，请采用 <code>.png</code> 后缀
     * @return 是否成功执行
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath) {
        return saveBitmap(bitmap, filePath, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * 对位图对象进行压缩，并保存到指定文件
     *
     * @param bitmap   需要压缩的位图对象，不能为空
     * @param filePath 要保存的图像文件的路径，不能为空。
     *                 若要让图片文件可见，请采用与 <code>format</code> 参数相匹配的后缀
     * @param format   压缩的格式
     * @param quality  压缩的质量 [0,100]
     * @return 是否成功执行
     */
    public static boolean saveBitmap(Bitmap bitmap, String filePath,
                                     Bitmap.CompressFormat format, int quality) {
        if (bitmap == null) {
            Log.w(TAG, "Argument 'bitmap' is null " +
                    "at saveBitmap(Bitmap, String, CompressFormat, int)");
            return false;
        }
        if (TextUtils.isEmpty(filePath)) {
            Log.w(TAG, "Argument 'filePath' is null or empty " +
                    "at saveBitmap(Bitmap, String, CompressFormat, int)");
            return false;
        }

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            File imageFile = new File(filePath);
            imageFile.createNewFile();

            fos = new FileOutputStream(imageFile);
            bos = new BufferedOutputStream(fos);

            bitmap.compress(format, quality, bos);

            bos.flush();
            fos.flush();
        } catch (Exception e) {
            Log.w(TAG, "Error on compressing and saving bitmap " +
                    "at saveBitmap(Bitmap, String, CompressFormat, int)", e);
            return false;
        } finally {
            try {
                if (bos != null) bos.close();
                if (fos != null) fos.close();
            } catch (Exception e) {
            }
        }

        return true;
    }


    public static Bitmap scale(Bitmap srcBitmap, int width, int height,
                               boolean IgnoreAspectRatio) {
        Rect srcRect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        return scale(srcBitmap, srcRect, width, height, IgnoreAspectRatio);
    }

    public static Bitmap scale(Bitmap srcBitmap, Rect srcRect, int width, int height,
                               boolean IgnoreAspectRatio) {
        float hFactor = (float) width / srcBitmap.getWidth();
        float vFactor = (float) height / srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        if (IgnoreAspectRatio) {
            matrix.postScale(hFactor, vFactor);
        } else {
            float factor = Math.min(vFactor, hFactor);
            matrix.postScale(factor, factor);
        }
        Bitmap resizeBmp = Bitmap.createBitmap(srcBitmap, srcRect.left, srcRect.top, srcRect.width(), srcRect.height(), matrix, true);
        return resizeBmp;
    }

    public static boolean saveAsFile(Bitmap bitmap, String path) {
        if (path == null || bitmap == null) {
            return false;
        }
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 对位图进行不指定尺寸的备份
     *
     * @param bmpSrc 被复制的位图
     * @return 复制的位图
     */
    public static Bitmap duplicateBitmaps(Bitmap bmpSrc) {
        if (null == bmpSrc) {
            return null;
        }

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }

        return bmpDest;
    }
}
