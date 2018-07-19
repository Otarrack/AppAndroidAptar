package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.relation.PresseWithData;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.viewModel.PresseViewModel;

import java.util.ArrayList;

public class PresseRecyclerAdapter extends RecyclerView.Adapter<PresseRecyclerAdapter.MyHolder> {
    private ArrayList<PresseWithData> presseArrayList;
    private CustomItemClickListener listener;
    private boolean isFavList;

    public PresseRecyclerAdapter(ArrayList<PresseWithData> presseWithDataArrayList, boolean isFavList, CustomItemClickListener listener) {
        this.presseArrayList = presseWithDataArrayList;
        this.isFavList = isFavList;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_presse, viewGroup, false);

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
    public void onBindViewHolder(final MyHolder holder, int position) {
        String name = presseArrayList.get(position).getPresse().getName();
        String totalQuantityProduced = Double.toString(presseArrayList.get(position).getTotalQuantity());

        holder.nameTextView.setText(name);
        holder.totalQuantityProducedTextView.setText(totalQuantityProduced);

        if (isFavList) {
            holder.favCheckBox.setVisibility(View.GONE);
        } else {
            holder.favCheckBox.setChecked(presseArrayList.get(position).getPresse().isFavorite());
            holder.favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    PresseViewModel presseViewModel = PresseViewModel.getInstance();
                    PresseWithData presseWithData = presseArrayList.get(holder.getAdapterPosition());
                    presseWithData.getPresse().setFavorite(b);
                    presseViewModel.updateFavPresse(presseWithData.getPresse());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return presseArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView totalQuantityProducedTextView;
        CheckBox favCheckBox;


        MyHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_presse_num);
            totalQuantityProducedTextView = itemView.findViewById(R.id.recycler_presse_total_qt_res);
            favCheckBox = itemView.findViewById(R.id.recycler_presse_fav_check);
        }
    }
}
