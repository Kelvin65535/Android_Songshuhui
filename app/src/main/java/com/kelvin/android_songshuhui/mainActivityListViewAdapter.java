package com.kelvin.android_songshuhui;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kelvi on 2016/11/1.
 */

public class mainActivityListViewAdapter extends ArrayAdapter<mainActivityListViewItem> {

    private int resourceID;

    public mainActivityListViewAdapter(Context context, int textViewResourceID, List<mainActivityListViewItem> lists){
        super(context, textViewResourceID, lists);
        resourceID = textViewResourceID;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mainActivityListViewItem item = getItem(position);
        /*
        View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.title_image);//图片
        TextView titleName = (TextView)view.findViewById(R.id.title_name);//名称
        TextView titleTag = (TextView)view.findViewById(R.id.title_tags);//标签

        imageView.setImageBitmap(item.getImageView());
        titleName.setText(item.getTitle());
        titleTag.setText(item.getTags());

        //设置imageView里的图片的显示方式为将图片按比例扩大/缩小到View的宽度，居中显示
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        */
        final ViewHolder viewHolder;
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

        //viewHolder.imageView.setImageBitmap(item.getImageView());
        viewHolder.imageView.setBackgroundResource(R.drawable.titleimage_default);
        viewHolder.titleName.setText(item.getTitle());
        viewHolder.titleTag.setText(item.getTags());

        //异步加载图片
        item.asyncLoadBitmap(viewHolder.imageView, new mainActivityListViewItem.ImageCallBack() {
            @Override
            public void imageLoadSuccess(ImageView imageView, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void imageLoadFailed() {

            }
        });

        //设置imageView里的图片的显示方式为将图片按比例扩大/缩小到View的宽度，居中显示
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return convertView;
    }

    private final class ViewHolder {
        private ImageView imageView;
        private TextView titleName;
        private TextView titleTag;
    }
}
