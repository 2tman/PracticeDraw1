package com.hencoder.hencoderpracticedraw1.utils;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by 加荣 on 2017/8/10.
 */

public class ScreenUtils {
    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    /**
     * dp转px
     * @param context
     * @param value
     * @return
     */
    public static int dp2px(Context context, int value) {
        float v = getScreenSize(context).density;
        return (int) (v * value + 0.5f);
    }

    /**
     * 获取文字的实际宽度
     * @param paint
     * @param demoText
     * @return
     */
    public static int calcTextWidth(Paint paint, String demoText) {
        return (int) paint.measureText(demoText);
    }

    public static int calcTextHeight(Paint paint, String demoText) {

        Rect r = new Rect();
        r.set(0,0,0,0);
        paint.getTextBounds(demoText, 0, demoText.length(), r);
        return r.height();
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
