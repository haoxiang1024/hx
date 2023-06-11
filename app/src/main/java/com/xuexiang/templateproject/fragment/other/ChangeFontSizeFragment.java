package com.xuexiang.templateproject.fragment.other;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentChangeFontSizeBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "调整字体大小")
public class ChangeFontSizeFragment extends BaseFragment<FragmentChangeFontSizeBinding> {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentChangeFontSizeBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentChangeFontSizeBinding.inflate(inflater,container,false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }
}