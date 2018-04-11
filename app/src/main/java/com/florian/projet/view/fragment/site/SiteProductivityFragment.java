package com.florian.projet.view.fragment.site;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.viewModel.ArticleViewModel;

public class SiteProductivityFragment extends Fragment {
    ArticleViewModel articleViewModel;

    public SiteProductivityFragment() {

    }

    public static SiteProductivityFragment newInstance() {
        SiteProductivityFragment fragment = new SiteProductivityFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleViewModel = ArticleViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_productivity, container, false);
    }

}
