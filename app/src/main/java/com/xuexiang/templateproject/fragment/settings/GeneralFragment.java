package com.xuexiang.templateproject.fragment.settings;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentGeneralBinding;
import com.xuexiang.templateproject.utils.CacheClean;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

@Page(name = "通用设置")
public class GeneralFragment extends BaseFragment<FragmentGeneralBinding> implements SuperTextView.OnSuperTextViewClickListener {


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
        binding.menuNight.setOnSuperTextViewClickListener(this);//夜间模式
        binding.menuFont.setOnSuperTextViewClickListener(this);//字体大小
        binding.menuCache.setOnSuperTextViewClickListener(this);//缓存清理

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(SuperTextView view) {
        switch (view.getId()) {
            case R.id.menu_night:
                Activity currentActivity = getActivity();
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(currentActivity, currentActivity.getTheme());
                int currentTheme = contextThemeWrapper.getThemeResId();
                currentActivity.setTheme(R.style.AppTheme_Dark);
                currentActivity.recreate();
                break;
            case R.id.menu_font:
                //字体大小
                break;
            case R.id.menu_cache:
                //清除缓存
                String cacheSize = CacheClean.getTotalCacheSize(getContext());
                Utils.showResponse("共清理" + cacheSize + "缓存数据");
                CacheClean.clearAllCache(getContext());
                break;
        }

    }
}