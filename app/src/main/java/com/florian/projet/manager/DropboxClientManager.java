package com.florian.projet.manager;

import android.app.Activity;
import android.os.AsyncTask;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.auth.DbxUserAuthRequests;
import com.florian.projet.asyncTasks.DropboxConnectionTask;

/**
 * Singleton instance of {@link DbxClientV2} and friends
 */
public class DropboxClientManager {

    private static DbxClientV2 sDbxClient;
    private static DbxUserAuthRequests dbxUserAuthRequests;

    private static DropboxClientManager instance;

    private DropboxClientManager() {
        init();
    }

    public static DropboxClientManager getInstance() {
        if(instance == null) {
            instance = new DropboxClientManager();
        }
        return instance;

    }

    private void init() {
        new DropboxConnectionTask(new DropboxConnectionTask.Callback() {
            @Override
            public void onSuccess(DbxClientV2 dbxClientV2) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }
        return sDbxClient;
    }
}