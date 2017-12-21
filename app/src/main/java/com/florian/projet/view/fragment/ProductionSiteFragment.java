package com.florian.projet.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.activity.ProductionDetailActivity;
import com.florian.projet.view.adapter.SiteRecyclerAdapter;
import com.florian.projet.viewModel.ProductionViewModel;

public class ProductionSiteFragment extends Fragment {
    RecyclerView recyclerViewSite;
    ProductionViewModel productionViewModel;

    public static ProductionSiteFragment newInstance() {
        ProductionSiteFragment fragment = new ProductionSiteFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productionViewModel = ProductionViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production_site, container, false);

        setRecyclerViewSite(view);

        return view;
    }

    private void setRecyclerViewSite(View view) {
        recyclerViewSite = view.findViewById(R.id.production_site_recycler);
        recyclerViewSite.setHasFixedSize(true);
        recyclerViewSite.setNestedScrollingEnabled(false);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        if (productionViewModel.getSiteList().size() > 0) {
            SiteRecyclerAdapter siteRecyclerAdapter = new SiteRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent intent = new Intent(getContext(),ProductionDetailActivity.class);

                    startActivity(intent);
                }
            });

            recyclerViewSite.setAdapter(siteRecyclerAdapter);
        }
        recyclerViewSite.setLayoutManager(layoutManager);
    }

}
