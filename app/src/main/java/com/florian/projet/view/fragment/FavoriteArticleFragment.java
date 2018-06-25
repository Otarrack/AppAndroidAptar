package com.florian.projet.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.tools.ArticleListCallback;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.MachineDetailActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.FavorisViewModel;
import com.florian.projet.viewModel.MachineViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteArticleFragment extends Fragment {
    private FavorisViewModel favorisViewModel;

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

        favorisViewModel = FavorisViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorites_article, container, false);

        favorisViewModel.getAllFavArticle(new ArticleListCallback() {
            @Override
            public void onSuccess(List<Article> articleList) {
                setRecyclerViewArticle(view, new ArrayList<>(articleList));
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(getContext(),
                        R.string.machine_get_fav_error,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void setRecyclerViewArticle(View view, final ArrayList<Article> articleArrayList) {
        RecyclerView recyclerViewArticle = view.findViewById(R.id.favorites_article_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewArticle.setLayoutManager(layoutManager);

        ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(articleArrayList, true);

        recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        recyclerViewArticle.requestFocus();

    }
}