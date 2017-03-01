package com.kelvin.android_songshuhui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by kelvi on 2016/11/3.
 */

public class mainActivityFragmentAdapter extends FragmentPagerAdapter {

    /**
     * Fragment列表，存放用于滑动的Fragment对象
     */
    private List<Fragment> fragmentList;

    /**
     * 构造方法
     * @param fm
     */
    public mainActivityFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    /**
     * 根据传来的参数position，返回在position为止的fragment
     */
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
