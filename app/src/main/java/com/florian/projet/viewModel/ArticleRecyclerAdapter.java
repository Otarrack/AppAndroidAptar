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

    private CustomItemClickListener listener;

    public ArticleRecyclerAdapter(ArrayList<Article> data, CustomItemClickListener listener) {
        this.listArticle = data;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycle_item, viewGroup, false);

        final MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, myHolder.getLayoutPosition());
            }
        });

        return myHolder;
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
