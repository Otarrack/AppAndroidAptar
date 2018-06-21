package com.florian.projet.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.viewModel.MachineViewModel;

import java.text.NumberFormat;

public class MachineOMEFragment extends Fragment {
    MachineViewModel machineViewModel;

    private TextView setupTime;
    private TextView microStopTime;
    private TextView otherStopTime;
    private TextView scrapRate;
    private TextView scrapLossEfficiency;
    private TextView cavityLossEfficiency;
    private TextView cycleTimeLossEfficiency;
    private TextView speedLostEfficiency;
    private TextView netProductiveTime;
    private TextView netProductiveTimeQME;
    private TextView goodQualityProduced;
    private TextView qme;
    private TextView averageOME;

    public MachineOMEFragment() {

    }

    public static MachineOMEFragment newInstance() {
        MachineOMEFragment fragment = new MachineOMEFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machine_ome, container, false);

        initViews(view);
        setValuesOnViews();

        return view;
    }

    private void initViews(View view) {
        setupTime = view.findViewById(R.id.machine_ome_setup_time_res);
        microStopTime = view.findViewById(R.id.machine_ome_micro_stop_time_res);
        otherStopTime = view.findViewById(R.id.machine_ome_other_stop_time_res);
        scrapRate = view.findViewById(R.id.machine_ome_scrap_rate_res);
        scrapLossEfficiency = view.findViewById(R.id.machine_ome_scrap_loss_efficiency_res);
        cavityLossEfficiency = view.findViewById(R.id.machine_ome_cavity_loss_efficiency_res);
        cycleTimeLossEfficiency = view.findViewById(R.id.machine_ome_cycle_time_loss_efficiency_res);
        speedLostEfficiency = view.findViewById(R.id.machine_ome_speed_lost_efficiency_res);
        netProductiveTime = view.findViewById(R.id.machine_ome_net_productive_time_res);
        netProductiveTimeQME = view.findViewById(R.id.machine_ome_net_productive_time_qme_res);
        goodQualityProduced = view.findViewById(R.id.machine_ome_good_quality_produced_res);
        qme = view.findViewById(R.id.machine_ome_qme_res);
        averageOME = view.findViewById(R.id.machine_ome_average_res);
    }

    private void setValuesOnViews() {
        Machine machine = machineViewModel.getCurrentMachine();

        if (machine != null) {
            setupTime.setText(machine.getSetupTime());
            microStopTime.setText(machine.getMicroStopTime());
            otherStopTime.setText(machine.getOtherStopTime());
            scrapRate.setText(String.format("%s%%", NumberFormat.getInstance().format(machine.getScrapRate() * 100)));
            scrapLossEfficiency.setText(machine.getScrapLossEfficiency());
            cavityLossEfficiency.setText(machine.getCavityLossEfficiency());
            cycleTimeLossEfficiency.setText(machine.getCycleTimeLossEfficiency());
            speedLostEfficiency.setText(machine.getSpeedLostEfficiency());
            netProductiveTime.setText(machine.getNetProductiveTime());
            netProductiveTimeQME.setText(machine.getNetProductiveTimeQME());
            goodQualityProduced.setText(NumberFormat.getInstance().format(machine.getGoodQualityProduced()));
            qme.setText(String.format("%s%%", NumberFormat.getInstance().format(machine.getQme() * 100)));
            averageOME.setText(String.format("%s%%", NumberFormat.getInstance().format(machine.getAverageOME() * 100)));
        }
    }

}
