package com.florian.projet.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.florian.projet.R;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.view.service.DownloadService;
import com.florian.projet.viewModel.SplashScreenViewModel;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private final int STEP_STATE_PROGRESS= 1;
    private final int STEP_STATE_OK = 2;
    private final int STEP_STATE_LOCAL_ONLY = 3;
    private final int STEP_STATE_FAIL = 4;

    private TextView machineStepTextView;
    private ImageView machineStep1;
    private ImageView machineStep2;
    private ImageView machineStep3;
    private TextView articleStepTextView;
    private ImageView articleStep1;
    private ImageView articleStep2;
    private ImageView articleStep3;

    private SplashScreenViewModel splashScreenViewModel;

    private ArrayList<Machine> allMachineInDatabase;
    private ArrayList<ArticleWithData> allArticleWithDataInDatabase;

    private boolean machineLocalData;
    private boolean articleLocalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashScreenViewModel = SplashScreenViewModel.getInstance();

        initProgress();
        machineStep1.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
        machineStepTextView.setText(R.string.splash_lib_step_1);

        if (DownloadService.splashScreenViewModel == null) {
            getMachineLocalData();
        } else if (DownloadService.isDone) {
            startFirstActivity();
        } else {
            machineStep1.setBackgroundColor(getStateColor(DownloadService.machineStep1State));
            machineStep2.setBackgroundColor(getStateColor(DownloadService.machineStep2State));
            machineStep3.setBackgroundColor(getStateColor(DownloadService.machineStep3State));
            articleStep1.setBackgroundColor(getStateColor(DownloadService.articleStep1State));
            articleStep2.setBackgroundColor(getStateColor(DownloadService.articleStep2State));
            articleStep3.setBackgroundColor(getStateColor(DownloadService.articleStep3State));
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultStep = bundle.getInt(DownloadService.STEP);
                int resultCode = bundle.getInt(DownloadService.RESULT);

                switch (resultStep) {
                    case DownloadService.STEP_MACHINE_1:
                        if (resultCode == RESULT_OK) {
                            machineStepTextView.setText(R.string.splash_lib_step_2);
                            machineStep1.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            machineStep2.setBackgroundColor(getStateColor(STEP_STATE_PROGRESS));
                            DownloadService.machineStep1State = STEP_STATE_OK;
                            DownloadService.machineStep2State = STEP_STATE_PROGRESS;
                        } else {
                            failLoadingMachinePerfFile();
                        }
                        break;
                    case DownloadService.STEP_MACHINE_2:
                        if (resultCode == RESULT_OK) {
                            machineStepTextView.setText(R.string.splash_lib_step_3);
                            machineStep2.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            machineStep3.setBackgroundColor(getStateColor(STEP_STATE_PROGRESS));
                            DownloadService.machineStep2State = STEP_STATE_OK;
                            DownloadService.machineStep3State = STEP_STATE_PROGRESS;
                        } else {
                            failLoadingMachinePerfFile();
                        }
                        break;
                    case DownloadService.STEP_MACHINE_3:
                        if (resultCode == RESULT_OK) {
                            articleStepTextView.setText(R.string.splash_lib_step_1);
                            machineStep3.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            articleStep1.setBackgroundColor(getStateColor(STEP_STATE_PROGRESS));
                            DownloadService.machineStep3State = STEP_STATE_OK;
                            DownloadService.articleStep1State = STEP_STATE_PROGRESS;

                            articleLocalData = true;
                        } else {
                            machineLocalData = false;
                            failLoadingMachinePerfFile();
                        }
                        break;
                    case DownloadService.STEP_ARTICLE_1:
                        if (resultCode == RESULT_OK) {
                            articleStepTextView.setText(R.string.splash_lib_step_2);
                            articleStep1.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            articleStep2.setBackgroundColor(getStateColor(STEP_STATE_PROGRESS));
                            DownloadService.articleStep1State = STEP_STATE_OK;
                            DownloadService.articleStep2State = STEP_STATE_PROGRESS;
                        } else {
                            failLoadingArticlePerfFile();
                        }
                        break;
                    case DownloadService.STEP_ARTICLE_2:
                        if (resultCode == RESULT_OK) {
                            articleStepTextView.setText(R.string.splash_lib_step_3);
                            articleStep2.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            articleStep3.setBackgroundColor(getStateColor(STEP_STATE_PROGRESS));
                            DownloadService.articleStep2State = STEP_STATE_OK;
                            DownloadService.articleStep3State = STEP_STATE_PROGRESS;
                        } else {
                            failLoadingArticlePerfFile();
                        }
                        break;
                    case DownloadService.STEP_ARTICLE_3:
                        if (resultCode == RESULT_OK) {
                            articleStep3.setBackgroundColor(getStateColor(STEP_STATE_OK));
                            DownloadService.articleStep3State = STEP_STATE_OK;

                            articleLocalData = true;
                            startFirstActivity();
                        } else {
                            articleLocalData = false;
                            failLoadingArticlePerfFile();
                        }
                        break;
                    default:

                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                DownloadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private int getStateColor(int state) {
        switch (state) {
            case STEP_STATE_PROGRESS:
                return getColor(R.color.aptar_bh_light_blue);
            case STEP_STATE_OK:
                return getColor(R.color.green);
            case STEP_STATE_LOCAL_ONLY:
                return getColor(R.color.yellow);
            case STEP_STATE_FAIL:
                return getColor(R.color.red);
            default:
                return 0;

        }
    }

    private void initProgress() {
        machineStepTextView = findViewById(R.id.machine_performance_step_lib);
        machineStep1 = findViewById(R.id.machine_performance_step_1);
        machineStep2 = findViewById(R.id.machine_performance_step_2);
        machineStep3 = findViewById(R.id.machine_performance_step_3);
        articleStepTextView = findViewById(R.id.article_performance_step_lib);
        articleStep1 = findViewById(R.id.article_performance_step_1);
        articleStep2 = findViewById(R.id.article_performance_step_2);
        articleStep3 = findViewById(R.id.article_performance_step_3);
    }

    private void getMachineLocalData() {
        splashScreenViewModel.getMachineLocalData(new MachineCallback() {
            @Override
            public void onSuccess(List<Machine> machineList) {
                allMachineInDatabase = new ArrayList<>(machineList);
                machineLocalData = true;

                getArticleLocalData();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Local Machine", e.getMessage() + " ");
                machineLocalData = false;

                getArticleLocalData();
            }
        });
    }

    private void getArticleLocalData() {
        splashScreenViewModel.getArticleLocalData(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {
                allArticleWithDataInDatabase = new ArrayList<>(articleList);
                articleLocalData = true;

                initApp();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Local Article", e.getMessage() + "");
                articleLocalData = false;

                initApp();
            }
        });
    }

    private void initApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage("Voulez-vous télécharger les données en ligne ? (Le traitement peut prendre quelques secondes)")
                .setPositiveButton("Télécharger", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        splashScreenViewModel.initApp();

                        Intent intent = new Intent(SplashScreenActivity.this, DownloadService.class);
                        startService(intent);
                    }
                })
                .setNegativeButton("Local", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (allArticleWithDataInDatabase == null && allMachineInDatabase == null) {
                            failLoadingData();
                        }
                        startFirstActivity();
                    }
                }).show();

    }

    private void failLoadingMachinePerfFile() {
        machineStepTextView.setText("");

        if (machineLocalData) {
            machineStep1.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            machineStep2.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            machineStep3.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            DownloadService.machineStep1State = STEP_STATE_LOCAL_ONLY;
            DownloadService.machineStep2State = STEP_STATE_LOCAL_ONLY;
            DownloadService.machineStep3State = STEP_STATE_LOCAL_ONLY;
        } else {
            machineStep1.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            machineStep2.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            machineStep3.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            DownloadService.machineStep1State = STEP_STATE_FAIL;
            DownloadService.machineStep2State = STEP_STATE_FAIL;
            DownloadService.machineStep3State = STEP_STATE_FAIL;
            ApplicationManager.failLoadingMachine = true;
        }
    }

    private void failLoadingArticlePerfFile() {
        articleStepTextView.setText("");

        if (articleLocalData) {
            articleStep1.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            articleStep2.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            articleStep3.setBackgroundColor(getStateColor(STEP_STATE_LOCAL_ONLY));
            DownloadService.articleStep1State = STEP_STATE_LOCAL_ONLY;
            DownloadService.articleStep2State = STEP_STATE_LOCAL_ONLY;
            DownloadService.articleStep3State = STEP_STATE_LOCAL_ONLY;
            startFirstActivity();
        } else {
            articleStep1.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            articleStep2.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            articleStep3.setBackgroundColor(getStateColor(STEP_STATE_FAIL));
            DownloadService.articleStep1State = STEP_STATE_FAIL;
            DownloadService.articleStep2State = STEP_STATE_FAIL;
            DownloadService.articleStep3State = STEP_STATE_FAIL;
            ApplicationManager.failLoadingArticle = true;
            startFirstActivity();
        }
    }

    private void initSiteEnum() {
        splashScreenViewModel.getMachineLocalData(new MachineCallback() {

            @Override
            public void onSuccess(List<Machine> machineList) {

                for (Machine machine : machineList) {
                    for (SiteEnum siteEnum: SiteEnum.values()) {
                        for (int num : siteEnum.getSiteNum()) {
                            if (machine.getSite() == num){
                                siteEnum.addOneToMachineNumber();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private void startFirstActivity() {
        initSiteEnum();
        closeAllNotification();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention")
                .setCancelable(false)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);

                        startActivity(intent);
                    }
                });
        if (ApplicationManager.failLoadingArticle && ApplicationManager.failLoadingMachine) {
            failLoadingData();
        } else if (ApplicationManager.failLoadingArticle) {
            builder.setMessage("Aucune donnée trouvée pour la performance article, seule la performance machine sera disponible.").show();
        } else if (ApplicationManager.failLoadingMachine) {
            builder.setMessage("Aucune donnée trouvée pour la performance machine, seule la performance article sera disponible.").show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }
    }

    private void closeAllNotification() {
        if (DownloadService.manager != null) {
            DownloadService.manager.cancelAll();
        }
    }

    private void failLoadingData() {
        closeAllNotification();

        new AlertDialog.Builder(this)
                .setTitle("Impossible de lancer l'application")
                .setMessage("Aucune donnée trouvée")
                .setCancelable(false)
                .setPositiveButton("Fermer l'application", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }
}
