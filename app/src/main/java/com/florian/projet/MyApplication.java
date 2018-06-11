package com.florian.projet;

import android.app.Application;

import com.florian.projet.manager.ApplicationManager;


public class MyApplication extends Application {
    private static MyApplication instance;
    private ApplicationManager applicationManager;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        applicationManager = ApplicationManager.getInstance();
    }
}