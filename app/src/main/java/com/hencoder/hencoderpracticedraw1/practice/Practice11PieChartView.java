package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw1.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制解析
 */
public class Practice11PieChartView extends View {

    private Paint paintPie = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();

    private float pointOffSet;

    private List<PieEntity> datas = new ArrayList<>();
    private float mTotalValue;

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

    private void init() {
        paintText.setTextSize(ScreenUtils.sp2px(getContext(), 12));
        paintLine.setStyle(Paint.Style.STROKE);
        pointOffSet = ScreenUtils.dp2px(getContext(), 10);
        initData();
        mTotalValue = getTotalValue();
    }

    private static final int[] COLORS = {
            Color.parseColor("#F44336"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#6F8188"),
            Color.parseColor("#009688"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#FF4081")
    };

    private void initData() {
        int size = 7;
        datas.clear();
        for (int i = 0; i < size; i++) {
            float mValue = (float) (Math.random() * 1000 + 100 * (i + 1));
            PieEntity pieEntity = new PieEntity("label" + i, mValue);
            pieEntity.setmColor(COLORS[i]);
            pieEntity.setmRemark("reamrk"+mValue);
            if (i > 4) {
                pieEntity.setDrawDashLine(true);
            }
            datas.add(pieEntity);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画饼图

        float letf = 200;
        float top = 100;
        float right = 500;
        float bottom = 400;
        RectF rectF = new RectF(letf, top, right, bottom);
        float radius = (rectF.right - rectF.left) / 2;

        float linePointRadius = radius + pointOffSet;

        float centerX = letf + radius;
        float centerY = top + radius;

        float startAngle = 0;
        for (int i = 0; i < datas.size(); i++) {
            PieEntity pieEntity = datas.get(i);
            float targetSwipe = pieEntity.getmValue() * 360 / mTotalValue;
            paintPie.setColor(pieEntity.getmColor());

            //画扇形
            canvas.drawArc(rectF, startAngle, targetSwipe, true, paintPie);

            float targetAngle = startAngle + targetSwipe / 2;
            paintLine.setColor(pieEntity.getmColor());

            final float sliceXBase = (float) Math.cos(targetAngle * FDEG2RAD);
            final float sliceYBase = (float) Math.sin(targetAngle * FDEG2RAD);
            float startX = linePointRadius * sliceXBase + centerX;
            float startY = linePointRadius * sliceYBase + centerY;

            //画圆点
            drawPoint(canvas, startX, startY);

            float endXDirect = (linePointRadius+ScreenUtils.dp2px(getContext(),15)) * sliceXBase + centerX;
            float endYDirect = (linePointRadius+ScreenUtils.dp2px(getContext(),15)) * sliceYBase + centerY;

            //画线条
            drawLine(canvas, startX, startY, endXDirect, endYDirect, targetAngle, pieEntity);

            startAngle += targetSwipe;
        }
    }

    public final static float FDEG2RAD = ((float) Math.PI / 180.f);

    /**
     * 画圆点
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas, float startX, float startY) {

        float roundWidth = ScreenUtils.dp2px(getContext(), 6);
        //画小圆点
        paintLine.setStrokeCap(Paint.Cap.ROUND);//小圆点
        paintLine.setStrokeWidth(roundWidth);
        canvas.drawPoint(startX, startY, paintLine);
    }

    /**
     * 画线
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas,
                          float startX,
                          float startY,
                          float endXDirect,
                          float endYDirect,
                          float targetAngle,
                          PieEntity pieEntity) {


        float lineWidth = ScreenUtils.dp2px(getContext(), 1);
        paintLine.setStrokeWidth(lineWidth);

        float nextEndX = 0;
        float defaultLineWidth = ScreenUtils.dp2px(getContext(),60);
        float nextLineWidth = defaultLineWidth;
        float labelPtx = 0;
        float remarkPtx = 0;

        int textWidth = ScreenUtils.calcTextWidth(paintText, pieEntity.getmLabel());
        int remarkWidth = ScreenUtils.calcTextWidth(paintText, pieEntity.getmRemark());
        if(remarkWidth>defaultLineWidth){
            nextLineWidth = remarkWidth;
        }

        if (targetAngle > 0 && targetAngle <= 90) {
            nextEndX = endXDirect + nextLineWidth;
            labelPtx = nextEndX - textWidth;
            remarkPtx = endXDirect;

        } else if (targetAngle > 90 && targetAngle <= 180) {
            nextEndX = endXDirect - nextLineWidth;
            if(nextEndX<0){
                nextEndX = 0;
                endXDirect = nextLineWidth;
            }
            labelPtx = nextEndX;
            remarkPtx = nextEndX;

        } else if (targetAngle > 180 && targetAngle <= 270) {
            nextEndX = endXDirect - nextLineWidth;
            if(nextEndX<0){
                nextEndX = 0;
                endXDirect = nextLineWidth;
            }
            labelPtx = nextEndX;
            remarkPtx = nextEndX;

        } else if (targetAngle > 270 && targetAngle <= 360) {
            nextEndX = endXDirect + nextLineWidth;
            labelPtx = nextEndX - textWidth;
            remarkPtx = endXDirect;
        }

        if (pieEntity.isDrawDashLine()) {
            //画虚线 如果直接drawLine 需要支持硬件加速  setLayerType(LAYER_TYPE_SOFTWARE, null);
            //改成drawPath
            PathEffect effects = new DashPathEffect(new float[]{8, 3, 8, 3}, 0);//设置虚线的间隔和点的长度
            paintLine.setPathEffect(effects);

        } else {
            paintLine.setPathEffect(null);
        }

        path.reset();
        path.moveTo(startX, startY);
        path.lineTo(endXDirect, endYDirect);
        canvas.drawPath(path, paintLine);

        path.reset();
        path.moveTo(endXDirect, endYDirect);//起始坐标
        path.lineTo(nextEndX, endYDirect);//终点坐标
        canvas.drawPath(path, paintLine);


        float labelY = endYDirect - ScreenUtils.dp2px(getContext(), 2);
        //绘制label
        canvas.drawText(pieEntity.getmLabel()+"", labelPtx, labelY, paintText);

        float remarkHeight = ScreenUtils.calcTextHeight(paintText, pieEntity.getmRemark());
        float remarkY = endYDirect + ScreenUtils.dp2px(getContext(), 1)+remarkHeight;

        //绘制remark
        canvas.drawText(pieEntity.getmRemark()+"", remarkPtx, remarkY, paintText);
    }


    /**
     * 获取总值
     *
     * @return
     */
    private float getTotalValue() {
        float totalValue = 0;
        for (int i = 0; i < datas.size(); i++) {
            totalValue += datas.get(i).getmValue();
        }
        return totalValue;
    }

    public class PieEntity {
        private String mLabel;
        private String mRemark;
        private float mValue;
        private int mColor;
        private boolean drawDashLine;

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

        public String getmRemark() {
            return mRemark;
        }

        public void setmRemark(String mRemark) {
            this.mRemark = mRemark;
        }

        public boolean isDrawDashLine() {
            return drawDashLine;
        }

        public void setDrawDashLine(boolean drawDashLine) {
            this.drawDashLine = drawDashLine;
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
