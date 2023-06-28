package com.school.assistant.fragment.other;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.school.assistant.R;
import com.school.assistant.core.BaseFragment;
import com.school.assistant.utils.Utils;
import com.school.assistant.utils.internet.OkHttpCallback;
import com.school.assistant.utils.internet.OkhttpUtils;
import com.school.assistant.utils.service.JsonOperate;
import com.school.assistant.databinding.FragmentResetBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

@Page
public class ResetFragment extends BaseFragment<FragmentResetBinding> implements View.OnClickListener {

    Timer timer;
    int count = 60;//定时
    EventHandler eventHandler;//事件处理
    private CountDownButtonHelper mCountDownHelper;//倒计时
    private Button resetBtn;//重置按钮

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentResetBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentResetBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        mCountDownHelper = new CountDownButtonHelper(binding.btnGetVerifyCode, 60);
        resetBtn = findViewById(R.id.btn_get_verify_code);
    }

    @Override
    protected TitleBar initTitle() {
        return super.initTitle()
                .setHeight(230);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.btnGetVerifyCode.setOnClickListener(this);
        binding.btnReset.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        public void handleMessage(Message msg) {
            int tag = msg.what;
            switch (tag) {
                case 1:
                    int arg = msg.arg1;
                    if (arg == 1) {
                        resetBtn.setText("重新获取");
                        //计时结束停止计时把值恢复
                        count = 60;
                        timer.cancel();
                        resetBtn.setEnabled(true);
                    } else {
                        resetBtn.setText(count + "");
                    }
                    break;
                case 2:
                    //验证码发送成功
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_send_mobile_detail));
                    break;
                case 3:
                    //验证码发送失败
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_network_error));
                    break;
                case 4:
                    //验证码验证失败
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_virificaition_code_wrong));
                    break;
                case 5:
                    //验证码验证成功
                    reset();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return Utils.getString(getContext(), R.string.resetpwd);
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
                        message.what = 5;
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
                    String status = data.toString();
                    Message message = new Message();
                    if (status.contains("468")) {
                        //验证码错误回调
                        message.what = 4;
                    } else {
                        //其他错误回调
                        message.what = 3;
                    }
                    handler.sendMessage(message);
                } else {
                    //其他失败回调
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        String phone = "";
        String code = "";
        if (id == R.id.btn_get_verify_code) {
            //获取手机号
            phone = binding.etPhoneNumber.getText().toString().trim();
            if (!TextUtils.isEmpty(phone)) {
                //倒计时
                CountdownStart();
                getVerificationCode("86", phone);
            } else {
                Utils.showResponse(Utils.getString(getContext(), R.string.inputnum));
            }
        } else if (id == R.id.btn_reset) {
            //重置密码
            if (binding.etPhoneNumber.validate() && binding.etVerifyCode.validate()&&binding.etPassword.validate()&&binding.rePassword.validate()) {
                if (binding.etPassword.getEditValue().equals(binding.rePassword.getEditValue())) {
                    //提交手机。验证码
                    //获取验证码
                    phone = binding.etPhoneNumber.getText().toString().trim();
                    code = binding.etVerifyCode.getText().toString().trim();
                    submitVerificationCode("86", phone, code);
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.pwdnotsame));
                }
            }
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
     * 提交验证码
     *
     * @param country 国家区号
     * @param phone   手机号
     * @param code    验证码
     */
    public static void submitVerificationCode(String country, String phone, String code) {
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    private void reset() {
        //重置密码
        String phone = binding.etPhoneNumber.getEditValue();
        String newPwd = binding.etPassword.getEditValue();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/resetPwd?phone=" + phone + "&newPwd=" + newPwd, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String msg = JsonOperate.getValue(result, "msg");
                        Utils.showResponse(msg);
                    }
                });
            }
        }.start();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
    }
}