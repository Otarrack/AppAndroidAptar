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

    public MachineRecyclerAdapter(ArrayList<Machine> data) {
        this.listMachine = data;
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
        holder.titleTextView.setText("re");
        holder.progressBar.setProgress(position);
    }

    @Override
    public int getItemCount() {
        return listMachine.size();
    }

}
