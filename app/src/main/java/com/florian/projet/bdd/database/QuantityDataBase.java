package com.florian.projet.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.bdd.converter.DateConverter;
import com.florian.projet.bdd.dao.ArticleDao;
import com.florian.projet.bdd.dao.OFDataDao;
import com.florian.projet.bdd.dao.PresseDao;
import com.florian.projet.bdd.entity.Article;
import com.florian.projet.bdd.entity.OFData;
import com.florian.projet.bdd.entity.Presse;

/**
 * Base de données des articles
 *
 * @author Florian
 */
@Database(entities = {Article.class, Presse.class, OFData.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class QuantityDataBase extends RoomDatabase {

    private static QuantityDataBase instance;
    public abstract ArticleDao articleDao();
    public abstract PresseDao presseDao();
    public abstract OFDataDao ofDataDao();

    /**
     * Méthode pour créer l'instance de la base de données
     *
     * @param context Context de l'application pour récupérer le string dans la classe R
     */
    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, QuantityDataBase.class, context.getString(R.string.quantity_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Méthode pour récupérer l'instance de la base de données
     *
     * @return Liste des machines avec les données associées
     */
    public static QuantityDataBase getInstance() {
        return instance;
    }

}
