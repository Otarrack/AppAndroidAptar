package com.florian.projet.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.bdd.dao.PourcentagePerformanceDao;
import com.florian.projet.bdd.entity.Machine;

/**
 * Base de données des machines
 *
 * @author Florian
 */
@Database(entities = {Machine.class}, version = 1)
public abstract class PourcentagePerformanceDataBase extends RoomDatabase {

    private static PourcentagePerformanceDataBase instance;
    public abstract PourcentagePerformanceDao machineDao();

    /**
     * Méthode pour créer l'instance de la base de données
     *
     * @param context Context de l'application pour récupérer le string dans la classe R
     */
    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, PourcentagePerformanceDataBase.class, context.getString(R.string.pourcentage_perf_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Méthode pour récupérer l'instance de la base de données
     *
     * @return Liste des machines avec les données associées
     */
    public static PourcentagePerformanceDataBase getInstance() {
        return instance;
    }

}
