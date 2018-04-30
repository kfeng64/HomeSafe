package com.example.kevin.homesafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity {
    private User user;
    private TimePicker timePicker1;
    private Switch willContact;
    private TextView time;
    private int hour, min;
    private String format = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final TextView phoneNumber = findViewById(R.id.phoneNumber);

        final Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = new User(phoneNumber.getText().toString());
                setContentView(R.layout.activity_main);
                setTime();
            }
        });





    }

    public void setTime() {
        timePicker1 = findViewById(R.id.timePicker1);
        time = findViewById(R.id.time);
        willContact = findViewById(R.id.switch1);

        willContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hour = timePicker1.getHour();
                    min = timePicker1.getMinute();
                    showTime(hour, min);
                } else {
                    time.setText("");
                }
            }
        });

//        Log.i("time:", "hour" + hour + " minute" + min);
    }

    public void showTime(int hour, int min) {
        String minFormat;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        if (min < 10) {
            minFormat = "0" + Integer.toString(min);
        } else {
            minFormat = Integer.toString(min);
        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(minFormat)
                .append(" ").append(format));
    }
}
