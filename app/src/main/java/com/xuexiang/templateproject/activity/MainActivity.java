

package com.xuexiang.templateproject.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.entity.User;
import com.xuexiang.templateproject.core.BaseActivity;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.ActivityMainBinding;
import com.xuexiang.templateproject.fragment.news.NewsFragment;
import com.xuexiang.templateproject.fragment.other.AboutFragment;
import com.xuexiang.templateproject.fragment.other.SearchFragment;
import com.xuexiang.templateproject.fragment.other.SettingsFragment;
import com.xuexiang.templateproject.fragment.profile.ProfileFragment;
import com.xuexiang.templateproject.fragment.profile.user.AccountFragment;
import com.xuexiang.templateproject.fragment.trending.TrendingFragment;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.sdkinit.XUpdateInit;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xui.adapter.FragmentAdapter;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.ThemeUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.common.CollectionUtils;
import com.xuexiang.xutil.display.Colors;

import java.util.Date;

/**
 * 程序主页面,只是一个简单的Tab例子
 *
 * @author xuexiang
 * @since 2019-07-07 23:53
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, ClickUtils.OnClick2ExitListener, Toolbar.OnMenuItemClickListener {

    private String[] mTitles;

    @Override
    protected ActivityMainBinding viewBindingInflate(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        initListeners();
    }


    @Override
    protected boolean isSupportSlideBack() {
        return false;
    }

    private void initViews() {
        WidgetUtils.clearActivityBackground(this);

        mTitles = ResUtils.getStringArray(R.array.home_titles);
        binding.includeMain.toolbar.setTitle(mTitles[0]);
        binding.includeMain.toolbar.inflateMenu(R.menu.menu_main);
        binding.includeMain.toolbar.setOnMenuItemClickListener(this);
        initHeader();
        //主页内容填充
        BaseFragment[] fragments = new BaseFragment[]{
                new NewsFragment(),//主页
                new TrendingFragment(),//查看信息页
                new ProfileFragment()//我的页面
        };
        FragmentAdapter<BaseFragment> adapter = new FragmentAdapter<>(getSupportFragmentManager(), fragments);
        binding.includeMain.viewPager.setOffscreenPageLimit(mTitles.length - 1);
        binding.includeMain.viewPager.setAdapter(adapter);
    }

    private void initData() {
        XUpdateInit.checkUpdate(this, false);
    }


    private void initHeader() {
        binding.navView.setItemIconTintList(null);
        View headerView = binding.navView.getHeaderView(0);
        LinearLayout navHeader = headerView.findViewById(R.id.nav_header);
        RadiusImageView ivAvatar = headerView.findViewById(R.id.iv_avatar);
        TextView tvAvatar = headerView.findViewById(R.id.tv_avatar);
        TextView tvSign = headerView.findViewById(R.id.tv_sign);
        ImageView sexView = headerView.findViewById(R.id.sex_photo);
        if (Utils.isColorDark(ThemeUtils.resolveColor(this, R.attr.colorAccent))) {
            tvAvatar.setTextColor(Colors.WHITE);
            tvSign.setTextColor(Colors.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_white));
            }
        } else {
            tvAvatar.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_title_text));
            tvSign.setTextColor(ThemeUtils.resolveColor(this, R.attr.xui_config_color_explain_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivAvatar.setImageTintList(ResUtils.getColors(R.color.xui_config_color_gray_3));
            }
        }
        //获取从其他界面传过来的数据
        Intent intent = getIntent();
        String loginMsg = intent.getStringExtra("loginMsg");
        //获取json对象
        JSONObject jsonObject = (JSONObject) JSON.parse(loginMsg);
        int balance = (int) jsonObject.get("balance");
        int id = (int) jsonObject.get("id");
        String nickname = (String) jsonObject.get("nickname");
        String password = (String) jsonObject.get("password");
        String phone = (String) jsonObject.get("phone");
        String photo = (String) jsonObject.get("photo");
        int prestige = (int) jsonObject.get("prestige");
        String sex = (String) jsonObject.get("sex");
        Date reg_date = jsonObject.getDate("reg_date");
        //构造user
        User user = new User(id, password, nickname, photo, sex, phone, balance, prestige, reg_date);
        //存储SharedPreferences以便之后调用
        Utils.saveBean2Sp(this, user, "User", "user");
        //设置头像
        //加载图片
        user = Utils.getBeanFromSp(this, "User", "user");//获取存储对象
        if (TextUtils.isEmpty(user.getPhoto())) {
            tvAvatar.setVisibility(View.GONE);
        } else {
            tvAvatar.setVisibility(View.VISIBLE);
            Glide.with(this).load(user.getPhoto()).into(ivAvatar);
        }
        //设置昵称
        tvAvatar.setText(user.getNickname());
        //设置简介
        //判断性别
        if (user.getSex().equals("男")) {
            tvSign.setText("小哥哥");
            //设置性别图标
            sexView.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.man).into(sexView);
        } else {
            tvSign.setText("小姐姐");
            //设置性别图标
            sexView.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.drawable.women).into(sexView);
        }
        navHeader.setOnClickListener(this);
    }


    protected void initListeners() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.includeMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //侧边栏点击事件
        binding.navView.setNavigationItemSelectedListener(menuItem -> {
            //判断菜单是否有选中行为的菜单项有则切换,无则打开页面
            if (menuItem.isCheckable()) {
                binding.drawerLayout.closeDrawers();//关闭抽屉
                return handleNavigationItemSelected(menuItem);//打开被选中项
            } else {
                int id = menuItem.getItemId();
                if (id == R.id.nav_settings) {
                    //设置页
                    openNewPage(SettingsFragment.class);
                } else if (id == R.id.nav_about) {
                    //关于页
                    openNewPage(AboutFragment.class);
                } else if (id == R.id.nav_search) {
                    //搜索页
                    openNewPage(SearchFragment.class);
                }
            }
            return true;
        });
        //主页事件监听
        binding.includeMain.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = binding.includeMain.bottomNavigation.getMenu().getItem(position);
                binding.includeMain.toolbar.setTitle(item.getTitle());
                item.setChecked(true);
                updateSideNavStatus(item);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.includeMain.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * 处理侧边栏点击事件
     *
     * @param menuItem
     * @return
     */
    private boolean handleNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());
            binding.includeMain.viewPager.setCurrentItem(index, false);
            return true;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.nav_header) {
            openNewPage(AccountFragment.class);//打开账户页
        }
    }

    //================Navigation================//

    /**
     * 底部导航栏点击事件
     *
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = CollectionUtils.arrayIndexOf(mTitles, menuItem.getTitle());
        if (index != -1) {
            binding.includeMain.toolbar.setTitle(menuItem.getTitle());
            binding.includeMain.viewPager.setCurrentItem(index, false);
            updateSideNavStatus(menuItem);
            return true;
        }
        return false;
    }

    /**
     * 更新侧边栏菜单选中状态
     *
     * @param menuItem
     */
    private void updateSideNavStatus(MenuItem menuItem) {
        MenuItem side = binding.navView.getMenu().findItem(menuItem.getItemId());
        if (side != null) {
            side.setChecked(true);
        }
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click(2000, this);
        }
        return true;
    }

    @Override
    public void onRetry() {
        XToastUtils.toast("再按一次退出程序");
    }


    @Override
    public void onExit() {
        XUtil.exitApp();
    }


}
