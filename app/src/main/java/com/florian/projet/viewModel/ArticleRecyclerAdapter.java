package com.florian.projet.viewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<MyHolder> {
    private List<Article> listArticle;

    public ArticleRecyclerAdapter(ArrayList<Article> data) {
        this.listArticle = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_item, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.titleTextView.setText(listArticle.get(position).toString());
        holder.progressBar.setProgress(position);
    }

    @Override
    public int getItemCount() {
        return listArticle.size();
    }

}
