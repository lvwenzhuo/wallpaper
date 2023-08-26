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
package com.lzy.okgo.cache.policy;


import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.yongzemei.nfccylinder.common.CommonResponse;
import com.yongzemei.nfccylinder.common.JsonCallback;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2017/5/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NoCachePolicy<T> extends BaseCachePolicy<T> {

    public NoCachePolicy(Request<T, ? extends Request> request) {
        super(request);
    }

    @Override
    public void onSuccess(final Response<T> success) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (success.body() instanceof CommonResponse) {
                    CommonResponse response = (CommonResponse) success.body();

                    if (  response.success /*|| response.code == CommonResponse.CODE_OK*/) { // 表示业务正常
                        mCallback.onSuccess(success);
                    } else if (response.code == CommonResponse.CODE_TOKEN_INVALID) { // -1统一表示 用户的Token校验失败
                        //((JsonCallback) mCallback).dispatchTokenError();
                        ((JsonCallback) mCallback).onTokenFail(success);
                    } else {
                        ((JsonCallback) mCallback).onFail(response.code, response.msg);

                    }

                } else {
                    mCallback.onSuccess(success);
                }

                mCallback.onFinish();
            }
        });
    }

    @Override
    public void onError(final Response<T> error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(error.code() == 401){
                    try {
                        ((JsonCallback) mCallback).onTokenFail(error);
                    }catch (Exception e){

                    }
                    //((JsonCallback) mCallback).dispatchTokenError();
                }else{
                    try {
                        mCallback.onError(error);
                    }catch (Exception e){

                    }
                }
                mCallback.onFinish();
            }
        });
    }

    @Override
    public Response<T> requestSync(CacheEntity<T> cacheEntity) {
        try {
            prepareRawCall();
        } catch (Throwable throwable) {
            return Response.error(false, rawCall, null, throwable);
        }
        return requestNetworkSync();
    }

    @Override
    public void requestAsync(CacheEntity<T> cacheEntity, Callback<T> callback) {
        mCallback = callback;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCallback.onStart(request);

                try {
                    prepareRawCall();
                } catch (Throwable throwable) {
                    Response<T> error = Response.error(false, rawCall, null, throwable);
                    mCallback.onError(error);
                    return;
                }
                requestNetworkAsync();
            }
        });
    }
}
