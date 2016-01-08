package com.yaobaohua.graduateyaobaohua.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/29 19：50
 * @DESC :用来异步获取本地视频缩略图的
 */
public class MyImageLoader {

    private LruCache<String, Bitmap> mCaches;

    public MyImageLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCaches = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void setBitmapToCache(String url, Bitmap bitmap) {
        if (getBitmapFromCache(url) == null) {
            mCaches.put(url, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCaches.get(url);
    }

    public void setImageByNativePath(ImageView view, String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
        } else {
            new MyAsyncTask(view, url).execute(url);
        }
    }

    class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        ImageView view;
        String mUrl;

        public MyAsyncTask(ImageView view, String url) {
            this.mUrl = url;
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlCache = params[0];
            Bitmap bitmap = FileSizeFormatUtils.getVideoThumbnail(urlCache, 100, 100,
                    MediaStore.Video.Thumbnails.MINI_KIND);
            if (bitmap != null) {
                setBitmapToCache(urlCache, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (view.getTag().equals(mUrl)) {
                view.setImageBitmap(bitmap);
            }
        }
    }

}
