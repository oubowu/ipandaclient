package com.oubowu.ipanda.api.response;


import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.oubowu.ipanda.BasicApp;
import com.oubowu.ipanda.util.NetUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by Oubowu on 2017/12/27 11:16.
 */
public class ApiResponse<T> {

    public int code;

    @Nullable
    public final T body;

    @Nullable
    public String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();

        if (error instanceof HttpException) {
            code = ((HttpException) error).code();
            Log.e("ApiResponse","41行-ApiResponse(): "+code+";"+errorMessage);
            switch (code) {
                case 403:
                    errorMessage = "没有权限访问此链接！";
                    break;
                case 504:
                    if (!NetUtil.isConnected(BasicApp.getInstance())) {
                        errorMessage = "没有联网哦！";
                    } else {
                        errorMessage = "网络连接超时！";
                    }
                    break;
                default:
                    errorMessage = ((HttpException) error).message();
                    break;
            }
        } else if (error instanceof UnknownHostException) {
            errorMessage = "链接找不到了！";
        } else if (error instanceof SocketTimeoutException) {
            errorMessage = "网络连接超时！";
        }else if (error instanceof JsonParseException
                || error instanceof JSONException
                || error instanceof ParseException){
            errorMessage = "数据解析异常！";
        }

    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        // Logger.e(mCode+";"+response.isSuccessful());
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String msg = null;
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    msg = errorBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (msg == null || msg.trim().length() == 0) {
                msg = response.message();
            }
            errorMessage = msg;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    @Override
    public String toString() {
        if (body != null) {
            return body.toString();
        } else if (errorMessage != null) {
            return errorMessage;
        }
        return super.toString();
    }
}
