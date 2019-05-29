package com.gmail.at.boban.talevski.fitnesslogger.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;
import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;
import com.gmail.at.boban.talevski.fitnesslogger.utils.AppExecutors;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class AddExerciseActivity extends AppCompatActivity {

    private Spinner categorySpinner;
    private EditText exerciseNameEditText, exerciseDescriptionEditText;
    private Button addExerciseButton;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        // Initialize the views
        initViews();

        // Initialize the database
        db = AppDatabase.getInstance(getApplicationContext());

        // Setup the add button onClick listener
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Prevent a blank entry for either name or description
                if (exerciseNameEditText.getText().toString().trim().equals("") ||
                        exerciseDescriptionEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(AddExerciseActivity.this,
                            getString(R.string.name_description_blank), Toast.LENGTH_LONG).show();
                } else {
                    // Create the new ExerciseEntry object using the entered text
                    // and the id of the currently logged in user
                    final ExerciseEntry newExercise = new ExerciseEntry(
                            exerciseNameEditText.getText().toString(),
                            exerciseDescriptionEditText.getText().toString(),
                            getExerciseIdFromSpinner(categorySpinner.getSelectedItem().toString()),
                            getUserId(),
                            null
                    );

                    // Add the ExerciseEntry object in the db using an Executor
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            db.exerciseDao().insertExercise(newExercise);
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void initViews() {
        categorySpinner = findViewById(R.id.spinnerCategory);
        exerciseNameEditText = findViewById(R.id.exerciseNameEditText);
        exerciseDescriptionEditText = findViewById(R.id.exerciseDescriptionEditText);
        addExerciseButton = findViewById(R.id.addExerciseButton);
    }

    private String getUserId() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            return account.getId();
        } else {
            return ExercisesActivity.ANONYMOUS_USER_ID;
        }
    }

    private int getExerciseIdFromSpinner(String category) {
        String[] categories = getResources().getStringArray(R.array.exercise_categories);
        if (category.equals(categories[0])) {
            return 1;
        } else if (category.equals(categories[1])) {
            return 2;
        } else {
            return 3;
        }
    }


}
