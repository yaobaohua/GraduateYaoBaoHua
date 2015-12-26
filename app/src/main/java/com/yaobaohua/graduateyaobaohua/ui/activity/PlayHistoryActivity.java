package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.db.VideoDBManager;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyNativeVideoAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.VideoAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/25 11：10
 * @DESC :
 */
@ContentView(R.layout.act_play_history)
public class PlayHistoryActivity extends BaseActivity {

    VideoDBManager db;

    private List<Video> listData;

    @ViewInject(R.id.lv_play_history)
    private ListView lvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new VideoDBManager(this);
        selectPlayRecord();
    }

    private void setAdapter() {
        lvRecord.setAdapter(new VideoAdapter(this, listData));
    }

    public void selectPlayRecord() {
        listData = db.queryAll();
        if (listData != null) {
            setAdapter();
        }


    }


}
