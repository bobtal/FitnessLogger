package com.gmail.at.boban.talevski.fitnesslogger.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Query("SELECT * FROM exercise")
    LiveData<List<ExerciseEntry>> loadAllExercises();

    @Query("SELECT * FROM exercise WHERE user_id = :userId")
    LiveData<List<ExerciseEntry>> loadAllExercisesForUser(String userId);

    @Query("SELECT * FROM exercise WHERE category IN (:ids)")
    List<ExerciseEntry> loadAllExercisesFilterByCategory(List<Integer> ids);

    @Insert
    void insertExercise(ExerciseEntry exerciseEntry);

    @Update
    void updateExercise(ExerciseEntry exerciseEntry);

    @Delete
    void deleteExercise(ExerciseEntry exerciseEntry);

}
