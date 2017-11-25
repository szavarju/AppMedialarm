package com.example.nk.trout.appmedialarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TabHost;
import java.util.Calendar;
import android.view.View;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;


@SuppressWarnings("deprecation")
//Screen 6: Zeitangaben Einrichten
public class ZeitangabenEinrichten extends AppCompatActivity {

    Date currentTime;

    int alarmHour;
    int alarmMinutes;
    int alarmSeconds;

    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zeitangaben_einrichten);

        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(R.id.tabhost);

        if(TabHostWindow != null){
            TabHostWindow.setup();
            //Creating tab menu.
            TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First Tab");
            TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
            TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");

            //Setting up tab 1 name.
            String tab1title = getString(R.string.start);
            TabMenu1.setIndicator(tab1title);
            //Set tab 1 activity to tab 1 menu.
            //Intent tab1 = new Intent(this,set_start_day.class);
            TabMenu1.setContent(R.id.tab1);

            //Setting up tab 2 name.
            TabMenu2.setIndicator(getString(R.string.time));
            //Set tab 3 activity to tab 1 menu.
            //TabMenu2.setContent(new Intent(this,set_time.class));
            TabMenu2.setContent(R.id.tab2);

            //Setting up tab 2 name.
            TabMenu3.setIndicator(getString(R.string.time_period));
            //Set tab 3 activity to tab 3 menu.
            //TabMenu3.setContent(new Intent(this,set_time_period.class));
            TabMenu3.setContent(R.id.tab3);
            //Adding tab1, tab2, tab3 to tabhost view.

            TabHostWindow.addTab(TabMenu1);
            TabHostWindow.addTab(TabMenu2);
            TabHostWindow.addTab(TabMenu3);

        }




        class Alarm extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Alarm", "Got alarm");
                final MediaPlayer mp = MediaPlayer.create(context, R.raw.breeze);
                mp.start();
                mp.isLooping();

                // Find stop alarm button
                View btnStopAlarm = (View) findViewById(R.id.stop_alarm);

                // Add click listener to stop alarm
                btnStopAlarm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mp.stop();
                        mp.release();
                    }
                });
            }
        }

        // here we register the receiver with the app so that it knows it exists
        // the intent filter is used as a form of unique identifier for an event that gets broadcast
        // you will see when we create the alarmIntent that we tag the intent in the parameters with the same tag as here
        registerReceiver(new Alarm(), new IntentFilter("Alarm"));


        currentTime = Calendar.getInstance().getTime();
        alarmHour = currentTime.getHours();
        alarmMinutes = currentTime.getMinutes();
        alarmSeconds = currentTime.getSeconds();

        Calendar cal = Calendar.getInstance();

        AlarmManager alarms ;

        // notice we added the "Alarm" tag to the intent so the app knows to fire the Alarm :D
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("Alarm"), 0);
        alarms = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
        cal.set(Calendar.HOUR_OF_DAY, alarmHour);
        cal.set(Calendar.MINUTE, alarmMinutes);
        cal.set(Calendar.SECOND, alarmSeconds);
        alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);


        // Find alarm view
        final View btnInFive =  findViewById(R.id.in_5_minutes);

        // Add click listener to create alarm view
        btnInFive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });

    }
}