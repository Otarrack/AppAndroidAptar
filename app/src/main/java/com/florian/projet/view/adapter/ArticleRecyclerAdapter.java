package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.viewModel.FavorisViewModel;

import java.util.ArrayList;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.MyHolder> {
    private ArrayList<Article> articleArrayList;
    private boolean isFavList;

    public ArticleRecyclerAdapter(ArrayList<Article> articleArrayList, boolean isFavList) {
        this.articleArrayList = articleArrayList;
        this.isFavList = isFavList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_article, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        String name = articleArrayList.get(position).getName();
        String quantity = Double.toString(articleArrayList.get(position).getQuantity()) + "";

        holder.nameTextView.setText(name);
        holder.qtProducedTextView.setText(quantity);
        if (isFavList) {
            holder.favCheckBox.setVisibility(View.GONE);
        } else {
            holder.favCheckBox.setChecked(articleArrayList.get(position).isFavorite());
            holder.favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    FavorisViewModel favorisViewModel = FavorisViewModel.getInstance();
                    Article article = articleArrayList.get(holder.getAdapterPosition());
                    article.setFavorite(b);
                    Log.d("UP dans la liste ?", articleArrayList.get(holder.getAdapterPosition()).isFavorite() + " !");
                    favorisViewModel.updateFavArticle(article);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView qtProducedTextView;
        CheckBox favCheckBox;


        MyHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_article_perf_num);
            qtProducedTextView = itemView.findViewById(R.id.recycler_article_perf_qt_res);
            favCheckBox = itemView.findViewById(R.id.recycler_article_perf_fav_check);
        }
    }
}