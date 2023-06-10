package com.xuexiang.templateproject.fragment.other;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentSearchBinding;
import com.xuexiang.xpage.annotation.Page;

@Page(name = "搜索")
public class SearchFragment extends BaseFragment<FragmentSearchBinding> {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentSearchBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentSearchBinding.inflate(inflater,container,false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }
}