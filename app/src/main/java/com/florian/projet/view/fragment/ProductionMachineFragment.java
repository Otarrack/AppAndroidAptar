package com.florian.projet.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.ProductionDetailActivity;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.ProductionViewModel;

public class ProductionMachineFragment extends Fragment {
    RecyclerView recyclerViewMachine;
    ProductionViewModel productionViewModel;

    public static ProductionMachineFragment newInstance() {
        ProductionMachineFragment fragment = new ProductionMachineFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productionViewModel = ProductionViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production_machine, container, false);

        setRecyclerViewMachine(view);

        return view;
    }

    private void setRecyclerViewMachine(View view) {
        recyclerViewMachine = view.findViewById(R.id.production_machine_recycler);
        recyclerViewMachine.setHasFixedSize(true);
        recyclerViewMachine.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        if (productionViewModel.getMachineList().size() > 0) {
            MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),ProductionDetailActivity.class);

                    startActivity(intent);
                }
            });

            recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        }
        recyclerViewMachine.setLayoutManager(layoutManager);
    }

}
