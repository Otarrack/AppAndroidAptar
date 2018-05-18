package com.florian.projet.quarantaine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticlePlanningFragment extends Fragment {
    ArticleViewModel articleViewModel;
    RecyclerView recyclerViewOf;

    public ArticlePlanningFragment() {

    }

    public static ArticlePlanningFragment newInstance() {
        ArticlePlanningFragment fragment = new ArticlePlanningFragment();
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
        View view = inflater.inflate(R.layout.fragment_article_planning, container, false);

        setRecyclerViewOf(view);

        return view;
    }


    private void setRecyclerViewOf(View view) {
        recyclerViewOf = view.findViewById(R.id.recycler_article_production_of);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (articleViewModel.getOfList().size() > 0) {
            ArticleOfRecyclerAdapter articleOfRecyclerAdapter = new ArticleOfRecyclerAdapter(new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),OFDetailActivity.class);
                    intent.putExtra(getString(R.string.key_num), articleViewModel.getOfList().get(position).getNumOF());

                    startActivity(intent);
                }
            }, getActivity());

            recyclerViewOf.setAdapter(articleOfRecyclerAdapter);
        }
        recyclerViewOf.setLayoutManager(layoutManager);
    }
}
