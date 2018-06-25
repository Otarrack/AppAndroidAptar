package com.florian.projet.viewModel;

import android.util.Log;

import com.florian.projet.bdd.entity.Article;
import com.florian.projet.manager.ArticleDatabaseManager;
import com.florian.projet.manager.MachineDatabaseManager;
import com.florian.projet.bdd.entity.Machine;
import com.florian.projet.tools.ArticleListCallback;

public class FavorisViewModel {
    private static FavorisViewModel instance;
    private MachineDatabaseManager machineDatabaseManager;
    private ArticleDatabaseManager articleDatabaseManager;

    public static FavorisViewModel getInstance() {
        if(instance == null) {
            instance = new FavorisViewModel();
        }
        return instance;
    }

    private FavorisViewModel() {
        machineDatabaseManager = MachineDatabaseManager.getInstance();
        articleDatabaseManager = ArticleDatabaseManager.getInstance();
    }

    public void updateFavMachine(Machine machine) {
        try {
            machineDatabaseManager.updateMachine(machine);
        } catch (Exception e) {
            Log.d("Fav Machine View Model", e.getMessage());
        }
    }

    public void updateFavArticle(Article article) {
        try {
            articleDatabaseManager.updateArticle(article);
        } catch (Exception e) {
            Log.d("Fav Article View Model", e.getMessage());
        }
    }

    public void getAllFavMachine(MachineDatabaseManager.GetAllFavTask.Callback callback) {
        machineDatabaseManager.getAllMachineFav(callback);
    }

    public void getAllFavArticle(ArticleListCallback callback) {
        articleDatabaseManager.getAllArticleFav(callback);
    }
}
