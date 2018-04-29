package com.example.kevin.homesafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker1;
    private Switch willContact;
    private int hour, min;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker1 = findViewById(R.id.timePicker1);
        willContact = findViewById(R.id.switch1);

        willContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hour = timePicker1.getHour();
                    min = timePicker1.getMinute();
                }
            }
        });

        Log.i("time:", "hour" + hour + " minute" + min);

    }

    protected void getTime() {
        TimePicker timePicker1 = findViewById(R.id.timePicker1);
        int hour = timePicker1.getHour();
        int min = timePicker1.getMinute();
        Log.i("time:", "hour" + hour + " minute" + min);
    }
}
