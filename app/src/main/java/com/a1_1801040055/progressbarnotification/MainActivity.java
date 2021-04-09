package com.a1_1801040055.progressbarnotification;


import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


public class MainActivity extends AppCompatActivity {
    private NotificationManager notifyManager;
    private NotificationCompat.Builder mBuilder;
    int id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button) findViewById(R.id.btnShow);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
                        .setContentText("Notification")
                        .setContentTitle("File Download")
                        .setContentText("Download in progress");
                notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // Start a the operation in a background thread
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                int incr;
                                // Do the "lengthy" operation 20 times
                                for (incr = 0; incr <= 100; incr+=15) {
                                    // Sets the progress indicator to a max value, the current completion percentage and "determinate" state
                                    builder.setProgress(100, incr, false);
                                    // Displays the progress bar for the first time.
                                    notifyManager.notify(id, builder.build());
                                    // Sleeps the thread, simulating an operation
                                    try {
                                        // Sleep for 1 second
                                        Thread.sleep(1*1000);
                                    } catch (InterruptedException e) {
                                        Log.d("TAG", "sleep failure");
                                    }
                                }
                                // When the loop is finished, updates the notification
                                builder.setContentText("Download completed")
                                        // Removes the progress bar
                                        .setProgress(0,0,false);
                                notifyManager.notify(id, builder.build());
                            }
                        }
                        // Starts the thread by calling the run() method in its Runnable
                ).start();
            }
        });
    }
}