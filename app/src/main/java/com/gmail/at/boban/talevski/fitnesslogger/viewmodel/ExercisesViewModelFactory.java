package com.gmail.at.boban.talevski.fitnesslogger.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;

public class ExercisesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase db;
    private final String userId;

    public ExercisesViewModelFactory(AppDatabase db, String userId) {
        this.db = db;
        this.userId = userId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ExercisesViewModel(db, userId);
    }
}
