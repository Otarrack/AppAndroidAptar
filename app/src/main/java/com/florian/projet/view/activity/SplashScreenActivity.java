package com.florian.projet.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dropbox.core.v2.users.FullAccount;
import com.florian.projet.R;
import com.florian.projet.asyncTasks.DropboxDownloadDataFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;
import com.florian.projet.asyncTasks.ParseMESFileTask;
import com.florian.projet.model.MachineMESFile;
import com.florian.projet.viewModel.SplashScreenViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    private SplashScreenViewModel splashScreenViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenViewModel = SplashScreenViewModel.getInstance();
        initApp();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initApp() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Connexion...");
        dialog.show();

        splashScreenViewModel.initApp(new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount fullAccount) {

                downloadFile();
            }

            @Override
            public void onError(Exception e) {
                dialog.dismiss();

                failLoadingData();
                Log.d("ERREUR getFullAccount()", e.getMessage());
            }
        });
    }

    private void downloadFile() {
        File path = getFilesDir();

        dialog.setMessage("Téléchargement du fichier...");
        splashScreenViewModel.downloadDataFile(path, new DropboxDownloadDataFileTask.Callback() {
            @Override
            public void onSuccess(File file) {

                if (file != null) {
                    sendDownloadedFile(file);

                    readFile(file);
                } else {
                    dialog.dismiss();
                    failLoadingData();
                }
            }

            @Override
            public void onFailed(Exception e) {
                dialog.dismiss();
                failLoadingData();
            }
        });
    }

    private void readFile(File file) {
        try {
            dialog.setMessage("Lecture du fichier...");
            splashScreenViewModel.parseXlsFile(file, new ParseMESFileTask.Callback() {
                @Override
                public void onSuccess(ArrayList<MachineMESFile> dataLineList) {
                    if (dataLineList.isEmpty()) {
                        dialog.dismiss();
                        failLoadingData();
                    } else {
                        dialog.dismiss();
                        startMainActivity();
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingData();
                }
            });
        } catch (IOException e) {
            Log.d("RATÉ", "" + e.getMessage());
            failLoadingData();
        }
    }

    private void sendDownloadedFile(File file) {
        // Tell android about the file
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    private void failLoadingData() {
        new AlertDialog.Builder(this)
                .setTitle("Erreur")
                .setMessage("Impossible de récupérer les données en ligne")
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO : Définir ce qu'il se passe si pas possible de récupérer le
                        // TODO : fichier -> BDD locale ?
                    }
                }).show();
    }
}
