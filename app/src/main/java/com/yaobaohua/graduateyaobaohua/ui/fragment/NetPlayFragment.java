package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyPlayActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
import com.yaobaohua.graduateyaobaohua.ui.widget.listview.XListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21
 * @DESC ：直播界面
 */
@ContentView(R.layout.frag_netplay)
public class NetPlayFragment extends BaseFragment {

    @ViewInject(R.id.list_live_zhibo)
    private XListView lvLive;
    CommonAdapter<Video> adapter;

    private ArrayList<Video> mDatas;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListData();

        setLiveAdapter();


    }

    @ViewInject(R.id.ed_live)
    private EditText edPath;
    @Event(value = R.id.btn_start)
    private void onClick(View view){
        String url = edPath.getText().toString().trim();
        Video video = new Video("浙江卫视",
                "0",
                url, "1", "0", "0", "2");
        Intent intent = new Intent(getActivity(), MyPlayActivity.class);
        intent.putExtra("video", video);
        startActivity(intent);
    }


    private void setLiveAdapter() {
        adapter = new CommonAdapter<Video>(getActivity(), mDatas, R.layout.item_zhibo_list) {
            @Override
            public void convert(ViewHolder holder, Video item) {
                holder.setText(R.id.tv_zhibo_name, item.getVideo_Name());
                holder.setImageByUrl(R.id.img_zhibo_logo, item.getVideo_previewImg());
            }
        };
        lvLive.setAdapter(adapter);

        setListener();
    }

    private void setListener() {
        lvLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MyPlayActivity.class);
                intent.putExtra("video", mDatas.get(position));
                startActivity(intent);
            }
        });
    }

    private void initListData() {
        mDatas = new ArrayList<Video>();
        mDatas.clear();

        for (int i = 0; i < 22; i++) {
            String url = "http://www.baitv.com/ext/shhai/shhai2/";
            Video video = new Video("浙江卫视",
                    "0",
                    url, "1", "0", "0", "2");
            //  mDatas.add(new Video("浙江卫视 " + i, url, "http://img.mukewang.com//551e470500018dd806000338.jpg"));
            mDatas.add(video);
        }
    }


}
