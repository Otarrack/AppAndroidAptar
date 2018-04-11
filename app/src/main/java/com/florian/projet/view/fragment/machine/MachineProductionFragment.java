package com.florian.projet.view.fragment.machine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.viewModel.MachineViewModel;

public class MachineProductionFragment extends Fragment {
    MachineViewModel machineViewModel;
    RecyclerView recyclerViewOf;

    TextView numArticleTextView;
    TextView siteTextView;
    TextView machineTextView;

    TextView declarationDateTextView;
    TextView startingDateTextView;
    TextView endingDateTextView;

    TextView volumeTextView;
    TextView wasteQtTextView;
    TextView wastePercentTextView;

    public MachineProductionFragment() {
    }

    public static MachineProductionFragment newInstance() {
        MachineProductionFragment fragment = new MachineProductionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        machineViewModel = MachineViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_machine_production, container, false);

        initViews(view);
        setValuesOnViews();


        return view;
    }

    private void initViews(View view) {

    }

    private void setValuesOnViews() {

    }

}
