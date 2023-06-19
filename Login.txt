package com.xuexiang.templateproject.fragment.other;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
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
    EventHandler eventHandler;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag) {
                case 1:
                    int arg = msg.arg1;
                    if (arg == 1) {
                        binding.btnGetVerifyCode.setText("重新获取");
                        //计时结束停止计时把值恢复
                        count = 60;
                        timer.cancel();
                        binding.btnGetVerifyCode.setEnabled(true);
                    } else {
                        binding.btnGetVerifyCode.setText(count + "");
                    }
                    break;
                case 2:
                    //发送成功
                    Utils.showResponse(Utils.getString(getContext(),R.string.smssdk_send_mobile_detail));
                    break;
                case 3:
                    //其他错误
                    Utils.showResponse(Utils.getString(getContext(),R.string.smssdk_network_error));
                    break;
                case 4:
                    //校验成功
                    Utils.showResponse(Utils.getString(getContext(),R.string.smssdk_smart_verify_already));
                    onLoginSuccess();
                    break;
                case 5:
                    //校验失败
                    Utils.showResponse(Utils.getString(getContext(),R.string.smssdk_virificaition_code_wrong));
                    break;
                default:
                    break;
            }

        }
    };
    private View mJumpView;//跳过按钮
    private CountDownButtonHelper mCountDownHelper;//倒计时

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobSDK.submitPolicyGrantResult(true);
        init();

    }

    private void init() {
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
// TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //成功回调
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交短信、语音验证码成功
                        Message message = new Message();
                        message.what = 4;
                        handler.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    } else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                        //获取语音验证码成功
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                } else if (result == SMSSDK.RESULT_ERROR) {
                    //失败回调
                    Log.e(TAG, "afterEvent: "+((Throwable) data).getMessage() );
                    String status = JsonOperate.getValue(((Throwable) data).getMessage(), "status");
                    if(status.equals("468")){
                        //校验码错误
                        Message message = new Message();
                        message.what = 5;
                        handler.sendMessage(message);
                    }
                } else {
                    //其他失败回调
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
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
        String phone = "";
        String code="";
        if (id == R.id.btn_get_verify_code) {
            if (binding.etPhoneNumber.validate()) {
                phone = binding.etPhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    //倒计时
                    CountdownStart();
                    getVerificationCode("86", phone);
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.inputnum));
                }
            }

        } else if (id == R.id.btn_login) {
            //登录
            if(binding.etPhoneNumber.validate()&&binding.etVerifyCode.validate()){
                phone = binding.etPhoneNumber.getText().toString().trim();
                code=binding.etVerifyCode.getText().toString().trim();
                submitVerificationCode("86",phone,code);
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


    /**
     * cn.smssdk.SMSSDK.class
     * 请求文本验证码
     *
     * @param country 国家区号
     * @param phone   手机号
     */
    public static void getVerificationCode(String country, String phone) {
        //获取短信验证码
        SMSSDK.getVerificationCode(country, phone);
    }

    /**
     * cn.smssdk.SMSSDK.class
     * <p>
     * 请求文本验证码(带模板编号)
     *
     * @param tempCode 模板编号
     * @param country  国家区号
     * @param phone    手机号
     */
    public static void getVerificationCode(String tempCode, String country, String phone) {
        //获取短信验证码
        SMSSDK.getVerificationCode(tempCode, country, phone);
    }
    /**
     * cn.smssdk.SMSSDK.class
     * 提交验证码
     * @param country   国家区号
     * @param phone     手机号
     * @param code      验证码
     */
    public static void submitVerificationCode(String country, String phone, String code){
        SMSSDK.submitVerificationCode(country, phone,code);

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
                        Utils.showResponse(Utils.getString(getContext(), R.string.internet_erro));

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
        // 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}
