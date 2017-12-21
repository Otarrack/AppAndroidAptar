package com.florian.projet.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.florian.projet.R;
import com.florian.projet.model.Machine;
import com.florian.projet.model.Site;
import com.florian.projet.view.fragment.ProductionMachineFragment;
import com.florian.projet.view.fragment.ProductionSiteFragment;
import com.florian.projet.view.adapter.ProductionPagerAdapter;

import java.util.Objects;

public class ProductionDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producution_detail);

        initializeViewPager();

        Bundle b = getIntent().getExtras();
        String page = null;
        if (b != null) {
            page = b.getString("page");
        }

        if (Objects.equals(page, getString(R.string.site))) {
            viewPager.setCurrentItem(0);

        } else if (Objects.equals(page, getString(R.string.machine))) {
            viewPager.setCurrentItem(1);

        } else if (Objects.equals(page, getString(R.string.article))) {
            viewPager.setCurrentItem(2);

        }
    }

    private void initializeViewPager() {

        this.toolbar = (Toolbar) findViewById(R.id.production_detail_toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.production_detail_tabLayout);
        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

            viewPager = (ViewPager) findViewById(R.id.production_detail_viewPager);
            viewPager.setAdapter(new ProductionPagerAdapter(getSupportFragmentManager(),this));
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(12);

            tabLayout.setupWithViewPager(viewPager);
        }
    }
}
