package com.yaobaohua.graduateyaobaohua.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseActivity;
import com.yaobaohua.graduateyaobaohua.utils.ScreenUtils;
import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.sql.Timestamp;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnSeekCompleteListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.VideoView;

/**
 * @Author yaobaohuae
 * @CreatedTime 2015/12/21 17/04
 * @DESC :
 */
public class PlayActivity extends Activity implements OnClickListener,
        OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
        OnVideoSizeChangedListener, SurfaceHolder.Callback, OnErrorListener,
        OnInfoListener, OnSeekCompleteListener {

    @ViewInject(R.id.image_lock)
    private ImageView imageView; //锁
    @ViewInject(R.id.movie_name)
    private TextView videoName;     //视频名字
    @ViewInject(R.id.movie_time)
    private TextView timeTextView;      //视频的时间
    @ViewInject(R.id.play)
    private Button playbButton;        //播放按钮
    @ViewInject(R.id.next)
    private Button nextButton;         //下一集俺就
    @ViewInject(R.id.surface_view)
    private VideoView videoView;        //频幕
    @ViewInject(R.id.title_fl)
    private FrameLayout fLayout;        //底部布局
    @ViewInject(R.id.buttom_lin)
    private LinearLayout layout;        //顶部布局
    @ViewInject(R.id.seekBar)
    private SeekBar seekBar;            //进度条
    @ViewInject(R.id.play_time)
    private TextView play_tiem;         //播放时间
    @ViewInject(R.id.play_end_time)
    private TextView endTime;           //视频总时间
    @ViewInject(R.id.operation_bg)
    private ImageView mOperationBg;     //视频声音背景
    @ViewInject(R.id.operation_percent)
    private ImageView mOperationPercent;            //声音大小背景
    @ViewInject(R.id.sudu)
    private TextView sudu;          //加载速度
    @ViewInject(R.id.sudu2)
    private TextView sudu2;         //网速
    @ViewInject(R.id.operation_volume_brightness)
    private View mVolumeBrightnessLayout;            //声音亮度
    @ViewInject(R.id.buff)
    private TextView textViewBF;       //缓存大小


    @ViewInject(R.id.pb)
    private View bar;           //中间背景进度
    private AudioManager mAudioManager;
    private String timeString;
    private String name;
    private boolean isLock;
    private int mMaxVolume;
    private boolean isPlay;
    private int mVolume = -1;
    private float mBrightness = -1f;

    private GestureDetector mGestureDetector;
    private upDateSeekBar update;
    private PopupWindow pWindow;

    @ViewInject(R.id.im_ad)
    private ImageView im_ad;

    private boolean isFinish;
    private long pauseSize;
    private long size;
    String urls;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_play);
        x.view().inject(this);

        initView();
        getInitDate();
        setPlayNameDate();


        nextButton.setEnabled(false);
        play();
    }


    private void getInitDate() {
        name = getIntent().getStringExtra("vName");
        urls = getIntent().getStringExtra("vUrl");
        type = getIntent().getStringExtra("vType");
        ToastUtils.show(this, urls, 2);
        if (type.equals("1")) {
            seekBar.setVisibility(View.GONE);
        }
    }

    private void setPlayNameDate() {
        videoName.setText(name);
    }

    private void initView() {
        fLayout.setVisibility(View.GONE);
        layout.setVisibility(View.GONE);
        imageView.setOnClickListener(this);
        imageView.setVisibility(View.GONE);
        videoName.setOnClickListener(this);
        timeString = (new Timestamp(System.currentTimeMillis())).toString()
                .substring(11, 16);
        timeTextView.setText(timeString);
        playbButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setListener();
        update = new upDateSeekBar();
        new Thread(update).start();
    }


    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();

            int windowWidth = ScreenUtils.getScreenW(getApplicationContext());
            int windowHeight = ScreenUtils.getScreenH(getApplicationContext());

            if (mOldX > windowWidth * 4.0 / 5)
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)
                onBrightnessSlide((mOldY - y) / windowHeight);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

    }

    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            mOperationBg.setImageResource(R.mipmap.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width
                * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

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

    private void play() {
        seekBar.setVisibility(View.VISIBLE);
        bar.setVisibility(View.VISIBLE);
        playbButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        if (type.equals("1")) {
            seekBar.setVisibility(View.GONE);
        }
        isPlay = true;
        try {
            videoView.setVideoPath(urls);
            videoView.setVisibility(View.VISIBLE);
            videoView.setOnCompletionListener(this);
            videoView.setOnBufferingUpdateListener(this);
            videoView.setOnErrorListener(this);
            videoView.setOnInfoListener(this);
            videoView.setOnPreparedListener(this);

        } catch (Exception e) {
        }
    }

    class upDateSeekBar implements Runnable {

        @Override
        public void run() {
            if (!isFinish) {
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
            timeString = (new Timestamp(System.currentTimeMillis())).toString()
                    .substring(11, 16);
            timeTextView.setText(timeString);
            play_tiem.setVisibility(View.VISIBLE);
            play_tiem.setText(StringUtils.generateTime(videoView
                    .getCurrentPosition()));
            if (videoView != null) {
                seekBar(videoView.getCurrentPosition());
            }

        }

    };

    private void seekBar(long size) {
        if (videoView.isPlaying()) {
            long mMax = videoView.getDuration();
            int sMax = seekBar.getMax();
            if (!type.equals("1")) {
                seekBar.setProgress((int) (size * sMax / mMax));
            }
        }
    }

    private void setListener() {
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                int value = (int) (seekBar.getProgress()
                        * videoView.getDuration() / seekBar.getMax());
                videoView.seekTo(value);
                videoView.start();
                isPlay = true;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPlay = false;
                videoView.pause();
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isLock) {
                    fLayout.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);

                }
                imageView.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                endGesture();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;

        disHandler.removeMessages(0);
        disHandler.sendEmptyMessageDelayed(0, 1000);
        disHandler.removeMessages(1);
        disHandler.sendEmptyMessageDelayed(1, 5000);
    }

    private Handler disHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mVolumeBrightnessLayout.setVisibility(View.GONE);
            } else {
                fLayout.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                if (pWindow != null && pWindow.isShowing()) {
                    pWindow.dismiss();
                }
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (isPlay) {
                    videoView.pause();
                    playbButton
                            .setBackgroundResource(R.mipmap.ic_controller_pause);
                    im_ad.setVisibility(View.VISIBLE);
                    isPlay = false;
                    seekBar.setEnabled(false);
                } else {
                    playbButton
                            .setBackgroundResource(R.mipmap.ic_controller_play);
                    im_ad.setVisibility(View.GONE);
                    videoView.start();
                    isPlay = true;
                    seekBar.setEnabled(true);
                }
                break;
            case R.id.image_lock:
                if (isLock) {
                    isLock = false;
                    imageView.setBackgroundResource(R.mipmap.lock_off);
                } else {
                    isLock = true;
                    imageView.setBackgroundResource(R.mipmap.lock_on);
                }
                break;
            case R.id.next:
                videoView.pause();
                if (videoView != null) {
                    size = videoView.getCurrentPosition() + videoView.getDuration()
                            / 10;
                    videoView.seekTo(size);
                    videoView.start();
                }
                break;


            default:
                break;

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        videoView.setVideoLayout(3, 0);
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
        textViewBF.setTextColor(Color.CYAN);
        textViewBF.setText(videoView.getBufferPercentage() + "%");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        nextButton.setEnabled(true);
        bar.setVisibility(View.GONE);
        if (urls.startsWith("http")) {
            videoView.setBufferSize(1024 * 1000);
        } else {
            videoView.setBufferSize(0);
        }
        endTime.setVisibility(View.VISIBLE);
        endTime.setText(StringUtils.generateTime(videoView.getDuration()));

        bar.setVisibility(View.GONE);
        if (pauseSize > 0) {
            videoView.seekTo(pauseSize);
        }
        pauseSize = 0;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        finish();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }


    @Override
    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        finish();
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
        switch (arg1) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (isPlay) {
                    bar.setVisibility(View.VISIBLE);
                    videoView.pause();
                    isPlay = false;

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!isPlay) {
                    bar.setVisibility(View.GONE);
                    videoView.start();
                    isPlay = true;

                }
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                sudu.setTextColor(Color.CYAN);
                sudu.setText(arg2 + "kb/s");
                sudu2.setText(arg2 + "kb/s");
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlay) {
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
        if (pWindow != null && pWindow.isShowing()) {
            pWindow.dismiss();
        }
        isPlay = false;
        isFinish = true;
        System.gc();

    }

}
