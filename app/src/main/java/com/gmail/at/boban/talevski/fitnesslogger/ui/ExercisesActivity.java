package com.gmail.at.boban.talevski.fitnesslogger.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.adapter.ExerciseAdapter;
import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;
import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewExercises;
    private ExerciseAdapter exerciseAdapter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Initialize the database
        db = AppDatabase.getInstance(getApplicationContext());

        // Initialize the recycler view
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        // Set the recycler view layout
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and attach it to the recycler view
        final LiveData<List<ExerciseEntry>> exercises = db.exerciseDao().loadAllExercises();
        exercises.observe(this, new Observer<List<ExerciseEntry>>() {
            @Override
            public void onChanged(@Nullable List<ExerciseEntry> exerciseEntries) {
                exerciseAdapter = new ExerciseAdapter(exerciseEntries, ExercisesActivity.this);
                recyclerViewExercises.setAdapter(exerciseAdapter);
            }
        });


        // Initialize the FAB and set its onClick listener
        FloatingActionButton fabAddExercise = findViewById(R.id.fabAddExercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addExerciseIntent = new Intent(ExercisesActivity.this, AddExerciseActivity.class);
                startActivity(addExerciseIntent);
            }
        });
    }
}
