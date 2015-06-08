package com.example.hani.job;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.util.Log;

/**
 * Created by hani on 2015-06-03.
 */
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("hanix", "Job " + params.getJobId() + " started");
        (new Worker(params)).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("hanix", "Job " + params.getJobId() + " stopped");
        return false;
    }

    private class Worker extends Thread {

        private JobParameters params;

        public Worker(JobParameters params) {
            this.params = params;
        }

        @Override
        public void run() {
            try {
                String name = params.getExtras().getString("k1");
                int sleepTime = params.getExtras().getInt("k2");
                sleep(sleepTime);
                Log.d("hanix", "Job " + name + " " + params.getJobId() + " done");
                jobFinished(params, false);
            } catch (Exception e) {

            }
        }

    }

}
