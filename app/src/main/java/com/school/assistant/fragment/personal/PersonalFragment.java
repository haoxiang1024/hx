package com.school.assistant.fragment.personal;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.school.assistant.R;
import com.school.assistant.adapter.entity.User;
import com.school.assistant.core.BaseFragment;
import com.school.assistant.core.webview.AgentWebActivity;

import com.school.assistant.fragment.other.AboutFragment;
import com.school.assistant.fragment.personal.user.AccountFragment;
import com.school.assistant.fragment.personal.user.PhotoFragment;
import com.school.assistant.fragment.personal.user.SuggestionFragment;
import com.school.assistant.fragment.settings.SettingsFragment;
import com.school.assistant.utils.Utils;
import com.school.assistant.databinding.FragmentProfileBinding;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;


//个人中心
@Page(anim = CoreAnim.none)
public class PersonalFragment extends BaseFragment<FragmentProfileBinding> implements SuperTextView.OnSuperTextViewClickListener {

    @NonNull
    @Override
    protected FragmentProfileBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }
    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return getResources().getString(R.string.menu_profile);
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
        initAc();//初始化账户数据

    }

    private void initAc() {
        User user = Utils.getBeanFromSp(getContext(), "User", "user");//获取存储对象
        //设置头像
        if (TextUtils.isEmpty(user.getPhoto())) {
            binding.rivHeadPic.setVisibility(View.GONE);
        } else {
            binding.rivHeadPic.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhoto()).into(binding.rivHeadPic);
        }
    }

    @Override
    protected void initListeners() {
        binding.photo.setOnSuperTextViewClickListener(this);
        binding.account.setOnSuperTextViewClickListener(this);
        binding.tips.setOnSuperTextViewClickListener(this);
        binding.suggestion.setOnSuperTextViewClickListener(this);
        binding.menuSettings.setOnSuperTextViewClickListener(this);
        binding.menuAbout.setOnSuperTextViewClickListener(this);


    }

    @SuppressLint("NonConstantResourceId")
    @SingleClick
    @Override
    public void onClick(SuperTextView view) {
        int id = view.getId();
        switch (id) {
            case R.id.photo:
                //设置头像
                openNewPage(PhotoFragment.class);
                break;
            case R.id.account:
                //账户页
                openNewPage(AccountFragment.class);
                break;
            case R.id.tips:
                //公告页
                //获取app当前语言
                String currentLanguage = Utils.language(getContext());
                if(currentLanguage.equals("zh")){
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification.html", getContext()));
                } else if (currentLanguage.equals("en")) {
                    AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/static/pages/notification_en.html", getContext()));
                }
                break;
            case R.id.suggestion:
                //帮助与反馈
                openNewPage(SuggestionFragment.class);
                break;
            case R.id.menu_settings:
                //设置页面
                openNewPage(SettingsFragment.class);
                break;
            case R.id.menu_about:
                //关于页面
                openNewPage(AboutFragment.class);
                break;


        }
    }
}
