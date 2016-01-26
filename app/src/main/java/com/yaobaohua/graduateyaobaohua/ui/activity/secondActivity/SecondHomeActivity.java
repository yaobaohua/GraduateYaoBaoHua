package com.yaobaohua.graduateyaobaohua.ui.activity.secondActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment.SecondCareFragment;
import com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment.SecondFriendFragment;
import com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment.SecondHomeFragment;
import com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment.SecondMyFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 16：11
 * @DESC :
 */
@ContentView(R.layout.act_secont_home)
public class SecondHomeActivity extends BaseActivity implements SecondHomeFragment.onClick2FriendFragmentListener {
    @ViewInject(R.id.rb_c)
    RadioButton rb_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SecondHomeFragment homeFragment = new SecondHomeFragment();
        changeFragment(homeFragment);
    }


    @Event(value = {R.id.radio_group}, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedEvent(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_a:
                SecondHomeFragment homeFragment = new SecondHomeFragment();
                switchContent(currentFragment, homeFragment);
                break;
            case R.id.rb_b:
                SecondCareFragment careFragment = new SecondCareFragment();
                switchContent(currentFragment, careFragment);
                break;
            case R.id.rb_c:
                SecondFriendFragment friendFragment = new SecondFriendFragment();
                switchContent(currentFragment, friendFragment);
                break;
            case R.id.rb_d:
                SecondMyFragment myFragment = new SecondMyFragment();
                switchContent(currentFragment, myFragment);
                break;
        }
    }

    Fragment currentFragment;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        currentFragment = fragment;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_frame, fragment);
        transaction.commit();


    }


    public void switchContent(Fragment from, Fragment to) {
        if (currentFragment != to) {
            currentFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                    android.R.anim.fade_in, R.anim.out_to_left);
            ;
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.layout_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


    @Override
    public void switch2Friend(String name, String tag) {
        SecondFriendFragment friendFragment = SecondFriendFragment.newInstance(name, tag);
        switchContent(currentFragment, friendFragment);
        rb_c.setChecked(true);
    }

    @Override
    public void switch2FriendWithVideo(Video video) {
        SecondFriendFragment friendFragment = SecondFriendFragment.newInstanceByVideo(video);
        switchContent(currentFragment, friendFragment);
        rb_c.setChecked(true);
    }
}
