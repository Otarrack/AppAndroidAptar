package com.florian.projet.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.florian.projet.R;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.Machine;
import com.florian.projet.model.Site;
import com.florian.projet.viewModel.ArticleRecyclerAdapter;
import com.florian.projet.viewModel.MachineRecyclerAdapter;
import com.florian.projet.viewModel.SiteRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductionFragment extends Fragment {
    RecyclerView recyclerViewSite;
    RecyclerView recyclerViewMachine;
    RecyclerView recyclerViewArticle;
    List<Site> siteList;
    List<Machine> machineList;
    List<Article> articleList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        siteList = SiteManager.getInstance().getListSite();
        machineList = MachineManager.getInstance().getListMachine();
        articleList = ArticleManager.getInstance().getListArticle();
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

        ArrayList<Site> arrayListSite = new ArrayList<>(siteList);

        if (arrayListSite.size() > 0 & recyclerViewSite != null) {
            recyclerViewSite.setAdapter(new SiteRecyclerAdapter(arrayListSite));
        }
        recyclerViewSite.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewMachine(View view) {
        recyclerViewMachine = view.findViewById(R.id.production_recycler_machine);
        recyclerViewMachine.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        ArrayList<Machine> arrayListMachine = new ArrayList<>(machineList);

        if (arrayListMachine.size() > 0 & recyclerViewMachine != null) {
            recyclerViewMachine.setAdapter(new MachineRecyclerAdapter(arrayListMachine));
        }
        recyclerViewMachine.setLayoutManager(layoutManager);
    }

    private void setRecyclerViewArticle(View view) {
        recyclerViewArticle = view.findViewById(R.id.production_recycler_article);
        recyclerViewArticle.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        ArrayList<Article> arrayListArticle = new ArrayList<>(articleList);

        if (arrayListArticle.size() > 0 & recyclerViewArticle != null) {
            recyclerViewArticle.setAdapter(new ArticleRecyclerAdapter(arrayListArticle));
        }
        recyclerViewArticle.setLayoutManager(layoutManager);
    }

}
