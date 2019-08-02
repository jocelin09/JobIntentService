package com.example.user.jobintentserviceexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static com.example.user.jobintentserviceexample.MainActivity.ACTION_CHECK_STATUS;
import static com.example.user.jobintentserviceexample.MainActivity.ACTION_DO_STUFF;

public class SimpleJobIntentService extends JobIntentService {

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SimpleJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        // We have received work to do.  The system or framework is already
        // holding a wake lock for us at this point, so we can just go.
        Log.i("SimpleJobIntentService", "Executing work: " + intent);
        if (intent.getAction() != null)
        {
            switch (intent.getAction())
            {
                case ACTION_DO_STUFF:
                    for (int i = 0; i <= 100; i++) {
                        Log.i("SimpleJobIntentService", "Running service " + (i + 1)
                                + "/5 @ " + SystemClock.elapsedRealtime());
                        try {
                            Thread.sleep(50); //50ms
                        } catch (InterruptedException e) {
                        }
                        Bundle bundle = new Bundle();
                        bundle.putInt("status",i);
                        sendBroadcast(new Intent().setAction(ACTION_CHECK_STATUS).putExtras(bundle));
                    }
            }
        }


        Log.i("SimpleJobIntentService", "Completed service @ " + SystemClock.elapsedRealtime());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toast("Done.");
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(SimpleJobIntentService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
