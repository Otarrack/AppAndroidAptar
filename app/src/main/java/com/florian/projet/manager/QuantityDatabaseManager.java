package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.database.QuantityDataBase;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.entity.Presse;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.bdd.relation.PresseWithData;
import com.florian.projet.model.QuantityFileLine;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.PresseWithDataCallback;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager de la base de données liée aux articles
 *
 * @author Florian
 */
public class QuantityDatabaseManager {
    private static QuantityDatabaseManager instance;
    QuantityDataBase db;

    public QuantityDatabaseManager() {
        this.db = QuantityDataBase.getInstance();
    }

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static QuantityDatabaseManager getInstance() {
        if (instance == null) {
            instance = new QuantityDatabaseManager();
        }

        return instance;
    }

    /**
     * Remplace les données par la liste renseignée
     *
     * @param quantityFileLineArrayList Liste des lignes du fichier à sauvegarder
     * @param callback Callback pour récupérer la réponse
     */
    public void refreshAllFromFile(final ArrayList<QuantityFileLine> quantityFileLineArrayList, final SimpleCallback callback) {
        new ClearAllTables(db, new SimpleCallback() {
            @Override
            public void onSuccess() {
                insertAllFromFile(quantityFileLineArrayList, callback);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        }).execute();
    }

    /**
     * Ajoute toutes les données de la liste renseignée
     *
     * @param quantityFileLineList Liste de données à ajouter
     * @param callback Callback pour récupérer la réponse
     */
    public void insertAllFromFile(List<QuantityFileLine> quantityFileLineList, SimpleCallback callback) {
        try {
            new InsertAllFromFileTask(db, callback).execute(quantityFileLineList.toArray(new QuantityFileLine[quantityFileLineList.size()]));

        } catch (Exception e) {
            Log.d("Article Insert All", e.getMessage());
        }
    }

    /**
     * Modifie l'article dans la base de données
     *
     * @param article Article à modifier
     */
    public void updateArticle(Article article) {
        try {
            new UpdateArticleTask(db).execute(article);

        } catch (Exception e) {
            Log.d("Article Update", e.getMessage());
        }
    }

    /**
     * Modifie la presse dans la base de données
     *
     * @param presse Presse à modifier
     */
    public void updatePresse(Presse presse) {
        try {
            new UpdatePresseTask(db).execute(presse);

        } catch (Exception e) {
            Log.d("Presse Update", e.getMessage());
        }
    }

    /**
     * Récupère tous les articles de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllArticleWithData(ArticleWithDataCallback callback) {
        try {
            new GetAllArticleWithDataTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get All", e.getMessage());
        }
    }

    /**
     * Récupère toutes les presses de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllPresseWithData(PresseWithDataCallback callback) {
        try {
            new GetAllPresseWithDataTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Presse Get All", e.getMessage());
        }
    }

    /**
     * Récupère tous les articles en favoris de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllArticleFavWithData(ArticleWithDataCallback callback) {
        try {
            new GetAllArticleFavWithDataTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get Fav", e.getMessage());
        }
    }

    /**
     * Récupère toutes les presses en favoris de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllPresseFavWithData(PresseWithDataCallback callback) {
        try {
            new GetAllPresseFavWithDataTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Presse Get Fav", e.getMessage());
        }
    }

    /**
     * Asynctask qui gère la suppression de toutes les données de la base de données
     */
    private static class ClearAllTables extends AsyncTask<Void, Void, Void> {

        private final SimpleCallback callback;
        private final QuantityDataBase db;
        private Exception exception;

        /**
         * @param db Instance de la base de données
         * @param callback Callback pour récupérer la réponse
         */
        ClearAllTables(QuantityDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        /**
         * Traitement de suppression des données de la base
         */
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                db.clearAllTables();
            } catch (Exception e) {
                exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess();
            }
        }
    }

    /**
     * Asynctask qui gère l'insertion dans la base de données d'une liste d'article et de données
     */
    private static class InsertAllFromFileTask extends AsyncTask<QuantityFileLine, Void, Void> {

        private final QuantityDataBase db;
        private SimpleCallback callback;
        private Exception exception;


        InsertAllFromFileTask(QuantityDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(QuantityFileLine... quantityFileLines) {
            try {
                OFData ofData;

                ArrayList<Article> articleArrayList = insertAllArticle(quantityFileLines);
                ArrayList<Presse> presseArrayList = insertAllPresse(quantityFileLines);

                ArrayList<OFData> ofDataArrayList = new ArrayList<>();

                for (QuantityFileLine quantityFileLine : quantityFileLines) {

                    ofData = new OFData();
                    ofData.setNumOf(quantityFileLine.getNumOf());
                    ofData.setDate(quantityFileLine.getDate());
                    ofData.setQuantity(quantityFileLine.getQuantity());

                    for (Article article : articleArrayList) {
                        if (article.getName().equals(quantityFileLine.getNameArticle())) {
                            ofData.setIdArticle(article.getId());
                        }
                    }

                    for (Presse presse : presseArrayList) {
                        if (presse.getName().equals(quantityFileLine.getNamePresse())) {
                            ofData.setIdPresse(presse.getId());
                        }
                    }

                    ofDataArrayList.add(ofData);
                }

                db.ofDataDao().insertAllData(ofDataArrayList);
            } catch (Exception e) {
                exception = e;
            }

            return null;
        }

        private ArrayList<Article> insertAllArticle(QuantityFileLine[] quantityFileLines) {
            List<Article> favArticleList = db.articleDao().getAllArticleFav();
            ArrayList<Article> articleArrayList = new ArrayList<>();

            boolean exist;

            for (QuantityFileLine quantityFileLine : quantityFileLines) {
                exist = false;

                for (Article a : articleArrayList) {
                    if (quantityFileLine.getNameArticle().equals(a.getName())) {
                        exist = true;
                    }
                }

                if (!exist) {
                    Article article = new Article();
                    article.setName(quantityFileLine.getNameArticle());
                    article.setCustomer(quantityFileLine.getCustomer());
                    article.setType(quantityFileLine.getType());

                    for (Article a : favArticleList) {
                        if (quantityFileLine.getNameArticle().equals(a.getName())) {
                            article.setFavorite(true);
                        }
                    }

                    articleArrayList.add(article);
                }
            }

            List<Long> idList = db.articleDao().insertAllArticle(articleArrayList);

            for (int i = 0; i < articleArrayList.size(); i++) {
                articleArrayList.get(i).setId(idList.get(i));
            }

            return articleArrayList;
        }

        private ArrayList<Presse> insertAllPresse(QuantityFileLine[] quantityFileLines) {
            List<Presse> favPresseList = db.presseDao().getAllPresseFav();
            ArrayList<Presse> presseArrayList = new ArrayList<>();

            boolean exist;

            for (QuantityFileLine quantityFileLine : quantityFileLines) {
                exist = false;

                for (Presse p : presseArrayList) {
                    if (quantityFileLine.getNamePresse().equals(p.getName())) {
                        exist = true;
                    }
                }

                if (!exist) {
                    Presse presse = new Presse();
                    presse.setName(quantityFileLine.getNamePresse());

                    for (Presse p : favPresseList) {
                        if (quantityFileLine.getNamePresse().equals(p.getName())) {
                            presse.setFavorite(true);
                        }
                    }

                    presseArrayList.add(presse);
                }
            }

            List<Long> idList = db.presseDao().insertAllPresse(presseArrayList);

            for (int i = 0; i < presseArrayList.size(); i++) {
                presseArrayList.get(i).setId(idList.get(i));
            }

            return presseArrayList;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailed(exception);
            }
        }
    }

    /**
     * Asynctask qui gère la modification d'un article de la base de données
     */
    private static class UpdateArticleTask extends AsyncTask<Article, Void, Integer> {

        private final QuantityDataBase db;

        UpdateArticleTask(QuantityDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Article... articles) {
            if (articles[0] != null) {
                return db.articleDao().updateArticle(articles[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

    /**
     * Asynctask qui gère la modification d'une presse dans la base de données
     */
    private static class UpdatePresseTask extends AsyncTask<Presse, Void, Integer> {

        private final QuantityDataBase db;

        UpdatePresseTask(QuantityDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Presse... presses) {
            if (presses[0] != null) {
                return db.presseDao().updatePresse(presses[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

    /**
     * Asynctask qui gère la récupération de tous les articles de la base de données
     */
    private static class GetAllArticleWithDataTask extends AsyncTask<Void, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final QuantityDataBase db;
        private Exception exception;

        GetAllArticleWithDataTask(QuantityDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAllArticleWithData();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ArticleWithData> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

    /**
     * Asynctask qui gère la récupération de toutes les presses de la base de données
     */
    private static class GetAllPresseWithDataTask extends AsyncTask<Void, Void, List<PresseWithData>> {

        private final PresseWithDataCallback callback;
        private final QuantityDataBase db;
        private Exception exception;

        GetAllPresseWithDataTask(QuantityDataBase db, PresseWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<PresseWithData> doInBackground(Void... voids) {
            try {
                return db.presseDao().getAllPresseWithData();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<PresseWithData> presseList) {
            super.onPostExecute(presseList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(presseList);
            }
        }
    }

    /**
     * Asynctask qui gère la récupération de tous les articles en favoris de la base de données
     */
    private static class GetAllArticleFavWithDataTask extends AsyncTask<Void, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final QuantityDataBase db;
        private Exception exception;

        GetAllArticleFavWithDataTask(QuantityDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAllArticleFavWithData();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ArticleWithData> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

    /**
     * Asynctask qui gère la récupération de toutes les presses en favoris de la base de données
     */
    private static class GetAllPresseFavWithDataTask extends AsyncTask<Void, Void, List<PresseWithData>> {

        private final PresseWithDataCallback callback;
        private final QuantityDataBase db;
        private Exception exception;

        GetAllPresseFavWithDataTask(QuantityDataBase db, PresseWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<PresseWithData> doInBackground(Void... voids) {
            try {
                return db.presseDao().getAllPresseFavWithData();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<PresseWithData> presseWithDataList) {
            super.onPostExecute(presseWithDataList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(presseWithDataList);
            }
        }
    }

}
