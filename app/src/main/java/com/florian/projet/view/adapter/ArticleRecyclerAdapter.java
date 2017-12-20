package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.MyHolder;
import com.florian.projet.viewModel.ProductionViewModel;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<MyHolder> {
    private ProductionViewModel productionViewModel;

    private CustomItemClickListener listener;

    public ArticleRecyclerAdapter(ProductionViewModel productionViewModel, CustomItemClickListener listener) {
        this.productionViewModel = productionViewModel;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.production_recycle_item, viewGroup, false);

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
        holder.titleTextView.setText(String.valueOf(productionViewModel.getArticle(position).getId()));
        holder.volumeTextView.setText(String.valueOf(productionViewModel.getArticle(position).getVolume()));
        holder.wasteQuantityTextView.setText(String.valueOf(productionViewModel.getArticle(position).getWasteInQuantity()));
        holder.wastePercentTextView.setText(String.valueOf(productionViewModel.getArticle(position).getWasteInPercent()));
    }

    @Override
    public int getItemCount() {
        return productionViewModel.getArticleList().size();
    }

}
