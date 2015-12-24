package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.fragment.MainFragment;
import com.yaobaohua.graduateyaobaohua.ui.fragment.MyFragment;
import com.yaobaohua.graduateyaobaohua.ui.fragment.NetPlayFragment;
import com.yaobaohua.graduateyaobaohua.ui.widget.TabTextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21 16/16
 * @DESC : 主界面
 */
@ContentView(R.layout.act_home)
public class HomeActivity extends BaseActivity {

    @ViewInject(R.id.id_viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.id_main)
    private TabTextView first;
    private List<TabTextView> tabTextViews;

    @ViewInject(R.id.id_two)
    private TabTextView two;
    @ViewInject(R.id.id_three)
    private TabTextView three;
    private Menu mMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setLeftButton(-1);
        tabTextViews = new ArrayList<>();
        tabTextViews.add(first);
        tabTextViews.add(two);
        tabTextViews.add(three);

        first.setIconAlpha(1.0f);

        MainFragment mainFragment = new MainFragment();
        NetPlayFragment netPlayFragment = new NetPlayFragment();
        MyFragment myFragment = new MyFragment();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(mainFragment);
        fragments.add(netPlayFragment);
        fragments.add(myFragment);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (positionOffset > 0) {
                    tabTextViews.get(position).setIconAlpha(1 - positionOffset);
                    tabTextViews.get(position + 1).setIconAlpha(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    setAppTitle(getResources().getString(R.string.tab_main));
                    mMenu.getItem(0).setVisible(false);
                    mMenu.getItem(1).setVisible(false);
                    viewPager.setCurrentItem(0, false);
                    first.setIconAlpha(1.0f);
                }
                if (position == 1) {
                    setAppTitle("直播");
                    mMenu.getItem(0).setVisible(false);
                    mMenu.getItem(1).setVisible(false);
                    viewPager.setCurrentItem(1, false);
                    two.setIconAlpha(1.0f);
                }

                if (position == 2) {
                    setLeftTitle("我的");
                    mMenu.getItem(0).setVisible(true);
                    mMenu.getItem(1).setVisible(true);
                    viewPager.setCurrentItem(2, false);
                    three.setIconAlpha(1.0f);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Event(value = {R.id.id_main, R.id.id_two, R.id.id_three}, type = View.OnClickListener.class)
    private void onClick(View v) {

        resetOtherTabs();
        switch (v.getId()) {
            case R.id.id_main:
                setAppTitle(getResources().getString(R.string.tab_main));
                mMenu.getItem(0).setVisible(false);
                mMenu.getItem(1).setVisible(false);
                viewPager.setCurrentItem(0, false);
                first.setIconAlpha(1.0f);

                break;
            case R.id.id_two:
                setAppTitle("直播");
                mMenu.getItem(0).setVisible(false);
                mMenu.getItem(1).setVisible(false);
                viewPager.setCurrentItem(1, false);
                two.setIconAlpha(1.0f);
                break;
            case R.id.id_three:
                setLeftTitle("我的");
                mMenu.getItem(0).setVisible(true);
                mMenu.getItem(1).setVisible(true);
                viewPager.setCurrentItem(2, false);
                three.setIconAlpha(1.0f);
                break;
        }
    }

    @Override
    public void leftTitleOnclick(View v) {
        super.leftTitleOnclick(v);
    }

    private void resetOtherTabs() {
        for (int i = 0; i < tabTextViews.size(); i++) {
            tabTextViews.get(i).setIconAlpha(0f);
        }
    }

    private long lastBack;

    @Override
    public void onBackPressed() {
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit));
            lastBack = nowCurrent;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.activity_my, menu);
        mMenu.getItem(0).setVisible(false);
        mMenu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
