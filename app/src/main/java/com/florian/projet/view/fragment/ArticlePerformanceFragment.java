package com.florian.projet.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.tools.ArticleListCallback;
import com.florian.projet.view.activity.PeriodActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.viewModel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticlePerformanceFragment extends Fragment {
    private ArticleViewModel articleViewModel;
    private RecyclerView recyclerViewArticle;

    ArrayList<Article> articleArrayList;

    private Context context;

    public ArticlePerformanceFragment() {
    }

    public static ArticlePerformanceFragment newInstance() {
        ArticlePerformanceFragment fragment = new ArticlePerformanceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleViewModel = ArticleViewModel.getInstance();
        articleArrayList = new ArrayList<>();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_perf, container, false);

        setRecyclerViewArticle(view);
        refreshData();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_period:
                Intent intent = new Intent(context, PeriodActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData() {
        articleViewModel.getArticleByPeriod(new ArticleListCallback() {
            @Override
            public void onSuccess(List<Article> articleList) {
                articleArrayList = new ArrayList<>(articleList);
                setNewRecyclerAdapterArticle(articleArrayList);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Article Fragment", "Msg -> " + e.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.article_perf_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewArticle.setLayoutManager(layoutManager);

    }

    private void setNewRecyclerAdapterArticle(final ArrayList<Article> articleList) {
        ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(articleList, false);

        recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        recyclerViewArticle.requestFocus();
    }
}