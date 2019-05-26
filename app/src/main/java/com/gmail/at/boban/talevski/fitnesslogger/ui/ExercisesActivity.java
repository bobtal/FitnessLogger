package com.gmail.at.boban.talevski.fitnesslogger.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmail.at.boban.talevski.fitnesslogger.R;

public class ExercisesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Initialize the FAB and set its onClick listener
        FloatingActionButton fabAddExercise = findViewById(R.id.fabAddExercise);
        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addExerciseIntent = new Intent(ExercisesActivity.this, AddExerciseActivity.class);
            }
        });
    }
}
