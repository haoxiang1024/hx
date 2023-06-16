package com.xuexiang.templateproject.fragment.other;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mob.MobSDK;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentSmsBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.xpage.annotation.Page;

import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@Page
public class SmsFragment extends BaseFragment<FragmentSmsBinding> implements View.OnClickListener {

    EventHandler eventHandler;
    Timer timer;
    int count = 60;

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentSmsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentSmsBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.btnGetVerifyCode.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
    }

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
                    Toast.makeText(getContext(), "获取短信验证码成功", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Log.i("Codr", "获取短信验证码失败");
                    Toast.makeText(getContext(), "获取短信验证码失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        }
    };

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
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                } else {
//其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    @Override
    public void onClick(View view) {
        String phone = "";
        String code = "";
        int id = view.getId();
        switch (id) {
            case R.id.btn_get_verify_code:
                phone = binding.etPhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    //倒计时
                    CountdownStart();
                    getVerificationCode("86", phone);
                } else {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_login:
                phone = binding.etPhoneNumber.getText().toString().trim();
                code = binding.etVerifyCode.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_LONG).show();
                } else {
                    Utils.showResponse("登录成功");
//登录逻辑
                }
                break;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
// 使用完EventHandler需注销，否则可能出现内存泄漏
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}