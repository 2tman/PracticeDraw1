package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice8DrawArcView extends View {

    Paint paint = new Paint();

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        //默认填充模式
        paint.setStyle(Paint.Style.FILL);
        //扇形
        canvas.drawArc(new RectF(200, 200, 400, 400), 240, 100, true, paint);

        //底部扇形
        canvas.drawArc(new RectF(200, 200, 400, 400), 20, 140, false, paint);

        //画线模式
        paint.setStyle(Paint.Style.STROKE);
        //弧形
        canvas.drawArc(new RectF(200, 200, 400, 400), 180,40, false, paint);
    }
}
