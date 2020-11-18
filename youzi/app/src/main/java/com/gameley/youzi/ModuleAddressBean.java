package com.gameley.youzi;

class ModuleAddressBean {
    public String tuijianxiang; //推荐项
    public String futuijianxiang;//父推荐项
    public String tuijianmingcheng;//推荐名称
    public String tuijianleixing; //推荐类型
    public String tuijianyemian; //推荐页面
    public String tuijiansucai; //推荐素材
    public String tuijianquanzhong; //推荐权重
    public String tuijianmiaoshu; //推荐描述
    public String shifoukaiqi; //是否开启
    public String zhanshishijian; //展示时间
    public String tiaozhuanleixing; //跳转类型
    public String tiaozhuancanshu; //跳转参数

    public ModuleAddressBean(int a, String line, String line1, String line2, String line3, String line4, String line5, String line6, String line7,
                             String line8, String line9, String line10, String line11 ,int i) {
        tuijianxiang = line; //推荐项
        futuijianxiang = line1;//父推荐项
        tuijianmingcheng = line2;//推荐名称
        tuijianleixing = line3; //推荐类型
        tuijianyemian = line4; //推荐页面
        tuijiansucai = line5; //推荐素材
        tuijianquanzhong = line6; //推荐权重
        tuijianmiaoshu = line7; //推荐描述
        shifoukaiqi = line8; //是否开启
        zhanshishijian = line9; //展示时间
        tiaozhuanleixing = line10; //跳转类型
        tiaozhuancanshu = line11; //跳转参数
    }
}
