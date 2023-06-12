package com.xuexiang.templateproject.utils;

import static com.xuexiang.xutil.app.ActivityUtils.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.xuexiang.templateproject.adapter.entity.User;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 功能描述：修改app内部的语言工具类
 */
public class LanguageUtil {

    /*语言类型：
     * 此处支持3种语言类型，更多可以自行添加。
     * */
    private static final String ENGLISH = "en";
    private static final String CHINESE = "ch";
    private static final String TRADITIONAL_CHINESE = "zh_rTW";
    public static final String LANG_KEY = "language";
    private static HashMap<String, Locale> languagesList = new HashMap<String, Locale>(3) {{
        put(ENGLISH, Locale.ENGLISH);
        put(CHINESE, Locale.CHINESE);
        put(TRADITIONAL_CHINESE, Locale.TRADITIONAL_CHINESE);
    }};

    /**
     * 修改语言
     *
     * @param activity 上下文
     * @param language 例如修改为 英文传“en”，参考上文字符串常量
     * @param cls      要跳转的类（一般为入口类）
     */
    public static void changeAppLanguage(Activity activity, String language, Class<?> cls) {
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        // app locale 默认简体中文
        Locale locale = getLocaleByLanguage(StringUtils.isEmpty(language) ? "zh" : language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
        // 保存语言设置
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LANG_KEY, language);
        editor.apply();
        // 重启app
        User user = Utils.getBeanFromSp(activity, "User", "user");//获取存储对象
        String phone = user.getPhone();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/login?phone=" + phone, activity), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String loginMsg = JsonOperate.getValue(result, "data");
                        Intent intent = new Intent(activity, cls);
                        intent.putExtra("loginMsg", loginMsg);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                        String msg = "网络连接失败!";
                        Utils.showResponse(msg);
                    }
                });
            }
        }.start();


        //加载动画
//        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
//        activity.overridePendingTransition(0, 0);
    }

    /**
     * 获取指定语言的locale信息，如果指定语言不存在
     * 返回本机语言，如果本机语言不是语言集合中的一种，返回英语
     */
    private static Locale getLocaleByLanguage(String language) {
        if (isContainsKeyLanguage(language)) {
            return languagesList.get(language);
        } else {
            Locale locale = Locale.getDefault();
            for (String key : languagesList.keySet()) {
                if (TextUtils.equals(languagesList.get(key).getLanguage(), locale.getLanguage())) {
                    return locale;
                }
            }
        }
        return Locale.ENGLISH;
    }

    /**
     * 如果此映射包含指定键的映射关系，则返回 true
     */
    private static boolean isContainsKeyLanguage(String language) {
        return languagesList.containsKey(language);
    }

}
