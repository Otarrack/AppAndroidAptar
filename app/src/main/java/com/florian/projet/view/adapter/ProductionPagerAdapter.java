package com.florian.projet.view.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.view.fragment.ProductionMachineFragment;
import com.florian.projet.view.fragment.ProductionSiteFragment;
import com.florian.projet.view.fragment.PlanningFragment;
import com.florian.projet.R;

public class ProductionPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private final static int PAGE_COUNT = 3;

    public ProductionPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProductionSiteFragment.newInstance(1);
            case 1:
                return ProductionMachineFragment.newInstance(1);
            case 2:
                return new PlanningFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.site);
            case 1:
                return mContext.getString(R.string.machine);
            case 2:
                return mContext.getString(R.string.article);
            default:
                return null;
        }
    }
}
