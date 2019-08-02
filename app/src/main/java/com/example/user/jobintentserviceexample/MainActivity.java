package com.example.user.jobintentserviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_CHECK_STATUS = "check_status";
    public static final String ACTION_DO_STUFF = "action_do_stuff";
    TextView textView;
    ProgressBar progressBar;
    Button startJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        startJob = (Button) findViewById(R.id.button);

        registerReceiver(broadcastReceiver,new IntentFilter(ACTION_CHECK_STATUS));

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(ACTION_CHECK_STATUS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void startjob(View view) {
        SimpleJobIntentService.enqueueWork(this,new Intent().setAction(ACTION_DO_STUFF));
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null)
            {
                int status = intent.getExtras().getInt("status");
                //System.out.println("status = " + status);
                textView.setText(String.valueOf(status)+"%");

                    progressBar.setProgress(status,true);

            }

        }
    };


}
