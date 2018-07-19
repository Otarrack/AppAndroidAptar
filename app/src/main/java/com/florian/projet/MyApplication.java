package com.florian.projet;

import android.app.Application;

import com.florian.projet.bdd.database.QuantityDataBase;
import com.florian.projet.bdd.database.PourcentagePerformanceDataBase;
import com.florian.projet.manager.PourcentagePerfDatabaseManager;
import com.florian.projet.manager.QuantityDatabaseManager;


public class MyApplication extends Application {
    private static MyApplication instance;
    private PourcentagePerfDatabaseManager pourcentagePerfDatabaseManager;
    private QuantityDatabaseManager quantityDatabaseManager;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        PourcentagePerformanceDataBase.setInstance(getApplicationContext());
        QuantityDataBase.setInstance(getApplicationContext());

        pourcentagePerfDatabaseManager = PourcentagePerfDatabaseManager.getInstance();
        quantityDatabaseManager = QuantityDatabaseManager.getInstance();

        /* //RESET DATABASE
        pourcentagePerfDatabaseManager.deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });*/
    }

}