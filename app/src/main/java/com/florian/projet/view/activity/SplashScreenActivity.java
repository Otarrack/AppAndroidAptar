package com.florian.projet.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.users.FullAccount;
import com.florian.projet.R;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.ParseArticlePerfFileTask;
import com.florian.projet.asyncTasks.ParseMachinePerfFileTask;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.ArticleData;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.manager.ApplicationManager;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.model.ArticleLine;
import com.florian.projet.model.SiteEnum;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.MachineCallback;
import com.florian.projet.tools.SimpleCallback;
import com.florian.projet.viewModel.SplashScreenViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
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
    private ArrayList<ArticleWithData> allArticleWithDataInFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashScreenViewModel = SplashScreenViewModel.getInstance();

        initProgress();
        machineStep1.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
        machineStepTextView.setText(R.string.splash_lib_step_1);
        getMachineLocalData();
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
                getArticleLocalData();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Local Machine", e.getMessage());
                getArticleLocalData();
            }
        });
    }

    private void getArticleLocalData() {
        splashScreenViewModel.getArticleLocalData(new ArticleWithDataCallback() {
            @Override
            public void onSuccess(List<ArticleWithData> articleList) {
                allArticleWithDataInDatabase = new ArrayList<>(articleList);
                initApp();

            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Get Local Article", e.getMessage() + "");
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

                        downloadFile(ApplicationManager.FILE_PERF_MACHINE_NAME);
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

    private void downloadFile(String fileName) {
        //dialog.setMessage("Téléchargement du fichier...");

        File path = getFilesDir();
        if (fileName.equals(ApplicationManager.FILE_PERF_MACHINE_NAME)) {
            splashScreenViewModel.downloadDataFile(path, fileName, new DropboxDownloadFileTask.Callback() {
                @Override
                public void onSuccess(File file) {
                    if (file != null) {
                        sendDownloadedFile(file);
                        machineStep1.setBackgroundColor(getColor(R.color.green));
                        machineStepTextView.setText(R.string.splash_lib_step_2);
                        machineStep2.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
                        readMachinePerformanceFile(file);
                    } else {
                        failLoadingMachinePerfFile();
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingMachinePerfFile();
                }
            });
        } else if (fileName.equals(ApplicationManager.FILE_PERF_ARTICLE_NAME)) {
            splashScreenViewModel.downloadDataFile(path, fileName, new DropboxDownloadFileTask.Callback() {
                @Override
                public void onSuccess(File file) {
                    if (file != null) {
                        sendDownloadedFile(file);
                        articleStep1.setBackgroundColor(getColor(R.color.green));
                        articleStepTextView.setText(R.string.splash_lib_step_2);
                        articleStep2.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
                        readArticlePerformanceFile(file);
                    } else {
                        failLoadingArticlePerfFile();
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingArticlePerfFile();
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
                        failLoadingMachinePerfFile();
                    } else {

                        machineStep2.setBackgroundColor(getColor(R.color.green));
                        machineStepTextView.setText(R.string.splash_lib_step_3);
                        machineStep3.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
                        getMachineListWithFavAndSite(dataLineList);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingMachinePerfFile();
                }
            });
        } catch (IOException e) {
            Log.d("Read Machine File", e.getMessage());
            failLoadingMachinePerfFile();
        }
    }

    private void getMachineListWithFavAndSite(ArrayList<Machine> allMachineInFile) {
        ArrayList<Machine> finalMachineList = splashScreenViewModel.initMachineListWithFav(allMachineInFile, allMachineInDatabase);

        refreshAllMachineInDatabase(finalMachineList);
    }

    private void refreshAllMachineInDatabase(final ArrayList<Machine> machineArrayList) {
        splashScreenViewModel.refreshAllMachineInDatabase(machineArrayList, new SimpleCallback() {
            @Override
            public void onSuccess() {
                allMachineInDatabase = new ArrayList<>(machineArrayList);
                machineStep3.setBackgroundColor(getColor(R.color.green));
                articleStepTextView.setText(R.string.splash_lib_step_1);
                articleStep1.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
                downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Refresh Machine Data", e.getMessage());
                allMachineInDatabase = null;
                failLoadingMachinePerfFile();
            }
        });
    }

    private void failLoadingMachinePerfFile() {
        machineStepTextView.setText("");

        if (allMachineInDatabase != null) {
            machineStep1.setBackgroundColor(getColor(R.color.yellow));
            machineStep2.setBackgroundColor(getColor(R.color.yellow));
            machineStep3.setBackgroundColor(getColor(R.color.yellow));
            new AlertDialog.Builder(this)
                    .setMessage("Impossible de récupérer les données en ligne pour la performance machine, les données locales seront chargées.")
                    .setTitle("")
                    .setCancelable(false)
                    .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
                        }
                    }).show();
        } else {
            machineStep1.setBackgroundColor(getColor(R.color.red));
            machineStep2.setBackgroundColor(getColor(R.color.red));
            machineStep3.setBackgroundColor(getColor(R.color.red));
            ApplicationManager.failLoadingMachine = true;

            downloadFile(ApplicationManager.FILE_PERF_ARTICLE_NAME);
        }
    }

    private void readArticlePerformanceFile(File file) {
        try {
            splashScreenViewModel.parseArticlePerfXlsFile(file, new ParseArticlePerfFileTask.Callback() {
                @Override
                public void onSuccess(ArrayList<ArticleLine> articleLineArrayList) {
                    if (articleLineArrayList.isEmpty()) {
                        failLoadingArticlePerfFile();
                    } else {
                        allArticleWithDataInFile = getAllArticleWithData(articleLineArrayList);

                        articleStep2.setBackgroundColor(getColor(R.color.green));
                        articleStepTextView.setText(R.string.splash_lib_step_3);
                        articleStep3.setBackgroundColor(getColor(R.color.aptar_bh_light_blue));
                        getArticleListWithFav(allArticleWithDataInFile);
                    }
                }

                @Override
                public void onFailed(Exception e) {
                    failLoadingArticlePerfFile();
                }
            });
        } catch (IOException e) {
            Log.d("Read Article File", e.getMessage());
            failLoadingArticlePerfFile();
        }
    }

    private ArrayList<ArticleWithData> getAllArticleWithData(ArrayList<ArticleLine> articleLines) {

        ArrayList<ArticleWithData> articleWithDataArrayList = new ArrayList<>();
        ArticleData articleData;
        ArticleWithData articleWithData;

        boolean articleExist = false;
        for (ArticleLine articleLine : articleLines) {
            articleData = new ArticleData();
            articleData.setNumOf(articleLine.getNumOf());
            articleData.setDate(articleLine.getDate());
            articleData.setQuantity(articleLine.getQuantity());

            for (ArticleWithData awd : articleWithDataArrayList) {
                if (awd.getArticle().getName().equals(articleLine.getName())) {
                    articleExist = true;
                    awd.getDataList().add(articleData);
                }
            }

            if (!articleExist) {
                articleWithData = new ArticleWithData();
                articleWithData.setArticle(new Article());
                articleWithData.getArticle().setName(articleLine.getName());
                articleWithData.getArticle().setCustomer(articleLine.getCustomer());
                articleWithData.getArticle().setType(articleLine.getType());
                articleWithData.setDataList(new ArrayList<ArticleData>());
                articleWithData.getDataList().add(articleData);
                articleWithDataArrayList.add(articleWithData);
            }

            articleExist = false;
        }

        return articleWithDataArrayList;
    }

    private void getArticleListWithFav(ArrayList<ArticleWithData> allArticleInFile) {
        ArrayList<ArticleWithData> finalArticleList = splashScreenViewModel.initArticleListWithFav(allArticleInFile, allArticleWithDataInDatabase);

        refreshAllArticleInDatabase(finalArticleList);
    }

    private void refreshAllArticleInDatabase(final ArrayList<ArticleWithData> articleArrayList) {
        splashScreenViewModel.refreshAllArticleInDatabase(articleArrayList, new SimpleCallback() {
            @Override
            public void onSuccess() {
                allArticleWithDataInDatabase = new ArrayList<>(articleArrayList);
                articleStep3.setBackgroundColor(getColor(R.color.green));
                startFirstActivity();
            }

            @Override
            public void onFailed(Exception e) {
                Log.d("Refresh Machine Data", e.getMessage());
                allArticleWithDataInDatabase = null;
                failLoadingArticlePerfFile();
            }
        });
    }

    private void failLoadingArticlePerfFile() {
        articleStepTextView.setText("");

        if (allArticleWithDataInDatabase != null) {
            articleStep1.setBackgroundColor(getColor(R.color.yellow));
            articleStep2.setBackgroundColor(getColor(R.color.yellow));
            articleStep3.setBackgroundColor(getColor(R.color.yellow));
            new AlertDialog.Builder(this)
                    .setMessage("Impossible de récupérer les données en ligne pour la performance article, les données locales seront chargées.")
                    .setTitle("")
                    .setCancelable(false)
                    .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startFirstActivity();
                        }
                    }).show();
        } else {
            articleStep1.setBackgroundColor(getColor(R.color.red));
            articleStep2.setBackgroundColor(getColor(R.color.red));
            articleStep3.setBackgroundColor(getColor(R.color.red));
            ApplicationManager.failLoadingArticle = true;
            startFirstActivity();
        }
    }

    private void startFirstActivity() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attention")
                .setCancelable(false)
                .setPositiveButton("Continuer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initSiteEnum();

                        Intent intent = new Intent(getBaseContext(), MainActivity.class);

                        startActivity(intent);
                    }
                });
        if (ApplicationManager.failLoadingArticle && ApplicationManager.failLoadingMachine) {
            failLoadingData();
        } else if (allArticleWithDataInDatabase == null) {
            builder.setMessage("Aucune donnée trouvée pour la performance article, seule la performance machine sera disponible.").show();
        } else if (allMachineInDatabase == null) {
            builder.setMessage("Aucune donnée trouvée pour la performance machine, seule la performance article sera disponible.").show();
        } else {
            initSiteEnum();

            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }
    }

    private void initSiteEnum() {
        for (Machine machine : allMachineInDatabase) {
            for (SiteEnum siteEnum: SiteEnum.values()) {
                for (int num : siteEnum.getSiteNum()) {
                    if (machine.getSite() == num){
                        siteEnum.addOneToMachineNumber();
                    }
                }
            }
        }
    }

    private void failLoadingData() {
        //dialog.dismiss();
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
