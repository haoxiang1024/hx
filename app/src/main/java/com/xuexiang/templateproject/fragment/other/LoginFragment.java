package com.xuexiang.templateproject.fragment.other;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.mob.MobSDK;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentLoginBinding;
import com.xuexiang.templateproject.utils.RandomUtils;
import com.xuexiang.templateproject.utils.SettingUtils;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.sdkinit.UMengInit;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 登录页面
 *
 * @author xuexiang
 * @since 2019-11-17 22:15
 */
@Page(anim = CoreAnim.none)
public class LoginFragment extends BaseFragment<FragmentLoginBinding> implements View.OnClickListener {

    Button get_code_id;//获取验证码按钮
    Timer timer;
    int count = 60;//定时
    String loginMsg = "";//登录信息
    //短信验证事件消息队列
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int tag = msg.what;
            if (tag == 1) {
                int arg = msg.arg1;
                if (arg == 1) {
                    get_code_id.setText("重新获取");
                    //计时结束停止计时把值恢复
                    count = 60;
                    timer.cancel();
                    get_code_id.setEnabled(true);
                } else {
                    get_code_id.setText(count + "");
                }
            }

        }
    };
    private View mJumpView;//跳过按钮
    private CountDownButtonHelper mCountDownHelper;//倒计时
    private String opCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //初始化控件
    @Override
    protected void initViews() {
        get_code_id = findViewById(R.id.btn_get_verify_code);
        mCountDownHelper = new CountDownButtonHelper(binding.btnGetVerifyCode, 60);
        //隐私政策弹窗
        if (!SettingUtils.isAgreePrivacy()) {
            Utils.showPrivacyDialog(getContext(), (dialog, which) -> {
                dialog.dismiss();
                handleSubmitPrivacy();
            });
        }
        boolean isAgreePrivacy = SettingUtils.isAgreePrivacy();
        binding.cbProtocol.setChecked(isAgreePrivacy);
        refreshButton(isAgreePrivacy);
        binding.cbProtocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingUtils.setIsAgreePrivacy(isChecked);
            refreshButton(isChecked);
        });

    }

    //初始化标题栏
    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle()
                .setImmersive(true);
        titleBar.setBackgroundColor(Color.TRANSPARENT);
        titleBar.setTitle("");
        titleBar.setLeftImageDrawable(ResUtils.getVectorDrawable(getContext(), R.drawable.ic_login_close));
        titleBar.setActionTextColor(ThemeUtils.resolveColor(getContext(), R.attr.colorAccent));
        mJumpView = titleBar.addAction(new TitleBar.TextAction(R.string.title_jump_login) {
            @Override
            public void performAction(View view) {
                onLoginSuccess();
            }
        });
        return titleBar;
    }

    //初始化监听器
    @Override
    protected void initListeners() {
        binding.btnGetVerifyCode.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.tvOtherLogin.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.tvUserProtocol.setOnClickListener(this);
        binding.tvPrivacyProtocol.setOnClickListener(this);
    }

    @NonNull
    @Override
    protected FragmentLoginBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    private void refreshButton(boolean isChecked) {
        ViewUtils.setEnabled(binding.btnLogin, isChecked);
        ViewUtils.setEnabled(mJumpView, isChecked);
    }

    //提交隐私政策
    private void handleSubmitPrivacy() {
        SettingUtils.setIsAgreePrivacy(true);
        UMengInit.init();
        //mobsdk隐私政策
        MobSDK.submitPolicyGrantResult(true);
    }

    //控件点击事件
    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_get_verify_code) {
            if (binding.etPhoneNumber.validate()) {
                //验证码倒计时
                CountdownStart();
                String phone = binding.etPhoneNumber.getEditValue();
                //操作码，用于判断是发送验证码还是校验验证码  send发送短信，verify验证短信
                opCode = "send";
                //向服务端发起请求
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        OkhttpUtils.get(Utils.rebuildUrl("/sms?phone=" + phone + "&" + "opCode=" + opCode, getContext()), new OkHttpCallback() {
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                super.onResponse(call, response);
                                Log.e(TAG, "onResponse: " + result);
                                Utils.showResponse(JsonOperate.getValue(result, "msg"));
                            }
                        });
                    }
                }.start();

            }
        } else if (id == R.id.btn_login) {
            if (binding.etPhoneNumber.validate()) {
                if (binding.etVerifyCode.validate()) {
                    String verify_code = binding.etVerifyCode.getEditValue();
                    String phone = binding.etPhoneNumber.getEditValue();
                    opCode = "verify";
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            OkhttpUtils.get(Utils.rebuildUrl("/sms?phone=" + phone + "&" + "codes=" + verify_code + "&" + "opCode=" + opCode, getContext()), new OkHttpCallback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    super.onResponse(call, response);
                                    Log.e(TAG, "onResponse: " + result);
                                    Utils.showResponse(JsonOperate.getValue(result, "msg"));
                                    onLoginSuccess();//登录成功
                                }
                            });
                        }
                    }.start();
                }
            }
        } else if (id == R.id.tv_user_protocol) {
            //用户协议
            Utils.gotoProtocol(this, false, true);
        } else if (id == R.id.tv_privacy_protocol) {
            //隐私政策
            Utils.gotoProtocol(this, true, true);
        } else if (id == R.id.tv_other_login) {
            //其他登录方式
            openPage(OtherLoginFragment.class);

        } else if (id == R.id.tv_forget_password) {
            //忘记密码
            openPage(ResetPwdFragment.class);

        }

    }


    //倒计时函数
    private void CountdownStart() {
        binding.btnGetVerifyCode.setEnabled(false);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                message.arg1 = count;
                if (count != 0) {
                    count--;
                } else {
                    return;
                }
                handler.sendMessage(message);
            }
        }, 1000, 1000);
    }

    /**
     * 登录成功的处理
     */
    private void onLoginSuccess() {
        //登录注册的处理
        String phoneNumber = binding.etPhoneNumber.getEditValue();
        login(phoneNumber);

    }

    private void login(String phone) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/login?phone=" + phone, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        loginMsg = JsonOperate.getValue(result, "data");
                        //获取信息并存储
                        Utils.doUserData(loginMsg);
                        //设置登录token
                        TokenUtils.setToken(RandomUtils.getRandomLetters(6));
                        //跳转主界面
                        ActivityUtils.startActivity(MainActivity.class);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                        Utils.showResponse(Utils.getString(getContext(),R.string.internet_erro));

                    }
                });
            }
        }.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
