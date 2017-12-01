package com.florian.projet.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.model.Machine;
import com.florian.projet.view.ProductionMachineFragment.OnListFragmentInteractionListener;

import java.util.List;

public class ProductionMachineRecyclerViewAdapter extends RecyclerView.Adapter<ProductionMachineRecyclerViewAdapter.ViewHolder> {

    private final List<Machine> machineList;
    private final OnListFragmentInteractionListener mListener;

    public ProductionMachineRecyclerViewAdapter(OnListFragmentInteractionListener listener) {
        machineList = MachineManager.getInstance().getListMachine();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_production_machine_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.machine = machineList.get(position);
        holder.textViewId.setText(String.valueOf(machineList.get(position).getId()));
        holder.textViewContent.setText(String.valueOf(machineList.get(position).getVolume()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListMachineFragmentInteraction(holder.machine);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return machineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final TextView textViewId;
        final TextView textViewContent;
        Machine machine;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewId = view.findViewById(R.id.production_machine_item_id);
            this.textViewContent = view.findViewById(R.id.production_machine_item_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewContent.getText() + "'";
        }
    }
}
