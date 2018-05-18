package com.florian.projet.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.florian.projet.R;
import com.florian.projet.view.adapter.MachinePagerAdapter;
import com.florian.projet.viewModel.MachineViewModel;

public class MachineDetailActivity extends AppCompatActivity {
    MachineViewModel machineViewModel;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_detail);

        machineViewModel = MachineViewModel.getInstance();
        setTitle("Machine : " + machineViewModel.getCurrentMachine().getMachineName());

        initializeViewPager();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        machineViewModel.delCurrentMachine();
        super.onStop();
    }

    private void initializeViewPager() {

        this.toolbar = findViewById(R.id.machine_detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        tabLayout = findViewById(R.id.machine_detail_tabLayout);
        if (tabLayout != null) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);

            viewPager = findViewById(R.id.machine_detail_viewPager);
            viewPager.setAdapter(new MachinePagerAdapter(getSupportFragmentManager(),this));
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(12);

            tabLayout.setupWithViewPager(viewPager);
        }
    }
}