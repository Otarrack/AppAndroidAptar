package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.florian.projet.model.Machine;

import java.util.List;

@Dao
public interface FavMachineDao {

    @Insert
    public Long insertMachine(Machine machine);

    @Delete
    public int deleteMachine(Machine machine);

    @Query("SELECT * FROM machines")
    List<Machine> getAll();
}
