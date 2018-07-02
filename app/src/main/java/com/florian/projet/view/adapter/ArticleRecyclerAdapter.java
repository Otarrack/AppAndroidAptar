package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.FavorisViewModel;

import java.util.ArrayList;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.MyHolder> {
    private ArrayList<ArticleWithData> articleArrayList;
    private CustomItemClickListener listener;
    private boolean isFavList;

    public ArticleRecyclerAdapter(ArrayList<ArticleWithData> articleArrayList, boolean isFavList, CustomItemClickListener listener) {
        this.articleArrayList = articleArrayList;
        this.isFavList = isFavList;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_article, viewGroup, false);

        final MyHolder holder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        String name = articleArrayList.get(position).getArticle().getName();
        String customer =  articleArrayList.get(position).getArticle().getCustomer();
        String type = articleArrayList.get(position).getArticle().getType();
        String totalQuantityProduced = Double.toString(articleArrayList.get(position).getTotalQuantity());

        holder.nameTextView.setText(name);
        holder.customerTextView.setText(customer);
        holder.typeTextView.setText(type);
        holder.totalQuantityProducedTextView.setText(totalQuantityProduced);

        if (isFavList) {
            holder.favCheckBox.setVisibility(View.GONE);
        } else {
            holder.favCheckBox.setChecked(articleArrayList.get(position).getArticle().isFavorite());
            holder.favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    FavorisViewModel favorisViewModel = FavorisViewModel.getInstance();
                    ArticleWithData articleWithData = articleArrayList.get(holder.getAdapterPosition());
                    articleWithData.getArticle().setFavorite(b);
                    favorisViewModel.updateFavArticle(articleWithData.getArticle());
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
        TextView customerTextView;
        TextView typeTextView;
        TextView totalQuantityProducedTextView;
        CheckBox favCheckBox;


        MyHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_article_num);
            customerTextView = itemView.findViewById(R.id.recycler_article_customer_res);
            typeTextView = itemView.findViewById(R.id.recycler_article_type_res);
            totalQuantityProducedTextView = itemView.findViewById(R.id.recycler_article_total_qt_res);
            favCheckBox = itemView.findViewById(R.id.recycler_article_fav_check);
        }
    }
}