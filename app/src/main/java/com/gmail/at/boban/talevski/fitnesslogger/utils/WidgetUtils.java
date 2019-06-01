package com.gmail.at.boban.talevski.fitnesslogger.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.gmail.at.boban.talevski.fitnesslogger.R;
import com.gmail.at.boban.talevski.fitnesslogger.widget.ExerciseWidgetProvider;

public class WidgetUtils {
    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context, ExerciseWidgetProvider.class));
        ExerciseWidgetProvider.updateExerciseWidgets(
                context, appWidgetManager, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_exercises);
    }
}
