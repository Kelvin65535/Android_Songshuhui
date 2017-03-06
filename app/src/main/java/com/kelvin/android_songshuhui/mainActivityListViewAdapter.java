package com.kelvin.android_songshuhui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by kelvi on 2016/11/1.
 */

public class mainActivityListViewAdapter extends ArrayAdapter<mainActivityListViewItem> {

    private ListView listView;

    private int resourceID;

    ImageDownloader imageDownloader;

    /**
     *
     * @param context
     * @param textViewResourceID
     * @param lists
     * @param listView
     */
    public mainActivityListViewAdapter(Context context, int textViewResourceID, List<mainActivityListViewItem> lists, ListView listView){
        super(context, textViewResourceID, lists);
        resourceID = textViewResourceID;
        this.listView = listView;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mainActivityListViewItem item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(resourceID, null);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.title_image);//图片
            viewHolder.titleName = (TextView)convertView.findViewById(R.id.title_name);//名称
            viewHolder.titleTag = (TextView)convertView.findViewById(R.id.title_tags);//标签

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String imageURL = item.getImageWebSite();
        viewHolder.imageView.setTag(imageURL);

        viewHolder.imageView.setBackgroundResource(R.drawable.titleimage_default);//加载预设图片
        viewHolder.titleName.setText(item.getTitle());
        viewHolder.titleTag.setText(item.getTags());

        //异步加载图片
        if (imageDownloader != null){
            imageDownloader.asyncLoadBitmap(imageURL, new ImageDownloader.ImageCallBack() {
                @Override
                public void imageLoadSuccess(Bitmap bitmap) {
                    ImageView imageView = (ImageView)listView.findViewWithTag(imageURL);
                    if (imageView != null){
                        imageView.setImageBitmap(bitmap);
                        imageView.setTag("");
                    }
                }
                @Override
                public void imageLoadFailed() {

                }
            });
        }

        //设置imageView里的图片的显示方式为将图片按比例扩大/缩小到View的宽度，居中显示
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView titleName;
        private TextView titleTag;
    }


}
