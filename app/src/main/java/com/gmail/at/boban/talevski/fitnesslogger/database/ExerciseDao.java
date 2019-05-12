package com.gmail.at.boban.talevski.fitnesslogger.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    List<ExerciseEntry> loadAllExercises();

    @Insert
    void insertExercise(ExerciseEntry exerciseEntry);

    @Update
    void updateExercise(ExerciseEntry exerciseEntry);

    @Delete
    void deleteExercise(ExerciseEntry exerciseEntry);

}
