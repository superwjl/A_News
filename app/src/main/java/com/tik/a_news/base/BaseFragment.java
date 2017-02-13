package com.tik.a_news.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected abstract void afterBindViews();

    protected abstract int getLayoutId();

    protected long start = 0;

    protected int mFlag = -1;		// 1:执行了onAttch()方法   2：执行了onDetach()方法

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        afterBindViews();
    }

    protected void toActivity(Class<?> cls){
        toActivity(cls, null);
    }

    protected void toActivity(Class<?> cls, Bundle extras){
        Intent intent = new Intent(getActivity().getApplicationContext(), cls);
        if(extras != null){
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

}
