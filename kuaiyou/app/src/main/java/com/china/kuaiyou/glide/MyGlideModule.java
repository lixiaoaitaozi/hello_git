package com.china.kuaiyou.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.china.kuaiyou.mybase.PhoneUtil;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class MyGlideModule implements GlideModule {
    private String tag = this.getClass().toString() + ">>>";
    private static final int DISK_CACHE_SIZE = 5 * 1024 * 1024;
    public static final int MAX_MEMORY_CACHE_SIZE = 5 * 1024 * 1024;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置磁盘缓存的路径 path
        final File cacheDir = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath()
                + File.separator
                + PhoneUtil.getInstance().getAppName() + File.separator + "cache");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return DiskLruCacheWrapper.get(cacheDir, DISK_CACHE_SIZE);
            }
        });
        //设置内存缓存大小，一般默认使用glide内部的默认值
        builder.setMemoryCache(new LruResourceCache(MAX_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(IDataModel.class, InputStream.class,
                new MyDataLoader.Factory());
    }
}
