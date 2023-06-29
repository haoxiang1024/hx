package com.school.assistant.fragment.navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.school.assistant.R;
import com.school.assistant.adapter.entity.Found;
import com.school.assistant.adapter.lostandfoundnav.FoundDetailAdapter;

import com.school.assistant.core.BaseFragment;
import com.school.assistant.fragment.navigation.content.AddFoundFragment;
import com.school.assistant.fragment.navigation.content.FoundDetailFragment;
import com.school.assistant.utils.Utils;
import com.school.assistant.utils.internet.OkHttpCallback;
import com.school.assistant.utils.internet.OkhttpUtils;
import com.school.assistant.utils.service.JsonOperate;
import com.school.assistant.databinding.FragmentFoundBinding;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xrouter.annotation.AutoWired;
import com.xuexiang.xrouter.launcher.XRouter;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@Page
public class FoundFragment extends BaseFragment<FragmentFoundBinding> {

    public static final String KEY_TITLE_NAME = "title_name";
    private static String tabTitle;//选项卡标题
    /**
     * 自动注入参数，不能是private
     */
    @AutoWired(name = KEY_TITLE_NAME)
    String title;
    private String[] tabs_data = new String[]{};//选项卡组
    private int currentPosition;//当前选项卡的位置
    private FoundDetailAdapter foundDetailAdapter;//招领物品详情adapter
    private List<Found> detailList = new ArrayList<>();//数据list

    /**
     * 构建ViewBinding
     *
     * @param inflater  inflater
     * @param container 容器
     * @return ViewBinding
     */
    @NonNull
    @Override
    protected FragmentFoundBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentFoundBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initArgs() {
        // 自动注入参数必须在initArgs里进行注入
        XRouter.getInstance().inject(this);
    }

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.addAction(new TitleBar.ImageAction(R.drawable.add) {
            @Override
            public void performAction(View view) {
                openPage(AddFoundFragment.class);
            }
        });
        return titleBar;
    }

    @Override
    protected String getPageTitle() {
        return title;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        startAnim();//显示加载动画
        String[] types = getResources().getStringArray(R.array.type_titles);//根据app语言获取不同的数据
        operate_tabs(types);//选项卡
        tabTitle = types[0];//初始值
        foundDetailAdapter = new FoundDetailAdapter(getContext());
        binding.listview.setAdapter(foundDetailAdapter);
        getTypeDetailList();
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //跳转详情页
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Found found = foundDetailAdapter.getItem(position);
                openPage(FoundDetailFragment.class, FoundDetailFragment.KEY_FOUND, found);

            }
        });
        //跳转到添加页
        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPage(AddFoundFragment.class);
            }
        });

    }

    //发送请求获取分类下的所有内容
    private void getTypeDetailList() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/DetailByTitleByFound?title=" + tabTitle, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(@NonNull Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        //获取返回结果转换list
                        detailList = JsonOperate.getList(result, Found.class);
                        //回调主线程
                        getActivity().runOnUiThread(() -> foundDetailAdapter.setData(detailList, 1));
                    }

                });
            }
        }.start();
    }



    //对选项卡进行操作
    private void operate_tabs(String[] tabs_datas) {
        currentPosition = 0;//选项卡当前位置
        tabs_data = tabs_datas;
        //选项卡内容
        for (String tab : tabs_data) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tab));
        }
        stopAnim();//结束加载动画
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentPosition = tab.getPosition();
                tabTitle = tabs_data[currentPosition];
                //数据更新
                foundDetailAdapter = new FoundDetailAdapter(getContext());
                binding.listview.setAdapter(foundDetailAdapter);
                getTypeDetailList();
                tab.setText(tabTitle);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //显示加载动画
    private void startAnim() {
        binding.avLoad.show();
    }

    //结束加载动画
    private void stopAnim() {
        binding.avLoad.hide();
    }

}