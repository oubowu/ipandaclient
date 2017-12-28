package com.oubowu.ipanda.api.converter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Oubowu on 2017/12/27 13:23.
 */
public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {

        try {
            String body = value.string();

            Map<String, T> map = gson.fromJson(body, new TypeToken<Map<String, T>>() {
            }.getType());
            // 针对下发json：{"data":{xxxx}}这种格式，只有一个map所以没必要再解析key值
            if (map != null && map.size() == 1) {
                Iterator<T> iterator = map.values().iterator();
                T next = iterator.next();
                if (!(next instanceof ArrayList)) {
                    return gson.fromJson(body, new TypeToken<T>() {
                    }.getType());
                }
                return next;
            } else {
                return gson.fromJson(body, new TypeToken<T>() {
                }.getType());
            }
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;

    }
}