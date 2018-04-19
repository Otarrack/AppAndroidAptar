package com.florian.projet.view.fragment.menu;

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
import com.florian.projet.view.activity.SiteDetailActivity;
import com.florian.projet.view.adapter.SiteRecyclerAdapter;
import com.florian.projet.viewModel.MenuViewModel;

public class SiteFragment extends Fragment {
    private RecyclerView recyclerViewSite;
    private MenuViewModel menuViewModel;

    private Context context;

    public SiteFragment() {
    }

    public static SiteFragment newInstance() {
        SiteFragment fragment = new SiteFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu_site, container, false);

        setRecyclerViewSite(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void setRecyclerViewSite(View view) {
        recyclerViewSite = view.findViewById(R.id.production_site_recycler);
        recyclerViewSite.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        if (menuViewModel.getSiteList().size() > 0) {
            SiteRecyclerAdapter siteRecyclerAdapter = new SiteRecyclerAdapter(context, menuViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),SiteDetailActivity.class);
                    intent.putExtra("sitePosition", position);

                    startActivity(intent);
                }
            });

            recyclerViewSite.setAdapter(siteRecyclerAdapter);
        }
        recyclerViewSite.setLayoutManager(layoutManager);
    }

}
