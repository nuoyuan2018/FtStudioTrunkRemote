package com.acmenxd.retrofit.converter;

import android.support.annotation.NonNull;

import com.acmenxd.retrofit.HttpDataEntity;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author AcmenXD
 * @version v1.0
 * @github https://github.com/AcmenXD
 * @date 2017/1/4 15:50
 * @detail 下载json对象拆包类
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(@NonNull Gson gson, @NonNull TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        JSONObject newJson = null;
        try {
            newJson = new JSONObject(json);
            newJson.put("json", json);
            json = newJson.toString();
        } catch (JSONException pE) {
            pE.printStackTrace();
        }
        JsonReader jsonReader = gson.newJsonReader(new StringReader(json));
        try {
            T data = adapter.read(jsonReader);
            /**
             * * 二次解析,如匹配HttpDataEntity,将把json的"data"层自动过滤掉
             */
            // TODO: 2018/3/5
            if (data instanceof HttpDataEntity && newJson != null) {
                JSONObject dataObj = null;
                try {
                    if(newJson.has("data")) {
                        dataObj = newJson.getJSONObject("data");
                        if (dataObj != null) {
                            dataObj.put("code", ((HttpDataEntity) data).getCode());
                            dataObj.put("msg", ((HttpDataEntity) data).getMsg());
                            json = dataObj.toString();
                        }
                    }else if(newJson.has("json")){ //特殊处理
                        dataObj=new JSONObject("date");
                        dataObj.put("code", ((HttpDataEntity) data).getCode());
                        dataObj.put("msg", ((HttpDataEntity) data).getMsg());
                        json = dataObj.toString();
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                data = (T) gson.fromJson(json, data.getClass());
            }
            return data;
        } finally {
            value.close();
        }
    }
}