package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticleOfRecyclerAdapter extends RecyclerView.Adapter<ArticleOfRecyclerAdapter.MyHolder> {
    private ArticleViewModel articleViewModel;

    private CustomItemClickListener listener;

    public ArticleOfRecyclerAdapter(CustomItemClickListener listener) {
        this.articleViewModel = ArticleViewModel.getInstance();
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_article_production_of, viewGroup, false);

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
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.numOFTextView.setText(String.valueOf(articleViewModel.getOfAt(position).getNumOF()));
    }

    @Override
    public int getItemCount() {
        return articleViewModel.getOfList().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView numOFTextView;

        public MyHolder(View itemView) {
            super(itemView);
            numOFTextView = itemView.findViewById(R.id.recycler_article_production_of_num);
        }
    }
}

