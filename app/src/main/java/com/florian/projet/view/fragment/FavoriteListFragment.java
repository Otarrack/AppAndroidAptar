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
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.MachineDetailActivity;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.FavorisViewModel;
import com.florian.projet.viewModel.MachineViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListFragment extends Fragment {
    private FavorisViewModel favorisViewModel;
    private MachineViewModel machineViewModel;
    private RecyclerView recyclerViewMachine;

    private Context context;

    public FavoriteListFragment() {
    }

    public static FavoriteListFragment newInstance() {
        FavoriteListFragment fragment = new FavoriteListFragment();
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
        final View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        favorisViewModel.getAllFavMachine(new MachineDatabaseManager.GetAllFavTask.Callback() {
            @Override
            public void onSuccess(List<Machine> machineList) {
                setRecyclerViewMachine(view, new ArrayList<>(machineList));
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(getContext(),
                        R.string.machine_get_fav_error,
                        Toast.LENGTH_SHORT).show();
            }
        });

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

        MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(machineList, true, new CustomItemClickListener() {
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