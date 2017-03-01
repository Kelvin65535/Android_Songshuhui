package com.kelvin.android_songshuhui;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class settingActivity extends AppCompatActivity {

    //原创
    private CheckBox checkBox_yuanchuang;
    boolean settings_yuanchuang;

    //译文
    private CheckBox checkBox_yiwen;
    boolean settings_yiwen;

    //健康
    private CheckBox checkBox_health;
    boolean settings_health;

    //数学
    private CheckBox checkBox_math;
    boolean settings_math;

    //化学
    private CheckBox checkBox_ch;
    boolean settings_ch;

    //医学
    private CheckBox checkBox_medi;
    boolean settings_medi;

    //航天
    private CheckBox checkBox_aero;
    boolean settings_aero;

    //天文
    private CheckBox checkBox_astro;
    boolean settings_astro;

    //物理
    private CheckBox checkBox_phy;
    boolean settings_phy;

    //心理
    private CheckBox checkBox_psy;
    boolean settings_psy;

    //计算机科学
    private CheckBox checkBox_cs;
    boolean settings_cs;

    //环境
    private CheckBox checkBox_envir;
    boolean settings_envir;

    //生物
    private CheckBox checkBox_bio;
    boolean settings_bio;

    //what-if
    private CheckBox checkBox_what_if;
    boolean settings_what_if;

    //xkcd
    private CheckBox checkBox_xkcd;
    boolean settings_xkcd;

    SharedPreferences pref;
    SharedPreferences.Editor prefEditer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //checkBox
        checkBox_yiwen = (CheckBox)findViewById(R.id.checkBox_yiwen);
        checkBox_what_if = (CheckBox)findViewById(R.id.checkBox_what_if);
        checkBox_xkcd = (CheckBox)findViewById(R.id.checkBox_xkcd);
        checkBox_health = (CheckBox)findViewById(R.id.health);
        checkBox_math = (CheckBox)findViewById(R.id.checkBox_math);
        checkBox_ch = (CheckBox)findViewById(R.id.checkBox_ch);
        checkBox_medi = (CheckBox)findViewById(R.id.checkBox_medi);
        checkBox_aero = (CheckBox)findViewById(R.id.checkBox_aerospace);
        checkBox_astro = (CheckBox)findViewById(R.id.checkBox_astro);
        checkBox_phy = (CheckBox)findViewById(R.id.checkBox_physics);
        checkBox_psy = (CheckBox)findViewById(R.id.checkBox_psychology);
        checkBox_cs = (CheckBox)findViewById(R.id.checkBox_cs);
        checkBox_envir = (CheckBox)findViewById(R.id.checkBox_environment);
        checkBox_bio = (CheckBox)findViewById(R.id.checkBox_biology);

        //载入设置
        pref = getSharedPreferences("settings", MODE_PRIVATE);
        prefEditer = pref.edit();

        //原创
        /*
        settings_yuanchuang = pref.getBoolean("yuanchuang", true);
        checkBox_yuanchuang.setChecked(settings_yuanchuang);
        checkBox_yuanchuang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("what-if", isChecked);
                prefEditer.commit();
            }
        });
        */

        //译文
        settings_yiwen = pref.getBoolean("yiwen", true);
        checkBox_yiwen.setChecked(settings_yiwen);
        checkBox_yiwen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("yiwen", isChecked);
                prefEditer.commit();
            }
        });

        //健康
        settings_health = pref.getBoolean("health", true);
        checkBox_health.setChecked(settings_health);
        checkBox_health.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("health", isChecked);
                prefEditer.commit();
            }
        });

        //化学
        settings_ch = pref.getBoolean("chemistry", true);
        checkBox_ch.setChecked(settings_ch);
        checkBox_ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("chemistry", isChecked);
                prefEditer.commit();
            }
        });

        //医学
        settings_medi = pref.getBoolean("medical", true);
        checkBox_medi.setChecked(settings_medi);
        checkBox_medi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("medical", isChecked);
                prefEditer.commit();
            }
        });

        //天文
        settings_astro = pref.getBoolean("astro", true);
        checkBox_astro.setChecked(settings_astro);
        checkBox_astro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("astro", isChecked);
                prefEditer.commit();
            }
        });

        //心理
        settings_psy = pref.getBoolean("psychology", true);
        checkBox_psy.setChecked(settings_psy);
        checkBox_psy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("psychology", isChecked);
                prefEditer.commit();
            }
        });

        //数学
        settings_math = pref.getBoolean("math", true);
        checkBox_math.setChecked(settings_math);
        checkBox_math.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("math", isChecked);
                prefEditer.commit();
            }
        });

        //环境
        settings_envir = pref.getBoolean("environment", true);
        checkBox_envir.setChecked(settings_envir);
        checkBox_envir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("environment", isChecked);
                prefEditer.commit();
            }
        });

        //航天
        settings_aero = pref.getBoolean("aerospace", true);
        checkBox_aero.setChecked(settings_aero);
        checkBox_aero.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("aerospace", isChecked);
                prefEditer.commit();
            }
        });

        //计算机科学
        settings_cs = pref.getBoolean("cs", true);
        checkBox_cs.setChecked(settings_cs);
        checkBox_cs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("cs", isChecked);
                prefEditer.commit();
            }
        });

        //生物
        settings_bio = pref.getBoolean("biology", true);
        checkBox_bio.setChecked(settings_bio);
        checkBox_bio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("biology", isChecked);
                prefEditer.commit();
            }
        });

        //物理
        settings_phy = pref.getBoolean("physics", true);
        checkBox_phy.setChecked(settings_phy);
        checkBox_phy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("physics", isChecked);
                prefEditer.commit();
            }
        });

        //what-if
        settings_what_if = pref.getBoolean("what-if", true);
        checkBox_what_if.setChecked(settings_what_if);
        checkBox_what_if.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("what-if", isChecked);
                prefEditer.commit();
            }
        });

        //xkcd
        settings_xkcd = pref.getBoolean("xkcd", true);
        checkBox_xkcd.setChecked(settings_xkcd);
        checkBox_xkcd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefEditer.putBoolean("xkcd", isChecked);
                prefEditer.commit();
            }
        });
    }


}
