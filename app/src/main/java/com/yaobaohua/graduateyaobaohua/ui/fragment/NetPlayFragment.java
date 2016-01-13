package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.LiveJson;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyPlayActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
import com.yaobaohua.graduateyaobaohua.ui.widget.listview.XListView;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21
 * @DESC ：直播界面
 */
@ContentView(R.layout.frag_netplay)
public class NetPlayFragment extends BaseFragment {

    @ViewInject(R.id.list_live_zhibo)
    private ListView lvLive;
    CommonAdapter<Video> adapter;

    private ArrayList<Video> mDatas;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvLive.setDivider(getResources().getDrawable(R.drawable.div_list));
        //获取json数据
        setJson(LiveJson.jsonResult);
        //设置适配器
        setLiveAdapter();


    }

    @ViewInject(R.id.ed_live)
    private EditText edPath;

    @Event(value = R.id.btn_start)
    private void onClick(View view) {
        String url = edPath.getText().toString().trim();
        if(url!=null&&!url.equals("")) {
            Video video = new Video("",
                    "0",
                    url, "1", "0", "0", "1");
            Intent intent = new Intent(getActivity(), MyPlayActivity.class);
            intent.putExtra("video", video);
            startActivity(intent);
        }
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

                intent.putExtra("video",(Video)parent.getAdapter().getItem(position));
              //  ToastUtils.showLong(getActivity(),position+"   "+mDatas.get(position).toString());
                startActivity(intent);
            }
        });
    }



    private void setJson(String result) {
        mDatas = new ArrayList<>();
        mDatas.clear();
        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject
                    .getJSONArray("data");
            Video video;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);

                String name = obj.getString("name");
                String videoUrl = obj.getString("videoUrl");
                String logo=obj.getString("logo");

                video = new Video(name,
                        "0",
                        videoUrl, "1", "0", "0", "1");
                video.setVideo_previewImg(logo);
                mDatas.add(video);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
