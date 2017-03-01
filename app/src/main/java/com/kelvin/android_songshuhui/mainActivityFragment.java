package com.kelvin.android_songshuhui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.android_songshuhui.Interface.onListLoadingListener;

/**
 * Created by kelvi on 2016/11/3.
 */

@SuppressLint("ValidFragment")
public class mainActivityFragment extends Fragment implements onListLoadingListener {

    private SwipeRefreshLayout refreshLayout;   //下拉刷新的layout
    private mainActivityListView listView;  //在该fragment内显示的listView
    private String FirstPageURL; //listView获取需要显示内容的URL

    //这是下拉刷新layout的响应刷新触发器
    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //当刷新发生时，调用listView的更新列表的方法
            listView.refreshUpdateList(mainActivityFragment.this, FirstPageURL);
        }
    };

    public mainActivityFragment(){

    }

    /**
     * 构造方法
     * @param website 在该fragment上显示的专题首页网址
     */
    public mainActivityFragment(String website){
        super();
        FirstPageURL = website;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStace){

        //初始化view
        View view = inflater.inflate(R.layout.activity_main_fragment, container, false);

        //初始化组件

        //初始化下拉刷新layout
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);


        //根据URL的内容初始化listView
        listView = (mainActivityListView)view.findViewById(R.id.mainListViewYuanchuang);//从view中获取ID

        //设置下拉刷新的监听器，当下拉刷新发生时调用该方法
        refreshLayout.setOnRefreshListener(refreshListener);

        //在构造函数中调用下拉刷新的方法，相当于载入布局时自动初始化列表中的数据
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        refreshListener.onRefresh();

        //初始化完毕
        return view;
    }


    /**
     * 这是在下拉列表后，listView载入完毕的回调响应方法
     * 当加载完毕时会响应该方法，把refreshLayout的加载状态（转盘动画）设为false
     */
    @Override
    public void onLoadingFinished() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        });
    }
}
