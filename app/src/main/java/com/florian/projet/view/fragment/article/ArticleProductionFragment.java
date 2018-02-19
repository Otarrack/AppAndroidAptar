package com.florian.projet.view.fragment.article;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticleProductionFragment extends Fragment {
    ArticleViewModel articleViewModel;

    public ArticleProductionFragment() {

    }

    public static ArticleProductionFragment newInstance() {
        ArticleProductionFragment fragment = new ArticleProductionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_production, container, false);
    }

}
