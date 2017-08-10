package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制解析
 *
 */
public class Practice11PieChartView extends View {

    private Paint paintPie = new Paint();
    private Paint paintText = new Paint();
    private Paint paintLine = new Paint();
    private Path path =new Path();

    private List<PieEntity> datas = new ArrayList<>();

    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

    }

    private void initData(){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

    }

    public class PieEntity{
        private String mLabel;
        private float mValue;
        private int mColor;

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

        public int getmColor() {
            return mColor;
        }

        public void setmColor(int mColor) {
            this.mColor = mColor;
        }

        public PieEntity(String mLabel, float mValue, int mColor) {
            this.mLabel = mLabel;
            this.mValue = mValue;
            this.mColor = mColor;
        }

        public PieEntity(String mLabel, float mValue) {
            this.mLabel = mLabel;
            this.mValue = mValue;
        }
    }
}
