package com.school.assistant.utils;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.school.assistant.R;
import com.school.assistant.activity.LoginActivity;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.StringUtils;

/**
 * Token管理工具
 *
 */
public final class TokenUtils {

    private static final String KEY_TOKEN = "com.school.assistant.utils.KEY_TOKEN";
    private static final String KEY_PROFILE_CHANNEL = "github";
    private static String sToken;
    private static Context context;

    public TokenUtils(Context context) {
        TokenUtils.context = context;
    }

    public void setContext(Context context) {
        TokenUtils.context = context;
    }

    private TokenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化Token信息
     */
    public static void init(Context context) {
        MMKVUtils.init(context);
        sToken = MMKVUtils.getString(KEY_TOKEN, "");
        TokenUtils.context = context;
    }

    public static void clearToken() {
        sToken = null;
        MMKVUtils.remove(KEY_TOKEN);
    }

    public static String getToken() {
        return sToken;
    }

    public static void setToken(String token) {
        sToken = token;
        MMKVUtils.put(KEY_TOKEN, token);
    }

    public static boolean hasToken() {
        return MMKVUtils.containsKey(KEY_TOKEN);
    }

    /**
     * 处理登录成功的事件
     *
     * @param token 账户信息
     */
    public static boolean handleLoginSuccess(String token) {
        if (!StringUtils.isEmpty(token)) {
            XToastUtils.success(Utils.getString(context, R.string.login_su));
            MobclickAgent.onProfileSignIn(KEY_PROFILE_CHANNEL, token);
            setToken(token);
            return true;
        } else {
            XToastUtils.success(Utils.getString(context, R.string.logout_failed));
            return false;
        }
    }

    /**
     * 处理登出的事件
     */
    public static void handleLogoutSuccess() {
        MobclickAgent.onProfileSignOff();
        //登出时，清除账号信息
        clearToken();

        XToastUtils.success(Utils.getString(context, R.string.logout_success));
        SettingUtils.setIsAgreePrivacy(false);
        //跳转到登录页
        ActivityUtils.startActivity(LoginActivity.class);
    }

}
