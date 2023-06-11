package com.xuexiang.templateproject.fragment.other;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentSettingsBinding;
import com.xuexiang.templateproject.fragment.settings.GeneralFragment;
import com.xuexiang.templateproject.utils.TokenUtils;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;
import com.xuexiang.xutil.XUtil;

/**
 * @author xuexiang
 * @since 2019-10-15 22:38
 */
@Page(name = "设置")
public class SettingsFragment extends BaseFragment<FragmentSettingsBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentSettingsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentSettingsBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initViews() {
        binding.menuCommon.setOnSuperTextViewClickListener(this);
        binding.menuPrivacy.setOnSuperTextViewClickListener(this);
        binding.menuPush.setOnSuperTextViewClickListener(this);
        binding.menuChangeAccount.setOnSuperTextViewClickListener(this);
        binding.menuLogout.setOnSuperTextViewClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(SuperTextView superTextView) {
        int id = superTextView.getId();
        switch (id) {
            case R.id.menu_common:
                //通用设置页面
                openPage(GeneralFragment.class);
                break;
            case R.id.menu_privacy:
                //隐私页面
                break;
            case R.id.menu_push:
                //通知页面
                break;
            case R.id.menu_change_account:
                //切换账户
                break;
            case R.id.menu_logout:
                //退出登录
                DialogLoader.getInstance().showConfirmDialog(
                        getContext(),
                        getString(R.string.lab_logout_confirm),
                        getString(R.string.lab_yes),
                        (dialog, which) -> {
                            dialog.dismiss();
                            XUtil.getActivityLifecycleHelper().exit();
                            TokenUtils.handleLogoutSuccess();
                        },
                        getString(R.string.lab_no),
                        (dialog, which) -> dialog.dismiss()
                );
                break;
        }
    }

}
