package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.R;
import com.florian.projet.view.fragment.ArticleFragment;
import com.florian.projet.view.fragment.MachineFragment;
import com.florian.projet.view.fragment.PlanningFragment;
import com.florian.projet.view.fragment.ProductionFragment;
import com.florian.projet.view.fragment.ProductivityFragment;
import com.florian.projet.view.fragment.QualityFragment;
import com.florian.projet.view.fragment.SiteFragment;
import com.florian.projet.view.fragment.TechniqueFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private final static int PAGE_COUNT = 5;

    public SectionPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ProductionFragment.newInstance();
            case 1:
                return ProductivityFragment.newInstance();
            case 2:
                return PlanningFragment.newInstance();
            case 3:
                return QualityFragment.newInstance();
            case 4:
                return TechniqueFragment.newInstance();
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
                return mContext.getString(R.string.quality);
            case 4:
                return mContext.getString(R.string.technique);
            default:
                return null;
        }
    }
}
