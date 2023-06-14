package com.xuexiang.templateproject.fragment.settings;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.activity.MainActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentGeneralBinding;
import com.xuexiang.templateproject.utils.CacheClean;
import com.xuexiang.templateproject.utils.LanguageUtil;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

@Page()
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
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.generalsetting);
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
        binding.menuLanguage.setOnSuperTextViewClickListener(this);//多语言
        binding.menuCache.setOnSuperTextViewClickListener(this);//缓存清理

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(SuperTextView view) {
        switch (view.getId()) {
            case R.id.menu_language:
                //多语言
                openPage(MultiLanguageFragment.class);
                break;
            case R.id.menu_cache:
                //清除缓存
                String cacheSize = CacheClean.getTotalCacheSize(getContext());
                if (cacheSize.equals("0.00MB")) {
                    Utils.showResponse(Utils.getString(getContext(),R.string.no_cache_to_clear));
                } else {
                    CacheClean.clearAllCache(getContext());
                    //判断语言显示不同消息
                    if (Utils.language(getContext()).equals("zh")){
                        Utils.showResponse("共清理" + cacheSize + "缓存");
                    }else if (Utils.language(getContext()).equals("en")){
                        Utils.showResponse("Clean" + cacheSize + "caches");
                    }
                }
                break;
        }

    }
}