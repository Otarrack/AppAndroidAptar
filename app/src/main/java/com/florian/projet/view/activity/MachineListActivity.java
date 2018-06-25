package com.florian.projet.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.viewModel.MachineViewModel;

import java.util.ArrayList;
import java.util.List;

public class MachineListActivity extends AppCompatActivity {
    RecyclerView recyclerViewMachine;
    Toolbar toolbar;

    MachineViewModel machineViewModel;

    ArrayList<Machine> machineArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_list);

        machineViewModel = MachineViewModel.getInstance();
        initToolbar();

        SiteEnum siteEnum = (SiteEnum) getIntent().getSerializableExtra("SiteEnum");
        List<Integer> siteList = new ArrayList<>();

        for (int i : siteEnum.getSiteNum()) {
            siteList.add(i);
        }

        machineViewModel.getMachineBySite(siteList, new MachineDatabaseManager.GetBySiteTask.Callback() {
            @Override
            public void onSuccess(List<Machine> machineList) {
                machineArrayList = new ArrayList<>(machineList);
                initSearchView();
                setRecyclerViewMachine();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("GET MACHINE SITE", e.getMessage());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.machine_list_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initSearchView() {
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = findViewById(R.id.machine_list_search);
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    setNewRecyclerViewMachine(machineArrayList);
                } else {
                    doMySearch(newText);
                }
                searchView.requestFocus();
                return false;
            }
        });
        searchView.clearFocus();
    }

    private void doMySearch(String query) {
        ArrayList<Machine> searchMachineList = new ArrayList<>();
        for (Machine machineMES : machineArrayList) {
            if (machineMES.getMachineName().toLowerCase().contains(query.toLowerCase())) {
                searchMachineList.add(machineMES);
            }
        }

        setNewRecyclerViewMachine(searchMachineList);
    }

    private void setRecyclerViewMachine() {
        recyclerViewMachine = findViewById(R.id.machine_list_recycler);
        recyclerViewMachine.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMachine.setLayoutManager(layoutManager);

        setNewRecyclerViewMachine(machineArrayList);

    }

    private void setNewRecyclerViewMachine(final ArrayList<Machine> machineList) {
        MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(machineList, false, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                machineViewModel.setCurrentMachine(machineList.get(position));

                Intent intent = new Intent(getApplicationContext(), MachineDetailActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        recyclerViewMachine.requestFocus();
    }
}
