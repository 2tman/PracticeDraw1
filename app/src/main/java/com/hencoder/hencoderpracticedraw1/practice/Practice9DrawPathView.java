package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice9DrawPathView extends View {

    Paint paint = new Paint();
    Path path = new Path();


    public Practice9DrawPathView(Context context) {
        super(context);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice9DrawPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.moveTo(300, 300);

//        练习内容：使用 canvas.drawPath() 方法画心形
        //1.添加半圆弧
        path.addArc(new RectF(180, 200, 280, 300),140, 220);

        path.arcTo(new RectF(280, 200, 380, 300),180, 220);

        path.lineTo(280,380);

        canvas.drawPath(path, paint);
    }
}
