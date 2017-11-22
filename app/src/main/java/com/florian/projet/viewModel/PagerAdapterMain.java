package com.florian.projet.viewModel;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.view.ProductivityFragment;
import com.florian.projet.view.PlanningFragment;
import com.florian.projet.view.ProductionFragment;
import com.florian.projet.view.QualityFragment;
import com.florian.projet.R;
import com.florian.projet.view.TechniqueFragment;

public class PagerAdapterMain extends FragmentPagerAdapter {
    private Context mContext;
    private final static int PAGE_COUNT = 5;

    public PagerAdapterMain(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProductionFragment();
            case 1:
                return new ProductivityFragment();
            case 2:
                return new PlanningFragment();
            case 3:
                return new TechniqueFragment();
            case 4:
                return new QualityFragment();
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
                return mContext.getString(R.string.production);
            case 1:
                return mContext.getString(R.string.productivity);
            case 2:
                return mContext.getString(R.string.planning);
            case 3:
                return mContext.getString(R.string.technique);
            case 4:
                return mContext.getString(R.string.quality);
            default:
                return null;
        }
    }
}
