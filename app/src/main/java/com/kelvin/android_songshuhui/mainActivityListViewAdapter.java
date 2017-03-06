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
    private List<mainActivityListViewItem> dataArray;
    private int index = 0;//存放item的索引
    private Bitmap bitmap = null;//从URL加载的图片bitmap
    private String imageURL;//图片URL

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
        this.dataArray = lists;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        mainActivityListViewItem item = getItem(position);
        index = position;

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


        //图片URL
        imageURL = item.getImageWebSite();
        viewHolder.imageView.setTag(imageURL);//设置imageview标签

        //启动线程，异步加载图片
        try {
            Bitmap bitmap = getBitmap(item.getImageWebSite(), index);
        }catch (Exception e){
            e.printStackTrace();
        }

        viewHolder.titleName.setText(item.getTitle());
        viewHolder.titleTag.setText(item.getTags());

        //设置imageView里的图片的显示方式为将图片按比例扩大/缩小到View的宽度，居中显示
        viewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return convertView;
    }

    /**
     * 使用线程加载图片
     * @param urlString 图片URL
     * @param index 要加载图片的item的索引
     * @return bitmap
     */
    public Bitmap getBitmap(final String urlString, final int index){
        //开启线程加载图片
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    java.net.URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if(conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                    Message msg = handler.obtainMessage(0, bitmap);
                    msg.what = index;
                    handler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return bitmap;
    }

    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            ImageView iv = (ImageView) listView.findViewWithTag(imageURL);
            Bitmap bitmap = (Bitmap)message.obj;
            if (iv != null){
                if(iv.getTag() != null && iv.getTag().equals(imageURL)){
                    if (bitmap == null){
                        iv.setImageResource(R.drawable.titleimage_default);//加载预设图片
                    }else {
                        iv.setImageBitmap(bitmap);
                        iv.setTag(null);
                    }
                }
            }

        }
    };

    private class ViewHolder {
        private ImageView imageView;
        private TextView titleName;
        private TextView titleTag;
    }


}
