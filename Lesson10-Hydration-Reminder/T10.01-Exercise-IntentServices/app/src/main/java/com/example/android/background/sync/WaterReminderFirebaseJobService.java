package com.example.android.background.sync;

import android.app.job.JobParameters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.JobService;

public class WaterReminderFirebaseJobService extends JobService {
    private AsyncTask mBackGroundTask;

    @Override
    public boolean onStartJob(@NonNull final com.firebase.jobdispatcher.JobParameters job) {
        mBackGroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);

            }
        };
        mBackGroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        if(mBackGroundTask!=null){
            mBackGroundTask.cancel(true);
        }
        return true;
    }
}
