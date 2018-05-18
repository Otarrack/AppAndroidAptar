package com.florian.projet.quarantaine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.viewModel.ArticleViewModel;

public class ArticleProductionFragment extends Fragment {
    ArticleViewModel articleViewModel;

    TextView numArticleTextView;
    TextView siteTextView;
    TextView machineTextView;

    TextView declarationDateTextView;
    TextView startingDateTextView;
    TextView endingDateTextView;

    TextView volumeTextView;
    TextView wasteQtTextView;
    TextView wastePercentTextView;

    public ArticleProductionFragment() {
    }

    public static ArticleProductionFragment newInstance() {
        ArticleProductionFragment fragment = new ArticleProductionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleViewModel = ArticleViewModel.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_production, container, false);

        initViews(view);
        setValuesOnViews();

        return view;
    }

    private void initViews(View view) {
        numArticleTextView = view.findViewById(R.id.machine_mcu_mcu_res);
        machineTextView = view.findViewById(R.id.article_production_global_machine_res);
        siteTextView = view.findViewById(R.id.article_production_global_site_res);

        declarationDateTextView = view.findViewById(R.id.article_production_date_declaration_res);
        startingDateTextView = view.findViewById(R.id.article_production_date_starting_res);
        endingDateTextView = view.findViewById(R.id.article_production_date_ending_res);

        volumeTextView = view.findViewById(R.id.article_production_data_volume_res);
        wasteQtTextView = view.findViewById(R.id.article_production_data_waste_qt_res);
        wastePercentTextView = view.findViewById(R.id.article_production_data_waste_percent_res);
    }

    private void setValuesOnViews() {
        numArticleTextView.setText(articleViewModel.getNumArticle());
        machineTextView.setText(articleViewModel.getNomMachine());
        siteTextView.setText(articleViewModel.getNomSite());

        declarationDateTextView.setText(articleViewModel.getDeclarationDate());
        startingDateTextView.setText(articleViewModel.getStartingDate());
        endingDateTextView.setText(articleViewModel.getEndingDate());

        volumeTextView.setText(String.valueOf(articleViewModel.getVolumeInPeriod()));
        wasteQtTextView.setText(String.valueOf(articleViewModel.getWasteQtInPeriod()));
        wastePercentTextView.setText(String.valueOf(articleViewModel.getWastePercentInPeriod()));
    }

}
