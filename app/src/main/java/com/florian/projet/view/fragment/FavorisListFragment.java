package com.florian.projet.view.fragment;

import android.content.Context;
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
import com.florian.projet.model.Machine;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.MachineDetailActivity;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.FavorisViewModel;
import com.florian.projet.viewModel.MachineViewModel;

import java.util.ArrayList;

public class FavorisListFragment extends Fragment {
    private FavorisViewModel favorisViewModel;
    private MachineViewModel machineViewModel;
    private RecyclerView recyclerViewMachine;

    private Context context;

    public FavorisListFragment() {
    }

    public static FavorisListFragment newInstance() {
        FavorisListFragment fragment = new FavorisListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favorisViewModel = FavorisViewModel.getInstance();
        machineViewModel = MachineViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        setRecyclerViewMachine(view, favorisViewModel.getMachineList());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void setRecyclerViewMachine(View view, final ArrayList<Machine> machineList) {
        recyclerViewMachine = view.findViewById(R.id.favorite_list_recycler);
        recyclerViewMachine.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewMachine.setLayoutManager(layoutManager);

        MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(machineList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                machineViewModel.setCurrentMachine(machineList.get(position));

                Intent intent = new Intent(context, MachineDetailActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        recyclerViewMachine.requestFocus();

    }
}