package com.florian.projet.bdd.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.florian.projet.R;
import com.florian.projet.bdd.dao.MachineDao;
import com.florian.projet.bdd.entity.Machine;

@Database(entities = {Machine.class}, version = 2)
public abstract class MachineDataBase extends RoomDatabase {

    private static MachineDataBase instance;
    public abstract MachineDao machineDao();

    public static void setInstance(Context context) {
        instance = Room.databaseBuilder(context, MachineDataBase.class, context.getString(R.string.machine_database_name))
                .fallbackToDestructiveMigration()
                .build();
    }

    public static MachineDataBase getInstance() {
        return instance;
    }

}
