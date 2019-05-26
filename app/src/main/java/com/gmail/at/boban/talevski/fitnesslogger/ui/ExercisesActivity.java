package com.gmail.at.boban.talevski.fitnesslogger.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.adapter.ExerciseAdapter;
import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;

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
        exerciseAdapter = new ExerciseAdapter(db.exerciseDao().loadAllExercises(), this);
        recyclerViewExercises.setAdapter(exerciseAdapter);

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
