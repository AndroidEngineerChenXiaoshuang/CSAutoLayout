package com.cs.autolayout.csautomaticlayout.utils;

import android.content.Context;

/**
 * Created by chenshuang on 2018/1/8.
 */

public class Utils {
    public static int px2sp(Context context,float pxValue){
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5f);
    }

    public static int dip2px(Context context,float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
}
