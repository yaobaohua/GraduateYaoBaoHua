package com.yaobaohua.graduateyaobaohua.ui.activity.secondActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yaobaohua.graduateyaobaohua.R;
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
public class SecondHomeActivity extends BaseActivity {


    @ViewInject(R.id.radio_group)
    RadioGroup radioGroup;

    @ViewInject(R.id.rb_a)
    RadioButton rb_index;


    @ViewInject(R.id.layout_frame)
    FrameLayout layout_frame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * 当Activity已经初始化完毕时
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        rb_index.setChecked(true);
    }

    @Event(value = {R.id.radio_group}, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedEvent(RadioGroup group, int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.rb_a:
                index = 10;
                break;
            case R.id.rb_b:
                index = 20;
                break;
            case R.id.rb_c:
                index = 30;
                break;
            case R.id.rb_d:
                index = 40;
                break;
        }
        Fragment fragment =
                (Fragment) fragmentAdapter.instantiateItem(layout_frame, index);

        fragmentAdapter.setPrimaryItem(layout_frame, 0, fragment);
        fragmentAdapter.finishUpdate(layout_frame);
    }


    FragmentStatePagerAdapter fragmentAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 10:
                    return new SecondHomeFragment();
                case 20:
                    return new SecondCareFragment();
                case 30:
                    return new SecondFriendFragment();
                case 40:
                    return new SecondMyFragment();
                default:
                    return new SecondHomeFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

    };


}
