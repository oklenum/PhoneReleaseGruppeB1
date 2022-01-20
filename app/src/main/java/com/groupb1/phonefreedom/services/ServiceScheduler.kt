package com.groupb1.phonefreedom.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import android.widget.Toast

class ServiceScheduler : JobService() {
    override fun onStopJob(p0: JobParameters?): Boolean {
        Toast.makeText(applicationContext, "JobCancelled", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        var k=0
        Toast.makeText(applicationContext, "Job Running", Toast.LENGTH_SHORT).show()
        for (k in 0..10)
        {
            Log.v("Job Scheduler", "Running Job $k")
        }
        Toast.makeText(applicationContext,"Job Finished", Toast.LENGTH_SHORT).show()
        return false
    }
}