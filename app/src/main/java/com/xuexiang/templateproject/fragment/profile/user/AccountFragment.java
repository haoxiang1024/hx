package com.xuexiang.templateproject.fragment.profile.user;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.adapter.entity.User;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentAccountBinding;
import com.xuexiang.templateproject.fragment.other.ResetPwdFragment;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

@Page
public class AccountFragment extends BaseFragment<FragmentAccountBinding> implements View.OnClickListener {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentAccountBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentAccountBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        initData();//初始化账户数据

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.tvResetPwd.setOnClickListener(this);//重置密码
        binding.btnSubmit.setOnClickListener(this);//提交
    }

    @SuppressLint({"ClickableViewAccessibility", "InflateParams"})
    private void initData() {
        //获取控件
        ImageView imgView = findViewById(R.id.riv_head_pic);
        //获取用户信息
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        // 设置头像，判断是否为空
        if (TextUtils.isEmpty(user.getPhoto())) {
            imgView.setVisibility(View.GONE);
        } else {
            imgView.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhoto()).into(imgView);
        }
        //设置昵称
        binding.tvNickName.setText(user.getNickname());
        //单击修改昵称
        binding.tvNickName.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setView(getLayoutInflater().inflate(R.layout.custom_alert_dialog, null));
            alertDialog.show();
            //默认值
            EditText editText = alertDialog.findViewById(R.id.tv_message);//编辑框
            editText.setText(user.getNickname());
            Button btnSure = alertDialog.findViewById(R.id.btn_positive);//确定按钮
            Button btnCancel = alertDialog.findViewById(R.id.btn_negative);//取消按钮
            btnSure.setOnClickListener(v1 -> {
                //确定操作
                String newText = editText.getText().toString();
                binding.tvNickName.setText(newText);
                alertDialog.dismiss();
            });
            btnCancel.setOnClickListener(v1 -> {
                alertDialog.dismiss();//取消操作
            });
        });
        if (user.getSex().equals("男")) {
            binding.rbMan.setChecked(true);
        } else {
            binding.rbWomen.setChecked(true);
        }
        //注册日期
        String reg_date = Utils.dateFormat(user.getReg_date());
        binding.regDate.append(reg_date);
        //手机号
        binding.phone.append(user.getPhone());


    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return "账号";
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_reset_pwd) {
            openNewPage(ResetPwdFragment.class);//修改密码页
        } else if (id == R.id.btn_submit) {
            //提交账户资料
            update();

        }
    }

    private void update() {
        if (binding.tvNickName.getText().length() == 0) {
            Utils.showResponse("昵称不能为空!");
        }
        //获取数据
        String nickName = String.valueOf(binding.tvNickName.getText());//昵称
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取user对象
        int id = user.getId();//获取用户id
        //性别
        if (binding.rbMan.isChecked()) {
            String sex = String.valueOf(binding.rbMan.getText());
            server(nickName, sex, id);
        } else {
            String sex = String.valueOf(binding.rbWomen.getText());
            server(nickName, sex, id);
        }
    }

    //网络请求
    private void server(String nickname, String sex, int id) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/updateAc?nickname=" + nickname + "&sex=" + sex + "&id=" + id, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        String msg = JsonOperate.getValue(result, "msg");
                        String data = JsonOperate.getValue(result, "data");
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("loginMsg", data);
                        startActivity(intent);
                        Utils.showResponse(msg);
                    }
                });
            }
        }.start();
    }


}