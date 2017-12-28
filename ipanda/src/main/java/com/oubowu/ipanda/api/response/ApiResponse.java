package com.oubowu.ipanda.api.response;


import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by Oubowu on 2017/12/27 11:16.
 */
public class ApiResponse<T> {

    public final int mCode;

    @Nullable
    public final T mBody;

    @Nullable
    public final String mErrorMessage;

    public ApiResponse(Throwable error) {
        mCode = 500;
        mBody = null;
        mErrorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        mCode = response.code();
        // Logger.e(mCode+";"+response.isSuccessful());
        if (response.isSuccessful()) {
            mBody = response.body();
            mErrorMessage = null;
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
            mErrorMessage = msg;
            mBody = null;
        }
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 300;
    }

    @Override
    public String toString() {
        if (mBody != null) {
            return mBody.toString();
        } else if (mErrorMessage != null) {
            return mErrorMessage;
        }
        return super.toString();
    }
}
