package com.florian.projet.bdd.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.florian.projet.model.Machine;

import java.util.List;

@Dao
public interface MachineDao {

    @Insert
    long insert(Machine machine);

    @Insert
    List<Long> insertAll(List<Machine> machineList);

    @Update
    int update(Machine machine);

    @Delete
    int delete(Machine machine);

    @Query("SELECT * FROM machines")
    List<Machine> getAll();

    @Query("SELECT * FROM machines WHERE name LIKE :name LIMIT 1")
    Machine getByName(String name);

    @Query("SELECT * FROM machines WHERE favorite = 1")
    List<Machine> getAllFav();
}
