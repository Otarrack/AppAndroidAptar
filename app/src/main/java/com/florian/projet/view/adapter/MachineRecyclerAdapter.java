package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.CustomItemClickListener;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MachineRecyclerAdapter extends RecyclerView.Adapter<MachineRecyclerAdapter.MyHolder> {
    private ArrayList<Machine> machineMESList;
    private CustomItemClickListener listener;

    public MachineRecyclerAdapter(ArrayList<Machine> machineMESList, CustomItemClickListener listener) {
        this.machineMESList = machineMESList;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item_machine, viewGroup, false);

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
        holder.nameTextView.setText(machineMESList.get(position).getMachineName());
        holder.qtProducedTextView.setText(NumberFormat.getInstance().format(machineMESList.get(position).getGoodQualityProduced()));
        holder.omeTextView.setText(String.format("%s%%", NumberFormat.getInstance().format(machineMESList.get(position).getAverageOME() * 100)));
        holder.scrapRateTextView.setText(String.format("%s%%", NumberFormat.getInstance().format(machineMESList.get(position).getScrapRate() * 100)));
    }

    @Override
    public int getItemCount() {
        return machineMESList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView qtProducedTextView;
        TextView omeTextView;
        TextView scrapRateTextView;


        MyHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_machine_num);
            qtProducedTextView = itemView.findViewById(R.id.recycler_machine_1_res);
            omeTextView = itemView.findViewById(R.id.recycler_machine_2_res);
            scrapRateTextView = itemView.findViewById(R.id.recycler_machine_3_res);
        }
    }
}
