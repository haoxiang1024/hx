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

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;


@Page
public class ResetPwdFragment extends BaseFragment<FragmentResetPwdBinding> implements View.OnClickListener {

    Button get_code_id;//获取验证码按钮
    Timer timer;
    int count = 60;//定时
    String loginMsg = "";//登录信息
    EventHandler eventHandler;//事件处理
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


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
    protected void initListeners() {
        binding.btnReset.setOnClickListener(this);
        binding.btnGetVerifyCode.setOnClickListener(this);

    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return Utils.getString(getContext(), R.string.resetpwd);
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
            if (binding.etPhoneNumber.validate()) {
                phone = binding.etPhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    //倒计时
                    CountdownStart();
                    //发送验证码
                    sendCode(phone);
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.inputnum));
                }
            }

        } else if (id == R.id.btn_reset) {
            if (binding.etPhoneNumber.validate() && binding.etVerifyCode.validate()) {
                if (binding.etPassword.getEditValue().equals(binding.rePassword.getEditValue())) {
                    phone = binding.etPhoneNumber.getText().toString().trim();
                    code = binding.etVerifyCode.getText().toString().trim();
                    //重置密码
                    verify(phone, code);
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.pwdnotsame));
                }
            }
        }
    }

    private void verify(String phone, String code) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/sms?phone=" + phone + "&codes=" + code + "&opCode=verify", getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String msg = JsonOperate.getValue(result, "msg");
                        if(msg.equals("手机号验证成功!")){
                            reset();
                        }else {
                            Utils.showResponse(msg);
                        }

                    }
                });
            }
        }.start();


    }

    private void sendCode(String phone) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/sms?phone=" + phone + "&opCode=send", getContext()), new OkHttpCallback() {
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