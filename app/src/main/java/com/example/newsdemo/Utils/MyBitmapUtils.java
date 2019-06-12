package com.example.newsdemo.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * 自定义的BitmapUtils,实现三级缓存
 */
public class MyBitmapUtils {

    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public MyBitmapUtils(Context context) {

        mMemoryCacheUtils = new MemoryCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils(context);
        mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
    }

    public Bitmap getBitmap(String url) {
        Bitmap bitmap;
        //内存缓存
        bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
        if (bitmap!= null) {
            Log.d("getBitmap", "内存获取Bitmap");
            return bitmap;
        }

        //本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            //从本地获取图片后,保存至内存中
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            Log.d("getBitmap", "本地获取Bitmap");
            return bitmap;
        }

        //网络缓存
        bitmap = mNetCacheUtils.getBitmapFromNet(url);
        Log.d("getBitmap", "网络获取Bitmap");
        return bitmap;
    }
}
