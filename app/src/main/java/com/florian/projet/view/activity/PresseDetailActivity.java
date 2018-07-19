package com.florian.projet.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.florian.projet.R;
import com.florian.projet.view.adapter.OFDataRecyclerAdapter;
import com.florian.projet.viewModel.PresseViewModel;

import java.util.ArrayList;

public class PresseDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPresse;
    private PresseViewModel presseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presse_detail);

        presseViewModel = PresseViewModel.getInstance();
        setRecyclerViewPresse();
    }

    private void setRecyclerViewPresse() {
        recyclerViewPresse = findViewById(R.id.presse_data_recycler);
        recyclerViewPresse.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPresse.setLayoutManager(layoutManager);

        setNewRecyclerAdapterPresse();

    }

    private void setNewRecyclerAdapterPresse() {
        OFDataRecyclerAdapter ofDataRecyclerAdapter = new OFDataRecyclerAdapter(new ArrayList<>(presseViewModel.getCurrentPresse().getDataList()));

        recyclerViewPresse.setAdapter(ofDataRecyclerAdapter);
        recyclerViewPresse.requestFocus();
    }
}
