

package com.xuexiang.templateproject.fragment.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xuexiang.templateproject.R;
import com.xuexiang.templateproject.adapter.base.broccoli.BroccoliSimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SimpleDelegateAdapter;
import com.xuexiang.templateproject.adapter.base.delegate.SingleDelegateAdapter;
import com.xuexiang.templateproject.adapter.entity.Found;
import com.xuexiang.templateproject.adapter.entity.Lost;
import com.xuexiang.templateproject.adapter.entity.NewInfo;
import com.xuexiang.templateproject.core.BaseFragment;
import com.xuexiang.templateproject.core.webview.AgentWebActivity;
import com.xuexiang.templateproject.databinding.FragmentNewsBinding;
import com.xuexiang.templateproject.fragment.navigation.FoundFragment;
import com.xuexiang.templateproject.fragment.navigation.LostFragment;
import com.xuexiang.templateproject.fragment.navigation.content.LostDetailFragment;
import com.xuexiang.templateproject.utils.DemoDataProvider;
import com.xuexiang.templateproject.utils.Utils;
import com.xuexiang.templateproject.utils.internet.OkHttpCallback;
import com.xuexiang.templateproject.utils.internet.OkhttpUtils;
import com.xuexiang.templateproject.utils.service.JsonOperate;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.enums.CoreAnim;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.adapter.simple.AdapterItem;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.banner.widget.banner.SimpleImageBanner;
import com.xuexiang.xui.widget.imageview.ImageLoader;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.samlss.broccoli.Broccoli;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 首页动态
 *
 * @author xuexiang
 * @since 2019-10-30 00:15
 */
@Page(anim = CoreAnim.fade)
public class NewsFragment extends BaseFragment<FragmentNewsBinding> {

    public static String newsKey = "9fbfe1092fa33bf4bf99d8b6a661963e";//新闻key
    private SimpleDelegateAdapter<NewInfo> mNewsAdapter;
    private DelegateAdapter delegateAdapter;

    @NonNull
    @Override
    protected FragmentNewsBinding viewBindingInflate(LayoutInflater inflater, ViewGroup container) {
        return FragmentNewsBinding.inflate(inflater, container, false);
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
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        binding.recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        //轮播条
        SingleDelegateAdapter bannerAdapter = new SingleDelegateAdapter(R.layout.include_head_view_banner) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                SimpleImageBanner banner = holder.findViewById(R.id.sib_simple_usage);
                banner.setSource(DemoDataProvider.getBannerList())
                        .setOnItemClickListener((view, item, position1) -> XToastUtils.toast("" + item.title)).startScroll();
            }
        };

        //九宫格菜单
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setPadding(0, 16, 0, 0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        SimpleDelegateAdapter<AdapterItem> commonAdapter = new SimpleDelegateAdapter<AdapterItem>(R.layout.adapter_common_grid_item, gridLayoutHelper, DemoDataProvider.getGridItems(getContext())) {
            @Override
            protected void bindData(@NonNull RecyclerViewHolder holder, int position, AdapterItem item) {
                if (item != null) {
                    RadiusImageView imageView = holder.findViewById(R.id.riv_item);
                    imageView.setCircle(true);
                    ImageLoader.get().loadImage(imageView, item.getIcon());
                    holder.text(R.id.tv_title, item.getTitle().toString().substring(0, 1));
                    holder.text(R.id.tv_sub_title, item.getTitle());
                    holder.click(R.id.ll_container, v -> {
                        // 注意: 这里由于NewsFragment是使用Viewpager加载的，并非使用XPage加载的，因此没有承载Activity， 需要使用openNewPage。
                        //获取标题,根据标题打开不同的页面
                        String title = item.getTitle().toString();
                        switch (title) {
                            case "失物":
                                openNewPage(LostFragment.class, LostFragment.KEY_TITLE_NAME, title);
                                break;
                            case "招领":
                                openNewPage(FoundFragment.class, FoundFragment.KEY_TITLE_NAME, title);
                                break;
                            case "搜索":
                                break;
                            case "意见反馈":
                                AgentWebActivity.goWeb(getContext(), Utils.rebuildUrl("/pages/contract.html", getContext()));
                                break;
                        }

                    });
                }
            }
        };

        //推荐的标题
        SingleDelegateAdapter titleAdapter = new SingleDelegateAdapter(R.layout.adapter_title_item) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
                holder.text(R.id.tv_title, "推荐");
            }
        };

        //推荐
        mNewsAdapter = new BroccoliSimpleDelegateAdapter<NewInfo>(R.layout.adapter_news_card_view_list_item, new LinearLayoutHelper(), DemoDataProvider.getEmptyNewInfo()) {
            List<NewInfo> newInfoList = getNewInfo();

            @Override
            protected void onBindData(RecyclerViewHolder holder, NewInfo model, int position) {
                if (model != null) {
                    holder.text(R.id.tv_user_name, model.getUserName());
                    holder.text(R.id.tv_tag, model.getTag());
                    holder.text(R.id.tv_title, model.getTitle());
                    holder.text(R.id.tv_summary, model.getSummary());
                    holder.image(R.id.iv_image, model.getImageUrl());
                    holder.click(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NewInfo newInfo = getItem(position);
                            //判断是招领or失物
                            if(newInfo.getState().equals("未找到")){
                                //失物
                                openPage(LostDetailFragment.class, LostDetailFragment.KEY_LOST, newInfo);
                            }


                        }
                    });
                }
            }

            /**
             * 获取列表项
             *
             * @param position
             * @return
             */
            @Override
            public NewInfo getItem(int position) {
                return newInfoList.get(position);
            }

            @Override
            protected void onBindBroccoli(RecyclerViewHolder holder, Broccoli broccoli) {
                broccoli.addPlaceholders(
                        holder.findView(R.id.tv_user_name),
                        holder.findView(R.id.tv_tag),
                        holder.findView(R.id.tv_title),
                        holder.findView(R.id.tv_summary),
                        holder.findView(R.id.iv_image)
                );
            }
        };
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(commonAdapter);
        delegateAdapter.addAdapter(titleAdapter);
        delegateAdapter.addAdapter(mNewsAdapter);
        binding.recyclerView.setAdapter(delegateAdapter);
    }

    @Override
    protected void initListeners() {
        //下拉刷新
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.getLayout().postDelayed(() -> {
                mNewsAdapter.refresh(getNewInfo());
                refreshLayout.finishRefresh();
            }, 1000);
        });
        //上拉加载
        binding.refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.getLayout().postDelayed(() -> {
                mNewsAdapter.loadMore(getNewInfo());
                refreshLayout.finishLoadMore();
            }, 1000);
        });
        binding.refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果


    }

    //获取新闻信息
    private List<NewInfo> getNewsData() {
        //定义一个空列表，用于存储解析出来的新闻对象
        final List<NewInfo> list = new ArrayList<>();
        //请求数据API获取新闻数据
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkhttpUtils.get("http://v.juhe.cn/toutiao/index?type=top&key=" + newsKey + "&page_size=5", new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        //解析JSON数据并封装成对象列表
                        list.addAll(JsonOperate.newsParseJsonData(result, "result"));

                    }
                });
            }
        }.start();

        //等待网络请求和数据解析完成
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //返回解析出来的新闻列表
        return list;
    }

    public List<NewInfo> getNewInfo() {
        //定义一个空列表，用于存储解析出来的置顶信息
        final List<NewInfo> list = new ArrayList<>();
        //失物置顶信息添加
        new Thread() {
            @Override
            public void run() {
                super.run();
                //失物信息展示
                OkhttpUtils.get(Utils.rebuildUrl("/showLostList?stick=1", getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        List<Lost> newInfoList = JsonOperate.getList(result, Lost.class);
                        //若无置顶信息则不执行
                        if (newInfoList == null) {
                            return;
                        }
                        for (Lost lost : newInfoList) {
                            list.add(new NewInfo(lost.getLostfoundtype().getName(), lost.getTitle())
                                    .setSummary(lost.getContent())
                                    .setUserName(lost.getNickname())
                                    .setState(lost.getState())
                                    .setImageUrl(lost.getImg()));

                        }

                    }
                });
                //招领信息展示
                OkhttpUtils.get(Utils.rebuildUrl("/showFoundList?stick=1", getContext()), new OkHttpCallback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        List<Found> founds = JsonOperate.getList(result, Found.class);
                        //若无置顶信息则不执行
                        if (founds == null) {
                            return;
                        }
                        for (Found found : founds) {
                            list.add(new NewInfo(found.getLostfoundtype().getName(), found.getTitle())
                                    .setSummary(found.getContent())
                                    .setUserName(found.getNickname())
                                    .setState(found.getState())
                                    .setImageUrl(found.getImg()));

                        }
                    }
                });
            }
        }.start();
        //等待网络请求和数据解析完成
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //返回解析出来的新闻列表
        return list;
    }

}
