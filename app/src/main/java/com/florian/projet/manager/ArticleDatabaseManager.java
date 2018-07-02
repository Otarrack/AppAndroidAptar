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

public class ArticleDatabaseManager {
    private static ArticleDatabaseManager instance;
    ArticleDataBase db;

    public ArticleDatabaseManager() {
        this.db = ArticleDataBase.getInstance();
    }

    public static ArticleDatabaseManager getInstance() {
        if (instance == null) {
            instance = new ArticleDatabaseManager();
        }

        return instance;
    }

    public void refreshAllArticle(final ArrayList<ArticleWithData> articleWithDataArrayList, final SimpleCallback insertCallback) {
        deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {
                insertAllArticle(articleWithDataArrayList, insertCallback);
            }

            @Override
            public void onFailed(Exception e) {
                insertCallback.onFailed(e);
            }
        });
    }

    public void insertAllArticle(List<ArticleWithData> articleList, SimpleCallback callback) {
        try {
            Log.d("NOAOPMEAPKEA", articleList.size() + "");
            new InsertAllTask(db, callback).execute(articleList.toArray(new ArticleWithData[articleList.size()]));

        } catch (Exception e) {
            Log.d("Article Insert All", e.getMessage());
        }
    }

    public void updateArticle(Article article) {
        try {
            new UpdateArticleTask(db).execute(article);

        } catch (Exception e) {
            Log.d("Article Update", e.getMessage());
        }
    }

    public void updateData(ArticleData articleData) {
        try {
            new UpdateDataTask(db).execute(articleData);

        } catch (Exception e) {
            Log.d("Article Data Update", e.getMessage());
        }
    }

    public void deleteAllData(SimpleCallback callback) {
        new ClearAllTables(db, callback).execute();
    }

    public void getAllArticle(ArticleWithDataCallback callback) {
        try {
            new GetAllTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get All", e.getMessage());
        }
    }

    public void getArticleByName(String name, ArticleWithDataCallback callback) {
        try {
            new GetByNameTask(db, callback).execute(name);

        } catch (Exception e) {
            Log.d("Article Get Name", e.getMessage());
        }
    }

    public void getArticleByPeriod(Date startDate, Date endDate, ArticleWithDataCallback callback) {
        try {
            new GetByPeriodTask(db, callback).execute(startDate, endDate);

        } catch (Exception e) {
            Log.d("Article Get Name", e.getMessage());
        }
    }

    public void getAllArticleFav(ArticleWithDataCallback callback) {
        try {
            new GetAllFavTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get Fav", e.getMessage());
        }
    }

    private static class ClearAllTables extends AsyncTask<Void, Void, Void> {

        private final SimpleCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        ClearAllTables(ArticleDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

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

    private static class UpdateDataTask extends AsyncTask<ArticleData, Void, Integer> {

        private final ArticleDataBase db;

        UpdateDataTask(ArticleDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(ArticleData... articles) {
            if (articles[0] != null) {
                return db.articleDao().updateData(articles[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
    }

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

    public static class GetByNameTask extends AsyncTask<String, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetByNameTask(ArticleDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(String... strings) {
            try {
                return db.articleDao().getByName(strings[0]);
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

    public static class GetByPeriodTask extends AsyncTask<Date, Void, List<ArticleWithData>> {

        private final ArticleWithDataCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetByPeriodTask(ArticleDataBase db, ArticleWithDataCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<ArticleWithData> doInBackground(Date... dates) {
            try {

                return db.articleDao().getByPeriod(dates[0], dates[1]);
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
