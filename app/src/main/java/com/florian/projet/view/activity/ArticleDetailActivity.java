package com.florian.projet.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.view.adapter.OFDataRecyclerAdapter;
import com.florian.projet.viewModel.ArticleViewModel;

import java.util.ArrayList;

public class ArticleDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewArticle;
    private ArticleViewModel articleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        articleViewModel = ArticleViewModel.getInstance();
        setRecyclerViewArticle();
    }

    private void setRecyclerViewArticle() {
        recyclerViewArticle = findViewById(R.id.article_data_recycler);
        recyclerViewArticle.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewArticle.setLayoutManager(layoutManager);

        setNewRecyclerAdapterArticle();

    }

    private void setNewRecyclerAdapterArticle() {
        OFDataRecyclerAdapter OFDataRecyclerAdapter = new OFDataRecyclerAdapter(new ArrayList<>(articleViewModel.getCurrentArticle().getDataList()));

        recyclerViewArticle.setAdapter(OFDataRecyclerAdapter);
        recyclerViewArticle.requestFocus();
    }
}
