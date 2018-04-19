package com.florian.projet.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.SectionHolder;
import com.florian.projet.viewModel.MenuViewModel;

public class SiteRecyclerAdapter extends RecyclerView.Adapter<SectionHolder> {
    private MenuViewModel menuViewModel;
    private Context context;

    private CustomItemClickListener listener;

    public SiteRecyclerAdapter(Context context, MenuViewModel menuViewModel, CustomItemClickListener listener) {
        this.context = context;
        this.menuViewModel = menuViewModel;
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
        String siteName = menuViewModel.getSite(position).getSiteName();
        String machineNumber = context.getString(R.string.recycler_site_nb_machine) + " " + menuViewModel.getSite(position).getMachineList().size();

        holder.titleTextView.setText(siteName);
        holder.titleTextView.setText(machineNumber);

//        holder.volumeTextView.setText(String.valueOf(menuViewModel.getSite(position).getVolume()));
//        holder.wasteTextView.setText(String.valueOf(menuViewModel.getSite(position).getWaste()));
    }

    @Override
    public int getItemCount() {
        return menuViewModel.getSiteList().size();
    }

}
