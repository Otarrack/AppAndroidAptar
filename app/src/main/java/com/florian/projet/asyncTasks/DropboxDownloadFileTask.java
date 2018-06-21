package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.florian.projet.manager.ApplicationManager;

import java.io.File;
import java.io.FileOutputStream;

public class DropboxDownloadFileTask extends AsyncTask<String, Void, File> {

    private final DropboxDownloadFileTask.Callback mCallback;
    private File path;
    private DbxClientV2 dbxClientV2;
    private Exception mException;

    public interface Callback {
        void onSuccess(File file);

        void onFailed(Exception e);
    }

    public DropboxDownloadFileTask(DbxClientV2 dbxClientV2, File path, DropboxDownloadFileTask.Callback callback) {
        this.dbxClientV2 = dbxClientV2;
        this.path = path;
        this.mCallback = callback;
    }

    @Override
    protected File doInBackground(String... strings) {
        ListFolderResult result;
        File file = new File(path.getAbsolutePath(), strings[0]);
        try {
            result = dbxClientV2.files().listFolder("");

            while (true) {
                for (Metadata metadata : result.getEntries()) {

                    if (metadata.getPathLower().equals("/" + strings[0])) {

                        DbxDownloader<FileMetadata> downloader = dbxClientV2.files().download("/" + strings[0]);

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
            Log.d("Download task", e.getMessage());
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
