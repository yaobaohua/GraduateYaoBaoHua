package com.yaobaohua.graduateyaobaohua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.api.IBase;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.ui.dialog.DialogManager;
import com.yaobaohua.graduateyaobaohua.utils.NetworkJudgment;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import org.xutils.x;

public class BaseActivity extends AppCompatActivity implements IBase, Toolbar.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MyApplication.getInstance().getActivityManager().pushActivity(this);//堆如activitymanager管理栈中
        if (!isInternetConnected())
            showToast(getResources().getString(R.string.toast_isinternet));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().getActivityManager().popActivity(this);//销毁时从管理栈中移除
    }

    public void showToast(String text) {
        ToastUtils.showShort(this, text);
    }//初始化toast提示

    private Toolbar toolbar;
    private TextView tvTitle;
    private ImageView imgTopic;

    private TextView tvLeftTitle;

    /**
     * 配合XML文件，设置toolbar
     * 在每个需要标题的XML中引用    <include layout="@layout/activity_title"/>
     */
    public void setToolbar() {
        setToolbar(getString(R.string.tab_main));
    }

    public void setToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        imgTopic = (ImageView) toolbar.findViewById(R.id.img_Topic);
        tvTitle.setText(title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitleOclic(v);
            }
        });
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.action_line_v);
        toolbar.setNavigationIcon(R.drawable.app_back);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftOnClick();
            }
        });
    }

    /**
     * title的点击事件
     *
     * @param v
     */
    public void setTitleOclic(View v) {
    }

    /**
     * 左侧title
     *
     * @param title
     */
    public void setLeftTitle(String title) {
        tvLeftTitle = (TextView) toolbar.findViewById(R.id.tv_left_title);
        tvTitle.setText("");
        tvLeftTitle.setText(title);
        tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftTitleOnclick(v);
            }
        });
    }

    /**
     * 左侧title的点击事件
     *
     * @param v
     */
    public void leftTitleOnclick(View v) {
    }

    public ImageView getImgTopic() {
        return imgTopic;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setAppTitle(String title) {
        tvTitle.setText(title);
        if (tvLeftTitle != null) tvLeftTitle.setText("");

    }

    /**
     * 更换左侧图标
     *
     * @param left 为－1时，隐藏图标
     */
    public void setLeftButton(int left) {
        if (left == -1) {
            toolbar.setLogo(null);
            toolbar.setNavigationIcon(null);
        } else {
//            toolbar.setLogo(R.drawable.action_line_v);
            toolbar.setNavigationIcon(left);
        }
    }

    /**
     * 左侧按钮的点击事件，默认关闭，如需重写，把继承的super删掉
     */
    public void leftOnClick() {
        finish();
    }

    /**
     * 右侧menu的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public String getUserName() {

        return (String) SPUtils.get(this, Constants.USERNAME, "");
    }

    @Override
    public void showLoading() {
        DialogManager.showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogManager.disDialog();
    }


    /**
     * 判断是否有网络连接,没有返回false
     */
    @Override
    public boolean isInternetConnected() {
        return NetworkJudgment.isConnected(this);
    }

    /**
     * 获取用户在线状态
     */
    @Override
    public boolean getUserOnlineState() {
        return (boolean) SPUtils.get(this, Constants.ISONLINE, false);
    }

    /**
     * 设置用户在线状态
     */
    @Override
    public void setUserOnlineState(boolean isOnline) {
        SPUtils.put(this, Constants.ISONLINE, true);
    }


    @Override
    public void initViews() {
        //初始化布局
    }

    @Override
    public void initData() {
        //数据处理
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
    }
}