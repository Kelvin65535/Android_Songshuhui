package com.kelvin.android_songshuhui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

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

    public mainActivityListViewItem(String title, String imageWebSiteString, String website, Bitmap imageView, String tags){
        this.title = title;
        this.website = website;
        this.imageWebSiteString = imageWebSiteString;
        this.imageView = imageView;
        this.tags = tags;
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

}
