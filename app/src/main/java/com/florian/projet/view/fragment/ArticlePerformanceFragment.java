package com.florian.projet.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.ArticleDetailActivity;
import com.florian.projet.view.activity.PeriodActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.viewModel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArticlePerformanceFragment extends Fragment {
    private ArticleViewModel articleViewModel;
    private RecyclerView recyclerViewArticle;

    private ArrayList<ArticleWithData> articleWithDataArrayList;
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_article_perf, container, false);

        setRecyclerViewArticle(view);
        initSearchView(view);

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
        articleViewModel.getAllArticleByPeriod(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {
                articleWithDataArrayList = new ArrayList<>(articleList);
                setNewRecyclerAdapterArticle(new ArrayList<>(articleList));
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

    private void initSearchView(View view) {
        // Get the SearchView and set the searchable configuration
        if (getActivity() != null) {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            final SearchView searchView = view.findViewById(R.id.article_perf_list_search);
            // Assumes current activity is the searchable activity
            if (searchManager != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.isEmpty()) {
                            setNewRecyclerAdapterArticle(articleWithDataArrayList);
                        } else {
                            doMySearch(newText);
                        }
                        searchView.requestFocus();
                        return false;
                    }
                });
                searchView.clearFocus();
            }
        }

    }

    private void doMySearch(String query) {
        ArrayList<ArticleWithData> searchArticleWithDataList = new ArrayList<>();
        for (ArticleWithData articleWithData : articleWithDataArrayList) {
            if (articleWithData.getArticle().getName().toLowerCase().contains(query.toLowerCase()) || articleWithData.getArticle().getCustomer().toLowerCase().contains(query.toLowerCase())) {
                searchArticleWithDataList.add(articleWithData);
            }
        }

        setNewRecyclerAdapterArticle(searchArticleWithDataList);
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.article_perf_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewArticle.setLayoutManager(layoutManager);

    }

    private void setNewRecyclerAdapterArticle(final ArrayList<ArticleWithData> articleList) {
        ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(articleList, false, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                articleViewModel.setCurrentArticle(articleList.get(position));

                Intent intent = new Intent(context, ArticleDetailActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        recyclerViewArticle.requestFocus();
    }
}