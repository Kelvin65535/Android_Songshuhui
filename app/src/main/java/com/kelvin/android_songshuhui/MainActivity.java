package com.kelvin.android_songshuhui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {


    SharedPreferences pref;
    SharedPreferences.Editor prefEditer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //载入设置
        pref = getSharedPreferences("settings", MODE_PRIVATE);
        prefEditer = pref.edit();

        //初始化fragment列表
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        mainActivityFragmentAdapter fragmentAdapter = new mainActivityFragmentAdapter(getSupportFragmentManager(), fragmentList);

        //初始化各个专题的fragment
        //添加fragment到list中，同时初始化tabLayout标题标签
        ArrayList<String> titleList = new ArrayList<>();
        boolean show_yuanchuang = pref.getBoolean("yuanchuang", true);
        if (show_yuanchuang){
            mainActivityFragment fragment_tag_yuanchuang = new mainActivityFragment(this.getResources().getString(R.string.website_yuanchuang));
            fragmentList.add(fragment_tag_yuanchuang);
            titleList.add("原创");
        }
        boolean show_yiwen = pref.getBoolean("yiwen", true);
        if (show_yiwen){
            mainActivityFragment fragment_tag_yiwen = new mainActivityFragment(this.getResources().getString(R.string.website_yiwen));
            fragmentList.add(fragment_tag_yiwen);
            titleList.add("译文");
        }
        boolean show_health = pref.getBoolean("health", true);
        if (show_health){
            mainActivityFragment fragment_tag_health = new mainActivityFragment(this.getResources().getString(R.string.website_jiankang));
            fragmentList.add(fragment_tag_health);
            titleList.add("健康");
        }
        boolean show_chemistry = pref.getBoolean("chemistry", true);
        if (show_chemistry){
            mainActivityFragment fragment_tag_chemistry = new mainActivityFragment(this.getResources().getString(R.string.website_ch));
            fragmentList.add(fragment_tag_chemistry);
            titleList.add("化学");
        }
        boolean show_medical = pref.getBoolean("medical", true);
        if (show_medical){
            mainActivityFragment fragment_tag_medical = new mainActivityFragment(this.getResources().getString(R.string.website_medi));
            fragmentList.add(fragment_tag_medical);
            titleList.add("医学");
        }
        boolean show_astro = pref.getBoolean("astro", true);
        if (show_astro){
            mainActivityFragment fragment_tag_astro = new mainActivityFragment(this.getResources().getString(R.string.website_astro));
            fragmentList.add(fragment_tag_astro);
            titleList.add("天文");
        }
        boolean show_psychology = pref.getBoolean("psychology", true);
        if (show_psychology){
            mainActivityFragment fragment_tag_psychology = new mainActivityFragment(this.getResources().getString(R.string.website_psychology));
            fragmentList.add(fragment_tag_psychology);
            titleList.add("心理");
        }
        boolean show_math = pref.getBoolean("math", true);
        if (show_math){
            mainActivityFragment fragment_tag_math = new mainActivityFragment(this.getResources().getString(R.string.website_math));
            fragmentList.add(fragment_tag_math);
            titleList.add("数学");
        }
        boolean show_environment = pref.getBoolean("environment", true);
        if (show_environment){
            mainActivityFragment fragment_tag_environment = new mainActivityFragment(this.getResources().getString(R.string.website_environment));
            fragmentList.add(fragment_tag_environment);
            titleList.add("环境");
        }
        boolean show_aerospace = pref.getBoolean("aerospace", true);
        if (show_aerospace){
            mainActivityFragment fragment_tag_aerospace = new mainActivityFragment(this.getResources().getString(R.string.website_aerospace));
            fragmentList.add(fragment_tag_aerospace);
            titleList.add("航天");
        }
        boolean show_cs = pref.getBoolean("cs", true);
        if (show_cs){
            mainActivityFragment fragment_tag_cs = new mainActivityFragment(this.getResources().getString(R.string.website_cs));
            fragmentList.add(fragment_tag_cs);
            titleList.add("计算机科学");
        }
        boolean show_biology = pref.getBoolean("biology", true);
        if (show_biology){
            mainActivityFragment fragment_tag_biology = new mainActivityFragment(this.getResources().getString(R.string.website_biology));
            fragmentList.add(fragment_tag_biology);
            titleList.add("生物");
        }
        boolean show_physics = pref.getBoolean("physics", true);
        if (show_physics){
            mainActivityFragment fragment_tag_physics = new mainActivityFragment(this.getResources().getString(R.string.website_physics));
            fragmentList.add(fragment_tag_physics);
            titleList.add("物理");
        }
        boolean show_what_if = pref.getBoolean("what-if", true);
        if (show_what_if){
            mainActivityFragment fragment_tag_whatif = new mainActivityFragment(this.getResources().getString(R.string.website_what_if));
            fragmentList.add(fragment_tag_whatif);
            titleList.add("what-if");
        }
        boolean show_xkcd = pref.getBoolean("xkcd", true);
        if (show_xkcd){
            mainActivityFragment fragment_tag_xkcd = new mainActivityFragment(this.getResources().getString(R.string.website_xkcd));
            fragmentList.add(fragment_tag_xkcd);
            titleList.add("xkcd");
        }


        //将list泛型转换成数组
        String[] array = new String[titleList.size()];
        String[] title = titleList.toArray(array);

        //初始化ViewPager
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size()-1);//设置ViewPager的缓存页面个数，避免fragment切换时被销毁从而需要重新联网更新

        //初始化TabLayout
        SlidingTabLayout tabLayout = (SlidingTabLayout)findViewById(R.id.tabLayout);

        tabLayout.setViewPager(viewPager, title);
        //tabLayout.setTextSelectColor(Color.rgb(255,97,0));
        tabLayout.setTextUnselectColor(Color.DKGRAY);

        //背景颜色
        //tabLayout.setBackgroundColor(this.getResources().getColor(R.color.colorPrimary));
        //色块颜色
        tabLayout.setIndicatorColor(this.getResources().getColor(R.color.colorPrimary));
        //文字颜色
        tabLayout.setTextSelectColor(this.getResources().getColor(R.color.colorPrimaryDark));
        //tabLayout.setIndicatorStyle(SlidingTabLayout.BL);
        tabLayout.setTextsize(18);

    }

    //右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, settingActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent intent1 = new Intent(MainActivity.this, aboutActivity.class);
                startActivity(intent1);
                return true;
            default:
                return true;
        }
    }

    //退出程序
    private long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0){
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void exitApp(){
        if ((System.currentTimeMillis() - exitTime > 2000)){
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();
        }
    }
}
