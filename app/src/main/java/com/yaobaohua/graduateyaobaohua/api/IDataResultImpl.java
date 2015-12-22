package com.yaobaohua.graduateyaobaohua.api;


import com.yaobaohua.graduateyaobaohua.utils.LogUtils;

import org.xutils.ex.HttpException;


public abstract class IDataResultImpl<T> implements IDataResult<T>{
    @Override
    public void onFailure(HttpException arg0, int arg1) {
        LogUtils.d("请求失败" + arg1);
//            EnjoyApplication.app.toastHelper.showError(arg1);
    }

    @Override
    public void onLoading(long total, long current, boolean isUploading) {
    }

    @Override
    public abstract void onSuccessData(T data);

    @Override
    public void onStart() {
    }

    @Override
    public void onCancel() {
    }
}
