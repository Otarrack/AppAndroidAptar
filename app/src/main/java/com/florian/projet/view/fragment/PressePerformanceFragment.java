package com.florian.projet.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.bdd.relation.PresseWithData;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.tools.PresseWithDataCallback;
import com.florian.projet.view.activity.ArticleDetailActivity;
import com.florian.projet.view.activity.PeriodActivity;
import com.florian.projet.view.activity.PresseDetailActivity;
import com.florian.projet.view.adapter.ArticleRecyclerAdapter;
import com.florian.projet.view.adapter.PresseRecyclerAdapter;
import com.florian.projet.viewModel.ArticleViewModel;
import com.florian.projet.viewModel.PresseViewModel;

import java.util.ArrayList;
import java.util.List;

public class PressePerformanceFragment extends Fragment {
    private PresseViewModel presseViewModel;
    private RecyclerView recyclerViewPresse;
    private ArrayList<PresseWithData> presseWithDataArrayList;
    private Context context;

    public PressePerformanceFragment() {
    }

    public static PressePerformanceFragment newInstance() {
        PressePerformanceFragment fragment = new PressePerformanceFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presseViewModel = PresseViewModel.getInstance();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_presse_perf, container, false);

        setRecyclerViewPresse(view);
        initSearchView(view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_period:
                Intent intent = new Intent(context, PeriodActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshData();
    }

    private void refreshData() {
        presseViewModel.getAllPresseByPeriod(new PresseWithDataCallback() {
            @Override
            public void onSuccess(List<PresseWithData> presseWithDataList) {
                presseWithDataArrayList = new ArrayList<>(presseWithDataList);
                setNewRecyclerAdapterPresse(new ArrayList<>(presseWithDataList));
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Presse Fragment", "Msg -> " + e.getMessage());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
    }

    private void initSearchView(View view) {
        // Get the SearchView and set the searchable configuration
        if (getActivity() != null) {
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            final SearchView searchView = view.findViewById(R.id.presse_perf_list_search);
            // Assumes current activity is the searchable activity
            if (searchManager != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.isEmpty()) {
                            setNewRecyclerAdapterPresse(presseWithDataArrayList);
                        } else {
                            doMySearch(newText);
                        }
                        searchView.requestFocus();
                        return false;
                    }
                });
                searchView.clearFocus();
            }
        }

    }

    private void doMySearch(String query) {
        ArrayList<PresseWithData> searchPresseWithDataList = new ArrayList<>();
        for (PresseWithData presseWithData : presseWithDataArrayList) {
            if (presseWithData.getPresse().getName().toLowerCase().contains(query.toLowerCase())) {
                searchPresseWithDataList.add(presseWithData);
            }
        }

        setNewRecyclerAdapterPresse(searchPresseWithDataList);
    }

    private void setRecyclerViewPresse(View view) {
        recyclerViewPresse = view.findViewById(R.id.presse_perf_recycler);
        recyclerViewPresse.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewPresse.setLayoutManager(layoutManager);

    }

    private void setNewRecyclerAdapterPresse(final ArrayList<PresseWithData> presseWithDataArrayList) {
        PresseRecyclerAdapter presseRecyclerAdapter = new PresseRecyclerAdapter(presseWithDataArrayList, false, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
            if (presseWithDataArrayList.get(position).getDataList() == null) {
                Toast.makeText(getActivity(), getString(R.string.data_not_found_in_period), Toast.LENGTH_LONG).show();
            } else {
                presseViewModel.setCurrentPresse(presseWithDataArrayList.get(position));

                Intent intent = new Intent(context, PresseDetailActivity.class);
                startActivity(intent);
            }
            }
        });

        recyclerViewPresse.setAdapter(presseRecyclerAdapter);
        recyclerViewPresse.requestFocus();
    }
}