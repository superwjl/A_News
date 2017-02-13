package com.tik.a_news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.tik.a_news.act.H5Activity;
import com.tik.a_news.api.NewsService;
import com.tik.a_news.R;
import com.tik.a_news.adapter.NewsAdapter;
import com.tik.a_news.base.BaseFragment;
import com.tik.a_news.bean.NewBean;
import com.tik.a_news.bean.NewsListBean;
import com.tik.a_news.bean.RespBaseBean;
import com.tik.a_news.custom.DividerItemDecoration;
import com.tik.a_news.custom.EndlessOnScrollListener;
import com.tik.a_news.custom.LRecyclerView;
import com.tik.a_news.global.Config;
import com.tik.a_news.interceptor.CustomInterceptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsFragment extends BaseFragment {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    LRecyclerView recyclerView;

    private int mType = 0;
    private boolean isPull = false;

    private List<NewBean> mNewsList = new ArrayList<>();
    private NewsAdapter mNewsAdapter;


    public static NewsFragment newInstance(int type){
        NewsFragment fragment = new NewsFragment();
        fragment.mType = type;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFlag = 1;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFlag = -1;
    }

    @Override
    protected void afterBindViews() {
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 通过 setEnabled(false) 禁用下拉刷新
        swipeRefreshLayout.setEnabled(true);
        // 设定下拉圆圈的背景
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.RED);
        // 设置手势下拉刷新的监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isPull = true;
                getNewsList();
            }
        });

        // init recyclerView and adatper
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mNewsAdapter = new NewsAdapter(getActivity(), mNewsList);
        recyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.setOnItemClickLitener(new NewsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("url", mNewsList.get(position).getUrl());
//                bundle.putString("title", mNewsList.get(position).getTitle());
                toActivity(H5Activity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerView.setLoadMoreListener(new LRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mNewsAdapter.setFooterViewVisible(true);
                getNewsList();
            }

            @Override
            public void onLoadAll() {
                mNewsAdapter.setFooterViewVisible(false);
                mNewsAdapter.notifyDataSetChanged();
            }

        });
        // first auto load data
        if(mNewsList == null || mNewsList.size() == 0){
            swipeRefreshLayout.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getNewsList();
                }
            }, 1500);
        }
    }

    private void getNewsList(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<RespBaseBean<NewsListBean>> call = newsService.getNewsList(Config.Tab.TAB_EN_ARRAY[mType]);
        call.enqueue(new Callback<RespBaseBean<NewsListBean>>() {
            @Override
            public void onResponse(Call<RespBaseBean<NewsListBean>> call, Response<RespBaseBean<NewsListBean>> response) {
                if(mFlag != 1){
                    return;
                }
                int size = mNewsList.size();
                swipeRefreshLayout.setRefreshing(false);
                List<NewBean> list = response.body().getResult().getData();
//                list = list.subList(0,6);
                if(isPull){
                    mNewsList = list;
                    Toast.makeText(getActivity(), "列表已刷新", Toast.LENGTH_SHORT).show();
                    isPull = false;
                }else{
                    //for test, 超过一百条则不再加载
                    if(mNewsList.size() >= 100){
                        list.clear();
                    }
                    //test end
                    if(list == null || list.size() == 0){//数据全部加载完毕
                        mNewsAdapter.setDataAll(true);
                    }else{//数据未加载完
                        mNewsList.addAll(list);
                        mNewsAdapter.setDataAll(false);
                    }
                }
                mNewsAdapter.setData(mNewsList);
                mNewsAdapter.notifyItemRangeInserted(size, list.size());
            }

            @Override
            public void onFailure(Call<RespBaseBean<NewsListBean>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }
}
