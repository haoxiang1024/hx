package com.xuexiang.templateproject.fragment.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentGeneralBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

@Page(name = "通用设置")
public class GeneralFragment extends BaseFragment<FragmentGeneralBinding> implements  SuperTextView.OnSuperTextViewClickListener {


    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentGeneralBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentGeneralBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {
        super.initListeners();
        binding.menuCommon.setOnSuperTextViewClickListener(this);
    }



    @Override
    public void onClick(SuperTextView superTextView) {


    }
}