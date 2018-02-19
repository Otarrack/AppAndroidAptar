package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.florian.projet.R;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.model.Article;
import com.florian.projet.view.fragment.article.ArticlePlanningFragment;
import com.florian.projet.view.fragment.article.ArticleProductionFragment;
import com.florian.projet.view.fragment.article.ArticleProductivityFragment;
import com.florian.projet.view.fragment.article.ArticleOperatorPerfFragment;

public class ArticlePagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private Context mContext;

    public ArticlePagerAdapter(FragmentManager fm, Context context) {
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
            case 3:
                return ArticlePlanningFragment.newInstance();
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
            case 3:
                return mContext.getString(R.string.section_pager_planning);
            default:
                return null;
        }
    }
}
