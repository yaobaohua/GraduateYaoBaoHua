package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.activity.NativeVideoActivity;
import com.yaobaohua.graduateyaobaohua.utils.UtilJump;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21 16/16
 * @DESC :
 */
@ContentView(R.layout.frag_my)
public class MyFragment extends BaseFragment {
    ImageOptions imageOptions;

    @ViewInject(R.id.img_head_my)
    private ImageView img_head;
    @ViewInject(R.id.img_history_my)
    private ImageView img_history_record;
    @ViewInject(R.id.img_native_my)
    private ImageView img_native;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initImageOptions();
        initView();
    }

    private void initView() {
        String url = "http://img.mukewang.com//5523711700016d1606000338.jpg";
        x.image().bind(img_head, url, imageOptions);
    }

    public void initImageOptions() {
        imageOptions = new ImageOptions.Builder()
                .setCircular(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();
    }

    @Event(value = {R.id.rl_native_my, R.id.rl_history_my, R.id.img_download_my})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_history_my:
                break;
            case R.id.rl_download_my:
                break;
            case R.id.rl_native_my:
                UtilJump.jump2Act(getActivity(), NativeVideoActivity.class);
                break;
        }
    }
}
