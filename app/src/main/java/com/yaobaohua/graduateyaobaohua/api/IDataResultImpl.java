package com.yaobaohua.graduateyaobaohua.api;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author sky QQ:1136096189
 * @Description: idataresult的实现类
 * @date 15/11/20 下午1:59
 */
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
