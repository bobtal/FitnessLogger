package com.gmail.at.boban.talevski.fitnesslogger.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gmail.at.boban.talevski.fitnesslogger.R;

public class CheckNetworkTask extends AsyncTask<Void, Void, Boolean> {

    private Context context;

    public CheckNetworkTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return NetworkUtils.isNetworkAvailable(context);
    }

    @Override
    protected void onPostExecute(Boolean isNetworkAvailable) {
        if (!isNetworkAvailable) {
            Toast.makeText(context, context.getString(R.string.no_network), Toast.LENGTH_LONG).show();
        }
    }
}
