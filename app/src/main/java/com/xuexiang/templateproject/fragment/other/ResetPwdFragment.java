package com.xuexiang.templateproject.fragment.other;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentResetPwdBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
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
public class ResetPwdFragment extends BaseFragment<FragmentResetPwdBinding> implements View.OnClickListener {

    Timer timer;
    Button get_code_id;//获取验证码按钮
    int count = 60;//定时
    EventHandler eventHandler;
    //短信验证事件消息队列

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
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_send_mobile_detail));
                    break;
                case 3:
                    //其他错误
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_network_error));
                    break;
                case 4:
                    //校验成功
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_smart_verify_already));
                    reset();
                    break;
                case 5:
                    //校验失败
                    Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_virificaition_code_wrong));
                    break;
                default:
                    break;
            }

        }
    };

    private CountDownButtonHelper mCountDownHelper;//倒计时

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentResetPwdBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentResetPwdBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        get_code_id = findViewById(R.id.btn_get_verify_code);
        mCountDownHelper = new CountDownButtonHelper(binding.btnGetVerifyCode, 60);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Log.e(TAG, "afterEvent: " + ((Throwable) data).getMessage());
                    String status = JsonOperate.getValue(((Throwable) data).getMessage(), "status");
                    if (status.equals("468")) {
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

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return "重置密码";
    }

    @Override
    protected void initListeners() {
        binding.btnReset.setOnClickListener(this);
        binding.btnGetVerifyCode.setOnClickListener(this);
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
        if (id == R.id.btn_reset) {
            if (binding.etVerifyCode.getEditValue().length() == 0) {
                Utils.showResponse(Utils.getString(getContext(), R.string.verification_code_empty));
                return;
            }            //验证手机密码格式是否通过
            if (binding.etPhoneNumber.validate()) {
                if (binding.etPassword.validate() && binding.etPassword.getEditValue().equals(binding.rePassword.getEditValue())) {
                    phone = binding.etPhoneNumber.getText().toString().trim();
                    code=binding.etVerifyCode.getText().toString().trim();
                    submitVerificationCode("86",phone,code);

                } else {
                    binding.rePassword.setError("两次密码不一致");
                }
            }
        } else if (id == R.id.btn_get_verify_code) {
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
     * @param country   国家区号
     * @param phone     手机号
     * @param code      验证码
     */
    public static void submitVerificationCode(String country, String phone, String code){
        SMSSDK.submitVerificationCode(country, phone,code);

    }
    private void reset() {
        //获取数据
        String number = binding.etPhoneNumber.getEditValue();
        String password = binding.etPassword.getEditValue();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/resetPwd?phone=" + number + "&newPwd=" + password, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        Utils.showResponse(Utils.getString(getContext(), R.string.reset_su));

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
    protected TitleBar initTitle() {
        return super.initTitle()
                .setHeight(230);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
    }
}