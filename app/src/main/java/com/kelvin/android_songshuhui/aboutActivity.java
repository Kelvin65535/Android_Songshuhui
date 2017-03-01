package com.kelvin.android_songshuhui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class aboutActivity extends AppCompatActivity {

    String about = "<h1>\n" +
            "关于科学松鼠会\n" +
            "</h1>\n" +
            "<p class=\"abouttext\" style=\"color:#545454;font-size:14px;font-family:宋体;text-indent:22px;\">\n" +
            "科学松鼠会（Songshuhui-Association of Science Communicators）是一个致力于在大众文化层面传播科学的非营利机构&nbsp;，成立于2008年4月。松鼠会汇聚了当代最优秀的一批华语青年科学传播者，旨在“剥开科学的坚果，帮助人们领略科学之美妙”。《南方周末》评价说：“松鼠会的文字作品兼具科学精神和人文精神，已经成为本土科普作品的重要来源。”\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "<strong>愿景：让科学流行起来</strong> \n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "<strong>价值观：严谨有容，独立客观</strong> \n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "主页：<a href=\"http://songshuhui.net\" target=\"_blank\">http://songshuhui.net</a>\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "<br />\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "本应用程序为科学松鼠会第三方客户端\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "联系作者\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "E-Mail: kelvin65535@gmail.com\n" +
            "</p>\n" +
            "<p style=\"color:#3D3D3D;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:13px;\">\n" +
            "GitHub:&nbsp;<a href=\"https://github.com/kelvin65535\" target=\"_blank\">https://github.com/kelvin65535</a>\n" +
            "</p>\n" +
            "<p>\n" +
            "<br />\n" +
            "</p>";

    TextView textView_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textView_about = (TextView)findViewById(R.id.textview_about);
        textView_about.setText(Html.fromHtml(about));
    }
}
