package com.kelvin.android_songshuhui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kelvin.android_songshuhui.Interface.onListLoadingListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kelvi on 2016/11/3.
 */

public class mainActivityListView extends ListView {

    /*******************************************
     * 局部变量
     *******************************************
     */

    //保存上下文
    private Context context;

    ///表示菜单项的list
    private ArrayList<mainActivityListViewItem> titleList = new ArrayList<>();
    private mainActivityListViewAdapter adapter;

    //在更新list元素（初始化、下拉刷新）的时候所用的临时list，将更新资源放在该list，等加载完毕后再覆盖回titleList
    //防止在刷新UI前直接对titleList更新元素，导致调用元素位置时产生错误
    private ArrayList<mainActivityListViewItem> newList = new ArrayList<>();

    private onListLoadingListener listener;

    //存储当前页面的“首页”地址
    private String FirstPageURL;

    //以下为在“加载更多页面项”方法执行时使用到的变量
    //表示当前list加载的当前页下一个网页的page号码，默认为2（初始化时的页码为1，即下一页为2）
    private static int currentNextPageIndex;
    //判断当前是否处于“加载更多”的状态
    private boolean isLoadingMore = false;
    //判断当前是否处于"下拉刷新"状态
    private boolean isRefresh = false;

    //用于处理在联网更新内容时，getListElementsByURL()子线程发送的信息，若接收到OK信号，即刷新视图
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    //列表初始化完成
                    for (int i = 0; i < newList.size(); i++){
                        if (titleList.size() < i){
                            titleList.add(newList.get(i));
                        }else{
                            titleList.set(i, newList.get(i));
                        }
                    }
                    isRefresh = false;
                    //若联网更新List内的数据已经完成，提交更改，写入UI中
                    commitListItemChange();
                    break;
                case 2:
                    //列表下拉刷新完成
                    //titleList.clear();
                    //titleList.addAll(newList);
                    //
                    for (int i = 0; i < newList.size(); i++){
                        if (titleList.size() <= i){
                            titleList.add(newList.get(i));
                        }else{
                            titleList.set(i, newList.get(i));
                        }
                    }
                    isRefresh = false;
                    commitListItemChange();
                    listener.onLoadingFinished();
                    break;
                case 3:
                    //列表上拉加载完成
                    //设置更新状态为false
                    currentNextPageIndex++;
                    isLoadingMore = false;
                    commitListItemChange();
                    break;
                case 4:
                    //列表上拉加载错误
                    //设置更新状态为false
                    isLoadingMore = false;
                    commitListItemChange();
                    break;
            }
        }
    };



    /**
     * 构造方法
     * @param context 调用这个构造方法的上下文，该上下文即为包含这个ListView的Activity的上下文，可用于点击Item时Intent的上下文传参使用
     */
    public mainActivityListView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    /**
     * 构造方法
     * @param context 调用这个构造方法的上下文，该上下文即为包含这个ListView的Activity的上下文，可用于点击Item时Intent的上下文传参使用
     */
    public mainActivityListView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        init(context);
    }

    /**
     * 构造方法
     * @param context 调用这个构造方法的上下文，该上下文即为包含这个ListView的Activity的上下文，可用于点击Item时Intent的上下文传参使用
     */
    public mainActivityListView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    /*******************************************
     * 可供Activity调用的方法
     *******************************************
     */

    /**
     * 初始化当前的ListView，每个构造方法都会调用这个方法用于初始化
     * @param context 调用这个构造方法的上下文，该上下文即为包含这个ListView的Activity的上下文，可用于点击Item时Intent的上下文传参使用
     */
    public void init(final Context context){
        //设置当前ListView的适配器
        adapter = new mainActivityListViewAdapter(context, R.layout.main_listview_item, titleList, this);
        this.setAdapter(adapter);

        //初始化当前listview获得数据的主页的下一页page编号为2
        currentNextPageIndex = 2;

        //设置ListView内的Item的点击事件
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivityListViewItem item = (mainActivityListViewItem)mainActivityListView.this.getItemAtPosition(position);
                //调用ContentActivity，载入点击项的网址
                Intent intent = new Intent(context, ContentActivity.class); //该context即为构造函数时传入的context上下文
                intent.putExtra("title", item.getTitle());
                intent.putExtra("website", item.getWebsite());
                context.startActivity(intent); //跳转到显示内容的Activity
            }
        });

        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //判断List是否为停止状态、是否滑动到最后一个元素、是否正在加载数据
                if (scrollState == SCROLL_STATE_IDLE && (view.getLastVisiblePosition() == (view.getCount()-1))){
                    //判断当前是否加载数据或正在刷新列表
                    if (!isLoadingMore && !isRefresh){
                        //调用加载数据的方法
                        pullUpLoadingList();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 该方法为设置ListView内容的方法，通过传入的URL地址，动态的初始化ListView内显示的Item内容
     * 可在Activity内新建当前对象后调用该方法，用于初始化列表
     */
    public void getListElementsByURL(String URL, final String situation){

        //将当前URL设置为首页
        final String urlString = URL;
        new Thread(new Runnable() {
            @Override
            public void run() {
            try {
                    Document doc = Jsoup.connect(urlString).get();
                    Elements elements = doc.getElementsByAttributeValueContaining("class", "step bspacing10");

                    mainActivityListViewItem item;

                    for (Element e : elements){
                        if (e.select("div.fontHeader").isEmpty()){

                            //根据以下的html字符找出正文网址、图片网址、作者、发布日期
                            /**
                             * <div class="step bspacing10  随机字符">
                             <p class="pic117"><a href="正文网址"> <img src="图片网址" alt="" /> </a></p>
                             <div class="alignright2">
                             <h3 class="storytitle"><a class="black" href="正文网址" rel="bookmark">标题</a></h3>
                             <div class="metax">
                             <em>Filed under: <a href="标签网址" rel="category tag">标签</a></em>
                             <a href="作者网址" title="由作者发布" rel="author">作者</a> 发表于 yyyy-mm-dd hh:mm
                             </div>
                             <div class="storycontent">
                             <p class="colord"> </p>
                             <p>简介</p>
                             <p></p>
                             </div>
                             <div class="feedback">
                             <a href="评论网址" class="huang">10 条评论 &raquo;</a>
                             </div>
                             </div>
                             </div>
                             */

                            //图片网址
                            String imageSite = e.select("img").attr("src");

                            //标题
                            String title = e.select("div.alignright2").select("h3.storytitle").text();

                            //正文网址
                            String website = e.select("div.alignright2").select("a").attr("href");

                            //使用tags标签存储“作者   发布日期”格式的字符串
                            String tags = null;

                            //作者
                            Elements tag = e.select("div.alignright2").select("div.metax").select("a");
                            tags = tag.last().text();

                            //获取发布日期
                            String temp = e.select("div.alignright2").select("div.metax").text();
                            Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}");//使用正则表达式匹配yyyy-mm-dd hh:mm字符
                            Matcher m = p.matcher(temp);
                            m.find();
                            tags += "   ";
                            tags += m.group();


                            //获取图片的bitmap

                            Bitmap bitmap = null;
                            /*
                            java.net.URL url = new URL(imageSite);

                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            conn.setConnectTimeout(5000);
                            conn.setRequestMethod("GET");
                            if(conn.getResponseCode() == 200) {
                                InputStream inputStream = conn.getInputStream();
                                bitmap = BitmapFactory.decodeStream(inputStream);
                            }
                            */
                            item = new mainActivityListViewItem(context, title, imageSite, website, bitmap, tags);
                            newList.add(item);
                        }
                    }

                    //将得到的HTML作为message传递到主线程
                    Message message = new Message();
                    if (situation == "init"){
                        //调用该方法的情况为初始化列表
                        message.what = 1;
                        message.obj = "OK";
                        handler.sendMessage(message);
                    }
                    else if (situation == "refresh"){
                        handler.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                handler.sendEmptyMessage(2);
            }
            }
        }).start();
    }

    //将每个文章项存入item中

    /**
     * 该方法用于刷新adapter更改的内容到当前ListView的UI中
     * 由于在子线程内无法更新UI，因此将其独立为一个方法调用
     */
    private void commitListItemChange() {
        adapter.notifyDataSetChanged();
    }

    /**
     * 该方法用于在下拉刷新时调用，重新从首页获取目录信息并更新列表
     */
    public void refreshUpdateList(onListLoadingListener listener, String firstPageURL){
        newList.clear();//清空用于存放更新列表元素的list
        isRefresh = true;//设置正在更新列表的状态标记
        this.listener = listener;
        FirstPageURL = firstPageURL;//保存首页
        getListElementsByURL(firstPageURL, "refresh");

    }

    public void pullUpLoadingList(){

        //判断当前是否属于加载更多状态
        if (!isLoadingMore){
            //当前没有处于加载状态，可以执行加载动作
            isLoadingMore = true;

            //获取当前页码
            int currentPage = currentNextPageIndex;
            final String URL = FirstPageURL + "/page/" + currentPage;//构造该页的URL

            Toast.makeText(getContext(), "正在加载更多内容...", Toast.LENGTH_SHORT).show();


            //获取网络连接
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Document doc = Jsoup.connect(URL).get();
                        Elements elements = doc.getElementsByAttributeValueContaining("class", "step bspacing10");

                        ArrayList<mainActivityListViewItem> tempList = new ArrayList<>();

                        mainActivityListViewItem item;

                        for (Element e : elements) {
                            if (e.select("div.fontHeader").isEmpty()) {

                                //根据以下的html字符找出正文网址、图片网址、作者、发布日期
                                /**
                                 * <div class="step bspacing10  随机字符">
                                 <p class="pic117"><a href="正文网址"> <img src="图片网址" alt="" /> </a></p>
                                 <div class="alignright2">
                                 <h3 class="storytitle"><a class="black" href="正文网址" rel="bookmark">标题</a></h3>
                                 <div class="metax">
                                 <em>Filed under: <a href="标签网址" rel="category tag">标签</a></em>
                                 <a href="作者网址" title="由作者发布" rel="author">作者</a> 发表于 yyyy-mm-dd hh:mm
                                 </div>
                                 <div class="storycontent">
                                 <p class="colord"> </p>
                                 <p>简介</p>
                                 <p></p>
                                 </div>
                                 <div class="feedback">
                                 <a href="评论网址" class="huang">10 条评论 &raquo;</a>
                                 </div>
                                 </div>
                                 </div>
                                 */

                                //图片网址
                                String imageSite = e.select("img").attr("src");

                                //标题
                                String title = e.select("div.alignright2").select("h3.storytitle").text();

                                //正文网址
                                String website = e.select("div.alignright2").select("a").attr("href");

                                //使用tags标签存储“作者   发布日期”格式的字符串
                                String tags = null;

                                //作者
                                Elements tag = e.select("div.alignright2").select("div.metax").select("a");
                                tags = tag.last().text();

                                //获取发布日期
                                String temp = e.select("div.alignright2").select("div.metax").text();
                                Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}\\s[0-9]{2}:[0-9]{2}");//使用正则表达式匹配yyyy-mm-dd hh:mm字符
                                Matcher m = p.matcher(temp);
                                m.find();
                                tags += "   ";
                                tags += m.group();


                                //获取图片的bitmap
                                Bitmap bitmap = null;
                                /*
                                java.net.URL url = new URL(imageSite);

                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setConnectTimeout(5000);
                                conn.setRequestMethod("GET");
                                if (conn.getResponseCode() == 200) {
                                    InputStream inputStream = conn.getInputStream();
                                    bitmap = BitmapFactory.decodeStream(inputStream);
                                }
                                */
                                item = new mainActivityListViewItem(context, title, imageSite, website, bitmap, tags);
                                tempList.add(item);
                            }
                        }

                        //加载完毕后添加到主List中
                        for (int i = 0; i < tempList.size(); i++) {
                            titleList.add(tempList.get(i));
                        }




                        //刷新视图
                        handler.sendEmptyMessage(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(4);
                    }

                }
            }).start();
        }
    }

}
