package com.school.assistant.fragment.other;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.school.assistant.core.BaseFragment;
import com.school.assistant.databinding.FragmentChangeFontSizeBinding;
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