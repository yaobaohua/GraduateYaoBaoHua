package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadInfo;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadManager;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadService;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadState;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadViewHolder;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/23 18：50
 * @DESC :
 */
@ContentView(R.layout.act_my_down_loads)
public class MyDownLoadVideoActivity extends BaseActivity {

    @ViewInject(R.id.lv_my_download_video_download)
    private ListView downloadList;


    private DownloadManager downloadManager;
    private DownloadListAdapter downloadListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadManager = DownloadService.getDownloadManager();
        downloadListAdapter = new DownloadListAdapter();
        downloadList.setAdapter(downloadListAdapter);
    }

    private class DownloadListAdapter extends BaseAdapter {

        private Context mContext;
        private final LayoutInflater mInflater;

        private DownloadListAdapter() {
            mContext = getBaseContext();
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            if (downloadManager == null) return 0;
            return downloadManager.getDownloadListCount();
        }

        @Override
        public Object getItem(int i) {
            return downloadManager.getDownloadInfo(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DownloadItemViewHolder holder = null;
            DownloadInfo downloadInfo = downloadManager.getDownloadInfo(i);
            if (view == null) {
                view = mInflater.inflate(R.layout.item_downloading_list, null);
                holder = new DownloadItemViewHolder(view, downloadInfo);
                view.setTag(holder);
                holder.refresh();
            } else {
                holder = (DownloadItemViewHolder) view.getTag();
                holder.update(downloadInfo);
            }

            if (downloadInfo.getState().value() < DownloadState.FINISHED.value()) {
                try {
                    downloadManager.startDownload(
                            downloadInfo.getUrl(),
                            downloadInfo.getLabel(),
                            downloadInfo.getFileSavePath(),
                            downloadInfo.isAutoResume(),
                            downloadInfo.isAutoRename(),
                            holder);
                } catch (DbException ex) {
                    Toast.makeText(x.app(), "添加下载失败", Toast.LENGTH_LONG).show();
                }
            }

            return view;
        }
    }

    public class DownloadItemViewHolder extends DownloadViewHolder {
        @ViewInject(R.id.download_state)
        TextView state;
        @ViewInject(R.id.download_pb)
        ProgressBar progressBar;
        @ViewInject(R.id.download_stop_btn)
        Button stopBtn;
        @ViewInject(R.id.rl_download_tip_download)
        RelativeLayout rlDownloadTip;
        @ViewInject(R.id.ll_download_item)
        LinearLayout llDownloadItem;

        @ViewInject(R.id.tv_download_name)
        TextView tvDownLoadName;
        @ViewInject(R.id.tv_download_size)
        TextView tvDownLoadSize;
        @ViewInject(R.id.tv_download_count)
        TextView tvDownLoadCount;

        public DownloadItemViewHolder(View view, DownloadInfo downloadInfo) {
            super(view, downloadInfo);
            refresh();
        }

        @Event(R.id.download_stop_btn)
        private void toggleEvent(View view) {
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case ERROR:
                case STOPPED:
                    try {
                        downloadManager.startDownload(
                                downloadInfo.getUrl(),
                                downloadInfo.getLabel(),
                                downloadInfo.getFileSavePath(),
                                downloadInfo.isAutoResume(),
                                downloadInfo.isAutoRename(),
                                this);
                    } catch (DbException ex) {
                        Toast.makeText(x.app(), "添加下载失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case FINISHED:
                    Toast.makeText(x.app(), "已经下载完成", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

        @Event(R.id.download_remove_btn)
        private void removeEvent(View view) {
            try {
                downloadManager.removeDownload(downloadInfo);
                downloadListAdapter.notifyDataSetChanged();
            } catch (DbException e) {
                Toast.makeText(x.app(), "移除任务失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void update(DownloadInfo downloadInfo) {
            super.update(downloadInfo);

            refresh();
        }

        @Override
        public void onWaiting() {
            refresh();
        }

        @Override
        public void onStarted() {
            refresh();
        }

        @Override
        public void onLoading(long total, long current) {
            refresh();
        }

        @Override
        public void onSuccess(File result) {
            refresh();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            refresh();
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            refresh();
        }

        public void refresh() {
            state.setText(downloadInfo.getState().toString());
            progressBar.setProgress(downloadInfo.getProgress());

            tvDownLoadName.setText(downloadInfo.getLabel());

            stopBtn.setVisibility(View.VISIBLE);
            stopBtn.setText("停止");
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    stopBtn.setText("停止");
                    break;
                case ERROR:
                case STOPPED:
                    stopBtn.setText("开始下载");
                    break;
                case FINISHED:
                    stopBtn.setVisibility(View.INVISIBLE);
                    rlDownloadTip.setVisibility(View.INVISIBLE);
                    llDownloadItem.removeView(rlDownloadTip);
                    downloadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            showToast(downloadInfo.getFileSavePath());

                            Intent intent = new Intent(getApplicationContext(), MyPlayActivity.class);

                            intent.putExtra("video", new Video(downloadInfo.getLabel(), "33", downloadInfo.getFileSavePath(), "333", "2","1"));

                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    stopBtn.setText("开始下载");
                    break;
            }
        }
    }


}
