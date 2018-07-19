package com.florian.projet.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.ArticleDetailActivity;
import com.florian.projet.view.activity.PeriodActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.viewModel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteArticleFragment extends Fragment {
    private ArticleViewModel articleViewModel;
    RecyclerView recyclerViewArticle;

    private Context context;

    public FavoriteArticleFragment() {
    }

    public static FavoriteArticleFragment newInstance() {
        FavoriteArticleFragment fragment = new FavoriteArticleFragment();
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
        final View view = inflater.inflate(R.layout.fragment_favorites_article, container, false);

        setRecyclerViewArticle(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
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
        articleViewModel.getAllFavArticleByPeriod(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {
                setNewRecyclerAdapterArticle(new ArrayList<>(articleList));
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(getContext(),
                        R.string.machine_get_fav_error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.favorites_article_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewArticle.setLayoutManager(layoutManager);

    }

    private void setNewRecyclerAdapterArticle(final ArrayList<ArticleWithData> articleList) {
        ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(articleList, true, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            if (articleList.get(position).getDataList() == null || articleList.get(position).getDataList().size() == 0) {
                Toast.makeText(getActivity(), getString(R.string.data_not_found_in_period), Toast.LENGTH_LONG).show();
            } else {
                articleViewModel.setCurrentArticle(articleList.get(position));

                Intent intent = new Intent(context, ArticleDetailActivity.class);
                startActivity(intent);
            }
            }
        });

        recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        recyclerViewArticle.requestFocus();
    }
}