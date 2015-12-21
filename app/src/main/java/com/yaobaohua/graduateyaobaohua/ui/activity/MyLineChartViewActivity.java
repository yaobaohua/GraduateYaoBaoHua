package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;

/**
 * Created by yaobaohua on 2015/12/21 0021.
 * Email 2584899504@qq.com
 * Desc
 */
public class MyLineChartViewActivity extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_linechartview);
        ViewUtils.inject(this);
        setToolbar();
        tvTitle.setText("线形图");


    }
}
