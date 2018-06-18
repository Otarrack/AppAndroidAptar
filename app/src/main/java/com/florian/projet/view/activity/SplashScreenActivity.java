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
import com.florian.projet.manager.DatabaseManager;
import com.florian.projet.model.Machine;
import com.florian.projet.tools.SimpleCallback;
import com.florian.projet.viewModel.SplashScreenViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static ProgressDialog dialog;
    private SplashScreenViewModel splashScreenViewModel;

    private ArrayList<Machine> allMachineInDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenViewModel = SplashScreenViewModel.getInstance();

        initProgressDialog();
        getLocalData();

    }

    private void initProgressDialog() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Connexion...");
        dialog.show();
    }

    private void getLocalData() {
        splashScreenViewModel.getLocalData(new DatabaseManager.GetAllTask.Callback() {
            @Override
            public void onSuccess(List<Machine> machineList) {
                allMachineInDatabase = new ArrayList<>(machineList);
                initApp();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Fail Get Local Data", e.getMessage());
                initApp();
            }
        });
    }

    private void initApp() {
        splashScreenViewModel.initApp(new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount fullAccount) {
                downloadFile();
            }

            @Override
            public void onError(Exception e) {
                Log.d("ERREUR getFullAccount()", e.getMessage());
                failLoadingMesFile();
            }
        });
    }

    private void downloadFile() {
        dialog.setMessage("Téléchargement du fichier...");

        File path = getFilesDir();
        splashScreenViewModel.downloadDataFile(path, new DropboxDownloadDataFileTask.Callback() {
            @Override
            public void onSuccess(File file) {
                if (file != null) {
                    sendDownloadedFile(file);
                    readFile(file);

                } else {
                    failLoadingMesFile();
                }
            }

            @Override
            public void onFailed(Exception e) {
                failLoadingMesFile();
            }
        });
    }

    private void sendDownloadedFile(File file) {
        // Tell android about the file
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    private void readFile(File file) {
        try {
            dialog.setMessage("Lecture du fichier...");
            splashScreenViewModel.parseXlsFile(this, file, new ParseMESFileTask.Callback() {
                @Override
                public void onSuccess(ArrayList<Machine> dataLineList) {
                    if (dataLineList.isEmpty()) {
                        failLoadingMesFile();
                    } else {

                        getMachineListWithFav(dataLineList);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingMesFile();
                }
            });
        } catch (IOException e) {
            Log.d("Catch Read File", "" + e.getMessage());
            failLoadingMesFile();
        }
    }

    private void getMachineListWithFav(ArrayList<Machine> allMachineInMESFile) {
        ArrayList<Machine> finalMachineList = splashScreenViewModel.initMachineListWithFav(allMachineInMESFile, allMachineInDatabase);

        refreshAllMachineInDatabase(finalMachineList);
    }

    private void refreshAllMachineInDatabase(final ArrayList<Machine> machineArrayList) {
        splashScreenViewModel.refreshAllMachineInDatabase(machineArrayList, new SimpleCallback() {
            @Override
            public void onSuccess() {
                getLocalDataAfterRefresh();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("POURQUOI ?", e.getMessage());
                failLoadingData();
            }
        });
    }
    private void getLocalDataAfterRefresh() {
        splashScreenViewModel.getLocalData(new DatabaseManager.GetAllTask.Callback() {
            @Override
            public void onSuccess(List<Machine> machineList) {
                allMachineInDatabase = new ArrayList<>(machineList);
                initSiteEnum(allMachineInDatabase);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Fail Get Data Refresh", e.getMessage());
                failLoadingData();
            }
        });
    }

    private void failLoadingMesFile() {
        dialog.dismiss();
        if (allMachineInDatabase != null) {
            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("Impossible de récupérer les données en ligne, les données locales seront chargées.")
                    .setCancelable(false)
                    .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SplashScreenActivity.dialog.setMessage("Chargement des données locales...");
                            SplashScreenActivity.dialog.show();
                            initSiteEnum(allMachineInDatabase);
                        }
                    }).show();
        } else {
            failLoadingData();
        }
    }

    private void initSiteEnum(ArrayList<Machine> machineArrayList) {
        splashScreenViewModel.initSiteEnum(machineArrayList);

        startMainActivity();
    }

    private void startMainActivity() {
        dialog.dismiss();
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    private void failLoadingData() {
        dialog.dismiss();
        new AlertDialog.Builder(this)
                .setTitle("Impossible de lancer l'application")
                .setMessage("Chargement en ligne impossible et aucune données locales trouvées")
                .setCancelable(false)
                .setPositiveButton("Fermer l'application", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
