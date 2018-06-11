package com.florian.projet.bdd.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.florian.projet.bdd.dao.FavMachineDao;
import com.florian.projet.model.Machine;

@Database(entities = {Machine.class}, version = 1)
public abstract class FavMachineDataBase extends RoomDatabase {

    private static FavMachineDataBase instance;

    public abstract FavMachineDao favMachineDao();

    public static FavMachineDataBase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),FavMachineDataBase.class,"FavDatabase")
                    .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Nothing to add, just migrate to the new Database and keep the old data
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Nothing to add, just migrate to the new Database and keep the old data
        }
    };


    public static void destroyInstance()
    {
        instance = null;
    }

}
