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

@Database(entities = {Article.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class ArticleDataBase extends RoomDatabase {

    private static ArticleDataBase instance;
    public abstract ArticleDao articleDao();

    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, ArticleDataBase.class, context.getString(R.string.article_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    public static ArticleDataBase getInstance() {
        return instance;
    }

}
