/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzy.okgo.callback;

import android.content.Context;


import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.OkLogger;
import com.yongzemei.nfccylinder.common.NetWorkUtil;
import com.yongzemei.util.NetWorkType;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlz
 * 版   本：1.0
 * 创建日期：2016/1/14
 * 描   述：抽象的回调接口
 * 修订历史：
 * ================================================
 */
public abstract class AbsCallback<T> implements Callback<T> {

    protected Context mContext;

    public AbsCallback(Context context) {
        mContext = context;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        request//.headers("header1", "HeaderValue1")//
                .headers("_timestamp", String.valueOf(System.currentTimeMillis() / 1000)) // 以秒为单位
                .headers("_network", String.valueOf(NetWorkUtil.getNetworkType(mContext) == NetWorkType.TYPW_WIFI ? 1 : 2)); // 1为wifi， 2为3G
    }

    @Override
    public void onCacheSuccess(Response<T> response) {
    }

    @Override
    public void onError(Response<T> response) {
        OkLogger.printStackTrace(response.getException());
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void uploadProgress(Progress progress) {
    }

    @Override
    public void downloadProgress(Progress progress) {
    }
}
