package com.example.newsdemo.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * 自定义的图片缓存工具类--BitmapUtils,实现三级缓存，提升访问速度和节约流量
 * 通过网络、本地、内存三级缓存图片，来减少不必要的网络交互，避免浪费流量
 * 参考Android三级缓存知识点：https://blog.csdn.net/alidexuetong/article/details/52774253
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
            Log.d("getBitmap", "通过内存获取图片");
            return bitmap;
        }

        //本地缓存
        bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
        if (bitmap != null) {
            //从本地获取图片后,保存至内存中
            mMemoryCacheUtils.setBitmapToMemory(url, bitmap);
            Log.d("getBitmap", "通过本地获取图片");
            return bitmap;
        }

        //网络缓存
        bitmap = mNetCacheUtils.getBitmapFromNet(url);
        Log.d("getBitmap", "通过网络获取图片");
        return bitmap;
    }
}
