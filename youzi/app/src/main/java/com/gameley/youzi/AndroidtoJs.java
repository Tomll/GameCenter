package com.gameley.youzi;

import android.webkit.JavascriptInterface;

// 继承自Object类
public class AndroidtoJs extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.playad1();
    }

    @JavascriptInterface
    public void insert_ad() {
        //System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.playad1();
    }

    @JavascriptInterface
    public void all_screen_ad() {
        //System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.playad2();
    }

    @JavascriptInterface
    public void reward_ad() {
        //System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.playad1();
    }

    @JavascriptInterface
    public void show_banner_ad() {
        //System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.show_banner_ad();
    }

    @JavascriptInterface
    public void hide_banner_ad() {
        //System.out.println(msg);
        System.out.println("JS调用了Android的hello方法ssdsd");
        MainActivity.hide_banner_ad();
    }

}
