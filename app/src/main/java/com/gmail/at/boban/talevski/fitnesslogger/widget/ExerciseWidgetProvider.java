package com.gmail.at.boban.talevski.fitnesslogger.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.adapter.ListWidgetService;
import com.gmail.at.boban.talevski.fitnesslogger.ui.ExercisesActivity;

/**
 * Implementation of App Widget functionality.
 */
public class ExerciseWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.exercise_widget);

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, ExercisesActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Set the text of the widget textview
        views.setTextViewText(R.id.widget_all_exercises, context.getString(R.string.all_exercises_text));

        // Set the adapter to the listview
        Intent adapterIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.widget_list_exercises, adapterIntent);

        // Set the click handler on the listview to open the app
        views.setOnClickPendingIntent(R.id.widget_root_linear_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // just call updateExerciseWidgets which is triggered for a manual update when the
        // user adds or remove an exercise
        // there are no automatic updates required since the state is saved in the database
        // and when it changes, a manual update is triggered by calling updateBakingWidgets
        ExerciseWidgetProvider.updateExerciseWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateExerciseWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

