package com.florian.projet.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.view.activity.ProductionDetailActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.view.adapter.MachineRecyclerAdapter;
import com.florian.projet.view.adapter.SiteRecyclerAdapter;
import com.florian.projet.viewModel.ProductionViewModel;

public class ProductionFragment extends Fragment {
    RecyclerView recyclerViewSite;
    RecyclerView recyclerViewMachine;
    RecyclerView recyclerViewArticle;
    ProductionViewModel productionViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productionViewModel = ProductionViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_production, container, false);

        setRecyclerViewSite(view);
        setRecyclerViewMachine(view);
        setRecyclerViewArticle(view);

        return view;
    }

    private void setRecyclerViewSite(View view) {
        recyclerViewSite = view.findViewById(R.id.production_recycler_site);
        recyclerViewSite.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (productionViewModel.getSiteList().size() > 0) {
            SiteRecyclerAdapter siteRecyclerAdapter = new SiteRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProductionDetailActivity.class);
                    intent.putExtra("page",getString(R.string.site));
                    intent.putExtra("title",title.getText());

                    startActivity(intent);
                }
            });

            recyclerViewSite.setAdapter(siteRecyclerAdapter);
        }
        recyclerViewSite.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewMachine(View view) {
        recyclerViewMachine = view.findViewById(R.id.production_recycler_machine);
        recyclerViewMachine.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (productionViewModel.getMachineList().size() > 0) {
            MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProductionDetailActivity.class);
                    intent.putExtra("page",getString(R.string.machine));
                    intent.putExtra("title",title.getText());

                    startActivity(intent);
                }
            });

            recyclerViewMachine.setAdapter(machineRecyclerAdapter);
        }
        recyclerViewMachine.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.production_recycler_article);
        recyclerViewArticle.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (productionViewModel.getArticleList().size() > 0) {
            ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(productionViewModel, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProductionDetailActivity.class);
                    intent.putExtra("page",getString(R.string.article));
                    intent.putExtra("title",title.getText());

                    startActivity(intent);
                }
            });

            recyclerViewArticle.setAdapter(articleRecyclerAdapter);
        }
        recyclerViewArticle.setLayoutManager(layoutManager);
    }

}
