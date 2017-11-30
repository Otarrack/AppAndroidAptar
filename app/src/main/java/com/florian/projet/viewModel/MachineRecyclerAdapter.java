package com.florian.projet.viewModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.model.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineRecyclerAdapter extends RecyclerView.Adapter<MyHolder> {
    private List<Machine> listMachine;

    private CustomItemClickListener listener;

    public MachineRecyclerAdapter(ArrayList<Machine> data, CustomItemClickListener listener) {
        this.listMachine = data;
        this.listener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
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
        holder.titleTextView.setText("re");
        holder.progressBar.setProgress(position);
    }

    @Override
    public int getItemCount() {
        return listMachine.size();
    }

}
