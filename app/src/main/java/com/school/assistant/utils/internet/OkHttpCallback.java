package com.school.assistant.utils.internet;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//监听服务端的响应，获取服务端的正确/报错信息
public class OkHttpCallback implements Callback {
    public final String TAG = com.school.assistant.utils.internet.OkHttpCallback.class.getSimpleName();

    public String url;
    public String result;

    //接口调用成功
    @Override
    public void onResponse(@NonNull Call call, Response response) throws IOException {
        //成功时获取接口数据
        assert response.body() != null;
        result = response.body().string();
        //调用onFinish输出获取的信息，可用通过重写onFinish()方法，运用hashmap获取需要的值并存储
        onFinish("success", result);
    }

    public void onFailure(@NonNull Call call, IOException e) {
        //请求失败，输出失败的原因
        onFinish("failure", e.toString());

    }

    public void onFinish(String status, String msg) {
        Log.e(TAG, "url: "+url );
        Log.e(TAG, "msg:"+msg );

    }

}

