package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.hencoder.hencoderpracticedraw1.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 直方图
 */
public class Practice10HistogramView extends View {

    //y轴的左侧纵线
    Paint paintYLineLeft = new Paint(Paint.ANTI_ALIAS_FLAG);
    //x轴的底部横线
    Paint paintXLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    //x轴底部的文字
    Paint paintXText = new Paint(Paint.ANTI_ALIAS_FLAG);
    //柱状图
    Paint paintRect = new Paint(Paint.ANTI_ALIAS_FLAG);

    int xPadding = 50;
    int startY = 50;
    int rectHeight = 300;//图形区域的高度
    int rectWidth = 500;//图形区域的宽度
    int barWidth = 0;//柱状图的宽度
    int barSpace = 0;
    int xtextHeight = 0;//x轴文字距离x轴线的高度
    int xBarOffset = 10;//x轴左右偏移

    {
        barWidth = ScreenUtils.dp2px(getContext(), 25);
        xtextHeight = ScreenUtils.dp2px(getContext(), 20);
        xBarOffset = ScreenUtils.dp2px(getContext(), 10);
        xPadding = ScreenUtils.dp2px(getContext(), 30);//左右边距
        rectWidth = ScreenUtils.getScreenSize(getContext()).widthPixels - xPadding*2;
    }

    private List<BarEntity> datas;

    public Practice10HistogramView(Context context) {
        super(context);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        datas = new ArrayList<>();
        //模拟数据
        initDatas();
        setPadding(xPadding, 0, xPadding, 0);
        barSpace = getBarSpace();
    }

    private void initDatas() {
        int size = 7;
        for (int i = 0; i < size; i++) {
            BarEntity barEntity = new BarEntity("label" + i, (float) (Math.random() * 1000 + (i) * 100));
            datas.add(barEntity);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图
        /**
         * 1.左侧y轴画线
         */
        drawYLine(canvas);

        /**
         * 2.底部x轴画线
         */
        drawXLine(canvas);

        paintXText.setColor(Color.WHITE);
        paintXText.setTextSize(16);
        int textY = startY + rectHeight + xtextHeight;//文字的y轴距

        paintRect.setColor(Color.GREEN);
        paintRect.setStyle(Paint.Style.FILL);

        for (int i = 0; i < datas.size(); i++) {
            String mLabel = datas.get(i).getmLabel() + "";
            float mValue = datas.get(i).getmValue();

            int textWidth = ScreenUtils.calcTextWidth(paintXText, mLabel);
            int textOffSet = (barWidth - textWidth) / 2;
            int textxPadding = xPadding + barSpace * i + barWidth * i + textOffSet + xBarOffset;

            /**
             * 3.x轴文字
             */
            drawXText(canvas, mLabel, textxPadding, textY);

            int barxPadding = xPadding + barSpace * i + barWidth * i + xBarOffset;
            /**
             * 4. 绘制Bar Rect
             */
            drawBar(canvas, mValue, barxPadding);
        }


    }

    /**
     * y轴画线
     *
     * @param canvas
     */
    private void drawYLine(Canvas canvas) {
        paintYLineLeft.setColor(Color.WHITE);
        paintYLineLeft.setStrokeWidth(2);

        int stopYLineY = startY + rectHeight;
        canvas.drawLine(xPadding, startY, xPadding, stopYLineY, paintYLineLeft);
    }

    /**
     * x轴画线
     *
     * @param canvas
     */
    private void drawXLine(Canvas canvas) {
        paintXLine.setColor(Color.WHITE);
        paintXLine.setStrokeWidth(2);

        int xLineStartY = startY + rectHeight;
        int xLineStopX = xPadding + rectWidth;
        canvas.drawLine(xPadding, xLineStartY, xLineStopX, xLineStartY, paintXLine);
    }

    /**
     * x轴文字
     *
     * @param canvas
     */
    private void drawXText(Canvas canvas, String mLabel, int textxPadding, int textY) {
        canvas.drawText(mLabel + "", textxPadding, textY, paintXText);
    }

    /**
     * 绘制柱状图
     *
     * @param canvas
     */
    private void drawBar(Canvas canvas, float mValue, int barxPadding) {
        RectF rectF = new RectF();
        rectF.left = barxPadding;
        rectF.right = barxPadding + barWidth;
        rectF.bottom = startY + rectHeight;

        //TODO 如果y轴右数据，则按实际比例换算
        int randHeight = (int)Math.round(rectHeight * Math.random());
        if(randHeight>0) {
            rectF.top = rectF.bottom - randHeight;

            canvas.drawRect(rectF, paintRect);
        }
    }

    /**
     * 获取柱状图的间隔距离
     *
     * @return
     */
    private int getBarSpace() {
        int barSize = datas.size();
        int totalBarWidth = barWidth * barSize;
        int totalBarSpace = rectWidth - totalBarWidth - xBarOffset * 2;
        int barSpace = totalBarSpace / (barSize - 1);
        return barSpace;
    }

    /**
     * 柱状图数据
     */
    public class BarEntity {
        private String mLabel;
        private float mValue;

        public String getmLabel() {
            return mLabel;
        }

        public void setmLabel(String mLabel) {
            this.mLabel = mLabel;
        }

        public float getmValue() {
            return mValue;
        }

        public void setmValue(float mValue) {
            this.mValue = mValue;
        }

        public BarEntity(String mLabel, float mValue) {
            this.mLabel = mLabel;
            this.mValue = mValue;
        }
    }
}
