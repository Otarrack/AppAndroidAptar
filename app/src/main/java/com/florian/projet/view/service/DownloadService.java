package com.florian.projet.view.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.florian.projet.R;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.asyncTasks.ParseQuantityFileTask;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.model.QuantityFileLine;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.SimpleCallback;
import com.florian.projet.view.activity.SplashScreenActivity;
import com.florian.projet.viewModel.SplashScreenViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadService extends IntentService {
    public static final String STEP = "step";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.florian.projet";

    public static final int STEP_MACHINE_1 = 1;
    public static final int STEP_MACHINE_2 = 2;
    public static final int STEP_MACHINE_3 = 3;
    public static final int STEP_ARTICLE_1 = 4;
    public static final int STEP_ARTICLE_2 = 5;
    public static final int STEP_ARTICLE_3 = 6;

    public static int machineStep1State;
    public static int machineStep2State;
    public static int machineStep3State;
    public static int articleStep1State;
    public static int articleStep2State;
    public static int articleStep3State;

    public static NotificationManager manager;
    private NotificationCompat.Builder builder;

    public static SplashScreenViewModel splashScreenViewModel;
    public static boolean isDone = false;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * 'name' Used to name the worker thread, important only for debugging.
     */
    public DownloadService() {
        super("DownloadService");

        splashScreenViewModel = SplashScreenViewModel.getInstance();
        builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_folder_blue_36dp)
                        .setContentTitle("Data Management")
                        .setColor(101);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        addNotification();
        downloadFile(ApplicationManager.FILE_PERF_MACHINE_NAME);
    }

    private void downloadFile(String fileName) {
        //dialog.setMessage("Téléchargement du fichier...");

        File path = getFilesDir();
        if (fileName.equals(ApplicationManager.FILE_PERF_MACHINE_NAME)) {
            splashScreenViewModel.downloadDataFile(path, fileName, new DropboxDownloadFileTask.Callback() {
                @Override
                public void onSuccess(File file) {
                    if (file != null) {
                        sendDownloadedFile(file);
                        publishResult(STEP_MACHINE_1, Activity.RESULT_OK);

                        readMachinePerformanceFile(file);
                    } else {
                        publishResult(STEP_MACHINE_1, Activity.RESULT_CANCELED);
                        downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    publishResult(STEP_MACHINE_1, Activity.RESULT_CANCELED);
                    downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                }
            });
        } else if (fileName.equals(ApplicationManager.FILE_PERF_ARTICLE_NAME)) {
            splashScreenViewModel.downloadDataFile(path, fileName, new DropboxDownloadFileTask.Callback() {
                @Override
                public void onSuccess(File file) {
                    if (file != null) {
                        sendDownloadedFile(file);
                        publishResult(STEP_ARTICLE_1, Activity.RESULT_OK);

                        readArticlePerformanceFile(file);
                    } else {
                        publishResult(STEP_ARTICLE_1, Activity.RESULT_CANCELED);
                        downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    publishResult(STEP_ARTICLE_1, Activity.RESULT_CANCELED);
                    downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                }
            });
        }
    }

    private void sendDownloadedFile(File file) {
        // Tell android about the file
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    private void readMachinePerformanceFile(File file) {
        try {
            splashScreenViewModel.parseMachinePerfXlsFile(file, new ParseMachinePerfFileTask.Callback() {
                @Override
                public void onSuccess(ArrayList<Machine> dataLineList) {
                    if (dataLineList.isEmpty()) {
                        publishResult(STEP_MACHINE_2, Activity.RESULT_CANCELED);
                        downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                    } else {

                        publishResult(STEP_MACHINE_2, Activity.RESULT_OK);
                        refreshAllMachineInDatabase(splashScreenViewModel.initMachineListWithSite(dataLineList));
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    publishResult(STEP_MACHINE_2, Activity.RESULT_CANCELED);
                    downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                }
            });
        } catch (IOException e) {
            Log.d("Read Machine File", e.getMessage());
            publishResult(STEP_MACHINE_2, Activity.RESULT_CANCELED);
            downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
        }
    }

    private void refreshAllMachineInDatabase(final ArrayList<Machine> machineArrayList) {
        splashScreenViewModel.refreshAllMachineInDatabase(machineArrayList, new SimpleCallback() {
            @Override
            public void onSuccess() {
                publishResult(STEP_MACHINE_3, Activity.RESULT_OK);
                downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Refresh Machine Data", e.getMessage());

                publishResult(STEP_MACHINE_3, Activity.RESULT_CANCELED);
                downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
            }
        });
    }

    private void readArticlePerformanceFile(File file) {
        try {
            splashScreenViewModel.parseArticlePerfXlsFile(file, new ParseQuantityFileTask.Callback() {
                @Override
                public void onSuccess(ArrayList<QuantityFileLine> quantityFileLineArrayList) {
                    if (quantityFileLineArrayList.isEmpty()) {
                        publishResult(STEP_ARTICLE_2, Activity.RESULT_CANCELED);
                        finishService();
                    } else {
                        publishResult(STEP_ARTICLE_2, Activity.RESULT_OK);

                        refreshAllArticleInDatabase(quantityFileLineArrayList);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    publishResult(STEP_ARTICLE_2, Activity.RESULT_CANCELED);
                    finishService();
                }
            });
        } catch (IOException e) {
            Log.d("Read Article File", e.getMessage());
            publishResult(STEP_ARTICLE_2, Activity.RESULT_CANCELED);
            finishService();
        }
    }

    private void refreshAllArticleInDatabase(final ArrayList<QuantityFileLine> quantityFileLineArrayList) {
        splashScreenViewModel.refreshAllArticleInDatabase(quantityFileLineArrayList, new SimpleCallback() {
            @Override
            public void onSuccess() {
                publishResult(STEP_ARTICLE_3, Activity.RESULT_OK);
                finishService();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Refresh Article Data", e.getMessage());
                publishResult(STEP_ARTICLE_3, Activity.RESULT_CANCELED);
                finishService();
            }
        });
    }

    private void finishService() {
        isDone = true;
        endNotification();
    }

    private void addNotification() {
         builder.setContentText("Téléchargement des données en cours");
         Intent intent = new Intent(this, SplashScreenActivity.class);
         PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         builder.setContentIntent(contentIntent);
          //Add as notification
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(0, builder.build());
        }
    }

    private void  endNotification() {
        if (manager != null) {
            manager.cancelAll();

            builder.setContentText("Téléchargement terminé");
            manager.notify(0, builder.build());
        }
    }

    private void publishResult(int step, int result) {
        Intent intentBack = new Intent(NOTIFICATION);
        intentBack.putExtra(STEP, step);
        intentBack.putExtra(RESULT, result);

        sendBroadcast(intentBack);
    }
}
