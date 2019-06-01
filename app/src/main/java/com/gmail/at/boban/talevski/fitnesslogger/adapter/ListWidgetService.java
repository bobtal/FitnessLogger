package com.gmail.at.boban.talevski.fitnesslogger.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.database.AppDatabase;
import com.gmail.at.boban.talevski.fitnesslogger.model.ExerciseEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String LOG_TAG = ListRemoteViewsFactory.class.getSimpleName();

    private Context context;
    private List<String> exerciseList;

    // used for waiting for the db call in onCreate to finish and populate exerciseList
    // before calls to getCount() and getViewAt() are done
    // so that the adapter has the date and populates the views properly
    private CountDownLatch countDown;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        // initialize the CountDownLatch to wait for 1 countdown done when the async task is finished
        // getCount() and getViewAt() await for this countdown before rendering the
        // listview inside the widget
        countDown = new CountDownLatch(1);

        // Use an AsyncTask to get the data from the db
        new AsyncTask<Context, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Context... contexts) {
                // Get all exercises from the db
                List<ExerciseEntry> allExercises = AppDatabase.getInstance(context).exerciseDao().loadAllExercises();
                // Set up a String list to hold only the names of the exercises
                final List<String> exerciseNames = new ArrayList<>();
                // Loop through the list of exercise objects and extract
                // the names in the separate String list
                for (ExerciseEntry exerciseEntry :
                        allExercises) {
                    exerciseNames.add(exerciseEntry.getName());
                }
                // return the extracted list of exercise names
                return exerciseNames;
            }

            @Override
            protected void onPostExecute(List<String> strings) {
                super.onPostExecute(strings);

                // set the returned list of exercise names to the exerciseList field
                exerciseList = strings;

                // indicate that the latch could be released so that getCount() and getViewAt()
                // can render the listview inside the widget
                countDown.countDown();
            }
        }.execute(context);

    }

    @Override
    public void onDataSetChanged() {
        // just do the same thing from onCreate to reinitialize exercise to
        // the current data found in the database
        onCreate();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        // wait until the latch is released before fetching the data
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "getCount() interrupted");
        }
        return exerciseList == null ? 0 : exerciseList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        // wait until the latch is released before fetching the data
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "getViewAt() interrupted");
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
        views.setTextViewText(R.id.widget_list_text, exerciseList.get(i));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}