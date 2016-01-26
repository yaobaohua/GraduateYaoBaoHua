package com.yaobaohua.graduateyaobaohua.ui.fragment.secondfragment;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yaobaohua.graduateyaobaohua.R;
import com.yaobaohua.graduateyaobaohua.model.Video;
import com.yaobaohua.graduateyaobaohua.ui.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * @Author yaobaohua
 * @CreatedTime 2016/01/15 17：04
 * @DESC :
 */
@ContentView(R.layout.frag_sec_home)
public class SecondHomeFragment extends BaseFragment {

    public void setFriendListener(onClick2FriendFragmentListener friendListener) {
        this.friendListener = friendListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        friendListener = (onClick2FriendFragmentListener) context;
    }

    private onClick2FriendFragmentListener friendListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(value = {R.id.btn_switch_to_friend,R.id.btn_switch_to_friend_with_object})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_switch_to_friend:
                friendListener.switch2Friend("dongjun", "liming");
                break;
            case R.id.btn_switch_to_friend_with_object:
                friendListener.switch2FriendWithVideo(new Video("aaa","bbb","ccc","ddd","eee","fff","hhh"));
                break;
        }
    }


    public static interface onClick2FriendFragmentListener {
        public void switch2Friend(String name, String tag);

        public void switch2FriendWithVideo(Video video);


    }


}
