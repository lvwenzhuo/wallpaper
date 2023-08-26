package com.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CommonResponse<T> implements Serializable {

    public static final int CODE_OK = 0; // 业务正常，返回了合理的业务数据
    public static final boolean SUCCESS = true;//业务正常，返回了合理的业务数据

    public static final int CODE_TOKEN_INVALID = 401; // 本次登录，Token校验失败
    private static final long serialVersionUID = 5213230387175987834L;
    @Expose
    @SerializedName("code")
    public int code;
    @Expose
    @SerializedName("message")
    public String msg;
    @Expose
    @SerializedName("data")
    public T data;

    @Expose
    @SerializedName("success")
    public boolean success;
}