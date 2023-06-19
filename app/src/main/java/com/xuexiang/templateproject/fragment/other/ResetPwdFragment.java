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
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;


@Page
public class ResetPwdFragment extends BaseFragment<FragmentResetPwdBinding> implements View.OnClickListener {

    Timer timer;
    Button get_code_id;//获取验证码按钮
    int count = 60;//定时
    private String opCode;//操作码


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
        if (id == R.id.btn_reset) {
            if (binding.etVerifyCode.getEditValue().length() == 0) {
                Utils.showResponse(Utils.getString(getContext(), R.string.verification_code_empty));
                return;
            }            //验证手机密码格式是否通过
            if (binding.etPhoneNumber.validate()) {
                if (binding.etPassword.validate() && binding.etPassword.getEditValue().equals(binding.rePassword.getEditValue())) {
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
//                                    Utils.showResponse(JsonOperate.getValue(result, "msg"));
                                    reset();//重置密码
                                }
                            });
                        }
                    }.start();

                } else {
                    binding.rePassword.setError("两次密码不一致");
                }
            }
        } else if (id == R.id.btn_get_verify_code) {
            if (binding.etPhoneNumber.validate()) {
                String phone = binding.etPhoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    //倒计时
                    CountdownStart();
                    opCode = "send";
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
                } else {
                    Utils.showResponse(Utils.getString(getContext(), R.string.inputnum));
                }
            }

        }

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