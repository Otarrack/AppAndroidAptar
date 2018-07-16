package com.florian.projet.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.bdd.dao.MachineDao;
import com.florian.projet.bdd.entity.Machine;

/**
 * Base de données des machines
 *
 * @author Florian
 */
@Database(entities = {Machine.class}, version = 3)
public abstract class MachineDataBase extends RoomDatabase {

    private static MachineDataBase instance;
    public abstract MachineDao machineDao();

    /**
     * Méthode pour créer l'instance de la base de données
     *
     * @param context Context de l'application pour récupérer le string dans la classe R
     */
    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, MachineDataBase.class, context.getString(R.string.machine_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Méthode pour récupérer l'instance de la base de données
     *
     * @return Liste des machines avec les données associées
     */
    public static MachineDataBase getInstance() {
        return instance;
    }

}
