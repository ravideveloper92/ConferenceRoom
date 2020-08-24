package com.example.conferenceroom.data.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.conferenceroom.data.model.Slots;
import com.example.conferenceroom.util.Constants;

import java.util.List;

@Dao
public interface SlotsDao {

    @Query("SELECT * FROM "+ Constants.TABLE_NAME)
    List<Slots> getAll();

    @Query("SELECT * FROM "+Constants.TABLE_NAME+" where room=:roomID")
    List<Slots> getAll(String roomID);

    @Insert
    void insert(Slots task);

    @Delete
    void delete(Slots task);

    @Update
    void update(Slots task);

}
