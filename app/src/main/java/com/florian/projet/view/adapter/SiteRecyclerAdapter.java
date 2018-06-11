package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.SectionHolder;

import java.util.ArrayList;

public class SiteRecyclerAdapter extends RecyclerView.Adapter<SectionHolder> {
    private ArrayList<SiteEnum> siteList;
    private Context context;

    private CustomItemClickListener listener;

    public SiteRecyclerAdapter(Context context, ArrayList<SiteEnum> siteList, CustomItemClickListener listener) {
        this.context = context;
        this.siteList = siteList;
        this.listener = listener;
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_site, viewGroup, false);

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
        String siteName = siteList.get(position).getName();
        String machineNumber = context.getString(R.string.recycler_site_nb_machine) + siteList.get(position).getMachineList().size();

        holder.titleTextView.setText(siteName);
        holder.machineNumberTextView.setText(machineNumber);

    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }
}