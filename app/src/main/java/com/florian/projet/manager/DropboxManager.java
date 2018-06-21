package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;
import com.florian.projet.asyncTasks.DropboxDownloadFileTask;
import com.florian.projet.asyncTasks.GetCurrentAccountTask;

import java.io.File;

/**
 * Singleton instance of {@link DbxClientV2} and friends
 */
public class DropboxManager {

    private DbxClientV2 clientV2;
    private final String ACCESS_TOKEN = "-9R3IBvu8LAAAAAAAAAAGW-3BSWHfzW-eL-TD6etgrfP-I0QJHuD2LCuf0_SFINa";

    private static DropboxManager instance;

    private DropboxManager() {

    }

    public static DropboxManager getInstance() {
        if(instance == null) {
            instance = new DropboxManager();
        }
        return instance;

    }

    void init(GetCurrentAccountTask.Callback callback) {
        DbxRequestConfig requestConfig = new DbxRequestConfig("dropbox/sebastien");
        clientV2 = new DbxClientV2(requestConfig, ACCESS_TOKEN);
        Log.d("INIT", "Client créé");
        initFullAccount(callback);
        Log.d("INIT", "Compte récupéré");
    }

    private void initFullAccount(final GetCurrentAccountTask.Callback callback) {
        new GetCurrentAccountTask(clientV2, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public void downloadFile(File path, String fileName, DropboxDownloadFileTask.Callback callback) {
        new DropboxDownloadFileTask(clientV2, path, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fileName);
    }
}