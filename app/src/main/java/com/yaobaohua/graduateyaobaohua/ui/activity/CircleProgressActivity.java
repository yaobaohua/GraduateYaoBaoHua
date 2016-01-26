package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.widget.CircleProgressView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 14：54
 * @DESC :
 */
@ContentView(R.layout.activity_circle_progress)
public class CircleProgressActivity  extends BaseActivity
{
    @ViewInject(R.id.circle_circle_progress)
    private CircleProgressView circleProgressView;

    @ViewInject(R.id.circle_circle_progress_2)
    private CircleProgressView circleProgressView_2;
    @ViewInject(R.id.circle_circle_progress_3)
    private CircleProgressView circleProgressView_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        circleProgressView.setNowValue(1000);
        circleProgressView.setTotalValue(4000);

        circleProgressView_2.setNowValue(1000);
        circleProgressView_2.setTotalValue(4000);
        circleProgressView_3.setNowValue(1000);
        circleProgressView_3.setTotalValue(4000);
    }
}
