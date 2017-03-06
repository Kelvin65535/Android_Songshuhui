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

    public String getImageWebSite() {return imageWebSiteString;}

    public String getWebsite(){
        return website;
    }

    public Bitmap getImageView(){
        return imageView;
    }

    public String getTags(){
        return tags;
    }

}
