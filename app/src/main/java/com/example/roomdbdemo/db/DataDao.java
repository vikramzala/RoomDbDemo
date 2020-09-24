package com.example.roomdbdemo.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {


    @Query("SELECT * FROM data")
    LiveData<List<DataEntity>> getAllDatas();

    @Query("SELECT * FROM data WHERE id=:id")
    DataEntity getDataById(int id);

    @Query("SELECT * FROM data WHERE id=:id")
    LiveData<DataEntity> getData(int id);

    @Insert
    long insert(DataEntity note);

    @Update
    void update(DataEntity note);

    @Delete
    void delete(DataEntity note);

    @Query("DELETE FROM data")
    void deleteAll();

    @Query("SELECT * FROM data")
    List<DataEntity> getAll();

    @Query("UPDATE data SET firstName = :firstName, lastName= :lastName, mobileNo= :mobileNo, birthDate= :birthDate WHERE id =:id")
    void updateById(String firstName, String lastName, String mobileNo, String birthDate, int id);
}
