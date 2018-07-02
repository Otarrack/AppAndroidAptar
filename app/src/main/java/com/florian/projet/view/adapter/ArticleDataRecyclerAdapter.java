package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.ArticleData;
import com.florian.projet.tools.CustomItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ArticleDataRecyclerAdapter extends RecyclerView.Adapter<ArticleDataRecyclerAdapter.MyHolder> {
    private ArrayList<ArticleData> articleDataArrayList;

    public ArticleDataRecyclerAdapter(ArrayList<ArticleData> articleDataArrayList) {
        this.articleDataArrayList = articleDataArrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_article_data, viewGroup, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        String numOf = articleDataArrayList.get(position).getNumOf();
        String date = formatter.format(articleDataArrayList.get(position).getDate());
        String quantity = Double.toString(articleDataArrayList.get(position).getQuantity()) + "";

        holder.ofNumTextView.setText(numOf);
        holder.dateTextView.setText(date);
        holder.qtProducedTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return articleDataArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView ofNumTextView;
        TextView dateTextView;
        TextView qtProducedTextView;


        MyHolder(View itemView) {
            super(itemView);
            ofNumTextView = itemView.findViewById(R.id.recycler_article_data_of_num);
            dateTextView = itemView.findViewById(R.id.recycler_article_data_date_res);
            qtProducedTextView = itemView.findViewById(R.id.recycler_article_data_qt_res);
        }
    }
}