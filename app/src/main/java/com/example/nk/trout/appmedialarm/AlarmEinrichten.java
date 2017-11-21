package com.example.nk.trout.appmedialarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

// Screen 5: Alarm Einrichten
public class AlarmEinrichten extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_einrichten);

        // Find ZeitAngaben view
        TextView launchTimeSettings = (TextView) findViewById(R.id.btn_time_settings);

        // Add click listener to button time settings view
        launchTimeSettings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create intent to open ZeitangabenEinrichten
                Intent zeitangabenEinrichtenIntent = new Intent(AlarmEinrichten.this, ZeitangabenEinrichten.class);

                // Start ZeitangabenEinrichten
                startActivity(zeitangabenEinrichtenIntent);
            }
        });

    }
}
