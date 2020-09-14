package com.cool.taobaojava.utils;

import android.util.Log;

public class LogUtils {
    private static int currentLev = 4;
    private static int debugLev = 4;
    private static int infoLev = 3;
    private static int warningLev = 2;
    private static int errorLev = 1;

    public static void d(Class clazz,String log){
        if (currentLev >= debugLev){
            Log.d(clazz.getSimpleName(), log);
        }
    }

    public static void i(Class clazz,String log){
        if (currentLev >= infoLev){
            Log.i(clazz.getSimpleName(), log);
        }
    }

    public static void w(Class clazz,String log){
        if (currentLev >= warningLev){
            Log.w(clazz.getSimpleName(), log);
        }
    }

    public static void e(Class clazz,String log){
        if (currentLev >= errorLev){
            Log.e(clazz.getSimpleName(), log);
        }
    }

}
