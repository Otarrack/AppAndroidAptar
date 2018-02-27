package com.florian.projet.view.fragment.article;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.databinding.FragmentArticleProductionBinding;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.view.activity.ArticleDetailActivity;
import com.florian.projet.view.fragment.menu.ArticleFragment;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticleProductionFragment extends Fragment {
    ArticleViewModel articleViewModel;

    TextView numArticleTextView;
    TextView siteTextView;
    TextView machineTextView;

    TextView declarationDateTextView;
    TextView startingDateTextView;
    TextView endingDateTextView;

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
        View view = inflater.inflate(R.layout.fragment_article_production, container, false);

        initViews(view);

        FragmentArticleProductionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_production, container, false);
        binding.setArticle(articleViewModel);

        return view;
    }

    private void initViews(View view) {
        numArticleTextView = view.findViewById(R.id.article_production_num_res);
        machineTextView = view.findViewById(R.id.article_production_machine_res);
        siteTextView = view.findViewById(R.id.article_production_site_res);

        declarationDateTextView = view.findViewById(R.id.article_production_declaration_date_res);
        startingDateTextView = view.findViewById(R.id.article_production_starting_date_res);
        endingDateTextView = view.findViewById(R.id.article_production_ending_date_res);
    }

}
