package com.yaobaohua.graduateyaobaohua.api;


import org.xutils.ex.HttpException;


public interface IDataResult<T> {
    void onFailure(HttpException arg0, int arg1);

    void onLoading(long total, long current, boolean isUploading);

    /**
     * 根据请求返回data或
     *
     * @param data
     */
    void onSuccessData(T data);

    void onStart();

    void onCancel();
}
