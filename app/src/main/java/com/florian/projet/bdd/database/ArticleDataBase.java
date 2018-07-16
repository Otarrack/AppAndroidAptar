package com.florian.projet.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.bdd.converter.DateConverter;
import com.florian.projet.bdd.dao.ArticleDao;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.ArticleData;

/**
 * Base de données des articles
 *
 * @author Florian
 */
@Database(entities = {Article.class, ArticleData.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class ArticleDataBase extends RoomDatabase {

    private static ArticleDataBase instance;
    public abstract ArticleDao articleDao();

    /**
     * Méthode pour créer l'instance de la base de données
     *
     * @param context Context de l'application pour récupérer le string dans la classe R
     */
    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, ArticleDataBase.class, context.getString(R.string.article_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Méthode pour récupérer l'instance de la base de données
     *
     * @return Liste des machines avec les données associées
     */
    public static ArticleDataBase getInstance() {
        return instance;
    }

}
