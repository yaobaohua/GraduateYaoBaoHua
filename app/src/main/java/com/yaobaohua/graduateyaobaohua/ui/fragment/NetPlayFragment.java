package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.LiveModel;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.widget.listview.XListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21
 * @DESC :
 */
@ContentView(R.layout.frag_netplay)
public class NetPlayFragment extends BaseFragment {

    @ViewInject(R.id.list_live_zhibo)
    private XListView lvLive;


    private ArrayList<LiveModel> mDatas;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListData();

        setLiveAdapter();


    }

    private void setLiveAdapter() {
        lvLive.setAdapter(new CommonAdapter<LiveModel>(getActivity(), mDatas, R.layout.item_zhibo_list) {
            @Override
            public void convert(ViewHolder holder, LiveModel item) {
                holder.setText(R.id.tv_zhibo_name, item.getName());
                holder.setImageByUrl(R.id.img_zhibo_logo, item.getLogoUrl());
            }
        });
    }

    private void initListData() {
        mDatas = new ArrayList<LiveModel>();
        mDatas.clear();
        for (int i = 0; i < 22; i++) {
            mDatas.add(new LiveModel("浙江卫视 " + i, "url", "http://img.mukewang.com//551e470500018dd806000338.jpg"));
        }
    }


}
