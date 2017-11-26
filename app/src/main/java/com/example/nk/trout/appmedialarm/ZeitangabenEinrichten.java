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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TabHost;
import java.util.Calendar;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

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

    boolean timeIsSet = false;
    boolean anAlarmIsSet = false;
    boolean playOnce;

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

        // Find cancel alarm button
        final View btnCancel =  findViewById(R.id.cancel_alarm);


        class Alarm extends BroadcastReceiver {

            // When alarm intent is received, the media file will be played
            @Override
            public void onReceive(Context context, Intent intent) {
                // Confirm that alarm is received
                //Log.d("Alarm", "Got alarm");

                // create media player to play alarm audio
                final MediaPlayer mp = MediaPlayer.create(context, R.raw.breeze);
                mp.start();
                mp.isLooping();

                // Find stop alarm button
                final View btnStopAlarm = findViewById(R.id.stop_alarm);
                btnStopAlarm.setEnabled(true);

                // Add click listener to stop alarm
                btnStopAlarm.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mp.stop();
                        mp.release();

                        // reset time settings
                        alarmHour = 0;
                        alarmMinutes = 0;
                        alarmSeconds = 0;
                        timeIsSet = false;

                        // disable stop button to avoid crash when clicked
                        btnStopAlarm.setEnabled(false);

                        // cancel button can be clicked but is not intuitive to user as there is no repetition
                        if(playOnce){
                            btnCancel.setEnabled(false);
                        }
                    }
                });
            }
        }

        // here we register the receiver with the app so that it knows it exists
        // the intent filter is used as a form of unique identifier for an event that gets broadcast
        // you will see when we create the alarmIntent that we tag the intent in the parameters with the same tag as here
        registerReceiver(new Alarm(), new IntentFilter("Alarm"));


        // Find now button
        final View btnNow =  findViewById(R.id.now);

        // Add click listener to set alarm time to now
        btnNow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                alarmHour = currentTime.getHours();
                alarmMinutes = currentTime.getMinutes();
                alarmSeconds = currentTime.getSeconds();
                timeIsSet = true;
            }
        });

        // Find in 5 minutes button
        final View btnInFive =  findViewById(R.id.in_5_minutes);

        // Add click listener to add 5 minutes to current time
        btnInFive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                currentTime = Calendar.getInstance().getTime();
                alarmHour = currentTime.getHours();
                alarmMinutes = currentTime.getMinutes()+5;
                alarmSeconds = currentTime.getSeconds();
                timeIsSet = true;

            }
        });

        // Clicking set alarm button triggers the alarm
        // Find set alarm button
        final View btnSetAlarm =  findViewById(R.id.setAlarm);

        // Add click listener to set alarm time to user input values
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // if now or in 5 minutes buttons have not been clicked, get user input values
                if(!timeIsSet){

                    //find time picker for time setting
                    final TimePicker dp = findViewById(R.id.tab2);

                    alarmHour = dp.getCurrentHour();
                    alarmMinutes = dp.getCurrentMinute();
                    alarmSeconds = Calendar.getInstance().getTime().getSeconds();
                }

                // Find repetition checkbox
                final CheckBox checkRepetition = (CheckBox) findViewById(R.id.repeat_2_min);

                Calendar cal = Calendar.getInstance();

                AlarmManager alarms ;

                // notice we added the "Alarm" tag to the intent so the app knows to fire the Alarm :D
                final PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("Alarm"), 0);
                alarms = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                cal.set(Calendar.HOUR_OF_DAY, alarmHour);
                cal.set(Calendar.MINUTE, alarmMinutes);
                cal.set(Calendar.SECOND, alarmSeconds);



                if(checkRepetition.isChecked()){

                    // if repeating is checked, alarm will be set to an interval of 2 minutes (Millisec * Second * Minute)
                    alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000 * 60 * 2, alarmIntent);
                    playOnce = false;
                }
                else{
                    alarms.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
                    playOnce = true;
                }
                anAlarmIsSet = true;



                if(anAlarmIsSet){
                    btnCancel.setEnabled(true);
                }

                // Add click listener to cancel currently set alarm
                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        PendingIntent pendingAlarm = alarmIntent;
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.cancel(pendingAlarm);

                        anAlarmIsSet = false;

                        btnCancel.setEnabled(false);

                        Toast.makeText(getApplicationContext(), "Alarm cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });



    }
}