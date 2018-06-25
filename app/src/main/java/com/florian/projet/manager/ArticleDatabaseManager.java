package com.florian.projet.manager;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.florian.projet.MyApplication;
import com.florian.projet.R;
import com.florian.projet.bdd.database.ArticleDataBase;
import com.florian.projet.bdd.database.MachineDataBase;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.ArticleListCallback;
import com.florian.projet.tools.SimpleCallback;

import java.util.ArrayList;
import java.util.Arrays;
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

    public void refreshAllArticle(final ArrayList<Article> articleArrayList, final SimpleCallback insertCallback) {
        deleteAllData(new SimpleCallback() {
            @Override
            public void onSuccess() {
                insertAllArticle(articleArrayList, insertCallback);
            }

            @Override
            public void onFailed(Exception e) {
                insertCallback.onFailed(e);
            }
        });
    }

    public void insertArticle(Article article) {
        try {
            new InsertTask(db).execute(article);

        } catch (Exception e) {
            Log.d("Article Insert", e.getMessage());
        }
    }

    public void insertAllArticle(List<Article> articleList, SimpleCallback callback) {
        try {
            new InsertAllTask(db, callback).execute(articleList.toArray(new Article[articleList.size()]));

        } catch (Exception e) {
            Log.d("Article Insert All", e.getMessage());
        }
    }

    public void updateArticle(Article article) {
        try {
            new UpdateTask(db).execute(article);

        } catch (Exception e) {
            Log.d("Article Update", e.getMessage());
        }
    }

    public void deleteArticle(Article article) {
        try {
            new DeleteTask(db).execute(article);

        } catch (Exception e) {
            Log.d("Article Delete", e.getMessage());
        }
    }

    public void deleteAllData(SimpleCallback callback) {
        new ClearAllTables(db, callback).execute();
    }

    public void getAllArticle(GetAllTask.Callback callback) {
        try {
            new GetAllTask(db, callback).execute();

        } catch (Exception e) {
            Log.d("Article Get All", e.getMessage());
        }
    }

    public void getArticleByName(String name, GetByNameTask.Callback callback) {
        try {
            new GetByNameTask(db, callback).execute(name);

        } catch (Exception e) {
            Log.d("Article Get Name", e.getMessage());
        }
    }

    public void getArticleByPeriod(Date startDate, Date endDate, ArticleListCallback callback) {
        try {
            new GetByPeriodTask(db, callback).execute(startDate, endDate);

        } catch (Exception e) {
            Log.d("Article Get Name", e.getMessage());
        }
    }

    public void getAllArticleFav(ArticleListCallback callback) {
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

    private static class InsertTask extends AsyncTask<Article, Void, Long> {

        private final ArticleDataBase db;

        InsertTask(ArticleDataBase db) {
            this.db = db;
        }

        @Override
        protected Long doInBackground(Article... articles) {
            if (articles[0] != null) {
                return db.articleDao().insert(articles[0]);
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            if (result > 0) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_one_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_one_error,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class InsertAllTask extends AsyncTask<Article, Void, List<Long>> {

        private final ArticleDataBase db;
        private SimpleCallback callback;
        private Exception exception;


        InsertAllTask(ArticleDataBase db, SimpleCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Long> doInBackground(Article... articles) {
            try {
                return db.articleDao().insertAll(Arrays.asList(articles));
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Long> result) {
            super.onPostExecute(result);

            if (result == null) {
                callback.onFailed(exception);
            } else if (result.size() > 0) {
                callback.onSuccess();
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_all_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                callback.onFailed(new Exception("Aucun article ajouté"));
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_add_all_error,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class UpdateTask extends AsyncTask<Article, Void, Integer> {

        private final ArticleDataBase db;

        UpdateTask(ArticleDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Article... articles) {
            if (articles[0] != null) {
                return db.articleDao().update(articles[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            //if (result > 0) {
            //TODO Voir pour rajouter un callback
        }
    }

    private static class DeleteTask extends AsyncTask<Article, Void, Integer> {

        private final ArticleDataBase db;

        DeleteTask(ArticleDataBase db) {
            this.db = db;
        }

        @Override
        protected Integer doInBackground(Article... articles) {
            if (articles[0] != null) {
                return db.articleDao().delete(articles[0]);
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            if (result > 0) {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_del_one_success,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                        R.string.machine_del_one_error,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class GetAllTask extends AsyncTask<Void, Void, List<Article>> {

        private final GetAllTask.Callback callback;
        private final ArticleDataBase db;
        private Exception exception;

        public interface Callback {
            void onSuccess(List<Article> articleList);

            void onFailed(Exception e);
        }

        GetAllTask(ArticleDataBase db, GetAllTask.Callback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Article> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAll();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Article> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

    public static class GetByNameTask extends AsyncTask<String, Void, List<Article>> {

        private final GetByNameTask.Callback callback;
        private final ArticleDataBase db;
        private Exception exception;

        public interface Callback {
            void onSuccess(List<Article> articleList);

            void onFailed(Exception e);
        }

        GetByNameTask(ArticleDataBase db, GetByNameTask.Callback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Article> doInBackground(String... strings) {
            try {
                return db.articleDao().getByName(strings[0]);
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Article> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

    public static class GetByPeriodTask extends AsyncTask<Date, Void, List<Article>> {

        private final ArticleListCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetByPeriodTask(ArticleDataBase db, ArticleListCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Article> doInBackground(Date... dates) {
            try {
                return db.articleDao().getByPeriod(dates[0], dates[1]);
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Article> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

    public static class GetAllFavTask extends AsyncTask<Void, Void, List<Article>> {

        private final ArticleListCallback callback;
        private final ArticleDataBase db;
        private Exception exception;

        GetAllFavTask(ArticleDataBase db, ArticleListCallback callback) {
            this.db = db;
            this.callback = callback;
        }

        @Override
        protected List<Article> doInBackground(Void... voids) {
            try {
                return db.articleDao().getAllFav();
            } catch (Exception e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Article> articleList) {
            super.onPostExecute(articleList);

            if (exception != null) {
                callback.onFailed(exception);
            } else {
                callback.onSuccess(articleList);
            }
        }
    }

}
