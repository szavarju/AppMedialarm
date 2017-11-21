package com.example.nk.trout.appmedialarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//Screen 1: Aufruf - Kein Alarm aktiv
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find alarm view
        TextView launchCreateAlarm = (TextView) findViewById(R.id.btn_create_alarm);

        // Add click listener to create alarm view
        launchCreateAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create intent to open AlarmEinrichten
                Intent alarmEinrichtenIntent = new Intent(MainActivity.this, AlarmEinrichten.class);

                // Start AlarmEinrichten
                startActivity(alarmEinrichtenIntent);
            }
        });

        // Find medialarm logo
        ImageView launchUebersicht = (ImageView) findViewById(R.id.medialarm_logo);

        // Add click listener to medialarm logo view
        launchUebersicht.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Create intent to open Uebersicht
                Intent uebersichtIntent = new Intent(MainActivity.this, Uebersicht.class);

                // Start Uebersicht
                startActivity(uebersichtIntent);
            }
        });




    }

}
