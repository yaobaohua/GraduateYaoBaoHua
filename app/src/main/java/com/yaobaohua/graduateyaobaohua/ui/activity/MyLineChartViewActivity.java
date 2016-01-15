package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.RateDate;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.widget.LineChartView;
import com.yaobaohua.graduateyaobaohua.utils.ScreenUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by yaobaohua on 2015/12/21 0021.
 * Email 2584899504@qq.com
 * Desc
 */
@ContentView(R.layout.activity_my_linechartview)
public class MyLineChartViewActivity extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;


    @ViewInject(R.id.ll_line_chart_view)
    private LinearLayout llLineChart;
    @ViewInject(R.id.my_line_chart_view_pocket)
    LineChartView lineChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("线形图");

        /**
         * 日期也不需要我自己写，，因为每天的利率都会对应一个日期。
         */
        ArrayList<RateDate> list = new ArrayList<>();
        list.add(new RateDate(2.33f, "12-25"));
        list.add(new RateDate(2.34f, "12-26"));
        list.add(new RateDate(2.32f, "12-27"));
        list.add(new RateDate(2.37f, "12-28"));
        list.add(new RateDate(2.35f, "12-29"));
        list.add(new RateDate(2.33f, "12-30"));
        list.add(new RateDate(2.39f, "12-31"));


        lineChartView.fillDateForRateDate(list);

        int width = ScreenUtils.getScreenW(this);

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) llLineChart.getLayoutParams();
        lp.height = width / 14 * 8;
        lp.width = width;
        lineChartView.setLayoutParams(lp);
    }
}
