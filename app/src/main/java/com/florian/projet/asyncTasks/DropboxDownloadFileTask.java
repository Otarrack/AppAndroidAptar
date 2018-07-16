package com.florian.projet.asyncTasks;

import android.os.AsyncTask;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Une classe qui télécharge en asynchrone un fichier sur le client Dropbox passé en paramètre.
 *
 * @author Florian
 *
 */

public class DropboxDownloadFileTask extends AsyncTask<String, Void, File> {

    private final DropboxDownloadFileTask.Callback mCallback;
    private File path;
    private DbxClientV2 dbxClientV2;
    private Exception mException;

    /**
     * Une interface qui permet de signaler la fin du téléchargement du fichier.
     */
    public interface Callback {
        /**
         * Une méthode pour envoyer le fichier téléchargé en cas de réussite.
         *
         * @param file Le fichier téléchargé
         */
        void onSuccess(File file);

        /**
         * Une méthode pour envoyer les détails en cas d'erreur
         *
         * @param e L'exception générée lors du traitement
         */
        void onFailed(Exception e);
    }

    /**
     *
     * @param dbxClientV2 Le client Dropbox sur lequel on va chercher le fichier.
     * @param path Le chemin de l'emplacement où télécharger le fichier
     * @param callback Le callback permettant de récupérer le résultat du traitement
     */
    public DropboxDownloadFileTask(DbxClientV2 dbxClientV2, File path, DropboxDownloadFileTask.Callback callback) {
        this.dbxClientV2 = dbxClientV2;
        this.path = path;
        this.mCallback = callback;
    }

    /**
     * Méthode contenant le traitement à réaliser
     *
     * @param strings Le nom du fichier à télécharger
     *
     * @return Renvoie le fichier téléchargé
     */
    @Override
    protected File doInBackground(String... strings) {
        ListFolderResult result;

        //Crée un "contenant" au chemin de destination
        File file = new File(path.getAbsolutePath(), strings[0]);
        try {

            //Récupèration de la liste des dossier du Dropbox
            result = dbxClientV2.files().listFolder("");

            while (true) {
                for (Metadata metadata : result.getEntries()) {

                    //Vérifie le nom du fichier
                    if (metadata.getPathLower().equals("/" + strings[0])) {

                        //Initialise le téléchargement du fichier
                        DbxDownloader<FileMetadata> downloader = dbxClientV2.files().download("/" + strings[0]);

                        //Prépare l'injection du fichier dans le "contenant" file
                        FileOutputStream out = new FileOutputStream(file);

                        //Téléchargement
                        downloader.download(out);

                        //Ferme le lien de téléchargement
                        out.close();
                        break;
                    }
                }

                //Vérifie si il y a d'autres fichiers sur le Dropbox
                if (!result.getHasMore()) {
                    break;
                }
            }
        } catch (Exception e) {
            mException = e;
        }

        return file;
    }

    /**
     * Méthode appelée automatiquement après doInBackgroud qui gère le callback
     *
     * @param file Le fichier téléchargé renvoyé par le traitement
     *
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);

        // Renvoie le fichier, ou l'exception en cas d'erreur
        if (mException != null) {
            mCallback.onFailed(mException);
        } else {
            mCallback.onSuccess(file);
        }
    }
}
