package com.florian.projet.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.viewModel.OFViewModel;

import java.util.Objects;

public class OFDetailActivity extends AppCompatActivity {
    OFViewModel ofViewModel;

    TextView ofTextView;
    TextView articleTextView;
    TextView machineTextView;
    TextView siteTextView;
    TextView personTextView;

    TextView declarationDateTextView;
    TextView startPlannedDateTextView;
    TextView endPlannedDateTextView;

    TextView qtAskedTextView;
    TextView volumeTextView;
    TextView wasteTextView;
    TextView cadenceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_of_detail);

        ofViewModel = OFViewModel.getInstance();

        String numOF = "";

        if(getIntent().getExtras() != null) {
             numOF = getIntent().getExtras().getString(getString(R.string.key_num));
        }

        if(!Objects.equals(numOF, "")) {
            ofViewModel.setCurrentOF(numOF);
        } else {
            displayErrorFinish();
        }

        if (ofViewModel.getCurrentOF() == null) {
            displayErrorFinish();
        }

        initViews();
        setValuesToViews();
    }

    private void displayErrorFinish() {
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        alertDialogBuilder.setMessage(R.string.article_detail_activity_loading_fail);
        alertDialogBuilder.show();
    }

    private void initViews() {
        ofTextView = findViewById(R.id.of_detail_of_res);
        articleTextView = findViewById(R.id.of_detail_article_res);
        machineTextView = findViewById(R.id.of_detail_machine_res);
        siteTextView = findViewById(R.id.of_detail_site_res);
        personTextView = findViewById(R.id.of_detail_person_res);

        declarationDateTextView = findViewById(R.id.of_detail_declaration_res);
        startPlannedDateTextView = findViewById(R.id.of_detail_started_res);
        endPlannedDateTextView = findViewById(R.id.of_detail_ending_res);

        qtAskedTextView = findViewById(R.id.of_detail_qt_asked_res);
        volumeTextView = findViewById(R.id.of_detail_volume_res);
        wasteTextView = findViewById(R.id.of_detail_waste_res);
        cadenceTextView = findViewById(R.id.of_detail_cadence_res);
    }

    private void setValuesToViews() {
        ofTextView.setText(ofViewModel.getNumOF());
        articleTextView.setText(ofViewModel.getNumArticle());
        machineTextView.setText(ofViewModel.getNameMachine());
        siteTextView.setText(ofViewModel.getNameSite());
        personTextView.setText(""); //TODO Verifier

        declarationDateTextView.setText(ofViewModel.getDeclarationDate());
        startPlannedDateTextView.setText(ofViewModel.getStartingDate());
        endPlannedDateTextView.setText(ofViewModel.getEndingDate());

        qtAskedTextView.setText(String.valueOf(ofViewModel.getQtAsked()));
        volumeTextView.setText(String.valueOf(ofViewModel.getVolume()));
        wasteTextView.setText(String.valueOf(ofViewModel.getWaste()));
        cadenceTextView.setText(String.valueOf(ofViewModel.getCadence()));
    }
}
