package com.cool.taobaojava.utils;

public class UrlUtils {

    public  static String createHomePagerUrl(int materialId, int page){
        return "discovery/" + materialId  + "/" + page;
    }

    public static String getCoverPath(String pic_url, int size){
        return "https:" + pic_url + "_" +  size + "x" + size + ".jpg";
    }
}
