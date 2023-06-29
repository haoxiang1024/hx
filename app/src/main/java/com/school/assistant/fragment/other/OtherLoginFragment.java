package com.school.assistant.fragment.other;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.school.assistant.R;
import com.school.assistant.activity.LoginActivity;
import com.school.assistant.activity.MainActivity;
import com.school.assistant.core.BaseFragment;
import com.school.assistant.utils.RandomUtils;
import com.school.assistant.utils.SettingUtils;
import com.school.assistant.utils.TokenUtils;
import com.school.assistant.utils.Utils;
import com.school.assistant.utils.internet.OkHttpCallback;
import com.school.assistant.utils.internet.OkhttpUtils;
import com.school.assistant.utils.sdkinit.UMengInit;
import com.school.assistant.utils.service.JsonOperate;
import com.school.assistant.databinding.FragmentOtherLoginBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xutil.app.ActivityUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

@Page
public class OtherLoginFragment extends BaseFragment<FragmentOtherLoginBinding> implements View.OnClickListener {

    @Override
    protected void initListeners() {
        binding.btnLogin.setOnClickListener(this);
        binding.otherLogin.setOnClickListener(this);
        binding.tvForgetPassword.setOnClickListener(this);
        binding.tvPrivacyProtocol.setOnClickListener(this);
        binding.tvUserProtocol.setOnClickListener(this);

    }

    @Override
    protected TitleBar initTitle() {
        return super.initTitle()
                .setHeight(230);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_login:
                //网络请求密码登录
                //验证手机号,密码是否通过
                if (binding.etPhoneNumber.validate()) {
                    if (binding.etPassword.validate()) {
                        pwdLogin();
                    }
                }
                break;
            case R.id.tv_user_protocol:
                //用户协议
                Utils.gotoProtocol(this, false, true);
                break;
            case R.id.tv_privacy_protocol:
                //隐私政策
                Utils.gotoProtocol(this, true, true);
                break;
            case R.id.other_login:
                //验证码登录
                Intent intent = new Intent(getContext(), LoginActivity.class);
                popToBack();
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                //忘记密码
                openPage(ResetFragment.class);
                break;
            default:
                break;

        }

    }

    private void pwdLogin() {
        //获取数据
        String number = binding.etPhoneNumber.getEditValue();
        String password = binding.etPassword.getEditValue();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/loginByPwd?phone=" + number + "&pwd=" + password, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(@NonNull Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String msg = JsonOperate.getValue(result, "msg");//登录信息
                        String loginMsg = JsonOperate.getValue(result, "data");//登录数据
                        if (msg.equals("登录成功")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.login_su));
                            //获取信息并存储
                            Utils.doUserData(loginMsg);
                            //设置登录token
                            TokenUtils.setToken(RandomUtils.getRandomLetters(6));
                            //跳转主界面
                            ActivityUtils.startActivity(MainActivity.class);
                        } else if (msg.equals("登录失败,请检查手机号密码是否正确")) {
                            Utils.showResponse(Utils.getString(getContext(),R.string.login_fail));
                        }

                    }
                });
            }
        }.start();

    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return "密码登录";
    }

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentOtherLoginBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentOtherLoginBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        //隐私政策
        boolean isAgreePrivacy = SettingUtils.isAgreePrivacy();
        binding.cbProtocol.setChecked(isAgreePrivacy);
        refreshButton(isAgreePrivacy);
        binding.cbProtocol.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SettingUtils.setIsAgreePrivacy(isChecked);
            handleSubmitPrivacy();
            refreshButton(isChecked);
        });
    }

    private void refreshButton(boolean isChecked) {
        ViewUtils.setEnabled(binding.btnLogin, isChecked);
    }

    //提交隐私政策
    private void handleSubmitPrivacy() {
        SettingUtils.setIsAgreePrivacy(true);
        UMengInit.init();
    }

    /**
     * 将Activity中onKeyDown在Fragment中实现，
     *
     * @param keyCode keyCode码
     * @param event   KeyEvent对象
     * @return 是否处理
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}