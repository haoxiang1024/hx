package com.school.assistant.fragment.other;

import android.annotation.SuppressLint;
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
import com.school.assistant.databinding.FragmentResetBinding;
import com.school.assistant.utils.Utils;
import com.school.assistant.utils.internet.OkHttpCallback;
import com.school.assistant.utils.internet.OkhttpUtils;
import com.school.assistant.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

@Page
public class ResetFragment extends BaseFragment<FragmentResetBinding> implements View.OnClickListener {
    //https://webapi.sms.mob.com/sms/verify?appkey=3803bb9f24195&phone=18682675515&zone=86&code=914226
    Timer timer;
    int count = 60;//定时
    private CountDownButtonHelper mCountDownHelper;//倒计时
    private Button resetBtn;//重置按钮
    String app_key = "3803bb9f24195";//验证密钥
    private final String url = "https://webapi.sms.mob.com/sms/verify";
    ;

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
            if (tag == 1) {
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
            }

        }
    };


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
            if (binding.etPhoneNumber.validate() && binding.etVerifyCode.validate() && binding.etPassword.validate() && binding.rePassword.validate()) {
                if (binding.etPassword.getEditValue().equals(binding.rePassword.getEditValue())) {
                    //提交手机。验证码
                    //获取验证码
                    phone = binding.etPhoneNumber.getText().toString().trim();
                    code = binding.etVerifyCode.getText().toString().trim();
                    //使用接口验证验证码避免与登录时验证混淆
                    //验证验证码
                    verifyCode(phone, "86", code);
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.pwdnotsame));
                }
            }
        }

    }

    /**
     * @param phone 要验证的手机号
     * @param zone  区号
     * @param code  验证码
     */
    private void verifyCode(String phone, String zone, String code) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //网络请求
                OkhttpUtils.get(url + "?appkey=" + app_key + "&phone=" + phone + "&zone=" + zone + "&code=" + code, new OkHttpCallback() {
                    @Override
                    public void onResponse(@NonNull Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String status = JsonOperate.getValue(result, "status");
                        if (status.equals("200")) {
                            //验证成功
                            reset();
                        } else if (status.equals("468")) {
                            //验证失败
                            Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_virificaition_code_wrong));
                        } else {
                            //其他错误
                            Utils.showResponse(Utils.getString(getContext(), R.string.smssdk_network_error));
                        }

                    }
                });
            }
        }.start();
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
                    public void onResponse(@NonNull Call call, Response response) throws IOException {
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
    public void onDestroyView() {
        super.onDestroyView();
        if (mCountDownHelper != null) {
            mCountDownHelper.recycle();
        }
    }
}