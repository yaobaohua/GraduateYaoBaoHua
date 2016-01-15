package com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment;


import android.os.Bundle;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 17：04
 * @DESC :
 */
@ContentView(R.layout.frag_sec_home)
public class SecondHomeFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null){
            this.getView().setVisibility
                    (menuVisible ? View.VISIBLE : View.GONE);
        }
    }
}
