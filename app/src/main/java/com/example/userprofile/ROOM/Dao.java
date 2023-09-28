package com.example.userprofile.ROOM;

import android.view.Display;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insert(Model model);

    @Update
    void update(Model model);

    @Delete
    void delete(Model model);

    @Query("DELETE FROM profile_table")
    void deleteAll();

    @Query("SELECT * FROM profile_table ORDER BY id ASC")
    LiveData<List<Model>> getAllUsers();

    @Query("SELECT * FROM profile_table WHERE id = :itemId")
    LiveData<Model> getUserById(String itemId);

}
