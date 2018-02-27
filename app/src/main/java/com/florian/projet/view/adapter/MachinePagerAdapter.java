package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.R;
import com.florian.projet.view.fragment.article.ArticleOperatorPerfFragment;
import com.florian.projet.view.fragment.article.ArticlePlanningFragment;
import com.florian.projet.view.fragment.article.ArticleProductionFragment;
import com.florian.projet.view.fragment.article.ArticleProductivityFragment;

public class MachinePagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private Context mContext;

    public MachinePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ArticleProductionFragment.newInstance();
            case 1:
                return ArticleProductivityFragment.newInstance();
            case 2:
                return ArticleOperatorPerfFragment.newInstance();
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
                return mContext.getString(R.string.section_pager_production);
            case 1:
                return mContext.getString(R.string.section_pager_productivity);
            case 2:
                return mContext.getString(R.string.section_pager_operator_performance);
            default:
                return null;
        }
    }
}
