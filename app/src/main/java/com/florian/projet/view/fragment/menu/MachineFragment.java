package com.florian.projet.view.fragment.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.MachineDetailActivity;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.ProductionViewModel;

public class MachineFragment extends Fragment {
    RecyclerView recyclerViewMachine;
    ProductionViewModel productionViewModel;

    public MachineFragment() {

    }

    public static MachineFragment newInstance() {
        MachineFragment fragment = new MachineFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu_machine, container, false);

        setRecyclerViewMachine(view);

        return view;
    }

    private void setRecyclerViewMachine(View view) {
        recyclerViewMachine = view.findViewById(R.id.production_machine_recycler);
        recyclerViewMachine.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (productionViewModel.getMachineList().size() > 0) {
            MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),MachineDetailActivity.class);
                    intent.putExtra("machinePosition", position);

                    startActivity(intent);
                }
            });

            recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        }
        recyclerViewMachine.setLayoutManager(layoutManager);
    }

}
