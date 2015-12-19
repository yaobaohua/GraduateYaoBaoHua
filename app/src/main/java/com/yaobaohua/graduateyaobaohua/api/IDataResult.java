package com.yaobaohua.graduateyaobaohua.api;

import com.lidroid.xutils.exception.HttpException;

/**
 * @author sky QQ:1136096189
 * @Description:
 * @date 15/11/20 下午1:59
 */
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
