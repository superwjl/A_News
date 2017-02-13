package com.tik.a_news.api;


import com.tik.a_news.bean.NewsListBean;
import com.tik.a_news.bean.RespBaseBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("toutiao/index")
    Call<RespBaseBean<NewsListBean>> getNewsList(@Query("type") String type);
}
