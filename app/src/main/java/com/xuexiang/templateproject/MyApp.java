

package com.xuexiang.templateproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.utils.LanguageUtil;
import com.xuexiang.templateproject.utils.sdkinit.ANRWatchDogInit;
import com.xuexiang.templateproject.utils.sdkinit.UMengInit;
import com.xuexiang.templateproject.utils.sdkinit.XBasicLibInit;
import com.xuexiang.templateproject.utils.sdkinit.XUpdateInit;
import com.xuexiang.xui.BuildConfig;


public class MyApp extends Application {

    /**
     * @return 当前app是否是调试开发模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initLibs();
    }

    /**
     * 初始化基础库
     */
    private void initLibs() {
        // X系列基础库初始化
        XBasicLibInit.init(this);
        // 版本更新初始化
        XUpdateInit.init(this);
        // 运营统计数据
        UMengInit.init(this);
        // ANR监控
        ANRWatchDogInit.init();
    }


}
