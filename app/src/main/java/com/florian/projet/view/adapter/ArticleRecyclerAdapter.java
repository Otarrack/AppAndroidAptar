package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.SectionHolder;
import com.florian.projet.viewModel.MenuViewModel;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<SectionHolder> {
    private MenuViewModel menuViewModel;

    private CustomItemClickListener listener;

    public ArticleRecyclerAdapter(CustomItemClickListener listener) {
        this.menuViewModel = MenuViewModel.getInstance();
        this.listener = listener;
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_main, viewGroup, false);

        final SectionHolder holder = new SectionHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, holder.getLayoutPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(SectionHolder holder, int position) {
        holder.titleTextView.setText(String.valueOf(menuViewModel.getArticle(position).getNumArticle()));
        holder.volumeTextView.setText(String.valueOf(menuViewModel.getArticle(position).getVolume()));
        holder.wasteTextView.setText(String.valueOf(menuViewModel.getArticle(position).getWaste()));
        holder.theoreticalPaceTextView.setText(String.valueOf(menuViewModel.getArticle(position).getCadence()));
    }

    @Override
    public int getItemCount() {
        return menuViewModel.getArticleList().size();
    }

}
