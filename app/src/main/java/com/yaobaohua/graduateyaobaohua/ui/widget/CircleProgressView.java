package com.yaobaohua.graduateyaobaohua.ui.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.utils.ScreenUtils;

import java.text.DecimalFormat;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/14 13：50
 * @DESC :仿小米天气环形空气质量
 */
public class CircleProgressView extends View {
    //控件所占矩形
    private RectF area;
    //外层所占矩形
    private RectF outArea;

    //外部的白线画笔
    private Paint ringInPaint;
    //灰色环形画笔
    private Paint outLinePaint;
    //圆心处的环的画笔
    private Paint centerRingPaint;

    //指针画笔
    private Paint clockwisePaint;

    private Paint progressBgPaint;

    //空气质量值文字画笔
    private Paint todayAirValuePaint;
    //说明文字画笔
    private Paint instructionPaint;
    //环形代表的总值
    private int totalValue = 100;
    //现在的值
    private int nowValue = 50;
    //比例(0度到260度)
    private int proportionValue;
    //比例（0-1）
    private float proportion;

    private String instruction = "程序员的必经之路";


    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }


    public void setNowValue(int nowValue) {
        this.nowValue = nowValue;
    }

    //中心点
    private float centerX;
    private float centerY;

    //粗空心线的宽度
    private float strokeWidth;

    //圆环的半径
    private float ringRadius;
    //文字的背景
    private Drawable textbackground;


    private float startAngle = 140;
    private float angel = 260;


    private void init() {
        //1.环形画笔
        ringInPaint = new Paint();
        ringInPaint.setColor(Color.parseColor("#ffffff"));//灰色
        //抗锯齿
        ringInPaint.setAntiAlias(true);
        // 画笔类型 STROKE空心 FILL 实心
        ringInPaint.setStyle(Paint.Style.STROKE);
        //2.外部表现画笔
        outLinePaint = new Paint();
        outLinePaint.setColor(Color.parseColor("#ffffff"));
        outLinePaint.setAntiAlias(true);
        outLinePaint.setStrokeWidth(3);
        outLinePaint.setStyle(Paint.Style.STROKE);
        //3.圆心处的环的画笔
        centerRingPaint = new Paint();
        centerRingPaint.setColor(Color.parseColor("#ffffff"));
        centerRingPaint.setAntiAlias(true);
        centerRingPaint.setStyle(Paint.Style.STROKE);
        centerRingPaint.setStrokeWidth(8);
        //4.指针的画笔
        clockwisePaint = new Paint();
        clockwisePaint.setColor(Color.parseColor("#666666"));
        clockwisePaint.setStrokeWidth(5);
        clockwisePaint.setAntiAlias(true);
        clockwisePaint.setStyle(Paint.Style.STROKE);
        //5.已有进度的画笔
        progressBgPaint = new Paint();
        progressBgPaint.setColor(Color.parseColor("#666666"));
        progressBgPaint.setAntiAlias(true);
        progressBgPaint.setStyle(Paint.Style.STROKE);
        //6.今日空气质量的画笔
        todayAirValuePaint = new Paint();
        todayAirValuePaint.setColor(Color.parseColor("#660000"));
        todayAirValuePaint.setAntiAlias(true);
        todayAirValuePaint.setStyle(Paint.Style.STROKE);
        //7.说明文字的画笔
        instructionPaint = new Paint();
        instructionPaint.setColor(Color.parseColor("#ff0000"));
        instructionPaint.setAntiAlias(true);
        instructionPaint.setStyle(Paint.Style.STROKE);

    }

    public CircleProgressView(Context context) {
        this(context, null);

    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray style = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);

        int count = style.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = style.getIndex(i);
            switch (attr) {
                case R.styleable.CircleProgressView_today_air_background:
                    textbackground = style.getDrawable(attr);
                    break;
            }
        }
        init();
    }

    private void setPaintByBiLi() {


        //1.中心点为获取到的宽度的1/2
        centerX = getMeasuredWidth() / 2;
        //2.粗线画笔。
        //设置空心线宽
        strokeWidth = centerX / 10;
        ringInPaint.setStrokeWidth(strokeWidth);
        progressBgPaint.setStrokeWidth(strokeWidth);

        //3.环的半径为
        ringRadius = centerX * 3 / 5;
        //4.外层的离边距
        float margin = centerX * 2 / 5;
        //5.环形的边距
        float outMargin = centerX / 5;
        //6.环形的矩形
        area = new RectF(margin, margin, getMeasuredWidth() - margin, getMeasuredHeight() - margin);
        //7.外层的矩形
        outArea = new RectF(outMargin, outMargin, getMeasuredWidth() - outMargin, getMeasuredHeight() - outMargin);

        //8.外部传值过来
        proportion = ((float) nowValue / (float) totalValue);
        proportionValue = (int) (((float) nowValue / (float) totalValue) * 260);
    }

    float angle = startAngle;
    boolean first = true;
    DecimalFormat df = new DecimalFormat("###.0");

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        //根据获取的大小按比例缩放控件
        setPaintByBiLi();
        ringInPaint.setStrokeWidth(strokeWidth);
        //1.先画底层环形的进度条false 表示不需要起始点和终点的连线。
        canvas.drawArc(area, startAngle, angel, false, ringInPaint);
        //2.再画底层的白线
        canvas.drawArc(outArea, startAngle, angel, false, outLinePaint);
        //3.再画出底层的圆心处的环
        canvas.drawCircle(centerX, centerX, 20, centerRingPaint);
        //4.画指针线（应该先画指针线）
        ringInPaint.setColor(Color.parseColor("#ffffffff"));
        ringInPaint.setStrokeWidth(5);


        final float centerX = area.centerX();
        final float centerY = area.centerY();
        final float radius = area.width() / 2;
        float x = (float) (Math.cos(Math.PI * (angle / 180)) * (radius + strokeWidth / 2)) + centerX;
        float y = (float) (Math.sin(Math.PI * (angle / 180)) * (radius + strokeWidth / 2)) + centerY;
        canvas.drawLine(centerX, centerY, x, y, clockwisePaint);
        //angle:从140到proportionValue渐变
        canvas.drawArc(area, startAngle, angle - startAngle, false, progressBgPaint);
        //5.画最下方的说明文字
        float outRadius = outArea.width() / 2;
        int outTextSize = ScreenUtils.px2dp(getContext(), centerX / 5);
        instructionPaint.setTextSize(outTextSize);

        Rect outRect = new Rect();
        instructionPaint.getTextBounds(instruction + ":已募：" + nowValue + "需募：" + totalValue, 0,
                (instruction + ":已募：" + nowValue + "需募：" + totalValue).length(), outRect);
        int outTextWidth = outRect.width();
        int outTextHeight = outRect.height();
        canvas.drawText(instruction + ":已募：" + nowValue + "需募：" + totalValue, centerX - outTextWidth / 2, centerY + outRadius, instructionPaint);


        //画文字
        float textX = centerX;
        float temp = (float) (Math.cos(Math.PI * (50 / 180)) * (radius + strokeWidth / 2));//他算的是中心点到直线的距离。
        //8.今日空气质量文字大小随着环的半径设定
        int textSize = ScreenUtils.px2dp(getContext(), temp * 4 / 5);
        todayAirValuePaint.setTextSize(textSize);

        float textY = (radius - temp) / 5 + temp + centerY;
        Rect textRect = new Rect();
        todayAirValuePaint.getTextBounds(df.format(proportion * 100) + "%", 0,
                (df.format(proportion * 100) + "%").length(), textRect);
        int textWidth = textRect.width();
        int textHeight = textRect.height();
        //左右两边加减高度的1/3,上下加减1/10。
        Rect rect = new Rect((int) (textX - textWidth / 2 - textHeight / 3), (int) (textY - textHeight - textHeight / 8), (int) (textX + textWidth / 2 + textHeight / 4), (int) (textY + textHeight / 8));
        textbackground.setBounds(rect);//为文字设置背景
        textbackground.draw(canvas);//画入画布中

        canvas.drawText(df.format(proportion * 100) + "%", textX - textWidth / 2, textY, todayAirValuePaint);

        if (first) {
            ValueAnimator animator = ValueAnimator.ofObject(new TypeEvaluator() {
                @Override
                public Object evaluate(float fraction, Object startValue, Object endValue) {
                    return fraction * ((int) endValue - (int) startValue);
                }
            }, 0, proportionValue);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float angle1 = (float) animation.getAnimatedValue();
               //     Log.i("angle1", "angle1:=" + angle1);
                    angle = startAngle + angle1;
                    nowValue = (int) (angle1 / 260 * totalValue);

                    invalidate();
                }
            });
            animator.setDuration(2000);
            animator.start();
            first = false;
        }

    }

    //按比例缩放打下
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measure(widthMeasureSpec, true);
        int height = measure(heightMeasureSpec, true);
        //取小的那个值
        width = Math.min(width, height);
        setMeasuredDimension(width, width);


    }

    //高度大于宽度的时候就会是正方形。
    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }
}
