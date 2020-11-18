package com.gameley.youzi;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements TTAdNative.RewardVideoAdListener {

    public static boolean APP_has_AD = true;


    public static String build_qudaohao = "appstore";//appstore";//"mtg";//""pangle";
    public static String build_rewardVideoId = "945557842";//"945557842";//"945557837";//"945550005";
    public static String build_bannerId = "945557843";//"945557843";//"945557840";//"945553937";
    public static String build_openVideoId = "887393540";//"887393540";//"887393539";//"887392876";


    private static final String TODO = "";
    private RadioGroup mRgGroup;
    private FragmentManager fragmentManager;

    private static final String[] FRAGMENT_TAG = {"tab_map", "tab_car", "tab_find", "tab_me"};

    private final int show_tab_car = 1;//找车
    private final int show_tab_map = 0;//地图
    private final int show_tab_find = 2;//发现
    private final int show_tab_me = 3;//我的
    private int mrIndex = show_tab_map;//默认选中地图

    private int index = -100;// 记录当前的选项
    /**
     * 上一次界面 onSaveInstanceState 之前的tab被选中的状态 key 和 value
     */
    private static final String PRV_SELINDEX = "PREV_SELINDEX";
    private TabCarFragment tabCarFragment;
    private TabMapFragment tabMapFragment;
    private TabFindFragment tabFindFragment;
    private TabMeFragment tabMeFragment;


    List<ModuleAddressBean> recentplay_beanlists;//最近在玩
    List<ModuleAddressBean> todaypush_beanlists;//今日推荐
    List<ModuleAddressBean> newgame_beanlists;//新游推荐
    List<ModuleAddressBean> fantasygame_beanlists;//创意游戏
    List<ModuleAddressBean> fantasynewgame_beanlists;//精品新游
    List<ModuleAddressBean> ranklistgame_beanlists;//排行榜

    static X5WebView myX5WebView;
    Context context;


    String save_index = "0";//寻找的下标

    String save_index_name1 = "羊羊保卫战";
    String save_index_name2 = "羊羊保卫战";
    String save_index_name3 = "羊羊保卫战";
    String save_index_name4 = "羊羊保卫战";
    String save_index_name5 = "羊羊保卫战";

    String save_index_icon1 = "icon000";
    String save_index_icon2 = "icon000";
    String save_index_icon3 = "icon000";
    String save_index_icon4 = "icon000";
    String save_index_icon5 = "icon000";

    String save_index_url1 = "icon000";
    String save_index_url2 = "icon000";
    String save_index_url3 = "icon000";
    String save_index_url4 = "icon000";
    String save_index_url5 = "icon000";

    String[] save_index_icons = new String[5];
    String[] save_index_names = new String[5];
    String[] save_index_urls = new String[5];


    public void openWeb(View view) {


        //Toast.makeText(act_sta, upoadMsg_androidid, Toast.LENGTH_LONG).show();

        String tag = view.getTag().toString();
        String url = "";
        String name = "";
        String icon = "";
        if (tag.startsWith("gameicon")) {
            int index1 = Integer.parseInt(tag.substring(8, 9));
            int index2 = Integer.parseInt(tag.substring(10, 11));
            if (index1 == 1) {
                url = todaypush_beanlists.get(index2).tiaozhuancanshu;
                name = todaypush_beanlists.get(index2).tuijianmingcheng;
                icon = todaypush_beanlists.get(index2).tuijiansucai;
            } else if (index1 == 2) {
                url = newgame_beanlists.get(index2).tiaozhuancanshu;
                name = newgame_beanlists.get(index2).tuijianmingcheng;
                icon = newgame_beanlists.get(index2).tuijiansucai;
            } else if (index1 == 3) {
                url = fantasygame_beanlists.get(index2).tiaozhuancanshu;
                name = fantasygame_beanlists.get(index2).tuijianmingcheng;
                icon = fantasygame_beanlists.get(index2).tuijiansucai;
            }
        } else if (tag.startsWith("rankicon")) {
            int index2 = Integer.parseInt(tag.substring(10, 11));
            url = ranklistgame_beanlists.get(index2).tiaozhuancanshu;
            name = ranklistgame_beanlists.get(index2).tuijianmingcheng;
            icon = ranklistgame_beanlists.get(index2).tuijiansucai;
        } else if (tag.startsWith("recentblock")) {
            int index3 = Integer.parseInt(tag.substring(11, 12));
            url = save_index_urls[index3 - 1];
            name = save_index_names[index3 - 1];
            icon = save_index_icons[index3 - 1];
        }
        RelativeLayout myX5WebViewlayout = findViewById(R.id.myX5WebViewlayout);
        myX5WebView = findViewById(R.id.myX5WebView);
        myX5WebViewlayout.setVisibility(View.VISIBLE);
        myX5WebView.reload();
        //myX5WebView.loadUrl("file:///android_asset/30/index.html");
        myX5WebView.loadUrl("https://cdnminigame.lightlygame.com/gc/dressing10/index.html");//(url);//"https://cdnminigame.lightlygame.com/gc/30/");


        //showAllScreenAD(act_sta,mTTAdNative);

        if (APP_has_AD) {
            mSplashContainer.setVisibility(View.VISIBLE);
            showKaiPingAD(act_sta, mTTAdNative);
        }

        saveRecentPlay(name, icon, url);

        openingGamename = name;
        openGameTime = getSecondTimestamp();

        postData(name, build_qudaohao, "-1", "in", upoadMsg_fl, 0, "", "", "");
    }

    void saveRecentPlay(String name, String icon, String url) {
        int index = Integer.parseInt(save_index);
        index++;


        SharedPreferences settings = getSharedPreferences("recentplay", 0);
        SharedPreferences.Editor editor = settings.edit();

        boolean hasEqual = false;

        if (index > 1) {
            if (index <= 5) {
                for (int i = 1; i < index; i++) {

                    String save_index_nametemp = settings.getString("save_index_name" + (i), "");
                    String save_index_icontemp = settings.getString("save_index_icon" + (i), "");
                    String save_index_urltemp = settings.getString("save_index_url" + (i), "");

                    if (save_index_nametemp.equals(name)
                            && save_index_icontemp.equals(icon)
                            && save_index_urltemp.equals(url)
                    ) {

                        //if( i != 1 ) {
                        String temp_name = settings.getString("save_index_name" + (index - 1), "");
                        String temp_url = settings.getString("save_index_icon" + (index - 1), "");
                        String temp_icon = settings.getString("save_index_url" + (index - 1), "");

                        editor.putString("save_index_name" + (index - 1), save_index_nametemp);
                        editor.putString("save_index_icon" + (index - 1), save_index_icontemp);
                        editor.putString("save_index_url" + (index - 1), save_index_urltemp);

                        editor.putString("save_index_name" + (i), temp_name);
                        editor.putString("save_index_icon" + (i), temp_url);
                        editor.putString("save_index_url" + (i), temp_icon);

                        // 提交本次编辑
                        editor.commit();
                        getSavrData();
                        resetRecentPlay();
                        //}

                        hasEqual = true;
                        return;

                    }
                }
            } else {
                for (int i = index - 5; i < index; i++) {

                    String save_index_nametemp = settings.getString("save_index_name" + (i), "");
                    String save_index_icontemp = settings.getString("save_index_icon" + (i), "");
                    String save_index_urltemp = settings.getString("save_index_url" + (i), "");

                    if (save_index_nametemp.equals(name)
                            && save_index_icontemp.equals(icon)
                            && save_index_urltemp.equals(url)
                    ) {

                        //if( i != 1 ){

                        String temp_name = settings.getString("save_index_name" + (index - 1), "");
                        String temp_url = settings.getString("save_index_icon" + (index - 1), "");
                        String temp_icon = settings.getString("save_index_url" + (index - 1), "");

                        editor.putString("save_index_name" + (index - 1), save_index_nametemp);
                        editor.putString("save_index_icon" + (index - 1), save_index_icontemp);
                        editor.putString("save_index_url" + (index - 1), save_index_urltemp);

                        editor.putString("save_index_name" + (i), temp_name);
                        editor.putString("save_index_icon" + (i), temp_url);
                        editor.putString("save_index_url" + (i), temp_icon);

                        // 提交本次编辑
                        editor.commit();
                        getSavrData();
                        resetRecentPlay();
                        //}

                        hasEqual = true;
                        return;
                    }
                }
            }
        }


        if (hasEqual) {
            return;
        }

        editor.putString("save_index_name" + index, name);
        editor.putString("save_index_icon" + index, icon);
        editor.putString("save_index_url" + index, url);

        save_index = "" + index;
        editor.putString("save_index", save_index);

        // 提交本次编辑
        editor.commit();

        getSavrData();
        resetRecentPlay();
    }

    void resetRecentPlay() {
        int index = Integer.parseInt(save_index);
        if (index == 0) {
            RelativeLayout imageTopview = (RelativeLayout) act_sta.findViewById(R.id.zuijinzaiwantitle);
            imageTopview.setVisibility(View.GONE);

            HorizontalScrollView imageTopview2 = (HorizontalScrollView) act_sta.findViewById(R.id.zuijinzaiwan);
            imageTopview2.setVisibility(View.GONE);

            return;
        }

        RelativeLayout imageTopview = (RelativeLayout) act_sta.findViewById(R.id.zuijinzaiwantitle);
        imageTopview.setVisibility(View.VISIBLE);

        HorizontalScrollView imageTopview2 = (HorizontalScrollView) act_sta.findViewById(R.id.zuijinzaiwan);
        imageTopview2.setVisibility(View.VISIBLE);

        int from_index = 1;
        if (index > 5) {
            from_index = index - 4;
        } else if (index <= 5) {
            for (int i = 1; i <= 5 - index; i++) {
                String icon_name = "recentblock" + (6 - i);
                int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
                RelativeLayout imageTopview3 = (RelativeLayout) act_sta.findViewById(image222);//R.id.tuijian_icon001);
                imageTopview3.setVisibility(View.GONE);
            }
            for (int j = 5 - index + 1; j <= 5; j++) {
                String icon_name = "recentblock" + (6 - j);
                int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
                RelativeLayout imageTopview3 = (RelativeLayout) act_sta.findViewById(image222);//R.id.tuijian_icon001);
                imageTopview3.setVisibility(View.VISIBLE);
            }
        }

        int temp = 1;
        for (int i = index; i >= from_index; i--) {
            String icon_name = "recent_icon" + temp;
            int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
            ImageView imageTopview3 = (ImageView) act_sta.findViewById(image222);//R.id.tuijian_icon001);

            int image333 = this.getResources().getIdentifier(save_index_icons[temp - 1], "drawable", getPackageName());
            Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image333);//R.drawable.icon001);
            imageTopview3.setImageBitmap(gameStatusBitmap);

            String recent_name = "recent_name" + temp;
            int image222t = this.getResources().getIdentifier(recent_name, "id", getPackageName());
            TextView textview = (TextView) act_sta.findViewById(image222t);//R.id.tuijian_icon001);
            textview.setText(save_index_names[temp - 1]);

            temp++;
        }

    }

    /***
     * 计算两个时间差，返回的是的秒s
     *
     *
     *
     * @return long
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String dete1, String date2) {


        long diff = 0;


        Date d1 = null;
        Date d2 = null;


        try {


            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            d1 = df.parse(dete1);
            d2 = df.parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return diff / 1000;
    }

    static String openingGamename = "";
    String openGameTime = "";
    String openAppTime = "";

    public void closeWeb(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act_sta);
        builder.setTitle("提示");
        builder.setMessage("是否关闭当前游戏");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                RelativeLayout myX5WebViewlayout = findViewById(R.id.myX5WebViewlayout);
                myX5WebViewlayout.setVisibility(View.GONE);
                //myX5WebView.reload ();
                myX5WebView.loadUrl("about:blank");

                String cur_time = getSecondTimestamp();

                long time = calDateDifferent(openGameTime, cur_time);

                postData(openingGamename, build_qudaohao, "-1", "out", upoadMsg_fl, time, "", "", "");

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();


    }

    public void playAD1(View view) {

        //showAllScreenAD(this,mTTAdNative);
        //showChaPingAD(this,mTTAdNative);
        myX5WebView.loadUrl("javascript:qg.callJS()");

    }

    public static void playAD2(View view) {

        //showAllScreenAD(this,mTTAdNative);
        //showAllScreenAD(act_sta, mTTAdNative);
        if (APP_has_AD) {
            showRewardAD(act_sta, mTTAdNative);
        }

    }

    public static void show_banner_ad() {
        //banner广告是否显示
        act_sta.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //showAllScreenAD(act_sta, mTTAdNative);
                if (APP_has_AD) {
                    mExpressContainer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void hide_banner_ad() {
        //banner广告是否显示
        act_sta.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //showAllScreenAD(act_sta, mTTAdNative);
                if (APP_has_AD) {
                    mExpressContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    void getSavrData() {

        SharedPreferences settings = getSharedPreferences("recentplay", 0);
        save_index = settings.getString("save_index", "0");

        int index__ = Integer.parseInt(save_index);

        save_index_name1 = settings.getString("save_index_name" + (index__ - 0), "");
        save_index_name2 = settings.getString("save_index_name" + (index__ - 1), "");
        save_index_name3 = settings.getString("save_index_name" + (index__ - 2), "");
        save_index_name4 = settings.getString("save_index_name" + (index__ - 3), "");
        save_index_name5 = settings.getString("save_index_name" + (index__ - 4), "");

        save_index_icon1 = settings.getString("save_index_icon" + (index__ - 0), "");
        save_index_icon2 = settings.getString("save_index_icon" + (index__ - 1), "");
        save_index_icon3 = settings.getString("save_index_icon" + (index__ - 2), "");
        save_index_icon4 = settings.getString("save_index_icon" + (index__ - 3), "");
        save_index_icon5 = settings.getString("save_index_icon" + (index__ - 4), "");

        save_index_url1 = settings.getString("save_index_url" + (index__ - 0), "");
        save_index_url2 = settings.getString("save_index_url" + (index__ - 1), "");
        save_index_url3 = settings.getString("save_index_url" + (index__ - 2), "");
        save_index_url4 = settings.getString("save_index_url" + (index__ - 3), "");
        save_index_url5 = settings.getString("save_index_url" + (index__ - 4), "");

        save_index_icons[0] = save_index_icon1;
        save_index_icons[1] = save_index_icon2;
        save_index_icons[2] = save_index_icon3;
        save_index_icons[3] = save_index_icon4;
        save_index_icons[4] = save_index_icon5;

        save_index_names[0] = save_index_name1;
        save_index_names[1] = save_index_name2;
        save_index_names[2] = save_index_name3;
        save_index_names[3] = save_index_name4;
        save_index_names[4] = save_index_name5;

        save_index_urls[0] = save_index_url1;
        save_index_urls[1] = save_index_url2;
        save_index_urls[2] = save_index_url3;
        save_index_urls[3] = save_index_url4;
        save_index_urls[4] = save_index_url5;


    }

    public static Activity act_sta;
    public static FrameLayout mSplashContainer;

    public static void playad1() {
        //showAllScreenAD(act_sta,mTTAdNative);

        act_sta.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //showAllScreenAD(act_sta, mTTAdNative);
                if (APP_has_AD) {
                    showRewardAD(act_sta, mTTAdNative);
                }
            }
        });
    }

    public static void playad2() {
        //showAllScreenAD(act_sta, mTTAdNative);
        if (APP_has_AD) {
            showRewardAD(act_sta, mTTAdNative);
        }
    }


    public void onClick(View v) {

        System.out.println("整个布局被点击");

    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
            int dsad = 1;
            int dsad2 = 1;
            int dsad3 = 1;
        }
        return deviceInfo;
    }

    final int REQUEST_READ_PHONE_STATE = 100;
    final int REQUEST_READ_FINE_LOCATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //String[] device_infos =  getTestDeviceInfo(this);
        //TextView text11 = (TextView) findViewById(R.id.deviceId);
        //String sadasd = device_infos[0];
        //text11.setText(sadasd);

        act_sta = this;
        //把屏幕设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        openAppTime = getSecondTimestamp();


        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }


        //解决底部选项按钮被输入文字框顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //显示界面
        setContentView(R.layout.activity_main);

        mExpressContainer = (FrameLayout) findViewById(R.id.express_container);
        mSplashContainer = (FrameLayout) findViewById(R.id.splash_container);

        if (APP_has_AD == false) {
            mSplashContainer.setVisibility(View.GONE);
        }

        mExpressContainer.setVisibility(View.GONE);

        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap map11 = new HashMap();
        map11.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map11.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map11);

        myX5WebView = findViewById(R.id.myX5WebView);
        myX5WebView.clearCache(true);
        myX5WebView.clearHistory();
        myX5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                Log.d("swallow", "shouldOverrideUrlLoading------>" + url);
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    webView.loadUrl(url);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                Log.d("swallow", "onPageStarted------->" + s);
                super.onPageStarted(webView, s, bitmap);
            }


            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.d("swallow", "onPageFinished------->" + s);
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                sslErrorHandler.proceed();
            }
        });

        WebSettings webSettings = myX5WebView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);

        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        myX5WebView.addJavascriptInterface(new AndroidtoJs(), "admanager");//AndroidtoJS类对象映射到js的test对象


        //强烈建议在应用对应的Application#onCreate()方法中调用，避免出现content为null的异常
        TTAdSdk.init(this,
                new TTAdConfig.Builder()
                        .appId("5112505")
                        .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                        .appName("柚子小游戏")
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                        .debug(false) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                        .supportMultiProcess(false) //是否支持多进程，true支持
                        //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                        .build());


        fragmentManager = getSupportFragmentManager();

        //防止app闪退后fragment重叠
        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            mrIndex = savedInstanceState.getInt(PRV_SELINDEX, mrIndex);
        }


        getSavrData();
        resetRecentPlay();

        String sdasd = getResources().getAssets().toString();
        List<ModuleAddressBean> beanlists = new ArrayList<ModuleAddressBean>();//临时列表
        beanlists = readCSV("/assets/0918csv.CSV", this);

        recentplay_beanlists = new ArrayList<ModuleAddressBean>();//最近在玩
        todaypush_beanlists = new ArrayList<ModuleAddressBean>();//今日推荐
        newgame_beanlists = new ArrayList<ModuleAddressBean>();//新游推荐
        fantasygame_beanlists = new ArrayList<ModuleAddressBean>();//创意游戏
        fantasynewgame_beanlists = new ArrayList<ModuleAddressBean>();//精品新游
        ranklistgame_beanlists = new ArrayList<ModuleAddressBean>();//排行榜

        int index = 0;
        List<ModuleAddressBean> temp_list = new ArrayList<ModuleAddressBean>();//临时列表
        for (int i = 0; i < beanlists.size(); i++) {
            ModuleAddressBean a = beanlists.get(i);
            String tuijianxiang = a.tuijianxiang;
            if (tuijianxiang.length() > 3) {
                if (index == 0) {
                    temp_list = todaypush_beanlists;
                } else if (index == 1) {
                    temp_list = newgame_beanlists;
                } else if (index == 2) {
                    temp_list = fantasygame_beanlists;
                } else if (index == 3) {
                    temp_list = fantasynewgame_beanlists;
                } else if (index == 4) {
                    temp_list = ranklistgame_beanlists;
                }
                index += 2;
                temp_list.add(a);
            } else {
                temp_list.add(a);
            }
        }

        generateTuijian(this);
        //generateXinyouTuijian(this);
        generateJingpinXinyou(this);
        generatePaiHangBang(this);

        //一定要在初始化后才能调用，否则为空
        ttAdManager = TTAdSdk.getAdManager();
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(this);//baseContext建议为activity


        getPhoneData();

        if (APP_has_AD) {
            shoBannerAD(act_sta, mTTAdNative);
        }


        //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
        //建议在宿主App的Application.onCreate函数中调用基础组件库初始化函数。
        //UMConfigure.init(this, "5f686803b473963242a330bf", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
        //建议在宿主App的Application.onCreate函数中调用此函数。
        //MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        //UMConfigure.setLogEnabled(true);



    }


    String oaid = "";

    /**
     * 根据IP地址获取MAC地址
     * @return
     */
    private static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }
                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                    int dasdas = 0;
                    int dsad = dasdas + 1;
                }
                //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
                //建议在宿主App的Application.onCreate函数中调用基础组件库初始化函数。
                UMConfigure.init(this, "5f686803b473963242a330bf", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

                //选择AUTO页面采集模式，统计SDK基础指标无需手动埋点可自动采集。
                //建议在宿主App的Application.onCreate函数中调用此函数。
                MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

                /**
                 * 设置组件化的Log开关
                 * 参数: boolean 默认为false，如需查看LOG设置为true
                 */
                UMConfigure.setLogEnabled(true);


                /*String[] device_infos =  getTestDeviceInfo(this);*/
                //TextView text11 = (TextView) findViewById(R.id.deviceId);
                //text11.setText(sadasd);


                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (ok) {//开启了定位服务
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            //没有权限，现在申请权限
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_READ_FINE_LOCATION);
                        }
                    }
                }

                break;

            default:
                break;
        }
    }

    // Activity页面onResume函数重载
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 不能遗漏
    }

    // Activity页面onResume函数重载
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 不能遗漏
    }


    //设备信息
    static String upoadMsg_did = "";
    static String upoadMsg_uid = "";
    static String upoadMsg_dt = "";
    static String upoadMsg_gid = "";
    static String upoadMsg_cid = "";
    static String upoadMsg_scid = "";
    static int upoadMsg_dw = 1;
    static int upoadMsg_dh = 1;
    static String upoadMsg_pt = "";
    static String upoadMsg_st = "android";
    static String upoadMsg_sv = "";
    static String upoadMsg_nt = "";
    static String upoadMsg_mac = "";
    static String upoadMsg_ev = "";
    static String upoadMsg_fl = "";
    static String upoadMsg_ip = "";
    static double upoadMsg_lon = 1.0f;
    static double upoadMsg_lat = 1.0f;
    static String upoadMsg_pv = "";
    static String upoadMsg_ct = "";
    static String upoadMsg_v = "";
    static long upoadMsg_dr = 1;
    static String upoadMsg_adt = "";
    static String upoadMsg_ada = "";
    static String upoadMsg_adid = "";

    static String upoadMsg_imei = "";
    static String upoadMsg_androidid = "";

    /**
     * 获取设备唯一ID
     * @return
     */
    public static String getDeviceId__() {
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);
        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            serial = "serial";
        }
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


    private String getUid() {
        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo("com.gameley.youzi", 0);
            return "" + ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static String getSecondTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date();
        String str_time = sdf.format(dt);
        return str_time;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 定位：得到位置对象
     * @return
     */
    private Location getLastKnownLocation() {
        //获取地理位置管理器
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * 定位：获取经纬度
     */
    private void getLocationLL() {
        Location location = getLastKnownLocation();
        if (location != null) {
            //传递经纬度给网页
            String result = "{code: '0',type:'2',data: {longitude: '" + location.getLongitude() + "',latitude: '" + location.getLatitude() + "'}}";

            upoadMsg_lon = location.getLongitude();
            upoadMsg_lat = location.getLatitude();
            //日志
            String locationStr = "维度：" + location.getLatitude() + "\n"
                    + "经度：" + location.getLongitude();
        } else {
            // Toast.makeText(this, "位置信息获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    public String getAddress(double latitude, double longitude) {
        String cityName = "";
        List<Address> addList = null;
        Geocoder ge = new Geocoder(act_sta);
        try {
            addList = ge.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addList != null && addList.size() > 0) {
            for (int i = 0; i < addList.size(); i++) {
                Address ad = addList.get(i);
                cityName += ad.getCountryName() + ";" + ad.getLocality() + ad.getAdminArea();
                upoadMsg_ct = ad.getLocality();
                upoadMsg_pv = ad.getAdminArea();
            }
        }
        return cityName;
    }


    void getPhoneData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                /*取得第一次登陆时间*/
                SharedPreferences settings = getSharedPreferences("gamelog", 0);
                String firstLoginTime = settings.getString("firstLoginTime", "");
                if (firstLoginTime.equals("")) {
                    SharedPreferences.Editor editor = settings.edit();
                    String currentTime = getSecondTimestamp();
                    upoadMsg_fl = currentTime;
                    editor.putString("firstLoginTime", currentTime);

                        /*editor.putString("upoadMsg_did", upoadMsg_did);
                        editor.putString("upoadMsg_uid", upoadMsg_uid);
                        editor.putString("upoadMsg_dt", upoadMsg_dt);
                        editor.putString("upoadMsg_dw", ""+upoadMsg_dw);
                        editor.putString("upoadMsg_dh", ""+upoadMsg_dh);
                        editor.putString("upoadMsg_pt", upoadMsg_pt);
                        editor.putString("upoadMsg_sv", upoadMsg_sv);
                        editor.putString("upoadMsg_nt", upoadMsg_nt);
                        editor.putString("upoadMsg_ip", upoadMsg_ip);
                        editor.putString("upoadMsg_lon", ""+upoadMsg_lon);
                        editor.putString("upoadMsg_lat", ""+upoadMsg_lat);
                        editor.putString("upoadMsg_pv", upoadMsg_pv);
                        editor.putString("upoadMsg_ct", upoadMsg_ct);
                        editor.putString("upoadMsg_v", upoadMsg_v);*/

                    // 提交本次编辑
                    editor.commit();
                } else {

                        /*try {
                            upoadMsg_did = settings.getString("upoadMsg_did", "");
                            upoadMsg_uid = settings.getString("upoadMsg_uid", "");
                            upoadMsg_dw = Integer.parseInt(settings.getString("upoadMsg_dw", ""));
                            upoadMsg_dh = Integer.parseInt(settings.getString("upoadMsg_dh", ""));
                            upoadMsg_pt = settings.getString("upoadMsg_pt", "");
                            upoadMsg_sv = settings.getString("upoadMsg_sv", "");
                            upoadMsg_nt = settings.getString("upoadMsg_nt", "");
                            upoadMsg_ip = settings.getString("upoadMsg_ip", "");
                            getLocationLL();
                            upoadMsg_pv = settings.getString("upoadMsg_pv", "");
                            upoadMsg_ct = settings.getString("upoadMsg_ct", "");
                            upoadMsg_v = settings.getString("upoadMsg_v", "");
                        }catch (Exception e){

                        }*/

                    upoadMsg_fl = firstLoginTime;
                }


                try {

                    upoadMsg_did = getDeviceId__();
                    upoadMsg_uid = getUid();
                    upoadMsg_dt = getSecondTimestamp();
                    upoadMsg_dw = act_sta.getResources().getDisplayMetrics().widthPixels;
                    upoadMsg_dh = act_sta.getResources().getDisplayMetrics().heightPixels;
                    upoadMsg_pt = getSystemModel();
                    upoadMsg_sv = getSystemVersion();
                    upoadMsg_nt = IntenetUtil.getNetworkState(act_sta);

                    if (upoadMsg_nt == "4g") {
                        upoadMsg_ip = NetWorkUtils.getIPAddress(act_sta);
                    } else {
                        upoadMsg_ip = NetWorkUtils.getIPAddress(act_sta);
                    }

                    upoadMsg_mac = getLocalMacAddressFromIp();

                    getLocationLL();
                    String aa = "" + upoadMsg_lon + "  " + upoadMsg_lat;

                    String temp = getAddress(upoadMsg_lat, upoadMsg_lon);
                    String aaa = upoadMsg_pv;
                    String bbb = upoadMsg_ct;

                    upoadMsg_v = "1.0";


                    final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    final String tmDevice, tmSerial, androidId;
                    if (ActivityCompat.checkSelfPermission(act_sta, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    tmDevice = "" + tm.getDeviceId();
                    tmSerial = "" + tm.getSimSerialNumber();
                    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

                    UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
                    String deviceId = deviceUuid.toString();

                    String adsdd = DeviceConfig.getDeviceIdForGeneral(act_sta);

                    String device_id = android.provider.Settings.Secure.getString(act_sta.getContentResolver(),
                            android.provider.Settings.Secure.ANDROID_ID);

                    upoadMsg_imei = tmDevice;
                    upoadMsg_androidid = device_id;


                    String[] device_infos =  getTestDeviceInfo(act_sta);
                    String deviceInfo = getDeviceInfo(act_sta);

                    //获取OAID等设备标识符
                    MiitHelper miitHelper = new MiitHelper(appIdsUpdater);
                    miitHelper.getDeviceIds(getApplicationContext());
                    String oiad = DevicesUtil.getOaid();

                    int aa11 = 0;
                    int aa22 = 0;
                    int aa33 = 0;



                    }catch (Exception e){

                    }

                    postData("-1",build_qudaohao,"-1","in",upoadMsg_fl,0,"","","");
                }
            }).start();

        }

        private MiitHelper.AppIdsUpdater appIdsUpdater = new MiitHelper.AppIdsUpdater() {
            @Override
            public void OnIdsAvalid(@NonNull String ids) {
                Log.e("++++++ids: ", ids);
                oaid = ids;
            }
        };

        /**
         * post数据请求  gid 游戏id  cid 渠道号  scid 子渠道号  ev事件类型：in out ad  fl 新用户第一次登录时间
         * dr ev为out时候，统计用户在线时间   adt ev为ad时，广告类型：v视频，b banner，i插屏，s开屏
         * ada 广告行为 o打开 c取消 f完成  adid ev为ad时，广告ID
         */
        public static void postData(String gid,String cid,String scid,String ev,String fl,long dr,String adt,String ada,String adid) {

            upoadMsg_gid = gid;
            upoadMsg_cid = cid;
            upoadMsg_scid = scid;
            upoadMsg_ev = ev;
            upoadMsg_fl = fl;
            upoadMsg_dr = dr;
            upoadMsg_adt = adt;
            upoadMsg_ada = ada;
            upoadMsg_adid = adid;

            upoadMsg_dt = getSecondTimestamp();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //String params = "{\"user_phone\":" +user_phone +",\"user_password\":" + user_password+  "}";
                    String params = "{\"did\":\"did\",\"uid\":\"uid\"}";

                    JSONObject root = new JSONObject();
                    try {

                        root.put("did", upoadMsg_did);
                        root.put("uid", upoadMsg_uid);
                        root.put("dt", upoadMsg_dt);
                        root.put("gid", upoadMsg_gid);
                        root.put("cid", upoadMsg_cid);

                        root.put("scid", upoadMsg_scid);
                        root.put("dw", upoadMsg_dw);
                        root.put("dh", upoadMsg_dh);
                        root.put("pt", upoadMsg_pt);
                        root.put("st", upoadMsg_st);

                        root.put("sv", upoadMsg_sv);
                        root.put("nt", upoadMsg_nt);
                        root.put("mac", upoadMsg_mac);
                        root.put("ev", upoadMsg_ev);
                        root.put("fl", upoadMsg_fl);
                        root.put("ip", upoadMsg_ip);

                        root.put("lon", upoadMsg_lon);
                        root.put("lat", upoadMsg_lat);
                        root.put("pv", upoadMsg_pv);
                        root.put("ct", upoadMsg_ct);
                        root.put("v", upoadMsg_v);

                        root.put("dr", upoadMsg_dr);
                        root.put("adt", upoadMsg_adt);
                        root.put("ada", upoadMsg_ada);
                        root.put("adid", upoadMsg_adid);

                        root.put("imei", upoadMsg_imei);
                        root.put("androidid", upoadMsg_androidid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("info",root.toString());


                    try {
                        URL url=new URL("http://gc.log.gameley.cn/");
                        HttpURLConnection connect=(HttpURLConnection)url.openConnection();
                        connect.setDoInput(true);
                        connect.setDoOutput(true);
                        connect.setRequestMethod("POST");
                        connect.setUseCaches(false);
                        connect.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        OutputStream outputStream = connect.getOutputStream();
                        outputStream.write(root.toString().getBytes());
                        int response = connect.getResponseCode();
                        if (response== HttpURLConnection.HTTP_OK)
                        {
                            System.out.println(response);
                            InputStream input=connect.getInputStream();
                            BufferedReader in = new BufferedReader(new InputStreamReader(input));
                            String line = null;
                            StringBuffer sb = new StringBuffer();  //创建字符串对象
                            while ((line = in.readLine()) != null) {
                                sb.append(line);
                            }
                            //JSONObject reinfo = new JSONObject(sb.toString());  //字符串转json对象
                            //Log.i("请求结果", reinfo.get("info").toString());
                            //System.out.println(reinfo.get("info"));  //用get获取json对象的值
                        }
                        else {
                            System.out.println(response);
                        }
                    } catch (Exception e) {
                        Log.e("e:", String.valueOf(e));
                    }
                }
            }).start();
        }




        TTAdManager ttAdManager;
        public static TTAdNative mTTAdNative;
        public static boolean mIsLoaded = false;
        public static TTRewardVideoAd mttRewardVideoAd;
        public static TTFullScreenVideoAd mttFullVideoAd;

        static void showRewardAD(final Activity act, TTAdNative mTTAdNative){
            AdSlot adSlot = new AdSlot.Builder()
                    .setCodeId(build_rewardVideoId)
                    .setSupportDeepLink(true)
                    .setAdCount(2)
                    //个性化模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500,500)
                    .setImageAcceptedSize(1080, 1920)
                    .setRewardName("金币") //奖励的名称
                    .setRewardAmount(3)   //奖励的数量
                    //必传参数，表来标识应用侧唯一用户；若非服务器回调模式或不需sdk透传
                    //可设置为空字符串
                    .setUserID(upoadMsg_did)
                    .setOrientation(TTAdConstant.VERTICAL)  //设置期望视频播放的方向，为TTAdConstant.HORIZONTAL或TTAdConstant.VERTICAL
                    .setMediaExtra("media_extra") //用户透传的信息，可不传
                    .build();
            mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
                @Override
                public void onError(int code, String message) {
                   // Toast.makeText(act, message, Toast.LENGTH_SHORT).show();
                }
                //视频广告加载后的视频文件资源缓存到本地的回调
                @Override
                public void onRewardVideoCached() {
                    mIsLoaded = true;
                    //Toast.makeText(act, "rewardVideoAd video cached", Toast.LENGTH_SHORT).show();
                }
                //视频广告素材加载到，如title,视频url等，不包括视频文件
                @Override
                public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                    //Toast.makeText(act, "rewardVideoAd loaded", Toast.LENGTH_SHORT).show();
                    mIsLoaded = false;
                    mttRewardVideoAd = ad;
                    //mttRewardVideoAd.setShowDownLoadBar(false);
                    mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                        @Override
                        public void onAdShow() {
                            //Toast.makeText(act, "rewardVideoAd show", Toast.LENGTH_SHORT).show();
                            MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","o","-1");
                        }

                        @Override
                        public void onAdVideoBarClick() {
                            //Toast.makeText(act,"rewardVideoAd bar click", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onAdClose() {
                            //Toast.makeText(act, "rewardVideoAd close", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onVideoComplete() {

                            //Toast.makeText(act, "rewardVideoAd complete", Toast.LENGTH_SHORT).show();
                            //TToast.show(FullScreenVideoActivity.this, "FullVideoAd complete");
                            //myX5WebView.loadUrl("javascript:callJS()");
                            myX5WebView.loadUrl("javascript:qg.callJS()");
                            MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","f","-1");
                        }

                        @Override
                        public void onVideoError() {

                        }

                        @Override
                        public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                            //Toast.makeText(act, "verify:"+rewardVerify+" amount:"+rewardAmount+
                                            //" name:"+rewardName,
                                    //Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSkippedVideo() {
                            //TToast.show(FullScreenVideoActivity.this, "FullVideoAd skipped");
                            //myX5WebView.loadUrl("javascript:callJS()");
                            myX5WebView.loadUrl("javascript:qg.callJSfailed()");
                            MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","c","-1");
                        }
                    });
                    mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
                        @Override
                        public void onIdle() {

                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });

                    mttRewardVideoAd.showRewardVideoAd(act);
                }
            });

        }


    static void shoBannerAD(final Activity act, TTAdNative mTTAdNative){
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(build_bannerId) //广告位id 945554285  945553937
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(600,130) //期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(640,320 )//这个参数设置即可，不影响个性化模板广告的size
                .build();
        //加载广告
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //Toast.makeText(act, "load error : " + code + ", " + message,Toast.LENGTH_SHORT).show();
                mExpressContainer.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0){
                    //Toast.makeText(act, "load error : " + ", 0000" ,Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(act, "load success : ",Toast.LENGTH_SHORT).show();
                mTTAd = ads.get(0);

                mTTAd.setSlideIntervalTime(30*1000);//设置轮播间隔 ms,不调用则不进行轮播展示
                bindAdListener(mTTAd,act);
                mTTAd.render();//调用render开始渲染广告
            }

        });


    }

    static void showKaiPingAD(final Activity act, TTAdNative mTTAdNative) {

        boolean mIsExpress = false;
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = null;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            adSlot = new AdSlot.Builder()
                    .setCodeId(build_openVideoId)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId("887392876")
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }


        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {

            }

            @Override
            @MainThread
            public void onTimeout() {

            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();

                mSplashContainer.removeAllViews();
                //把SplashView 添加到ViewGroup中
                mSplashContainer.addView(view);
                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        mSplashContainer.removeAllViews();
                        mSplashContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdShow(View view, int type) {

                    }

                    @Override
                    public void onAdSkip() {
                        mSplashContainer.removeAllViews();
                        mSplashContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdTimeOver() {
                        mSplashContainer.removeAllViews();
                        mSplashContainer.setVisibility(View.GONE);
                    }
                });
            }
        }, 3000);
    }



    static void showAllScreenAD(final Activity act, TTAdNative mTTAdNative){
        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("887392876")
                .setSupportDeepLink(true)
                //个性化模板广告需要设置期望个性化模板广告的大小,单位dp,全屏视频场景，只要设置的值大于0即可
                .setExpressViewAcceptedSize(500,500)
                .setImageAcceptedSize(1080, 1920)
                .setOrientation(TTAdConstant.VERTICAL)
                .build();
        //加载全屏视频
        mTTAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
               // Toast.makeText(act, "load fail : ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
               // TToast.show(FullScreenVideoActivity.this, "FullVideoAd loaded");
                mIsLoaded = false;
                mttFullVideoAd = ad;
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        //TToast.show(FullScreenVideoActivity.this, "FullVideoAd show");
                        MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","o","-1");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        //TToast.show(FullScreenVideoActivity.this, "FullVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                       // TToast.show(FullScreenVideoActivity.this, "FullVideoAd close");
                        //myX5WebView.loadUrl("javascript:callJS()");
                        //Cocos2dxJavascriptJavaBridge.evalString("cc.log(\"Javascript Java bridge!\")");
                    }

                    @Override
                    public void onVideoComplete() {
                        //TToast.show(FullScreenVideoActivity.this, "FullVideoAd complete");
                        //myX5WebView.loadUrl("javascript:callJS()");
                        //myX5WebView.loadUrl("javascript:callJS()");
                        MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","f","-1");
                    }

                    @Override
                    public void onSkippedVideo() {
                        //TToast.show(FullScreenVideoActivity.this, "FullVideoAd skipped");
                        //myX5WebView.loadUrl("javascript:callJS()");
                        //myX5WebView.loadUrl("javascript:callJSfailed()");
                        MainActivity.postData(openingGamename,build_qudaohao,"-1","ad",upoadMsg_fl,0,"v","c","-1");
                    }

                });

                mttFullVideoAd.showFullScreenVideoAd(act);
            }

            @Override
            public void onFullScreenVideoCached() {
                mIsLoaded = true;
                //TToast.show(FullScreenVideoActivity.this, "FullVideoAd video cached");
            }
        });
    }

    public static ViewGroup mExpressContainer;
    public static TTNativeExpressAd mTTAd;

    public static void showChaPingAD(final Activity act, TTAdNative mTTAdNative) {

        //设置广告参数
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId("5112505") //广告位id 945494253
                .setSupportDeepLink(true)
                .setAdCount(1) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(500,500)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //加载广告
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                //TToast.show(NativeExpressActivity.this, "load error : " + code + ", " + message);
                mExpressContainer.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0){
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd,act);
                mTTAd.render();//调用render开始渲染广告
            }
        });

    }

    public static boolean mHasShowDownloadActive = false;

    //绑定广告行为
    private static void bindAdListener(TTNativeExpressAd ad, final Activity act) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {

            @Override
            public void onAdDismiss() {
                // TToast.show(mContext, "广告关闭");
            }

            @Override
            public void onAdClicked(View view, int type) {
                // TToast.show(mContext, "广告被点击");
            }

            @Override
            public void onAdShow(View view, int type) {
                // TToast.show(mContext, "广告展示");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                //Log.e("ExpressView","render fail:"+(System.currentTimeMillis() - startTime));
                // TToast.show(mContext, msg+" code:"+code);
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                //返回view的宽高 单位 dp
                // TToast.show(mContext, "渲染成功");
                //在渲染成功回调时展示广告，提升体验
                mTTAd.showInteractionExpressAd(act);
                //返回view的宽高 单位 dp

                //在渲染成功回调时展示广告，提升体验
                mExpressContainer.removeAllViews();
                mExpressContainer.addView(view);
            }
        });

        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
            return;
        }
        //可选，下载监听设置
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                // TToast.show(InteractionExpressActivity.this, "点击开始下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                if (!mHasShowDownloadActive) {
                    mHasShowDownloadActive = true;
                    //   TToast.show(InteractionExpressActivity.this, "下载中，点击暂停", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                //TToast.show(InteractionExpressActivity.this, "下载暂停，点击继续", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                //TToast.show(InteractionExpressActivity.this, "下载失败，点击重新下载", Toast.LENGTH_LONG);
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                // TToast.show(InteractionExpressActivity.this, "安装完成，点击图片打开", Toast.LENGTH_LONG);
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                //  TToast.show(InteractionExpressActivity.this, "点击安装", Toast.LENGTH_LONG);
            }
        });
    }

    //在合适的时机，释放广告的资源
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTTAd != null) {
            //调用destroy()方法释放
            mTTAd.destroy();
        }

        String cur_time = getSecondTimestamp();
        long time = calDateDifferent(openAppTime,cur_time);
        postData("-1",build_qudaohao,"-1","out",upoadMsg_fl,time,"","","");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK) {

            AlertDialog.Builder builder = new AlertDialog.Builder(act_sta);
            builder.setTitle("提示");
            builder.setMessage("是否关闭当前应用");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String cur_time = getSecondTimestamp();
                    long time = calDateDifferent(openAppTime,cur_time);
                    postData("-1",build_qudaohao,"-1","out",upoadMsg_fl,time,"","","");

                    android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                    System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.show();

            //return true;//不执行父类点击事件
            return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }





        void generateTuijian(Activity act){

            ModuleAddressBean mab = todaypush_beanlists.get(0);
            TextView text11 = (TextView) act.findViewById(R.id.xinyoutuijian_title);
            text11.setText(mab.tuijianmingcheng);
            if(mab.tuijianleixing.equals("101")&&mab.tuijianyemian.equals("101")){
                HorizontalScrollView scr00 = findViewById(R.id.xinyoutuijian_hor);
                scr00.setVisibility(View.GONE);
                LinearLayout scr02 = findViewById(R.id.xinyoutuijian_ver1);
                scr02.setVisibility(View.VISIBLE);
                LinearLayout scr03 = findViewById(R.id.xinyoutuijian_ver2);
                scr03.setVisibility(View.VISIBLE);
            }

            for( int i=1; i<todaypush_beanlists.size(); i++ ) {
                String icon_name = "tuijian_icon00"+(i);
                int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
                ImageView imageTopview = (ImageView) act.findViewById(image222);//R.id.tuijian_icon001);
                String sImage = todaypush_beanlists.get(i).tuijiansucai;//"icon000.jpg";
                int image = this.getResources().getIdentifier(sImage.substring(0, sImage.length()), "drawable", getPackageName());
                Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image);//R.drawable.icon001);
                imageTopview.setImageBitmap(gameStatusBitmap);
                //imageTopview.setImageDrawable(Drawable.createFromPath("@drawable/icon000"));

                if (todaypush_beanlists.get(i).tuijianmingcheng.length() > 5) {
                    //todaypush_beanlists.get(i).tuijianmingcheng = todaypush_beanlists.get(i).tuijianmingcheng.substring(0, 5) + "...";
                }

                String game_name = "tuijian_text00"+(i);
                int gamenametext = this.getResources().getIdentifier(game_name, "id", getPackageName());
                TextView text = (TextView) act.findViewById(gamenametext);
                text.setText(todaypush_beanlists.get(i).tuijianmingcheng);
            }
        }

    void generateXinyouTuijian(Activity act){

        ModuleAddressBean mab = newgame_beanlists.get(0);
        TextView text11 = (TextView) act.findViewById(R.id.jinrituijian_title);
        text11.setText(mab.tuijianmingcheng);
        if(mab.tuijianleixing.equals("101")&&mab.tuijianyemian.equals("101")){
            HorizontalScrollView scr00 = findViewById(R.id.jinrituijian_hor);
            scr00.setVisibility(View.GONE);
            LinearLayout scr02 = findViewById(R.id.jinrituijian_ver1);
            scr02.setVisibility(View.VISIBLE);
            LinearLayout scr03 = findViewById(R.id.jinrituijian_ver2);
            scr03.setVisibility(View.VISIBLE);
        }

        for( int i=1; i<newgame_beanlists.size(); i++ ) {
            String icon_name = "jinrituijian_icon00"+(i);
            int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
            ImageView imageTopview = (ImageView) act.findViewById(image222);//R.id.tuijian_icon001);
            String sImage = newgame_beanlists.get(i).tuijiansucai;//"icon000.jpg";
            int image = this.getResources().getIdentifier(sImage.substring(0, sImage.length()), "drawable", getPackageName());
            Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image);//R.drawable.icon001);
            imageTopview.setImageBitmap(gameStatusBitmap);
            //imageTopview.setImageDrawable(Drawable.createFromPath("@drawable/icon000"));

            if (newgame_beanlists.get(i).tuijianmingcheng.length() > 5) {
                //newgame_beanlists.get(i).tuijianmingcheng = newgame_beanlists.get(i).tuijianmingcheng.substring(0, 5) + "...";
            }

            String game_name = "tv_des11"+(i)+(i);
            int gamenametext = this.getResources().getIdentifier(game_name, "id", getPackageName());
            TextView text = (TextView) act.findViewById(gamenametext);
            text.setText(newgame_beanlists.get(i).tuijianmingcheng);
        }

    }


    void generatePaiHangBang(Activity act){

        ModuleAddressBean mab = ranklistgame_beanlists.get(0);

        for( int i=1; i<ranklistgame_beanlists.size(); i++ ) {
            String icon_name = "rankicon"+(i);
            int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
            ImageView imageTopview = (ImageView) act.findViewById(image222);//R.id.tuijian_icon001);
            String sImage = ranklistgame_beanlists.get(i).tuijiansucai;//"icon000.jpg";
            int image = this.getResources().getIdentifier(sImage.substring(0, sImage.length()), "drawable", getPackageName());
            Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image);//R.drawable.icon001);
            imageTopview.setImageBitmap(gameStatusBitmap);
            //imageTopview.setImageDrawable(Drawable.createFromPath("@drawable/icon000"));

            if (ranklistgame_beanlists.get(i).tuijianmingcheng.length() > 5&&i<=3) {
                //ranklistgame_beanlists.get(i).tuijianmingcheng = ranklistgame_beanlists.get(i).tuijianmingcheng.substring(0, 5) + "...";
            }

            String game_name = "rankname"+(i);
            int gamenametext = this.getResources().getIdentifier(game_name, "id", getPackageName());
            TextView text = (TextView) act.findViewById(gamenametext);
            text.setText(ranklistgame_beanlists.get(i).tuijianmingcheng);

            if(i>3){
                String game_des = "rankdes"+(i-3);
                int gamedestext = this.getResources().getIdentifier(game_des, "id", getPackageName());
                TextView destext = (TextView) act.findViewById(gamedestext);
                destext.setText(ranklistgame_beanlists.get(i).tuijianmiaoshu);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        //String cur_time = getSecondTimestamp();
        //long time = calDateDifferent(openAppTime,cur_time);
        //postData("-1","-1","-1","out",upoadMsg_fl,time,"","","");
    }

    @Override
    protected void onStart() {
        super.onStart();

        //postData("-1","-1","-1","in",upoadMsg_fl,0,"","","");
    }




    void generateJingpinXinyou(Activity act) {

        ModuleAddressBean mab = fantasygame_beanlists.get(0);
        TextView text11 = (TextView) act.findViewById(R.id.jingpinxinyou_title);
        text11.setText(mab.tuijianmingcheng);
        if(mab.tuijianleixing.equals("101")&&mab.tuijianyemian.equals("101")){
            HorizontalScrollView scr00 = findViewById(R.id.jingpinxinyou_hor);
            scr00.setVisibility(View.GONE);
            LinearLayout scr02 = findViewById(R.id.jingpinxinyou_ver1);
            scr02.setVisibility(View.VISIBLE);
            LinearLayout scr03 = findViewById(R.id.jingpinxinyou_ver2);
            scr03.setVisibility(View.VISIBLE);

            for( int i=1; i<fantasygame_beanlists.size(); i++ ) {
                String icon_name = "jinrituijian_icon00"+(i);
                int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
                ImageView imageTopview = (ImageView) act.findViewById(image222);//R.id.tuijian_icon001);
                String sImage = fantasygame_beanlists.get(i).tuijiansucai;//"icon000.jpg";
                int image = this.getResources().getIdentifier(sImage.substring(0, sImage.length()), "drawable", getPackageName());
                Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image);//R.drawable.icon001);
                imageTopview.setImageBitmap(gameStatusBitmap);
                //imageTopview.setImageDrawable(Drawable.createFromPath("@drawable/icon000"));

                if (fantasygame_beanlists.get(i).tuijianmingcheng.length() > 5) {
                    //fantasygame_beanlists.get(i).tuijianmingcheng = fantasygame_beanlists.get(i).tuijianmingcheng.substring(0, 5) + "...";
                }

                String game_name = "tuijian_text00"+(i);
                int gamenametext = this.getResources().getIdentifier(game_name, "id", getPackageName());
                TextView text = (TextView) act.findViewById(gamenametext);
                text.setText(fantasygame_beanlists.get(i).tuijianmingcheng);
            }

        }else if(mab.tuijianleixing.equals("102")&&mab.tuijianyemian.equals("101")){
            HorizontalScrollView scr00 = findViewById(R.id.jingpinxinyou_hor);
            scr00.setVisibility(View.VISIBLE);
            LinearLayout scr02 = findViewById(R.id.jingpinxinyou_ver1);
            scr02.setVisibility(View.GONE);
            LinearLayout scr03 = findViewById(R.id.jingpinxinyou_ver2);
            scr03.setVisibility(View.GONE);

            for( int i=1; i<fantasygame_beanlists.size(); i++ ) {
                String icon_name = "tuijian_icon001xinyou"+(i);
                int image222 = this.getResources().getIdentifier(icon_name, "id", getPackageName());
                ImageView imageTopview = (ImageView) act.findViewById(image222);//R.id.tuijian_icon001);
                String sImage = fantasygame_beanlists.get(i).tuijiansucai;//"icon000.jpg";
                int image = this.getResources().getIdentifier(sImage.substring(0, sImage.length()), "drawable", getPackageName());
                Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), image);//R.drawable.icon001);
                imageTopview.setImageBitmap(gameStatusBitmap);
                //imageTopview.setImageDrawable(Drawable.createFromPath("@drawable/icon000"));

                if (fantasygame_beanlists.get(i).tuijianmingcheng.length() > 5) {
                    //fantasygame_beanlists.get(i).tuijianmingcheng = fantasygame_beanlists.get(i).tuijianmingcheng.substring(0, 5) + "...";
                }

                String game_name = "tuijian_text001xinyou"+(i);
                int gamenametext = this.getResources().getIdentifier(game_name, "id", getPackageName());
                TextView text = (TextView) act.findViewById(gamenametext);
                text.setText(fantasygame_beanlists.get(i).tuijianmingcheng);
            }


        }




    }


        public void switchPop(View view){

            ScrollView scr = findViewById(R.id.page01);
            scr.setVisibility(View.VISIBLE);
            ScrollView scr2 = findViewById(R.id.page02);
            scr2.setVisibility(View.GONE);



            ImageView imageTopview3 = (ImageView) act_sta.findViewById(R.id.popPage);
            Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.popbtnchoosed);
            imageTopview3.setImageBitmap(gameStatusBitmap);

            ImageView imageTopview4 = (ImageView) act_sta.findViewById(R.id.rankPage);
            Bitmap gameStatusBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.rankbtn);
            imageTopview4.setImageBitmap(gameStatusBitmap4);

            TextView text01 = (TextView) act_sta.findViewById(R.id.poptitle);
            text01.setTextColor(Color.RED);

            TextView text02 = (TextView) act_sta.findViewById(R.id.ranktitle);
            text02.setTextColor(Color.GRAY);

        }

        public void switchRank(View view){
            ScrollView scr = findViewById(R.id.page01);
            scr.setVisibility(View.GONE);
            ScrollView scr2 = findViewById(R.id.page02);
            scr2.setVisibility(View.VISIBLE);



            ImageView imageTopview3 = (ImageView) act_sta.findViewById(R.id.popPage);
            Bitmap gameStatusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.popbtn);
            imageTopview3.setImageBitmap(gameStatusBitmap);

            ImageView imageTopview4 = (ImageView) act_sta.findViewById(R.id.rankPage);
            Bitmap gameStatusBitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.rankbtnchoosed);
            imageTopview4.setImageBitmap(gameStatusBitmap4);

            TextView text01 = (TextView) act_sta.findViewById(R.id.poptitle);
            text01.setTextColor(Color.GRAY);

            TextView text02 = (TextView) act_sta.findViewById(R.id.ranktitle);
            text02.setTextColor(Color.RED);
        }


        @Override
        protected void onSaveInstanceState(Bundle outState) {
            outState.putInt(PRV_SELINDEX, mrIndex);
            super.onSaveInstanceState(outState);
        }


        //读取CSV文件
        public static List<ModuleAddressBean> readCSV(String path, Activity activity){
            List<ModuleAddressBean> list=new ArrayList<ModuleAddressBean>();
            File file=new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileInputStream fiStream;
            Scanner scanner;
            try {
                //fiStream=new FileInputStream(file);
                InputStream fiStreams= activity.getApplicationContext().getAssets().open("0918csv.CSV");
                scanner=new Scanner(fiStreams,"UTF-8");
                //scanner.nextLine();//读下一行,把表头越过。不注释的话第一行数据就越过去了
                int a=0;
                while (scanner.hasNextLine()) {
                    String sourceString = scanner.nextLine();
                    Log.e("source-->", sourceString);
                    sourceString=sourceString+',';
                    Pattern pattern = Pattern.compile("[^,]*,");
                    Matcher matcher = pattern.matcher(sourceString);
                    String[] lines=new String[12];
                    int i=0;
                    while(matcher.find()) {
                        String find = matcher.group().replace(",", "");
                        lines[i]=find.trim();
                        Log.e("split________csv", "find="+find+",i="+i+",lines="+lines[i]);
                        i++;
                    }
                    ModuleAddressBean bean = new ModuleAddressBean(a,lines[0],lines[1],lines[2],lines[3],lines[4],lines[5],lines[6],lines[7],lines[8],lines[9],lines[10],lines[11],0);
                    list.add(bean);
                    a++;
                    i=0;
                }
            } catch (NumberFormatException e) {
                //showToast(activity, "NumberFormatException");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                //showToast(activity, "文件不存在");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }



        /**
         * 根据传入的index参数来设置选中的tab页。
         *
         * @param id 传入的选择的fragment
         */
        private void setTabSelection(int id) {    //根据传入的index参数来设置选中的tab页。
            if (id == index) {
                return;
            }
            index = id;
            // 开启一个Fragment事务
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // 设置切换动画
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
            hideFragments(transaction);
            switch (index) {
                case show_tab_map://地图的fragment
                    Log.println(0,"2","button02clicked");
                    ScrollView scr = findViewById(R.id.page01);
                    scr.setVisibility(View.VISIBLE);
                    ScrollView scr2 = findViewById(R.id.page02);
                    scr2.setVisibility(View.GONE);
                    if (tabMapFragment == null) {
                        tabMapFragment = new TabMapFragment();
                        //transaction.add(R.id.fl_container, tabMapFragment, FRAGMENT_TAG[index]);
                    } else {
                        //transaction.show(tabMapFragment);
                    }
                    transaction.commit();
                    break;
                case show_tab_car://导航的fragment
                    Log.println(0,"2","button01clicked");
                    ScrollView scr00 = findViewById(R.id.page01);
                    scr00.setVisibility(View.GONE);
                    ScrollView scr02 = findViewById(R.id.page02);
                    scr02.setVisibility(View.VISIBLE);
                    if (tabCarFragment == null) {
                        tabCarFragment = new TabCarFragment();
                        //transaction.add(R.id.fl_container, tabCarFragment, FRAGMENT_TAG[index]);
                    } else {
                        //transaction.show(tabCarFragment);
                    }
                    transaction.commit();
                    break;
                case show_tab_find://的fragment
                    break;
                default:
                    break;
            }
        }

        /**
         * 隐藏fragment
         *
         * @param transaction
         */
        private void hideFragments(FragmentTransaction transaction) {
            if (tabCarFragment != null) {
                //transaction.hide(tabCarFragment);
            }
            if (tabMapFragment != null) {
                //transaction.hide(tabMapFragment);
            }
            if (tabFindFragment != null) {
                //transaction.hide(tabFindFragment);
            }
            if (tabMeFragment != null) {
                //transaction.hide(tabMeFragment);
            }
        }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onRewardVideoAdLoad(TTRewardVideoAd ttRewardVideoAd) {

    }

    @Override
    public void onRewardVideoCached() {

    }




    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (TextUtils.isEmpty(mac)) {
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;
    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration < NetworkInterface > interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }
}
