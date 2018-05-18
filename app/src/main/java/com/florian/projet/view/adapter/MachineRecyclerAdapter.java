package com.florian.projet.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.tools.CustomItemClickListener;

import java.util.ArrayList;

public class MachineRecyclerAdapter extends RecyclerView.Adapter<MachineRecyclerAdapter.MyHolder> {
    private ArrayList<MachineMESFile> machineMESList;
    private CustomItemClickListener listener;

    public MachineRecyclerAdapter(ArrayList<MachineMESFile> machineMESList, CustomItemClickListener listener) {
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
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTextView.setText(String.valueOf(machineMESList.get(position).getMachineName()));
    }

    @Override
    public int getItemCount() {
        return machineMESList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;


        MyHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.recycler_machine_num);
        }
    }
}
