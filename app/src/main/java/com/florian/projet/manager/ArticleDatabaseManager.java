package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;

import com.florian.projet.bdd.database.ArticleDataBase;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.ArticleData;
import com.florian.projet.bdd.relation.ArticleWithData;
import com.florian.projet.tools.ArticleWithDataCallback;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manager de la base de données liée aux articles
 *
 * @author Florian
 */
public class ArticleDatabaseManager {
    private static ArticleDatabaseManager instance;
    ArticleDataBase db;

    public ArticleDatabaseManager() {
        this.db = ArticleDataBase.getInstance();
    }

    /**
     * Singleton qui permet de récupérer l'instance en cours si elle a déjà été créée
     *
     * @return Instance du manager
     */
    public static ArticleDatabaseManager getInstance() {
        if (instance == null) {
            instance = new ArticleDatabaseManager();
        }

        return instance;
    }

    /**
     * Remplace les données par la liste renseignée
     *
     * @param articleWithDataArrayList Liste des données à sauvegarder
     * @param callback Callback pour récupérer la réponse
     */
    public void refreshAllArticle(final ArrayList<ArticleWithData> articleWithDataArrayList, final SimpleCallback callback) {
        new ClearAllTables(db, new SimpleCallback() {
            @Override
            public void onSuccess() {
                insertAllArticle(articleWithDataArrayList, callback);
            }

            @Override
            public void onFailed(Exception e) {
                callback.onFailed(e);
            }
        }).execute();
    }

    /**
     * Ajoute tous les article de la liste à la base de données
     *
     * @param articleList Liste d'article à ajouter
     * @param callback Callback pour récupérer la réponse
     */
    public void insertAllArticle(List<ArticleWithData> articleList, SimpleCallback callback) {
        try {
            Log.d("NOAOPMEAPKEA", articleList.size() + "");
            new InsertAllTask(db, callback).execute(articleList.toArray(new ArticleWithData[articleList.size()]));

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
     * Récupère tous les articles de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllArticle(ArticleWithDataCallback callback) {
        try {
            new GetAllTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get All", e.getMessage());
        }
    }

    /**
     * Récupère tous les articles en favoris de la base de données
     *
     * @param callback Callback pour récupérer la réponse
     */
    public void getAllArticleFav(ArticleWithDataCallback callback) {
        try {
            new GetAllFavTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get Fav", e.getMessage());
        }
    }

    /**
     * Asynctask qui gère la suppression de toutes les données de la base de données
     */
    private static class ClearAllTables extends AsyncTask<Void, Void, Void> {

        private final SimpleCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        /**
         * @param db Instance de la base de données
         * @param callback Callback pour récupérer la réponse
         */
        ClearAllTables(ArticleDataBase db, SimpleCallback callback) {
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
    private static class InsertAllTask extends AsyncTask<ArticleWithData, Void, Void> {

        private final ArticleDataBase db;
        private SimpleCallback callback;
        private Exception exception;


        InsertAllTask(ArticleDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(ArticleWithData... articles) {
            try {
                ArrayList<Article> articleArrayList = new ArrayList<>();
                for (ArticleWithData articleWithData : articles) {
                    articleArrayList.add(articleWithData.getArticle());
                }

                List<Long> idList = db.articleDao().insertAllArticle(articleArrayList);

                for (int i = 0; i < idList.size(); i++) {
                    for (ArticleData articleData : articles[i].getDataList()) {
                        articleData.setIdArticle(idList.get(i));
                    }

                    db.articleDao().insertAllData(articles[i].getDataList());
                }
            } catch (Exception e) {
                exception = e;
            }

            return null;
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

        private final ArticleDataBase db;

        UpdateArticleTask(ArticleDataBase db) {
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
     * Asynctask qui gère la récupération de tous les articles de la base de données
     */
    public static class GetAllTask extends AsyncTask<Void, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetAllTask(ArticleDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAll();
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
     * Asynctask qui gère la récupération de tous les articles en favoris de la base de données
     */
    public static class GetAllFavTask extends AsyncTask<Void, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetAllFavTask(ArticleDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAllFav();
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

}
