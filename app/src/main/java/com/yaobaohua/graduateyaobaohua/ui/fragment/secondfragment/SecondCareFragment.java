package com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment;


import android.os.Bundle;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.activity.CircleProgressActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyLineChartViewActivity;
import com.yaobaohua.graduateyaobaohua.ui.widget.CircleProgressView;
import com.yaobaohua.graduateyaobaohua.utils.UtilJump;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 17：04
 * @DESC :
 */
@ContentView(R.layout.frag_sec_care)
public class SecondCareFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



     @Event(value = {R.id.btn_weather_view_second_frag_care,R.id.btn_pay_line_chart_view_second_frag_care})
     private void onClick(View view){
         switch (view.getId()){
             case R.id.btn_pay_line_chart_view_second_frag_care:
                 UtilJump.jump2Act(getActivity(), MyLineChartViewActivity.class);
                 break;
             case R.id.btn_weather_view_second_frag_care:
                 UtilJump.jump2Act(getActivity(), CircleProgressActivity.class);
                 break;
         }
     }

}
