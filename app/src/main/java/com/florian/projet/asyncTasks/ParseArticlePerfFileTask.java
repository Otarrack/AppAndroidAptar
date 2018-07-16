package com.florian.projet.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.model.ArticleLine;
import com.florian.projet.tools.DateFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Une classe qui lit en asynchrone le fichier de performance article.
 *
 * @author Florian
 */

public class ParseArticlePerfFileTask extends AsyncTask<File, Void, ArrayList<ArticleLine>> {
    private Callback callback;
    private Exception mException;

    /**
     * Une interface qui permet de signaler la fin de la lecture du fichier.
     */
    public interface Callback {
        /**
         * Une méthode pour envoyer la liste des lignes articles en cas de réussite.
         *
         * @param dataLineList La liste des lignes récupérées
         */
        void onSuccess(ArrayList<ArticleLine> dataLineList);

        /**
         * Une méthode pour envoyer les détails en cas d'erreur
         *
         * @param e L'exception générée lors du traitement
         */
        void onFailed(Exception e);
    }

    /**
     * @param callback Le callback permettant de récupérer le résultat du traitement
     */
    public ParseArticlePerfFileTask(Callback callback) {
        this.callback = callback;
    }

    /**
     * Méthode contenant le traitement de lecture du fichier
     *
     * @param files Le fichier à lire
     *
     * @return Renvoie les lignes du fichier en ArrayList de {@link ArticleLine}
     */
    @Override
    protected ArrayList<ArticleLine> doInBackground(File... files) {
        ArrayList<ArticleLine> dataLineList = new ArrayList<>();

        try {
            //Récupère le fichier et initialise le lecteur
            InputStream excelFile = new FileInputStream(files[0]);
            InputStreamReader streamReader = new InputStreamReader(excelFile);
            BufferedReader br = new BufferedReader(streamReader);
            String line;
            String csvSplitBy = ";";

            br.readLine();
            ArticleLine article;

            boolean hasData = true;
            int index = 1;

            //Execute la boucle tant qu'il y a des lignes de données
            while ((line = br.readLine()) != null && hasData) {
                article = new ArticleLine();

                if (index > 4) {
                    String[] row = line.split(csvSplitBy);

                    if (row.length >= 24) {

                        //Colonne de vérification de données éxistantes
                        if (row[3].equals("")) {
                            hasData = false;
                        }

                        //Colonne concernant le type
                        if (!row[4].equals("")) {
                            article.setType(row[4]);
                        }

                        //Colonne concernant le numéro d'OF
                        if (!row[5].equals("")) {
                            article.setNumOf(row[5]);
                        }

                        //Colonne concernant le client
                        if (!row[12].equals("")) {
                            article.setCustomer(row[12]);
                        }

                        //Colonne concernant la quantité
                        if (!row[14].equals("")) {
                            Double d = Double.valueOf(row[14].replaceAll(" ", ""));
                            article.setQuantity(d);
                        }

                        //Colonne concernant la date
                        if (!row[22].equals("")) {
                            Date date = DateFormatter.parseDate(row[22],"dd/MM/yyyy");
                            if (date != null) {
                                article.setDate(date);
                            }
                        }

                        //Colonne concernant le nom de l'article
                        if (!row[24].equals("")) {
                            article.setName(row[24]);
                        }

                        //Vérifie qu'il s'agit bien d'un article pour l'ajouter à la liste
                        if (article.getName() != null && article.getDate() != null) {
                            dataLineList.add(article);
                        }
                    } else {
                        for (String s : row) {
                            Log.d("ROW -> ", s + " _");
                        }
                    }
                }

                index++;
            }

        } catch (Exception e) {
            Log.d("Article Parse Error ", e.getMessage() + " ! ");
            mException = e;
        }
        return dataLineList;
    }

    /**
     * Méthode appelée automatiquement après doInBackgroud qui gère le callback
     *
     * @param articleArrayList La liste des lignes renvoyée par le traitement
     */
    @Override
    protected void onPostExecute(ArrayList<ArticleLine> articleArrayList) {
        if (mException == null) {
            callback.onSuccess(articleArrayList);
        } else {
            callback.onFailed(mException);
        }
    }
}
