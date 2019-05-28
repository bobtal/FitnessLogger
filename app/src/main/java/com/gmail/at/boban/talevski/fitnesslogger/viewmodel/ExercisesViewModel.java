package com.gmail.at.boban.talevski.fitnesslogger.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;
import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;

import java.util.List;

public class ExercisesViewModel extends ViewModel {

    private LiveData<List<ExerciseEntry>> exercises;

    public ExercisesViewModel(AppDatabase db, String userId) {
        exercises = db.exerciseDao().loadAllExercisesForUser(userId);
    }

    public LiveData<List<ExerciseEntry>> getExercises() {
        return exercises;
    }
}
