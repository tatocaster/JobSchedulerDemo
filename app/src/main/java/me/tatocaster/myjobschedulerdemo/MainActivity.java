package me.tatocaster.myjobschedulerdemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.tatocaster.myjobschedulerdemo.service.MyJobService;

public class MainActivity extends AppCompatActivity {
    private static int kJobId = 0;
    private ComponentName mServiceComponent;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceComponent = new ComponentName(this, MyJobService.class);
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        createJob();
    }

    public void scheduleJob(View view) {
        createJob();
    }

    private void createJob() {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, mServiceComponent);

        builder.setMinimumLatency(1 * 1000);
        builder.setOverrideDeadline(20 * 1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // only WIFI
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);

        mJobScheduler.schedule(builder.build());
    }

    public void cancelJob(View view) {
        mJobScheduler.cancelAll();
    }
}
