package com.florian.projet.viewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.model.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteRecyclerAdapter extends RecyclerView.Adapter<MyHolder> {
    private List<Site> listSite;

    public SiteRecyclerAdapter(ArrayList<Site> data) {
        this.listSite = data;
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
        holder.titleTextView.setText(listSite.get(position).getName());
        holder.progressBar.setProgress(position);
    }

    @Override
    public int getItemCount() {
        return listSite.size();
    }

}
