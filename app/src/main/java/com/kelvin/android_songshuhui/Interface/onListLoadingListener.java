package com.kelvin.android_songshuhui.Interface;

/**
 * Created by kelvi on 2016/11/4.
 */

/**
 * 该接口由fragment实现，在fragment调用listView中的方法进行操作时传入该对象，在子方法完成后回调接口内的方法，由fragment更新UI
 */
public interface onListLoadingListener {

    //当列表加载完成后调用该方法，通知上层的fragment更新UI
    public void onLoadingFinished();
}
