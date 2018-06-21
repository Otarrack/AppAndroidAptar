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
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.MachineListActivity;
import com.florian.projet.view.adapter.SiteRecyclerAdapter;
import com.florian.projet.viewModel.SiteViewModel;

public class MachinePerformanceFragment extends Fragment {
    private SiteViewModel siteViewModel;
    private RecyclerView recyclerViewSite;

    private Context context;

    public MachinePerformanceFragment() {
    }

    public static MachinePerformanceFragment newInstance() {
        MachinePerformanceFragment fragment = new MachinePerformanceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        siteViewModel = SiteViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_site, container, false);

        setRecyclerViewSite(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void setRecyclerViewSite(View view) {
        recyclerViewSite = view.findViewById(R.id.site_list_recycler);
        recyclerViewSite.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (siteViewModel.getSiteList().size() > 0) {
            SiteRecyclerAdapter siteRecyclerAdapter = new SiteRecyclerAdapter(context, siteViewModel.getSiteList(), new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),MachineListActivity.class);

                    intent.putExtra("SiteEnum", siteViewModel.getSiteList().get(position));

                    startActivity(intent);
                }
            });

            recyclerViewSite.setAdapter(siteRecyclerAdapter);
        }
        recyclerViewSite.setLayoutManager(layoutManager);
    }
}