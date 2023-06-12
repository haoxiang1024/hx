/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.xuexiang.templateproject.fragment.look;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentTrendingBinding;
import com.xuexiang.templateproject.fragment.look.FoundInfoFragment;
import com.xuexiang.templateproject.fragment.look.LostInfoFragment;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

/**
 * @author xuexiang
 * @since 2019-10-30 00:19
 */
//查看信息页
@Page(anim = CoreAnim.none)
public class LookFragment extends BaseFragment<FragmentTrendingBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentTrendingBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentTrendingBinding.inflate(inflater, container, false);
    }

    /**
     * @return 返回为 null意为不需要导航栏
     */
    @Override
    protected TitleBar initTitle() {
        return null;
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
        binding.lost.setOnSuperTextViewClickListener(this);
        binding.found.setOnSuperTextViewClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        if (view.getId() == binding.lost.getId()) {
            //失物信息页
            openNewPage(LostInfoFragment.class);
        } else if (view.getId() == binding.found.getId()) {
            //招领信息页
            openNewPage(FoundInfoFragment.class);
        }

    }
}
