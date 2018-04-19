package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.florian.projet.manager.ApplicationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class DropboxDownloadDataFileTask extends AsyncTask<String, Void, File> {

    private final DropboxDownloadDataFileTask.Callback mCallback;
    private File path;
    private DbxClientV2 dbxClientV2;
    private Exception mException;

    public interface Callback {
        void onSuccess(File file);

        void onFailed(Exception e);
    }

    public DropboxDownloadDataFileTask(DbxClientV2 dbxClientV2, File path, DropboxDownloadDataFileTask.Callback callback) {
        this.dbxClientV2 = dbxClientV2;
        this.path = path;
        this.mCallback = callback;
    }

    @Override
    protected File doInBackground(String... strings) {
        ListFolderResult result;
        File file = new File(path.getAbsolutePath(), ApplicationManager.FILE_MES_NAME);
        try {
            result = dbxClientV2.files().listFolder("");

            while (true) {
                for (Metadata metadata : result.getEntries()) {

                    if (metadata.getPathLower().equals("/" + ApplicationManager.FILE_MES_NAME)) {

                        DbxDownloader<FileMetadata> downloader = dbxClientV2.files().download("/" + ApplicationManager.FILE_MES_NAME);

                        FileOutputStream out = new FileOutputStream(file);
                        downloader.download(out);
                        out.close();
                        break;
                    }
                }

                if (!result.getHasMore()) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.d("ESXAZK", e.getMessage());
            mException = e;
        }

        return file;
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        if (mException != null) {
            mCallback.onFailed(mException);
        } else {
            mCallback.onSuccess(file);
        }
    }
}
