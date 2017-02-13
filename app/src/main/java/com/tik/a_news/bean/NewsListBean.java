package com.tik.a_news.bean;

import java.util.List;

public class NewsListBean {

    private String stat;
    private List<NewBean> data;
    private int error_code;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<NewBean> getData() {
        return data;
    }

    public void setData(List<NewBean> data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
