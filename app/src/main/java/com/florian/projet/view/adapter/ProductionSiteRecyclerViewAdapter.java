package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Site;
import com.florian.projet.view.fragment.ProductionSiteFragment.OnListFragmentInteractionListener;

import java.util.List;

public class ProductionSiteRecyclerViewAdapter extends RecyclerView.Adapter<ProductionSiteRecyclerViewAdapter.ViewHolder> {

    private final List<Site> siteList;
    private final OnListFragmentInteractionListener listener;

    public ProductionSiteRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        this.listener = listener;
        siteList = SiteManager.getInstance().getListSite();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.production_site_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.site = siteList.get(position);
        holder.textViewId.setText(String.valueOf(siteList.get(position).getId()));
        holder.textViewContent.setText(String.valueOf(siteList.get(position).getVolume()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onListSiteFragmentInteraction(holder.site);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return siteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textViewId;
        final TextView textViewContent;
        Site site;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewId = view.findViewById(R.id.production_site_item_id);
            this.textViewContent = view.findViewById(R.id.production_site_item_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewContent.getText() + "'";
        }
    }
}
