package com.kelvin.android_songshuhui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ContentActivity extends AppCompatActivity {

    WebView webView;
    String response;
    ProgressBar progressBar;
    ScrollView scrollView;

    String url;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    response = (String) msg.obj;

                    //隐藏载入提示
                    webView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    webView.loadData(response, "text/html;charset=UTF-8", null);//以UTF-8加载HTML代码的内容，防止出现乱码

                    //以下为对webView的设置
                    //开启javascript
                    webView.getSettings().setJavaScriptEnabled(true);

                    //设置滚动条
                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.setHorizontalScrollBarEnabled(false);

                    //<IMPORTENT!>
                    //保证webview里图片不会超出当前显示的设置，我也不知道原理，暂时不要更改！
                    webView.getSettings().setTextZoom(180);
                    webView.setInitialScale(getScale());
            }

        }
    };

    //<IMPORTENT!>
    //保证webview里图片不会超出当前显示的设置，我也不知道原理，暂时不要更改！
    private int getScale(){
        Display display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(640);
        val = val * 100d;

        Log.d("getscale", "getScale: width" + width + "val: " + val);
        
        return val.intValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //根据传入的intent获取URL和标题
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String website = bundle.getString("website");
        String title = bundle.getString("title");
        setTitle(title);//根据传入的文章标题信息，设置当前页的标题

        //初始化组件
        setContentView(R.layout.activity_content);
        webView = (WebView)findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //保存获得的URL
        url = website;



        //初始化完毕，开始显示网页

        //传入URL，从服务器获取HTML
        getContentHtmlData(url);

    }

    /**
     * 根据传入的URL，从服务器获取整个网页的HTML数据，并存放在字符串中，通过Message传递到主线程
     * @param urlString 要显示的正文的URL
     */
    public void getContentHtmlData(String urlString){
        final String urlFinalString = urlString;

        //显示载入提示（转圈动画）
        webView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        //新建线程，用于启动网络连接
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document doc = Jsoup.connect(urlFinalString).get();
                    Elements entry = doc.select("div.entry");
                    Elements elements = entry.select("p");
                    //初始化文章首部的原文链接
                    String content = "<p>文章来源于科学松鼠会</p><p>原文：<a href=\""+ url + "\" target=\"_blank\">" + url +"</a></p><hr align=\"center\" />";
                    for(Element e : elements){
                        if (!(e.select("img").isEmpty())) {
                            e.attr("style", "text-align:center");   //修改图片属性为居中显示
                        }

                        content += e.toString();
                    }


                    String head = "<!DOCTYPE html><html><head><style type=\"text/css\">a{word-wrap:break-word} p{line-height:150%}</style></head><body>";
                    String tail = "</body></html>";
                    String response = head+content+tail;

                    Message message = new Message();
                    message.what = 1;
                    message.obj = response;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
