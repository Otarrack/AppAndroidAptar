package com.florian.projet.viewModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.model.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteRecyclerAdapter extends RecyclerView.Adapter<MyHolder> {
    private List<Site> listSite;

    private CustomItemClickListener listener;

    public SiteRecyclerAdapter(ArrayList<Site> data, CustomItemClickListener listener) {
        this.listSite = data;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        final View view = LayoutInflater.from(viewGroup.getContext())
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
        holder.titleTextView.setText(listSite.get(position).getName());
        holder.progressBar.setProgress(position);
    }

    @Override
    public int getItemCount() {
        return listSite.size();
    }

}
