package com.tik.a_news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.tik.a_news.base.BaseActivity;
import com.tik.a_news.fragment.NewsFragment;
import com.tik.a_news.global.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        final List<Fragment> fragments = new ArrayList<>();

//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < Config.Tab.TAB_ZH_ARRAY.length; i++){
            tabLayout.addTab(tabLayout.newTab().setText(Config.Tab.TAB_ZH_ARRAY[i]));
            fragments.add(NewsFragment.newInstance(i));
        }
        tabLayout.setupWithViewPager(viewpager);

        viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Config.Tab.TAB_ZH_ARRAY[position];
            }
        });
        viewpager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
