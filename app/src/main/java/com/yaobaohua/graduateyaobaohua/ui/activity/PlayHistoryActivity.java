package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.db.MyDataBaseHelper;
import com.yaobaohua.graduateyaobaohua.db.VideoDBManager;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.model.VideoInfo;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.VideoAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/25 11：10
 * @DESC :
 */
@ContentView(R.layout.act_play_history)
public class PlayHistoryActivity extends BaseActivity {
    VideoDBManager db;
    @ViewInject(R.id.tv_title)
    private TextView tvTitle;

    private ArrayList<Video> listData;

    @ViewInject(R.id.lv_play_history)
    private ListView lvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("历史记录");
        db = new VideoDBManager(this);
        selectPlayRecord();
    }


    @Override
    protected void onRestart() {
        selectPlayRecord();
        super.onRestart();
    }

    private void setAdapter() {
        final ArrayList<Video> mDatas = new ArrayList<Video>();

        mDatas.clear();
        for (Video video : listData) {
            if (video.getVideo_Played()!=null&&"1".equals(video.getVideo_Played())) {
                mDatas.add(video);
            }
        }
        if (mDatas != null) {
            final VideoAdapter adapter = new VideoAdapter(this, mDatas);
            lvRecord.setAdapter(adapter);
            lvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mDatas.get(position).getVideo_Path().equals("http")) {
                        Intent intent = new Intent(PlayHistoryActivity.this, MyPlayActivity.class);
                        intent.putExtra("video", mDatas.get(position));
                        startActivity(intent);
                    } else {
                        Video video = mDatas.get(position);
                        File file = new File(video.getVideo_Path());
                        if (!file.exists()) {
                            mDatas.remove(video);
                            adapter.notifyDataSetChanged();
                            db.delete(video.getVideo_Id());
                        } else {
                            Intent intent = new Intent(PlayHistoryActivity.this, MyPlayActivity.class);
                            intent.putExtra("video", mDatas.get(position));
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    public void selectPlayRecord() {
        listData=new ArrayList<>();
        listData.clear();
        listData = db.query();
        if (listData != null) {
            setAdapter();
        }


    }


}
