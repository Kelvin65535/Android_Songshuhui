package com.kelvin.android_songshuhui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by kelvi on 2016/10/31.
 */

public class mainActivityListViewItem {
    //标题
    private String title;
    //图片网址
    private String imageWebSiteString;
    //网址
    private String website;
    //图片
    private Bitmap imageView;
    //标签
    private String tags;

    //保存上下文信息，用于引用资源
    private Context context;

    public mainActivityListViewItem(Context context, String title, String imageWebSiteString, String website, Bitmap imageView, String tags){
        this.title = title;
        this.website = website;
        this.imageWebSiteString = imageWebSiteString;
        //this.imageView = imageView;
        this.tags = tags;
        this.context = context;
        this.imageView = BitmapFactory.decodeResource(context.getResources(), R.drawable.titleimage_default);
    }

    public String getTitle(){
        return title;
    }

    public String getWebsite(){
        return website;
    }

    public Bitmap getImageView(){
        return imageView;
    }

    public String getTags(){
        return tags;
    }

    /**
     * 异步加载图片
     * @param imageView
     * @param imageCallBack 加载完成后的回调接口
     */
    public void asyncLoadBitmap(final ImageView imageView, final ImageCallBack imageCallBack){
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    imageCallBack.imageLoadSuccess(imageView, (Bitmap) msg.obj);
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
                    java.net.URL url = new URL(imageWebSiteString);

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
        public void imageLoadSuccess(ImageView imageView, Bitmap bitmap);
        public void imageLoadFailed();
    }
}
