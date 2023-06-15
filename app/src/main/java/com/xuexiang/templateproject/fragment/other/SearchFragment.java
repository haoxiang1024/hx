package com.xuexiang.templateproject.fragment.other;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.SearchInfoAdapter;
import com.xuexiang.templateproject.adapter.entity.SearchInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.databinding.FragmentSearchBinding;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@Page()
public class SearchFragment extends BaseFragment<FragmentSearchBinding> {
    private SearchInfoAdapter searchInfoAdapter;//搜索适配器

    private List<SearchInfo> detailList = new ArrayList<>();//数据list

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
        return FragmentSearchBinding.inflate(inflater, container, false);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {
        searchInfoAdapter = new SearchInfoAdapter(getContext());
        binding.listview.setAdapter(searchInfoAdapter);
    }

    /**
     * 获取页面标题
     */
    @Override
    protected String getPageTitle() {
        return Utils.getString(getContext(),R.string.search);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        //搜索
        binding.searchButton.setOnClickListener(v -> {
            searchInfoAdapter = new SearchInfoAdapter(getContext());
            binding.listview.setAdapter(searchInfoAdapter);
            getData();
        });
        //list跳转详情页
        binding.listview.setOnItemClickListener((parent, view, position, id) -> {
            SearchInfo searchInfo = searchInfoAdapter.getItem(position);
            openPage(SearchInfoFragment.class, SearchInfoFragment.KEY_INFO, searchInfo);

        });

    }

    private void getData() {
        //获取输入框的值
        String value = binding.searchEdittext.getEditValue();
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get(Utils.rebuildUrl("/searchInfo?value=" + value, getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        //判断是否有返回数据
                        if (JsonOperate.getValue(result, "msg").equals("没有找到相关信息")) {
                            Utils.showResponse(Utils.getString(getContext(), R.string.no_relevant_info_found));
                            return;
                        }
                        //获取返回结果转换list
                        detailList = JsonOperate.getList(result, SearchInfo.class);
                        //更新ui
                        getActivity().runOnUiThread(() -> searchInfoAdapter.setData(detailList, 1));
                    }
                });
            }
        }.start();
    }


}