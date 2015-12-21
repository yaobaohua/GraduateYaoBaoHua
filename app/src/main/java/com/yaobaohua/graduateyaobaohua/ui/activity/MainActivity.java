package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.MyApplication;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyShufflingFigureAdapter;
import com.yaobaohua.graduateyaobaohua.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;


    private DotImageHandler handler = new DotImageHandler(
            new WeakReference<MainActivity>(this));

    @ViewInject(R.id.home_viewPager)
    private ViewPager home_viewPager;

    private ArrayList<String> adPicture = new ArrayList<String>();

    private int[] dots = {R.mipmap.dot_blur, R.mipmap.dot_focus};

    private int dotIndex;

    public int count;

    private LinearLayout home_lin;

    @ViewInject(R.id.copy_of_home_rel)
    private RelativeLayout copy_of_home_rel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        setToolbar();
        tvTitle.setText("首页");
        LogUtils.e(MyApplication.getInstance().getIMEI());
        initLunBoData();


    }


    public void initLunBoData() {
        //第一步,初始化存放图片地址的ArrayList<String>
        adPicture
                .add("http://img.mukewang.com/55237dcc0001128c06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/551de0570001134f06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/5518ecf20001cb4e06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/5513e20600017c1806000338.jpg");
        adPicture
                .add("http://img.mukewang.com/549bda090001c53e06000338.jpg");
        adPicture
                .add("http://img.mukewang.com/547ed1c9000150cc06000338.jpg");

        //2.得到下面小圆圈的数量
        count = adPicture.size();
        //3.给ViewPager设置适配器
        home_viewPager
                .setAdapter(new MyShufflingFigureAdapter(this, adPicture));
        home_viewPager.setOnPageChangeListener(this);
        initPageResoure();
    }


    public void initPageResoure() {
        addDotImage();
        handler.sendEmptyMessageDelayed(DotImageHandler.MSG_UPDATE_IMAGE,
                DotImageHandler.MSG_DELAY);
    }

    public void addDotImage() {
        Context context = this;
        home_lin = new LinearLayout(context);
        ImageView dotIV = null;
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        llp.setLayoutDirection(LinearLayout.HORIZONTAL);
        llp.rightMargin = 20;
        llp.bottomMargin = 20;
        for (int i = 0; i < count; i++) {
            dotIV = new ImageView(context);
            dotIV.setImageResource(dots[0]);
            home_lin.addView(dotIV, llp);
        }
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        copy_of_home_rel.addView(home_lin, rlp);

        dotIndex = 0;
        ((ImageView) home_lin.getChildAt(dotIndex)).setImageResource(dots[1]);
    }

    private void setCurrentPoint(int position) {
        if (position < 0 || position == dotIndex)
            return;
        if (position > count - 1) {
            position %= count;
        }
        ((ImageView) home_lin.getChildAt(dotIndex)).setImageResource(dots[0]);
        ((ImageView) home_lin.getChildAt(position)).setImageResource(dots[1]);
        dotIndex = position;
    }

    /**
     * 实现轮播的Handler类
     */
    private static class DotImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        // 轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        // 使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<MainActivity> weakReference;

        private int currentItem = 0;

        protected DotImageHandler(WeakReference<MainActivity> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity home = weakReference.get();
            if (home == null) {
                // Activity已经回收，无需再处理UI了
                return;
            }
            // 检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (home.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                home.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    home.home_viewPager.setCurrentItem(currentItem);
                    // 准备下次播放
                    home.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
                            MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    // 只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    home.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,
                            MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    // 记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                handler.sendEmptyMessage(DotImageHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                handler.sendEmptyMessageDelayed(DotImageHandler.MSG_UPDATE_IMAGE,
                        DotImageHandler.MSG_DELAY);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentPoint(arg0);
        handler.sendMessage(Message.obtain(handler,
                DotImageHandler.MSG_PAGE_CHANGED, arg0, 0));

    }
}

