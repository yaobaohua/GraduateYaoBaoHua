package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.db.VideoDBManager;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.ui.adapter.MyPlayHistoryVideoAdapter;
import com.yaobaohua.graduateyaobaohua.ui.download.DownloadService;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.VideoView;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/24 10：42
 * @DESC :
 */
@ContentView(R.layout.act_my_play)
public class MyPlayActivity extends BaseActivity implements
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback, MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, OnSeekCompleteListener {

    @ViewInject(R.id.videoview_play)
    private VideoView videoView;

    //左上侧的返回
    @ViewInject(R.id.img_back_play)
    private ImageView imgBack;
    //右上侧的下载
    @ViewInject(R.id.img_download_play)
    private ImageView imgDownLoad;
    //中间的锁按钮
    @ViewInject(R.id.img_lock_screen_play)
    private ImageView imgLockScreen;

    //下方的播放按钮
    @ViewInject(R.id.img_play_play)
    private ImageView imgPlay;
    //下方的下一集按钮
    @ViewInject(R.id.img_next_play)
    private ImageView imgNext;

    //进度条
    @ViewInject(R.id.seekBar_play)
    private SeekBar mSeekBar;
    //当前播放时间
    @ViewInject(R.id.tv_play_time_play)
    private TextView tvPlayTime;
    //视频总时间
    @ViewInject(R.id.tv_total_time_play)
    private TextView tvTotalTime;
    //视频的名称
    @ViewInject(R.id.tv_name_play)
    private TextView tvName;


    //上方布局
    @ViewInject(R.id.rl_top_play)
    private RelativeLayout rlTop;
    //下方布局
    @ViewInject(R.id.rl_bottom_play)
    private RelativeLayout rlBottom;
    //中间锁屏布局
    @ViewInject(R.id.rl_img_lock_play)
    private RelativeLayout rlLock;
    //center的数字进度布局
    @ViewInject(R.id.ll_progress_play)
    private LinearLayout llProgress;
    @ViewInject(R.id.tv_speed_play)
    private TextView tvSpeed;
    @ViewInject(R.id.tv_bufferedsize_play)
    private TextView tvBufferedSize;


    /**
     * 从其他界面要传过来的字段
     */

    //视频的路径
    private String vPath;
    //视频类型
    private String vType;
    //视频的名字
    private String vName;

    Video video;


    /**
     * 声音亮度三个
     */
    @ViewInject(R.id.operation_volume_brightness)
    private View mVolumeBrightnessLayout;
    @ViewInject(R.id.operation_bg)
    private ImageView mOperationBg;
    @ViewInject(R.id.operation_percent)
    private ImageView mOperationPercent;


    private AudioManager mAudioManager;
    /**
     * 声音
     */
    private int mMaxVolume;
    /**
     * 当前声音
     */
    private int mVolume = -1;
    /**
     * 当前亮度
     */
    private float mBrightness = -1f;
    //收拾监听
    private GestureDetector mGestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //初始化声音亮度
        initVoiceBrigthViews();
        //接受从其他Activity传过来的值。
        initValues();
        //初始化布局显示
        initLayoutVisiable();

        initSetProgressBar();


        startPlay();

    }

    private void initSetProgressBar() {

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int value = (int) (seekBar.getProgress()
                        * videoView.getDuration() / seekBar.getMax());
                videoView.seekTo(value);
                videoView.start();
                isPlaying = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPlaying = false;
                videoView.pause();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }
        });

    }

    private void startPlay() {
        llProgress.setVisibility(View.VISIBLE);
        try {
            videoView.setVideoPath(vPath);
            if (!video.getVideo_Type().equals("1")) {
                videoView.seekTo(Long.valueOf(video.getVideo_Progress()));
            } else {
                imgDownLoad.setVisibility(View.INVISIBLE);
            }
            isPlaying = true;
            videoView.setOnCompletionListener(this);
            videoView.setOnBufferingUpdateListener(this);
            videoView.setOnErrorListener(this);
            videoView.setOnInfoListener(this);
            videoView.setOnPreparedListener(this);
        } catch (Exception e) {

        }
    }

    private void initValues() {
        video = (Video) getIntent().getSerializableExtra("video");

        /*
         * 1:直播，不要进度条，不要下载条
         * 2.本地，要进度条，不要下载条
         * 3.网络，都要
         */

        vPath = video.getVideo_Path();
        vType = video.getVideo_Type();
        vName = video.getVideo_Name();
        tvName.setText(vName);
    }

    private void initLayoutVisiable() {
        update = new upDateSeekBar();
        new Thread(update).start();
        //刚进入就得隐藏三个布局
        rlLock.setAlpha(0.6f);
        rlBottom.setAlpha(0.6f);
        rlTop.setAlpha(0.6f);
        rlTop.setVisibility(View.INVISIBLE);
        rlBottom.setVisibility(View.INVISIBLE);
        rlLock.setVisibility(View.INVISIBLE);
    }

    public void downloadVideo() {
        String url = vPath;
        String label = vName;
        try {
            DownloadService.getDownloadManager().startDownload(
                    url, label,
                    Constants.DOWN_LOAD_PATH + "/" + vName + ".mp4", true, false, null);
            showToast("正在下载");
            imgDownLoad.setImageResource(R.mipmap.mini_download_load_selected);
            imgDownLoad.setEnabled(false);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initVoiceBrigthViews() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
    }


    @Event(value = {R.id.img_back_play,
            R.id.img_download_play, R.id.img_lock_screen_play,
            R.id.img_play_play, R.id.img_next_play})
    private void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.img_back_play:
                finishPlay();
                break;
            //下载
            case R.id.img_download_play:
                downloadVideo();

                break;
            //锁屏
            case R.id.img_lock_screen_play:
                if (lockLabor) {
                    imgLockScreen.setImageResource(R.mipmap.player_landscape_screen_on_press);
                    isLocked = true;
                    lockLabor = false;
                    //一锁屏，三个布局立马消失
                    rlLock.setVisibility(View.INVISIBLE);
                    rlBottom.setVisibility(View.INVISIBLE);
                    rlTop.setVisibility(View.INVISIBLE);
                } else {
                    imgLockScreen.setImageResource(R.mipmap.player_landscape_screen_on_noraml);
                    isLocked = false;
                    lockLabor = true;
                    rlLock.setVisibility(View.INVISIBLE);

                }
                break;
            //播放
            case R.id.img_play_play:
                if (isPlaying) {
                    videoView.pause();
                    imgPlay.setImageResource(R.mipmap.ic_controller_pause);
                    isPlaying = false;
                    if(!vType.equals("1"))
                    mSeekBar.setEnabled(false);
                } else {
                    imgPlay.setImageResource(R.mipmap.video_play_img);
                    videoView.start();
                    isPlaying = true;
                    if(!vType.equals("1"))
                    mSeekBar.setEnabled(true);
                }


                break;
            //下一集
            case R.id.img_next_play:
                break;
            default:
                break;
        }
    }

    //
    private boolean lockLabor = true;


    //在这里写保存当前进度的东西，并且存入播放记录
    private void finishPlay() {
        long currentPosition = videoView.getCurrentPosition();
        if (vType.equals("2")) {
            VideoDBManager db = new VideoDBManager(this);
            Video now_video = db.queryAVideoByName(video.getVideo_Name());
            if (now_video != null) {
                /**
                 * 如果数据库已经有
                 * 1.更新已经播放
                 * 2.更新进度
                 */
                now_video.setVideo_Played("1");
                now_video.setVideo_Progress(currentPosition + "");
                db.update(now_video);
            } else {
                video.setVideo_Played("1");
                video.setVideo_Progress(currentPosition + "");
                db.insert(video);
            }
        }
        //判断是在线视频
        if (video.getVideo_Type() != null && video.getVideo_Type().equals("3")) {
            final String video_userId = video.getVideo_userId();
            video.setVideo_Progress(currentPosition + "");
            video.setVideo_Played("1");
            if (video_userId != null && (!video_userId.equals(""))) {
                video.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                    }

                    //如果插入失败，表示已经插入过这个
                    @Override
                    public void onFailure(int i, String s) {

                        BmobQuery<Video> query = new BmobQuery<Video>();
                        query.addWhereEqualTo(Constants.VIDEO_USER_ID, video_userId).addWhereEqualTo(Constants.VIDEO_PATH, video.getVideo_Path());
                        query.findObjects(getApplicationContext(), new FindListener<Video>() {
                            //查询成功在更新
                            @Override
                            public void onSuccess(List<Video> list) {
                                String user_objectId = list.get(0).getObjectId();
                                video.update(getApplicationContext(), user_objectId, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailure(int i, String s) {

                                    }
                                });
                            }

                            @Override
                            public void onError(int i, String s) {

                            }
                        });
                    }
                });
            }

        }


        finish();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        tvBufferedSize.setTextColor(Color.CYAN);
        tvBufferedSize.setText(videoView.getBufferPercentage() + "%");
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        isFinish = true;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {

        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (isPlaying) {
                    llProgress.setVisibility(View.VISIBLE);
                    videoView.pause();
                    isPlaying = false;

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!isPlaying) {
                    llProgress.setVisibility(View.GONE);
                    videoView.start();
                    isPlaying = true;
                }
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                tvSpeed.setTextColor(Color.CYAN);
                tvSpeed.setText(extra + "kb/s");
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (vPath.startsWith("http")) {
            videoView.setBufferSize(512 * 1024);
        } else {
            videoView.setBufferSize(0);
        }
        tvTotalTime.setVisibility(View.VISIBLE);
        tvTotalTime.setText(StringUtils.generateTime(videoView.getDuration()));
        llProgress.setVisibility(View.GONE);

    }


    private long lastBack;

    @Override
    public void onBackPressed() {
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit_play));
            lastBack = nowCurrent;
        } else {
            finishPlay();
            super.onBackPressed();

        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlaying) {
            videoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
        isPlaying = false;
        isFinish = true;
        System.gc();

    }


    private upDateSeekBar update;

    class upDateSeekBar implements Runnable {

        @Override
        public void run() {
            if (!isFinish&&!vType.equals("1")) {
                mHandler.sendMessage(Message.obtain());
                mHandler.postDelayed(update, 1000);
            }
        }
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (videoView == null) {
                return;
            }
            if (videoView != null&&!vType.equals("1")) {
                tvPlayTime.setText(StringUtils.generateTime(videoView
                        .getCurrentPosition()));
                seekBar(videoView.getCurrentPosition());
            }

        }

    };

    private void seekBar(long size) {
        if (videoView.isPlaying()) {
            long mMax = videoView.getDuration();
            int sMax=0;
            if (!vType.equals("1")) {
                 sMax = mSeekBar.getMax();
            }
            if (!vType.equals("1")) {
                mSeekBar.setProgress((int) (size * sMax / mMax));
            }
            if (vType.equals("1") || vType.equals("2")) {
                imgDownLoad.setVisibility(View.INVISIBLE);
            }
        }
    }


    /**
     * 以下是控制声音和亮度的。
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
            case MotionEvent.ACTION_DOWN:
                if (!isLocked) {
                    rlBottom.setVisibility(View.VISIBLE);
                    rlTop.setVisibility(View.VISIBLE);
                } else {
                    rlBottom.setVisibility(View.INVISIBLE);
                    rlTop.setVisibility(View.INVISIBLE);
                }
                rlLock.setVisibility(View.VISIBLE);
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isLocked = false;
    private boolean isPlaying = false;
    private boolean isFinish = false;


    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
        mDismissHandler.removeMessages(1);
        mDismissHandler.sendEmptyMessageDelayed(1, 5000);
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    break;
                case 1:
                    rlLock.setVisibility(View.INVISIBLE);
                    rlBottom.setVisibility(View.INVISIBLE);
                    rlTop.setVisibility(View.INVISIBLE);
            }
        }
    };


    /**
     * 手势探测器。 双击事件 单机事件 滑动事件
     *
     * @author Administrator
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        /**
         * 滑动
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            DisplayMetrics disp = new DisplayMetrics();

            getWindowManager().getDefaultDisplay().getMetrics(disp);
            int windowWidth = disp.widthPixels;
            int windowHeight = disp.heightPixels;

            if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;
            // 显示
            mOperationBg.setImageResource(R.mipmap.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);


        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width
                * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }


}
