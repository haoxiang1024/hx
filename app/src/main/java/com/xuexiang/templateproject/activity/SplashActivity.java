

package com.xuexiang.templateproject.activity;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.User;
import com.xuexiang.templateproject.utils.LanguageUtil;
import com.xuexiang.templateproject.utils.SettingUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;

import java.io.IOException;

import me.jessyan.autosize.internal.CancelAdapt;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 启动页【无需适配屏幕大小】
 *
 * @author xuexiang
 * @since 2019-06-30 17:32
 */
@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseSplashActivity implements CancelAdapt {
    public static final String LANG_KEY = "language";//语言设置

    @Override
    protected long getSplashDurationMillis() {
        return 500;
    }

    /**
     * activity启动后的初始化
     */
    @Override
    protected void onCreateActivity() {
        initSplashView(R.drawable.xui_config_bg_splash);
        startSplash(true);
    }

    /**
     * 启动页结束后的动作
     */
    @Override
    protected void onSplashFinished() {
        if (SettingUtils.isAgreePrivacy()) {
            loginOrGoMainPage();
        } else {
            Utils.showPrivacyDialog(this, (dialog, which) -> {
                dialog.dismiss();
                SettingUtils.setIsAgreePrivacy(true);
                loginOrGoMainPage();
            });
        }
    }

    private void loginOrGoMainPage() {
        //通过用户令牌的操作
        if (TokenUtils.hasToken()) {
            User user = Utils.getBeanFromSp(this, "User", "user");//获取存储对象
            String phone = user.getPhone();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    OkhttpUtils.get(Utils.rebuildUrl("/login?phone=" + phone, SplashActivity.this), new OkHttpCallback() {
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            super.onResponse(call, response);
                            String loginMsg = JsonOperate.getValue(result, "data");
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            intent.putExtra("loginMsg", loginMsg);
                            //判断保存的语言
                            String language = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).getString(LANG_KEY, "");
                            if (!TextUtils.isEmpty(language)) {
                                //英文app登录
                                TokenUtils.setToken("language_token");
                                LanguageUtil.changeAppLanguage(SplashActivity.this, language, MainActivity.class);//更改语言设置
                            } else {
                               ActivityUtils.startActivity(SplashActivity.this,intent);
                            }
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        } else {
            ActivityUtils.startActivity(LoginActivity.class);
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }
}
