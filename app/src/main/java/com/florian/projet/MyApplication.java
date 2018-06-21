package com.florian.projet;

import android.app.Application;

import com.florian.projet.bdd.database.ArticleDataBase;
import com.florian.projet.bdd.database.MachineDataBase;
import com.florian.projet.manager.ArticleDatabaseManager;
import com.florian.projet.manager.MachineDatabaseManager;


public class MyApplication extends Application {
    private static MyApplication instance;
    private MachineDatabaseManager machineDatabaseManager;
    private ArticleDatabaseManager articleDatabaseManager;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        MachineDataBase.setInstance(getApplicationContext());
        ArticleDataBase.setInstance(getApplicationContext());

        machineDatabaseManager = MachineDatabaseManager.getInstance();
        articleDatabaseManager = ArticleDatabaseManager.getInstance();

        /* //RESET DATABASE
        machineDatabaseManager.deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });*/
    }

}