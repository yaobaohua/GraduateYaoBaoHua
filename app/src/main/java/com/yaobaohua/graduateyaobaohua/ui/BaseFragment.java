package com.yaobaohua.graduateyaobaohua.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaobaohua.graduateyaobaohua.utils.ToastUtils;

import org.xutils.x;


/**
 * @Author yaobaohua
 * @CreatedTime 2015/12/21 16/16
 * @DESC :
 */
public class BaseFragment extends Fragment {
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    public void showToast(String text) {
        ToastUtils.showShort(getActivity(), text);
    }//初始化toast提示
}
