package com.cool.taobaojava.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cool.taobaojava.base.BaseApplication;
import com.cool.taobaojava.model.domain.CacheWithDuration;
import com.google.gson.Gson;

import java.security.PublicKey;

public class JsonCacheUtil {
    public static final String JSON_CACHE_SP_NAME = "JSON_CACHE_SP_NAME";
    private final SharedPreferences mSharedPreferences;
    private final Gson mGson;

    private JsonCacheUtil(){
        mSharedPreferences = BaseApplication.getAppContext().getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE);
        mGson = new Gson();
    }

    // 增加
    public void saveCache(String key,Object value){
        saveCache(key,value,-1);
    }

    public void saveCache(String key,Object value, long duration){

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        String valueStr = mGson.toJson(value);

        if (duration != -1){
            duration += System.currentTimeMillis();
        }

        CacheWithDuration cacheWithDuration = new CacheWithDuration(duration,valueStr);
        String cacheWithTime =  mGson.toJson(cacheWithDuration);

        editor.putString(key,cacheWithTime);

        editor.apply();
    }

    // 删除
    public void delCache(String key){
      mSharedPreferences.edit().remove(key);
    }

    // 获取
    public <T extends Class>  T getValue(String key,Class<T> clazz){
        String valueWithDuration =  mSharedPreferences.getString(key,null);
        if (valueWithDuration==null) {
            return null;
        }
        CacheWithDuration cacheWithDuration = mGson.fromJson(valueWithDuration,CacheWithDuration.class);
        long duration = cacheWithDuration.getDuration();
        if (duration != -1 && duration - System.currentTimeMillis() < 0){
            return null;
        }else {
            String  cache = cacheWithDuration.getCache();
            T result = mGson.fromJson(cache,clazz);
            return result;
        }
    }

    private static JsonCacheUtil sJsonCacheUtil = null;
    private static JsonCacheUtil getInstance(){
        if (sJsonCacheUtil == null) {
            sJsonCacheUtil = new JsonCacheUtil();
        }
        return sJsonCacheUtil;
    }
}
