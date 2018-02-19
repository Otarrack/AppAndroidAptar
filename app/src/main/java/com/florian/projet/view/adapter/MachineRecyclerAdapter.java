package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.SectionHolder;
import com.florian.projet.viewModel.ProductionViewModel;

public class MachineRecyclerAdapter extends RecyclerView.Adapter<SectionHolder> {
    private ProductionViewModel productionViewModel;

    private CustomItemClickListener listener;

    public MachineRecyclerAdapter(ProductionViewModel productionViewModel, CustomItemClickListener listener) {
        this.productionViewModel = productionViewModel;
        this.listener = listener;
    }

    @Override
    public SectionHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_production_recycle_item, viewGroup, false);

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
        holder.titleTextView.setText(String.valueOf(productionViewModel.getMachine(position).getId()));
        holder.volumeTextView.setText(String.valueOf(productionViewModel.getMachine(position).getVolume()));
        holder.wasteTextView.setText(String.valueOf(productionViewModel.getMachine(position).getWasteInQuantity()));
        holder.theoreticalPaceTextView.setText(String.valueOf(productionViewModel.getMachine(position).getWasteInPercent()));
    }

    @Override
    public int getItemCount() {
        return productionViewModel.getMachineList().size();
    }

}
