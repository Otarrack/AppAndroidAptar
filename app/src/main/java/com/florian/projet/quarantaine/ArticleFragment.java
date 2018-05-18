package com.florian.projet.quarantaine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.MenuViewModel;

public class ArticleFragment extends Fragment {
    RecyclerView recyclerViewArticle;
    MenuViewModel menuViewModel;

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuViewModel = MenuViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_article, container, false);

        setRecyclerViewArticle(view);

        return view;
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.production_article_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (menuViewModel.getArticleList().size() > 0) {
            ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),ArticleDetailActivity.class);
                    intent.putExtra(getString(R.string.key_position), position);

                    startActivity(intent);
                }
            });

            recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        }
        recyclerViewArticle.setLayoutManager(layoutManager);
    }

}
