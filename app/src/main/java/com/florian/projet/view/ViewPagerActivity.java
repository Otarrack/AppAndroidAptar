package com.florian.projet.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.florian.projet.R;
import com.florian.projet.viewModel.PagerAdapterMain;

import java.util.Objects;

public class ViewPagerActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_viewpager);

        initializeViewPager();

        Bundle b = getIntent().getExtras();
        String page = b.getString("page");

        if (Objects.equals(page, getString(R.string.production))) {
            viewPager.setCurrentItem(0);

        }else if (Objects.equals(page, getString(R.string.productivity))) {
            viewPager.setCurrentItem(1);

        }else if (Objects.equals(page, getString(R.string.planning))) {
            viewPager.setCurrentItem(2);

        }else if (Objects.equals(page, getString(R.string.technique))) {
            viewPager.setCurrentItem(3);

        } else {
            viewPager.setCurrentItem(4);

        }
    }

    private void initializeViewPager() {

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapterMain(getSupportFragmentManager(),this));
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);

        tabLayout.setupWithViewPager(viewPager);
    }
}
