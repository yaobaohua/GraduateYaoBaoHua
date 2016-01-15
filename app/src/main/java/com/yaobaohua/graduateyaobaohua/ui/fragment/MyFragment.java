package com.yaobaohua.graduateyaobaohua.ui.fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.common.Constants;
import com.yaobaohua.graduateyaobaohua.model.Users;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyDownLoadVideoActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.MyPlayHistoryActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.NativePlayActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.PersonalInfoActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.PlayHistoryActivity;
import com.yaobaohua.graduateyaobaohua.ui.activity.UpdateVersionActivity;
import com.yaobaohua.graduateyaobaohua.utils.SPUtils;
import com.yaobaohua.graduateyaobaohua.utils.UtilJump;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

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
        String objectId = (String) SPUtils.get(getActivity(), Constants.USER_OBJECT_ID, "");
        BmobQuery<Users> query = new BmobQuery<>();
        query.getObject(getActivity(), objectId, new GetListener<Users>() {
            @Override
            public void onSuccess(Users users) {
                x.image().bind(img_head, users.getUser_head_img(), imageOptions);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });


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

    @Event(value = {R.id.rl_native_my, R.id.rl_history_my, R.id.rl_download_my, R.id.rl_check_update_my, R.id.rl_login_my})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_history_my:
                UtilJump.jump2Act(getActivity(), MyPlayHistoryActivity.class);
                break;
            case R.id.rl_download_my:
                UtilJump.jump2Act(getActivity(), MyDownLoadVideoActivity.class);
                break;
            case R.id.rl_native_my:
                UtilJump.jump2Act(getActivity(), NativePlayActivity.class);
                break;
            case R.id.rl_check_update_my:
                UtilJump.jump2Act(getActivity(), UpdateVersionActivity.class);
                break;
            case R.id.rl_login_my:
                UtilJump.jump2Act(getActivity(), PersonalInfoActivity.class);
                break;
        }
    }



}
