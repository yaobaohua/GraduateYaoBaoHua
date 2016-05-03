package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyPlayHistoryVideoAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/29 23：14
 * @DESC :
 */
@ContentView(R.layout.act_play_history)
public class MyPlayHistoryActivity extends BaseActivity {
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    private List<Video> listData;

    @ViewInject(R.id.lv_play_history)
    private ListView lvRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("历史记录");
        selectHistoryPlayedFromNet();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        selectHistoryPlayedFromNet();
    }

    private void selectHistoryPlayedFromNet() {
        String video_userId = (String) SPUtils.get(this, Constants.VIDEO_USER_ID, "");
        if (video_userId != null && !video_userId.equals("")) {
            BmobQuery<Video> query = new BmobQuery<>();
            query.addWhereEqualTo(Constants.VIDEO_USER_ID, video_userId);
            query.findObjects(this, new FindListener<Video>() {
                @Override
                public void onSuccess(List<Video> list) {
                    listData = list;
                    lvRecord.setAdapter(new MyPlayHistoryVideoAdapter(getApplicationContext(), listData));
                    initClick();
                }

                @Override
                public void onError(int i, String s) {
                    showToast("查询失败");
                }
            });
        }

    }

    private void initClick() {
        lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MyPlayHistoryActivity.this,MyPlayActivity.class);
                intent.putExtra("video",listData.get(position));
                startActivity(intent);
            }
        });

    }


}
