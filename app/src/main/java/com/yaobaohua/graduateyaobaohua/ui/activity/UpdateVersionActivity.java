package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/27 14：58
 * @DESC :
 */
@ContentView(R.layout.act_update_version)
public class UpdateVersionActivity extends BaseActivity {
    @ViewInject(R.id.btn_update_version)
    private Button btnUpdate;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("检查更新");
    }

    @Event(value = R.id.btn_update_version)
    private void checkUpdateClick(View view) {
        UmengUpdateAgent.update(this);
        //考虑到用户流量的限制，目前我们默认在Wi-Fi接入情况下才进行自动提醒。
        // 如需要在任意网络环境下都进行更新自动提醒，则请在update调用之前添加以下代码：
        // UmengUpdateAgent.setUpdateOnlyWifi(false)。
    }
}
