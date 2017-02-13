package com.tik.a_news.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void beforeBindViews();

    protected abstract void afterBindViews();

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeBindViews();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        afterBindViews();
    }

    protected void toActivity(Class<?> cls){
        toActivity(cls, null);
    }

    protected void toActivity(Class<?> cls, Bundle extras){
        Intent intent = new Intent(getApplicationContext(), cls);
        if(extras != null){
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    protected void hideKeyboard(){
        InputMethodManager imm =  (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
