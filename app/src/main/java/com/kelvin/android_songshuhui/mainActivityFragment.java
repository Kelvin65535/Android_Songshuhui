package com.kelvin.android_songshuhui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
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

    private View rootview; //存储fragment内的view

    /**
     * 标记fragment内的view是否已初始化
     * 防止setUserVisibleHint()在view未初始化前找不到view的引用
     */
    private boolean isCreateView;

    /**
     * 当前Fragment是否处于可见状态
     */
    private boolean isFragmentVisible;

    /**
     * 标记view内的数据是否已经初始化完毕，防止当fragment可见状态切换时自动重新加载数据
     */
    private boolean hasInitedData;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当fragment第一次载入时，初始化成员变量
        isCreateView = false;
        isFragmentVisible = false;
        hasInitedData = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStace){

        //初始化view
        rootview = inflater.inflate(R.layout.activity_main_fragment, container, false);

        //初始化组件

        //初始化下拉刷新layout
        refreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);


        //根据URL的内容初始化listView
        listView = (mainActivityListView)rootview.findViewById(R.id.mainListViewYuanchuang);//从view中获取ID

        //设置下拉刷新的监听器，当下拉刷新发生时调用该方法
        refreshLayout.setOnRefreshListener(refreshListener);

        //初始化完毕
        return rootview;
    }

    /**
     * 当view创建完毕时调用该方法
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!isCreateView && getUserVisibleHint()){
            onFragmentVisibleChanged(true);
            isFragmentVisible = true;
        }
    }

    /**
     * 当前Fragment是否UI可见时执行下列动作
     * @param isVisibleToUser 若true则fragment可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //当fragment中的view未加载好时不执行任何动作
        if (rootview == null) {
            return;
        }
        //rootview不为空，则view已正确初始化完毕
        isCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChanged(true);//执行不可见->可见时的动作
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChanged(false);//可见->不可见时的动作
            isFragmentVisible = false;
        }
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

    /**
     * 当fragment的状态发生变化（如可见变为不可见、不可见变为可见）时候执行的动作
     * @param isVisible true 不可见 -> 可见
     *                  false 可见 -> 不可见
     */
    public void onFragmentVisibleChanged(boolean isVisible){
        if (isVisible){
            //根据hasInitedData判断是否自动执行下拉刷新操作，防止数据加载完毕后再次下拉刷新
            if (!hasInitedData){
                //在此调用下拉刷新的处理方法，相当于载入布局时自动初始化列表中的数据
                refreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(true);
                    }
                });
                refreshListener.onRefresh();
                hasInitedData = true;
            }else {
                refreshLayout.setRefreshing(false);
            }
        }else {
            refreshLayout.setRefreshing(false);
        }
    }
}
