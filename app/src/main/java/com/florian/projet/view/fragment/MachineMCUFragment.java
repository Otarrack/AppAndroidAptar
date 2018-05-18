package com.florian.projet.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.viewModel.MachineViewModel;

import java.text.NumberFormat;

public class MachineMCUFragment extends Fragment {
    MachineViewModel machineViewModel;

    private TextView maxTimeOpennedTextView;
    private TextView holidaysTextView;
    private TextView plannedStopTextView;
    private TextView breakTimeTextView;
    private TextView preventiveMaintenanceTextView;
    private TextView missingOFTextView;
    private TextView sampleTextView;
    private TextView actualProductiveTimeTextView;
    private TextView mcuTextView;

    public MachineMCUFragment() {

    }

    public static MachineMCUFragment newInstance() {
        MachineMCUFragment fragment = new MachineMCUFragment();
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
        View view = inflater.inflate(R.layout.fragment_machine_mcu, container, false);

        initViews(view);
        setValuesOnViews();


        return view;
    }

    private void initViews(View view) {
        maxTimeOpennedTextView = view.findViewById(R.id.machine_mcu_max_time_openned_res);
        holidaysTextView = view.findViewById(R.id.machine_mcu_holidays_res);
        plannedStopTextView = view.findViewById(R.id.machine_mcu_planned_stop_res);
        breakTimeTextView = view.findViewById(R.id.machine_mcu_break_time_res);
        preventiveMaintenanceTextView = view.findViewById(R.id.machine_mcu_preventive_maintenance_res);
        missingOFTextView = view.findViewById(R.id.machine_mcu_missing_of_res);
        sampleTextView = view.findViewById(R.id.machine_mcu_sample_res);
        actualProductiveTimeTextView = view.findViewById(R.id.machine_mcu_actual_productive_time_res);
        mcuTextView = view.findViewById(R.id.machine_mcu_mcu_res);
    }

    private void setValuesOnViews() {
        MachineMESFile machine = machineViewModel.getCurrentMachine();

        if (machine != null) {
            maxTimeOpennedTextView.setText(machine.getMaxTimeOpenned());
            holidaysTextView.setText(machine.getHolidays());
            plannedStopTextView.setText(machine.getPlannedStop());
            breakTimeTextView.setText(machine.getBreakTime());
            preventiveMaintenanceTextView.setText(machine.getPreventiveMaintenance());
            missingOFTextView.setText(machine.getMissingOF());
            sampleTextView.setText(machine.getSample());
            actualProductiveTimeTextView.setText(machine.getActualProductiveTime());
            mcuTextView.setText(String.format("%s%%", NumberFormat.getInstance().format(machine.getMcu() * 100)));
        }
    }

}
