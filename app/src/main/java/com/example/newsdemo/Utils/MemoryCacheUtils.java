package com.example.newsdemo.Utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import java.util.Iterator;
import java.util.Map;

/**
 * 三级缓存之内存缓存
 */
public class MemoryCacheUtils {
    private static final String TAG = "MemoryCacheUtils";

    // private HashMap<String,Bitmap> mMemoryCache=new HashMap<>();//1.因为强引用,容易造成内存溢出，所以考虑使用下面弱引用的方法
    // private HashMap<String, SoftReference<Bitmap>> mMemoryCache = new HashMap<>();//2.因为在Android2.3+后,系统会优先考虑回收弱引用对象,官方提出使用LruCache
    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;//得到手机最大允许内存的1/8,即超过指定内存,则开始回收
        Log.d(TAG, "getBitmapFromMemory-->maxMemory:" + maxMemory);
        //需要传入允许的内存最大值,虚拟机默认内存16M,真机不一定相同
        mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
            //用于计算每个条目的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                Log.d(TAG, "getBitmapFromMemory-->sizeOf:" + byteCount);
                return byteCount;
            }
        };


    }

    /**
     * 从内存中读图片
     *
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url) {
        //Bitmap bitmap = mMemoryCache.get(url);//1.强引用方法
            /*2.弱引用方法
            SoftReference<Bitmap> bitmapSoftReference = mMemoryCache.get(url);
            if (bitmapSoftReference != null) {
                Bitmap bitmap = bitmapSoftReference.get();
                return bitmap;
            }
            */
        //Log.d(TAG, "getBitmapFromMemory: " + mMemoryCache.createCount());
        Bitmap bitmap = mMemoryCache.get(url);

        Map<String, Bitmap> snapshot = mMemoryCache.snapshot();
        Log.d(TAG, "getBitmapFromMemory-->url:" + url + "******size:--->" + snapshot.size());
        Iterator<Map.Entry<String, Bitmap>> it = snapshot.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Bitmap> entry = it.next();
            Log.d("Map", entry.getKey() + "******" + entry.getValue());
        }

        return bitmap;

    }

    /**
     * 往内存中写图片
     *
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap) {
        //2. 弱引用方法
        //mMemoryCache.put(url, new SoftReference<>(bitmap));

        //1.强引用方法
        try {

            mMemoryCache.put(url, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "setBitmapToMemory: " + url + "***------->" + bitmap.getByteCount());
    }
}
//http://inews.gtimg.com/newsapp_ls/0/5131380631_300240/0