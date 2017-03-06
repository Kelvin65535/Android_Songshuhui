package com.kelvin.android_songshuhui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kelvin on 2017/3/6.
 */

public class ImageDownloader {
    /**
     * 异步加载图片
     * @param imageCallBack 加载完成后的回调接口
     */
    public void asyncLoadBitmap(final String imageURL, final ImageCallBack imageCallBack){
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    imageCallBack.imageLoadSuccess((Bitmap) msg.obj);
                } else if (msg.what == 1) {
                    imageCallBack.imageLoadFailed();
                }
            }
        };

        //开启线程加载图片
        new Thread(new Runnable() {
            Bitmap bitmap;

            @Override
            public void run() {
                try {
                    java.net.URL url = new URL(imageURL);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if(conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();

                        Message msg = Message.obtain();
                        msg.obj = bitmap;
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    public interface ImageCallBack {
        public void imageLoadSuccess(Bitmap bitmap);
        public void imageLoadFailed();
    }
}
