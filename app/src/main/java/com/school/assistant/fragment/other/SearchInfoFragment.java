package com.school.assistant.fragment.other;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.school.assistant.adapter.entity.SearchInfo;

import com.school.assistant.core.BaseFragment;
import com.school.assistant.utils.Utils;
import com.school.assistant.databinding.FragmentSearchInfoBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;

@Page(name = "详情")
public class SearchInfoFragment extends BaseFragment<FragmentSearchInfoBinding> {

    public static final String KEY_INFO = "info";


    @AutoWired(name = KEY_INFO)
    SearchInfo searchInfo;//实体类不能序列化，否则无法注入

    /**
     * 初始化参数
     */
    @Override
    protected void initArgs() {
        super.initArgs();
        XRouter.getInstance().inject(this);
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
    protected FragmentSearchInfoBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchInfoBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
            setData();
    }

    private void setData() {
        //设置标题
        binding.tvLostTitle.setText(searchInfo.getTitle());
        //设置内容
        binding.tvLostContent.setText(searchInfo.getContent());
        //加载图片
        if (TextUtils.isEmpty(searchInfo.getImg())) {
            binding.imgLost.setVisibility(View.GONE);

        } else {
            binding.imgLost.setVisibility(View.VISIBLE);
            Glide.with(this).load(searchInfo.getImg()).into(binding.imgLost);
        }
        //设置失主名称
        binding.tvAuthor.setText(searchInfo.getNickname());
        //设置联系方式
        binding.tvPhonenum.setText(searchInfo.getPhone());
        //设置地点
        binding.location.setText(searchInfo.getPlace());
        //设置状态
        binding.state.setText(searchInfo.getState());
        //设置发布日期
        String date = Utils.dateFormat(searchInfo.getPub_date());
        binding.tvDate.setText(date);
    }
}