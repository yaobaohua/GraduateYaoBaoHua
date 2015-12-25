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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.NativeVideoModel;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.MyApplication;
import com.yaobaohua.graduateyaobaohua.ui.adapter.CommonAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyNativeVideoAdapter;
import com.yaobaohua.graduateyaobaohua.ui.adapter.ViewHolder;
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
import java.util.List;

/**
 * @Author yaobaohuae
 * @CreatedTime 2015/12/22 22：32
 * @DESC :
 */
@ContentView(R.layout.act_native_video)
public class NativeVideoActivity extends BaseActivity {

    @ViewInject(R.id.tv_title)
    private TextView tvTitle;
    @ViewInject(R.id.lv_native)
    private ListView lvNative;
    private ArrayList<NativeVideoModel> listData;

    File dir;//用于递归扫描本地视频


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        tvTitle.setText("本地视频");
        initListData();
        try {
            boolean isFirst = (boolean) SPUtils.get(this, Constants.IS_FIRST_NATIVE, false);
            showToast(isFirst + "");
            if ((isFirst) && (MyApplication.getDbManager().selector(NativeVideoModel.class).findAll() != null)) {
                List<NativeVideoModel> models = MyApplication.getDbManager().selector(NativeVideoModel.class).findAll();
                setAdapter(models);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void setAdapter(final List<NativeVideoModel> models) {
        if (models.isEmpty())
            return;

        final MyNativeVideoAdapter adapter1 = new MyNativeVideoAdapter(this, models);
        lvNative.setAdapter(adapter1);


        lvNative.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NativeVideoActivity.this, MyPlayActivity.class);
                if (!models.get(position).getVideoPath().isEmpty() && new File(models.get(position).getVideoPath()).exists()) {
                    intent.putExtra("video",new Video("models.get(position).getVideoName()","33",models.get(position).getVideoPath(),"333","2"));
                    startActivity(intent);
                } else {
                    try {
                        NativeVideoModel data = MyApplication.getDbManager().selector(NativeVideoModel.class).where("videoPath", "=", models.get(position).getVideoPath()).findFirst();
                        MyApplication.getDbManager().delete(data);
                        models.remove(position);
                        adapter1.notifyDataSetChanged();

                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        lvNative.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final File file = new File(models.get(position).getVideoPath());

                if (file.exists()) {
                    DialogManager.createYesNoWarningDialog(NativeVideoActivity.this, 1, null, "确定要删除该文件吗？", "确定", "取消", new DialogManager.EnterOrBackDialogListener() {
                        @Override
                        public void onWarningDialogOK(int id) {
                            if (file.delete()) {
                                ToastUtils.show(NativeVideoActivity.this, "删除成功", 2);
                                models.remove(models.get(position));
                                adapter1.notifyDataSetChanged();
                                try {
                                    NativeVideoModel data = MyApplication.getDbManager().selector(NativeVideoModel.class).where("videoPath", "=", models.get(position).getVideoPath()).findFirst();
                                    MyApplication.getDbManager().delete(data);
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onWarningDialogCancel(int id) {

                        }
                    }).show();
                } else {
                    try {
                        NativeVideoModel data = MyApplication.getDbManager().selector(NativeVideoModel.class).where("videoPath", "=", models.get(position).getVideoPath()).findFirst();
                        MyApplication.getDbManager().delete(data);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    models.remove(position);
                    adapter1.notifyDataSetChanged();
                }
                return true;
            }
        });
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

    private void initListData() {
        listData = new ArrayList<>();
        listData.clear();
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory();
        }

    }


    private Handler mHandler = new MyHandler(this);

    public static class MyHandler extends Handler {

        private final WeakReference<NativeVideoActivity> mActivity;

        public MyHandler(NativeVideoActivity activity) {
            mActivity = new WeakReference<NativeVideoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final NativeVideoActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 10:
                        final ArrayList<NativeVideoModel> listData = (ArrayList<NativeVideoModel>) msg.obj;
                        final MyNativeVideoAdapter adapter = new MyNativeVideoAdapter(mActivity.get(), listData);
                        activity.lvNative.setAdapter(adapter);
                        for (NativeVideoModel model : listData) {
                            try {
                                MyApplication.getDbManager().save(model);
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }
                        SPUtils.put(mActivity.get(), Constants.IS_FIRST_NATIVE, true);
                        DialogManager.disDialog();
                        activity.lvNative.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(mActivity.get(), MyPlayActivity.class);
                                intent.putExtra("video",new Video(listData.get(position).getVideoName(),"33",listData.get(position).getVideoPath(),"333","2"));
                                intent.putExtra("isNative",true);
                                activity.startActivity(intent);
                            }
                        });
                        activity.lvNative.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                                final File file = new File(listData.get(position).getVideoPath());
                                if (file.exists()) {

                                    DialogManager.createYesNoWarningDialog(mActivity.get(), 1, null, "确定要删除该文件吗？", "确定", "取消", new DialogManager.EnterOrBackDialogListener() {
                                        @Override
                                        public void onWarningDialogOK(int id) {
                                            file.delete();
                                            ToastUtils.show(mActivity.get(), "删除成功", 2);
                                            listData.remove(listData.get(position));
                                            adapter.notifyDataSetChanged();
                                            try {
                                                NativeVideoModel data = MyApplication.getDbManager().selector(NativeVideoModel.class).where("videoPath", "=", listData.get(position).getVideoPath()).findFirst();
                                                MyApplication.getDbManager().delete(data);
                                            } catch (DbException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onWarningDialogCancel(int id) {

                                        }
                                    }).show();


                                } else {
                                    try {
                                        NativeVideoModel data = MyApplication.getDbManager().selector(NativeVideoModel.class).where("videoPath", "=", listData.get(position).getVideoPath()).findFirst();
                                        MyApplication.getDbManager().delete(data);
                                    } catch (DbException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return true;
                            }
                        });

                        break;

                    default:
                        break;
                }
            }
        }
    }

    class MyThread implements Runnable {
        ArrayList<NativeVideoModel> listData;

        public MyThread(ArrayList<NativeVideoModel> listData) {
            this.listData = listData;
        }

        @Override
        public void run() {
            listData.clear();
            tempId = 0;
            listData = printFile(dir, 1);

            Message msg = Message.obtain();
            msg.what = 10;
            msg.obj = listData;
            mHandler.sendMessage(msg);
        }

    }

    private int tempId = 0;

    public ArrayList<NativeVideoModel> printFile(File dir, int tab) {


        if (dir != null && dir.isDirectory()) {
            if (dir.listFiles() != null) {
                File[] next = dir.listFiles();
                if (next != null && next.length != 0) {
                    for (int i = 0; i < next.length; i++) {
                        if (next[i] != null && next[i].isFile()) {
                            if (next[i].getName().endsWith(".mp4") || next[i].getName().endsWith(".rmvb") || next[i].getName().endsWith(".avi") || next[i].getName().endsWith(".flv")) {
                                NativeVideoModel model = new NativeVideoModel("" + tempId++, next[i].getName(), next[i].getAbsolutePath(),
                                        "", FileSizeFormatUtils.formatSize(next[i].length()));
                                listData.add(model);
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
