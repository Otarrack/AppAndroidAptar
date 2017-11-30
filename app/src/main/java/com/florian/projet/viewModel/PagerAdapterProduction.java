package com.florian.projet.viewModel;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.view.ProductivityFragment;
import com.florian.projet.view.PlanningFragment;
import com.florian.projet.view.ProductionFragment;
import com.florian.projet.R;

public class PagerAdapterProduction extends FragmentPagerAdapter {
    private Context mContext;
    private final static int PAGE_COUNT = 3;

    public PagerAdapterProduction(FragmentManager fm, Context context) {
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
                return mContext.getString(R.string.volume);
            case 1:
                return mContext.getString(R.string.waste_percent);
            case 2:
                return mContext.getString(R.string.waste_quantity);
            default:
                return null;
        }
    }
}
