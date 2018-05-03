package com.example.kevin.homesafe;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

        final TextView name = findViewById(R.id.name);

        final Button button = findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user = new User(name.getText().toString());
                setContentView(R.layout.activity_main);
                settingTimePage();
            }
        });

    }

    public void settingTimePage() {
        timePicker1 = findViewById(R.id.timePicker1);
        time = findViewById(R.id.time);
        willContact = findViewById(R.id.switch1);
        final FloatingActionButton addFriend1 = findViewById(R.id.addFriend1);
        final FloatingActionButton addFriend2 = findViewById(R.id.addFriend2);
        final FloatingActionButton addFriend3 = findViewById(R.id.addFriend3);
        final FloatingActionButton addFriend4 = findViewById(R.id.addFriend4);

//        TextView debug =  findViewById(R.id.debug);
//        debug.setText(user.name);

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

        addFriend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(addFriend1);
            }
        });
        addFriend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(addFriend2);
            }
        });
        addFriend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(addFriend3);
            }
        });
        addFriend4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriend(addFriend4);
            }
        });



    }

    public void addFriend(FloatingActionButton friend) {
        //just do new window
    }

    private void showTime(int hour, int min) {
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
