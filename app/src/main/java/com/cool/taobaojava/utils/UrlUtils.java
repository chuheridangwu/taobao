package com.cool.taobaojava.utils;

public class UrlUtils {

    public  static String createHomePagerUrl(int materialId, int page){
        return "discovery/" + materialId  + "/" + page;
    }

    public static String getCoverPath(String pic_url){
        return "https:" + pic_url;
    }
}
