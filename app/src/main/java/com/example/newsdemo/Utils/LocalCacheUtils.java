package com.example.newsdemo.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 三级缓存之本地缓存
 */
public class LocalCacheUtils {

    private Context context;
    String path = null;

    public LocalCacheUtils(Context context) {
        this.context = context;
        path = context.getCacheDir().getAbsolutePath();

    }


    private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WebNews";

    /**
     * 从本地读取图片
     *
     * @param url
     */
    public Bitmap getBitmapFromLocal(String url) {
        String fileName = null;//把图片的url当做文件名
        try {
            fileName = MD5Encoder.encode(url);//进行MD5加密
            File file = new File(path, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
                return bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从网络获取图片后,保存至本地缓存
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToLocal(String url, Bitmap bitmap) {
        try {
            Log.d("setBitmapToLocal", "url:" + url);

            String fileName = MD5Encoder.encode(url);//把图片的url当做文件名,并进行MD5加密
            Log.d("setBitmapToLocal", "fileName:" + fileName);
            File file = new File(path, fileName);

            //把图片保存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));


        } catch (Exception e) {

            e.printStackTrace();
        }

    }
}

