package com.florian.projet.quarantaine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.viewModel.MenuViewModel;

public class MachineFragment extends Fragment {
    RecyclerView recyclerViewMachine;
    MenuViewModel menuViewModel;

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

        menuViewModel = MenuViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_machine, container, false);

        setRecyclerViewMachine(view);

        return view;
    }

    private void setRecyclerViewMachine(View view) {
        /*recyclerViewMachine = view.findViewById(R.id.production_machine_recycler);
        recyclerViewMachine.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (menuViewModel.getMachineList().size() > 0) {
            MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(menuViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),MachineDetailActivity.class);
                    intent.putExtra("machinePosition", position);

                    startActivity(intent);
                }
            });

            recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        }
        recyclerViewMachine.setLayoutManager(layoutManager);*/
    }

}
