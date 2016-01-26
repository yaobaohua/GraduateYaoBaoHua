package com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment;


import android.os.Bundle;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 17：04
 * @DESC :
 */
@ContentView(R.layout.frag_sec_friend)
public class SecondFriendFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            String name = getArguments().getString("name");

            String tag = getArguments().getString("tag");
            if (name != null && tag != null) {
                showToast(name + tag);
            }
            Video video = (Video) getArguments().getSerializable("video");

            if (video != null) {
                showToast(video.toString());
            }
        }



    }

    public static SecondFriendFragment newInstance(String param1, String param2) {
        SecondFriendFragment fragment = new SecondFriendFragment();
        Bundle args = new Bundle();
        args.putString("name", param1);
        args.putString("tag", param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * fragment接受对象
     *
     * @param video
     * @return
     */
    public static SecondFriendFragment newInstanceByVideo(Video video) {
        SecondFriendFragment fragment = new SecondFriendFragment();
        Bundle args = new Bundle();
        args.putSerializable("video", video);
        fragment.setArguments(args);
        return fragment;
    }

}
