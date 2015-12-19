package com.yaobaohua.graduateyaobaohua.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;


import com.yaobaohua.graduateyaobaohua.model.RateDate;
import com.yaobaohua.graduateyaobaohua.utils.ScreenUtils;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by yaobaohua on 2015/12/7 0007. Email 2584899504@qq.com
 */
public class LineChartView extends View {
    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private Paint gridPaint;

    private Paint textPaint;

    private Paint ratePaint;

    private Paint textWithBGPaint;


    private Paint gridBgPaing;

    private int screenWidth;
    /**
     * 离上方和左侧的距离为每格宽度的一半。
     */
    private int marginLeftAndTop;

    public ArrayList<RateDate> mDatas;

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {

        mDatas = new ArrayList<RateDate>();

        screenWidth = ScreenUtils.getScreenW(getContext());
        /**
         * 每格宽度为屏幕的1/7,高度为宽度的一半; 离上方和左侧的距离为每格宽度的一半。
         */
        everyWidth = screenWidth / 7;
        everyHeigt = everyWidth / 2;

        marginLeftAndTop = everyWidth / 2;

        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#d1d1d1"));
        gridPaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#FFFD7201"));
        textPaint.setTextSize(18);
        textPaint.setAntiAlias(true);

        ratePaint = new Paint();
        ratePaint.setColor(Color.parseColor("#FFff0000"));
        ratePaint.setAntiAlias(true);
        ratePaint.setStrokeWidth(10);
        ratePaint.setStyle(Paint.Style.STROKE);


        LinearGradient shader = new LinearGradient(0, 0, everyWidth * 6, 0,
                new int[]{Color.YELLOW, Color.RED}, null, Shader.TileMode.CLAMP);
        ratePaint.setShader(shader);

        textWithBGPaint = new Paint();
        textWithBGPaint.setColor(Color.parseColor("#ffffff"));
        textWithBGPaint.setTextSize(18);


        gridBgPaing = new Paint();
        gridBgPaing.setColor(Color.parseColor("#fffdf8"));
    }

    private int everyWidth;// 每格宽度
    private int everyHeigt;// 每格高度

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackGroud(canvas);

        if (mDatas.size() < 7 || mDatas == null) {
            ToastUtils.show(getContext(), "数据不完整...", Toast.LENGTH_LONG);
        } else {

            /**
             * 横线
             */
            for (int i = 0; i < 7; i++) {
                if (i != 6) {
                    canvas.drawLine(marginLeftAndTop + everyWidth * 2 / 3,
                            marginLeftAndTop + i * everyHeigt, screenWidth,
                            marginLeftAndTop + i * everyHeigt, gridPaint);
                } else {
                    canvas.drawLine(marginLeftAndTop, i * everyHeigt
                            + marginLeftAndTop, screenWidth, marginLeftAndTop + i
                            * everyHeigt, gridPaint);
                }
            }
            /**
             * 开始解决y轴上的值 首先得到七日化利率最大和最小值。
             *
             */
            float maxRate = mDatas.get(0).getRate();
            float minRate = mDatas.get(0).getRate();

            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).getRate() > maxRate) {
                    maxRate = mDatas.get(i).getRate();
                }
                if (mDatas.get(i).getRate() < minRate) {
                    minRate = mDatas.get(i).getRate();
                }
            }

            //刻度，间距（y2-y1的值）
            float scale = 0.015f;
            //规定年率的范围在4个格之内，
            int number = (int) ((maxRate - minRate) / (scale * 4));
            if (number > 0) {
                scale = scale * (number + 1);
            }
            int num = (int) (minRate / scale);
            float minY = num * scale;
            /**
             * 每一个间距的值
             */
            float everyValue = scale;
            DecimalFormat df = new DecimalFormat("###.000");

            /**
             * 画文字Y轴上
             */
            for (int i = 0; i < 7; i++) {
                Rect textRect = new Rect();
                textPaint.getTextBounds(df.format(minY + i * scale) + "",
                        0, (df.format(minY + i * scale) + "").length(),
                        textRect);
                int textHeight = textRect.height();
                if (i != 6) {
                    canvas.drawText(df.format(minY + i * scale) + "",
                            marginLeftAndTop + everyHeigt / 3, marginLeftAndTop + (5 - i)
                                    * everyHeigt + textHeight / 2, textPaint);
                }
            }
            /**
             * 竖线
             */
            for (int j = 0; j < 7; j++) {
                canvas.drawLine(marginLeftAndTop + j * everyWidth,
                        marginLeftAndTop / 2, marginLeftAndTop + j * everyWidth,
                        marginLeftAndTop + 6 * everyHeigt, gridPaint);
                /**
                 * 文字居中 倒是候有具体数字的时候可以把代码提出
                 */
                Rect textRect = new Rect();
                textPaint.getTextBounds(mDatas.get(j).getDateString(), 0,
                        mDatas.get(j).getDateString().length(), textRect);
                int textWidth = textRect.width();
                canvas.drawText(mDatas.get(j).getDateString(), marginLeftAndTop + j
                        * everyWidth - textWidth / 2, 7 * everyHeigt - everyHeigt
                        / 3 + marginLeftAndTop, textPaint);
            }
            Path path = new Path();
            for (int i = 0; i < 7; i++) {

                float y1 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                float x1 = marginLeftAndTop + (i) * everyWidth;
                if (i == 0) {
                    path.moveTo(x1, y1);

                } else {
                    path.lineTo(x1, y1);
                }
                canvas.drawPath(path, ratePaint);
                if (i == 6) {
                    float y2 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                    /**
                     * 画文字背景图
                     */
                    Rect textRect = new Rect();
                    textPaint.getTextBounds(
                            df.format(mDatas.get(6).getRate()) + "", 0,
                            (df.format(mDatas.get(6).getRate()) + "").length(),
                            textRect);
                    int textHeight = textRect.height();
                    int textWidth = textRect.width();

                    RectF oval3 = new RectF(marginLeftAndTop + i * everyWidth
                            - textWidth, y2
                            - everyHeigt, marginLeftAndTop + i * everyWidth
                            - textWidth / 2 + textWidth * 3 / 2,
                            y2 - everyHeigt / 2 + textHeight);// 设置个新的长方形

                    canvas.drawRoundRect(oval3, 10, 15, textPaint);

                    /**
                     * 画文字。
                     */
                    canvas.drawText(df.format(mDatas.get(i).getRate()) + "",
                            marginLeftAndTop + i * everyWidth - textWidth / 2,
                            y2 - everyHeigt / 2,
                            textWithBGPaint);

                }


                if (i == 6) {
                    float y2 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                    /**
                     * 画外层的小圆点。
                     */
                    canvas.drawCircle(marginLeftAndTop + i * everyWidth,
                            y2, 12, textPaint);
                    /**
                     * 内层
                     */
                    canvas.drawCircle(marginLeftAndTop + i * everyWidth,
                            y2, 6, textWithBGPaint);
                }

            }


        }

    }

    /**
     * 为填充数据预留
     *
     * @param mDatas
     */
    public void fillDateForRateDate(ArrayList<RateDate> mDatas) {
        this.mDatas = mDatas;
        invalidate();
    }


    public void setBackGroud(Canvas canvas) {
        /**
         * 画网格当中的淡色背景。
         */

        for (int i = 1; i < 7; i++) {

            Rect rect = new Rect();
            rect.left = marginLeftAndTop + 1 * everyWidth;

            rect.right = marginLeftAndTop + 2 * everyWidth;

            rect.top = marginLeftAndTop + (i - 1) * everyHeigt;

            rect.bottom = marginLeftAndTop + (i) * everyHeigt;

            canvas.drawRect(rect, gridBgPaing);

        }

        for (int i = 1; i < 7; i++) {

            Rect rect = new Rect();
            rect.left = marginLeftAndTop + 3 * everyWidth;

            rect.right = marginLeftAndTop + 4 * everyWidth;

            rect.top = marginLeftAndTop + (i - 1) * everyHeigt;

            rect.bottom = marginLeftAndTop + (i) * everyHeigt;

            canvas.drawRect(rect, gridBgPaing);

        }

        for (int i = 1; i < 7; i++) {

            Rect rect = new Rect();
            rect.left = marginLeftAndTop + 5 * everyWidth;

            rect.right = marginLeftAndTop + 6 * everyWidth;

            rect.top = marginLeftAndTop + (i - 1) * everyHeigt;

            rect.bottom = marginLeftAndTop + (i) * everyHeigt;

            canvas.drawRect(rect, gridBgPaing);

        }

    }

}
