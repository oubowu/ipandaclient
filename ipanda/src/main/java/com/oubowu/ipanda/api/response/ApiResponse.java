package com.oubowu.ipanda.api.response;


import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Oubowu on 2017/12/27 11:16.
 */
public class ApiResponse<T> {

    public final int code;

    @Nullable
    public final T body;

    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
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
