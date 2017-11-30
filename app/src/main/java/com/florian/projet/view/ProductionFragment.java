package com.florian.projet.view;

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
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.manager.ArticleManager;
import com.florian.projet.manager.MachineManager;
import com.florian.projet.manager.SiteManager;
import com.florian.projet.model.Article;
import com.florian.projet.model.Machine;
import com.florian.projet.model.Site;
import com.florian.projet.viewModel.ArticleRecyclerAdapter;
import com.florian.projet.viewModel.CustomItemClickListener;
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
            SiteRecyclerAdapter siteRecyclerAdapter = new SiteRecyclerAdapter(arrayListSite, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProducutionDetailActivity.class);
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

        ArrayList<Machine> arrayListMachine = new ArrayList<>(machineList);

        if (arrayListMachine.size() > 0 & recyclerViewMachine != null) {
            MachineRecyclerAdapter machineRecyclerAdapter = new MachineRecyclerAdapter(arrayListMachine, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProducutionDetailActivity.class);
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

        ArrayList<Article> arrayListArticle = new ArrayList<>(articleList);

        if (arrayListArticle.size() > 0 & recyclerViewArticle != null) {
            ArticleRecyclerAdapter articleRecyclerAdapter = new ArticleRecyclerAdapter(arrayListArticle, new CustomItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    TextView title = v.findViewById(R.id.recycle_item_title);

                    Intent intent = new Intent(getContext(),ProducutionDetailActivity.class);
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
