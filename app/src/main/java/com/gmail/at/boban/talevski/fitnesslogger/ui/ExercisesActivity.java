package com.gmail.at.boban.talevski.fitnesslogger.ui;

import android.app.ActivityOptions;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.adapter.ExerciseAdapter;
import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;
import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;
import com.gmail.at.boban.talevski.fitnesslogger.utils.AppExecutors;
import com.gmail.at.boban.talevski.fitnesslogger.utils.WidgetUtils;
import com.gmail.at.boban.talevski.fitnesslogger.viewmodel.ExercisesViewModel;
import com.gmail.at.boban.talevski.fitnesslogger.viewmodel.ExercisesViewModelFactory;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    public static final String ANONYMOUS_USER_ID = "ANONYMOUS";

    private RecyclerView recyclerViewExercises;
    private ExerciseAdapter exerciseAdapter;
    private String signedInUserId;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Initialize the signed in account
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            signedInUserId = account.getId();
        } else {
            signedInUserId = ANONYMOUS_USER_ID;
        }

        // Initialize the database
        db = AppDatabase.getInstance(getApplicationContext());

        // Initialize the recycler view
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        // Set the recycler view layout
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));

        // Set up the viewmodel
        ExercisesViewModelFactory factory = new ExercisesViewModelFactory(db, signedInUserId);
        ExercisesViewModel viewModel =
                ViewModelProviders.of(this, factory).get(ExercisesViewModel.class);
        final LiveData<List<ExerciseEntry>> exercises = db.exerciseDao().loadAllExercisesForUser(signedInUserId);
        viewModel.getExercises().observe(this, new Observer<List<ExerciseEntry>>() {
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(addExerciseIntent,
                            ActivityOptions.makeSceneTransitionAnimation(ExercisesActivity.this).toBundle());
                } else {
                    startActivity(addExerciseIntent);
                }
            }
        });

        // Touch helper for the recycler view to detect swipes (left and right)
        // to delete an exercise
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Delete the "swiped" exercise from the database
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<ExerciseEntry> exercises = exerciseAdapter.getExercises();
                        db.exerciseDao().deleteExercise(exercises.get(position));

                        // update the widget
                        WidgetUtils.updateWidget(ExercisesActivity.this);
                    }
                });
            }
        }).attachToRecyclerView(recyclerViewExercises);

        setupTransitions();
    }

    private void setupTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Slide(Gravity.START));
        }
    }

}
