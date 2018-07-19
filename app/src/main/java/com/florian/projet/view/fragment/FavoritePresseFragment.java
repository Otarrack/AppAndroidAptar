package com.florian.projet.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.florian.projet.R;
import com.florian.projet.bdd.relation.PresseWithData;
import com.florian.projet.tools.CustomItemClickListener;
import com.florian.projet.tools.PresseWithDataCallback;
import com.florian.projet.view.activity.ArticleDetailActivity;
import com.florian.projet.view.activity.PeriodActivity;
import com.florian.projet.view.activity.PresseDetailActivity;
import com.florian.projet.view.adapter.PresseRecyclerAdapter;
import com.florian.projet.viewModel.PresseViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritePresseFragment extends Fragment {
    private PresseViewModel presseViewModel;
    RecyclerView recyclerViewPresse;

    private Context context;

    public FavoritePresseFragment() {
    }

    public static FavoritePresseFragment newInstance() {
        FavoritePresseFragment fragment = new FavoritePresseFragment();
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
        final View view = inflater.inflate(R.layout.fragment_favorites_presse, container, false);

        setRecyclerViewPresse(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;

        super.onAttach(context);
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
        presseViewModel.getAllFavPresseByPeriod(new PresseWithDataCallback() {
            @Override
            public void onSuccess(List<PresseWithData> presseWithDataList) {
                setNewRecyclerAdapterPresse(new ArrayList<>(presseWithDataList));
            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(getContext(),
                        R.string.machine_get_fav_error,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerViewPresse(View view) {
        recyclerViewPresse = view.findViewById(R.id.favorites_presse_recycler);
        recyclerViewPresse.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewPresse.setLayoutManager(layoutManager);

    }

    private void setNewRecyclerAdapterPresse(final ArrayList<PresseWithData> presseWithDataArrayList) {
        PresseRecyclerAdapter presseRecyclerAdapter = new PresseRecyclerAdapter(presseWithDataArrayList, true, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (presseWithDataArrayList.get(position).getDataList() == null || presseWithDataArrayList.get(position).getDataList().size() == 0) {
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