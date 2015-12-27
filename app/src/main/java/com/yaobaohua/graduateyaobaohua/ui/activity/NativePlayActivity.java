package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.db.VideoDBManager;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.MyApplication;
import com.yaobaohua.graduateyaobaohua.ui.adapter.VideoAdapter;
import com.yaobaohua.graduateyaobaohua.ui.dialog.DialogManager;
import com.yaobaohua.graduateyaobaohua.utils.FileSizeFormatUtils;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/26 16：08
 * @DESC :
 */
@ContentView(R.layout.act_native_video)
public class NativePlayActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.lv_native)
    private ListView lvNative;

    private ArrayList<Video> listData;

    File dir;//用于递归扫描本地视频
    VideoDBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("本地视频");
        initSecond();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initSecond();
    }

    public void initSecond() {
        listData = new ArrayList<>();
        db = new VideoDBManager(this);
        listData.clear();
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory();
        }
        boolean isFirst = (boolean) SPUtils.get(this, Constants.IS_FIRST_NATIVE, false);
        if (isFirst) {
            /**
             * 初始化listdata
             */
            listData = db.query();
            if (listData != null) {
                setListAdapter(lvNative, listData);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_native, menu);
        return true;
    }

    /**
     * 在这里写扫描的事件。
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan_native:
                new Thread(new MyThread(listData)).start();
                DialogManager.showDialog(this);
                break;
        }
        return super.onMenuItemClick(item);
    }

    private Handler mHandler = new MyHandler(this);

    public class MyHandler extends Handler {

        private final WeakReference<NativePlayActivity> mActivity;

        public MyHandler(NativePlayActivity activity) {
            mActivity = new WeakReference<NativePlayActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final NativePlayActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 10:
                        ArrayList<Video> listData = (ArrayList<Video>) msg.obj;
                        DialogManager.disDialog();
                        SPUtils.put(mActivity.get(), Constants.IS_FIRST_NATIVE, true);
                        for (int i = 0; i < listData.size(); i++) {
                            Video now_video = db.queryAVideoByName(listData.get(i).getVideo_Name());
                            if (now_video != null) {
                                listData.get(i).setVideo_Progress(now_video.getVideo_Progress());
                                listData.get(i).setVideo_Played(now_video.getVideo_Played());
                                listData.get(i).setVideo_Id(now_video.getVideo_Id());
                            }
                            if (db.insert(listData.get(i)) == -1) {

                            }

                        }
                        setListAdapter(activity.lvNative, listData);
                        break;

                    default:
                        break;
                }
            }
        }
    }


    public void setListAdapter(ListView listView, final ArrayList<Video> mDatas) {
        final ArrayList<Video> list = new ArrayList<>();
        for (Video video : mDatas) {
            if (video.getVideo_DownFlag().equals("1")) {
                list.add(video);
            }
        }
        if (list == null) {
            return;
        }
        final VideoAdapter adapter = new VideoAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 路径是否存在，
                 * 存在 播放
                 * 不存在 删除该项，同时在数据库中移除
                 *
                 */
                Intent intent = new Intent(NativePlayActivity.this, MyPlayActivity.class);
                Video video = list.get(position);
                intent.putExtra("video", video);
                File file = new File(video.getVideo_Path());
                if (!file.exists()) {
                    list.remove(video);
                    adapter.notifyDataSetChanged();
                    db.delete(video.getVideo_Id());
                } else {
                    startActivity(intent);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final File file = new File(list.get(position).getVideo_Path());
                if (file.exists()) {
                    DialogManager.createYesNoWarningDialog(NativePlayActivity.this, 1, null, "确定要删除该文件吗？", "确定", "取消", new DialogManager.EnterOrBackDialogListener() {
                        @Override
                        public void onWarningDialogOK(int id) {
                            if (file.delete()) {
                                ToastUtils.show(NativePlayActivity.this, "删除成功", 2);
                                list.remove(list.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onWarningDialogCancel(int id) {

                        }
                    }).show();

                } else {
                    list.remove(list.get(position));
                    adapter.notifyDataSetChanged();
                }

                return true;
            }
        });
    }


    class MyThread implements Runnable {
        ArrayList<Video> listData;

        public MyThread(ArrayList<Video> listData) {
            this.listData = listData;
        }

        @Override
        public void run() {
            listData.clear();
            listData = printFile(dir, 1);
            Message msg = Message.obtain();
            msg.what = 10;
            msg.obj = listData;
            mHandler.sendMessage(msg);
        }

    }

    public ArrayList<Video> printFile(File dir, int tab) {
        if (dir != null && dir.isDirectory()) {
            if (dir.listFiles() != null) {
                File[] next = dir.listFiles();
                if (next != null && next.length != 0) {
                    for (int i = 0; i < next.length; i++) {
                        if (next[i] != null && next[i].isFile()) {
                            if (next[i].getName().endsWith(".mp4") || next[i].getName().endsWith(".rmvb") || next[i].getName().endsWith(".avi") || next[i].getName().endsWith(".flv")) {
                                /**
                                 *    this.video_Name =
                                 *this.video_Size =
                                 *this.video_Path =
                                 *this.video_DownFla
                                 *this.video_Played
                                 *this.video_Progres
                                 *this.video_Type =
                                 */
                                Video video = new Video(next[i].getName(),
                                        FileSizeFormatUtils.formatSize(next[i].length()),
                                        next[i].getAbsolutePath(), "1", "0", "0", "2");
                                listData.add(video);
                            }
                        } else if (next[i] != null && next[i].isDirectory()) {
                            printFile(next[i], tab + 1);
                        }
                    }
                }
            }
        }
        return listData;
    }


}
