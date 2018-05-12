package com.example.kevin.homesafe2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobile.api.idxz1n4qn66g.HomeSafeAPIMobileHubClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;



public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker1;
    private Switch willContact;
    private TextView time;
    private int hour, min;
    private String format = "";
    private String friends[] = new String[4];
    DynamoDBMapper dynamoDBMapper;
    private String uniqueUserID = "0";
    private boolean isOut;
    private String name;

    private HomeSafeAPIMobileHubClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        apiClient = new ApiClientFactory()
                .credentialsProvider(AWSMobileClient.getInstance().getCredentialsProvider())
                .build(HomeSafeAPIMobileHubClient.class);

        //Get user's name
        userName();

        //Create get single user from DB
        createUserItem();

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
                    isOut = true;
                } else {
                    time.setText("");
                    isOut = false;
                }

                updateUser(friends, isOut, hour, min);

            }
        });

    }

    public void createUserItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDataDO userItem = dynamoDBMapper.load(
                        UserDataDO.class,
                        uniqueUserID);

                friends[0] = userItem.getFriend1();
                friends[1] = userItem.getFriend2();
                friends[2] = userItem.getFriend3();
                friends[3] = userItem.getFriend4();
                isOut = userItem.getIsOut();
            }
        }).start();

    }

    public void addFriend1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Contact Number (+19876543210)");

        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDataDO userItem = dynamoDBMapper.load(
                        UserDataDO.class,
                        uniqueUserID);

                friends[0] = userItem.getFriend1();
            }
        }).start();

        final EditText input = new EditText(this);

        input.setText(friends[0]);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                friends[0] = input.getText().toString();

                updateUser(friends, isOut, hour, min);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void addFriend2(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Contact Number (+19876543210)");

        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDataDO userItem = dynamoDBMapper.load(
                        UserDataDO.class,
                        uniqueUserID);

                friends[1] = userItem.getFriend2();
            }
        }).start();

        final EditText input = new EditText(this);

        input.setText(friends[1]);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                friends[1] = input.getText().toString();

                updateUser(friends, isOut, hour, min);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void addFriend3(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Contact Number (+19876543210)");

        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDataDO userItem = dynamoDBMapper.load(
                        UserDataDO.class,
                        uniqueUserID);

                friends[2] = userItem.getFriend3();
            }
        }).start();

        final EditText input = new EditText(this);

        input.setText(friends[2]);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                friends[2] = input.getText().toString();

                updateUser(friends, isOut, hour, min);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void addFriend4(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Contact Number (+19876543210)");

        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDataDO userItem = dynamoDBMapper.load(
                        UserDataDO.class,
                        uniqueUserID);

                friends[3] = userItem.getFriend4();
            }
        }).start();

        final EditText input = new EditText(this);

        input.setText(friends[3]);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                friends[3] = input.getText().toString();

                updateUser(friends, isOut, hour, min);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void updateUser(String friends[], boolean isOut, int hour, int min) {
        final UserDataDO userItem = new UserDataDO();
        userItem.setUserId(uniqueUserID);
        userItem.setFriend1(friends[0]);
        userItem.setFriend2(friends[1]);
        userItem.setFriend3(friends[2]);
        userItem.setFriend4(friends[3]);
        userItem.setIsOut(isOut);
        userItem.setHour(hour);
        userItem.setMin(min);
        userItem.setName(name);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userItem);

            }
        }).start();
    }

    private void userName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter Your Name:");

        final EditText input = new EditText(this);

        String defaultName = "John Smith";
        input.setText(defaultName);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
