package com.florian.projet.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.florian.projet.R;
import com.florian.projet.view.adapter.SectionPagerAdapter;

public class SiteDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_detail);

        initializeViewPager();
    }

    private void initializeViewPager() {

        this.toolbar = (Toolbar) findViewById(R.id.site_detail_toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.site_detail_tabLayout);
        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

            viewPager = (ViewPager) findViewById(R.id.site_detail_viewPager);
            viewPager.setAdapter(new SectionPagerAdapter(getSupportFragmentManager(),this));
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(12);

            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
