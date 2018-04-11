package com.florian.projet.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.android.Auth;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.florian.projet.R;
import com.florian.projet.dropbox.UserActivity;

public class DropboxConnectionTask extends AsyncTask<String, Void, DbxClientV2>{

    private final Callback mCallback;
    private String accessToken;
    private Exception mException;

    public interface Callback {
        void onSuccess(DbxClientV2 dbxClientV2);

        void onFailed(Exception e);
    }

    public DropboxConnectionTask(DropboxConnectionTask.Callback callback) {
        this.accessToken = "-9R3IBvu8LAAAAAAAAAAGW-3BSWHfzW-eL-TD6etgrfP-I0QJHuD2LCuf0_SFINa";
        this.mCallback = callback;
    }

    @Override
    protected DbxClientV2 doInBackground(String... strings) {
        try {
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("examples-v2-demo")
                .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
                .build();

            return new DbxClientV2(requestConfig, accessToken);

        } catch (Exception e) {
            mException = e;
        }

        return null;
    }

    @Override
    protected void onPostExecute(DbxClientV2 dbxClientV2) {
        super.onPostExecute(dbxClientV2);

        if (mException != null) {
            mCallback.onFailed(mException);
        } else {
            mCallback.onSuccess(dbxClientV2);
        }

    }
}
