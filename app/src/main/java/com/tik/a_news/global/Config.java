package com.tik.a_news.global;

import com.tik.a_news.BuildConfig;

public class Config {
    public static String SERVER_PATH = "";
    public static boolean DEBUG = BuildConfig.DEBUG_OR_RELEASE;

    static {
        if(DEBUG){
            SERVER_PATH = "http://v.juhe.cn/";
        }else{
            SERVER_PATH = "http://v.juhe.cn/";
        }
    }

    public static final class Tab{
        public static final String[] TAB_ZH_ARRAY = {
                "头条", "社会", "国内", "国际", "娱乐",
                "体育", "军事", "科技", "财经", "时尚"
        };

        public static final String[] TAB_EN_ARRAY = {
                "top", "shehui", "guonei", "guoji", "yule",
                "tiyu", "junshi", "keji", "caijing", "shishang"
        };
    }

}
