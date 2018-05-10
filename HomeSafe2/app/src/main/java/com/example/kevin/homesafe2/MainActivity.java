package com.example.kevin.homesafe2;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;



public class MainActivity extends AppCompatActivity {
    private TimePicker timePicker1;
    private Switch willContact;
    private TextView time;
    private int hour, min;
    private String format = "";
    private String friends[] = new String[4];
    DynamoDBMapper dynamoDBMapper;
    private String uniqueUserID = "0";
    private AWSCredentialsProvider credentialsProvider;
    private AWSConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {

                // Obtain the reference to the AWSCredentialsProvider and AWSConfiguration objects
                credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
                configuration = AWSMobileClient.getInstance().getConfiguration();

                // Use IdentityManager#getUserID to fetch the identity id.
                IdentityManager.getDefaultIdentityManager().getUserID(new IdentityHandler() {
                    @Override
                    public void onIdentityId(String identityId) {
                        Log.d("YourMainActivity", "Identity ID = " + identityId);

                        // Use IdentityManager#getCachedUserID to
                        //  fetch the locally cached identity id.
                        final String cachedIdentityId =
                                IdentityManager.getDefaultIdentityManager().getCachedUserID();
                    }

                    @Override
                    public void handleError(Exception exception) {
                        Log.d("YourMainActivity", "Error in retrieving the identity" + exception);
                    }
                });
            }
        }).execute();


        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        //Create single item in DB
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
                } else {
                    time.setText("");
                }
            }
        });
    }

    public void createUserItem() {
        final UserDataDO userItem = new UserDataDO();

        userItem.setUserId(uniqueUserID);

        userItem.setFriend1("");
        userItem.setFriend1("");
        userItem.setFriend1("");
        userItem.setFriend1("");

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userItem);
                // Item saved
            }
        }).start();
    }

    public void addFriend1(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Contact Number");

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

                final UserDataDO userItem = new UserDataDO();
                userItem.setUserId(uniqueUserID);
                userItem.setFriend1(friends[0]);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(userItem);

                    }
                }).start();
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
        builder.setTitle("Enter Contact Number");

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

                final UserDataDO userItem = new UserDataDO();
                userItem.setUserId(uniqueUserID);
                userItem.setFriend2(friends[1]);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(userItem);

                    }
                }).start();
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
        builder.setTitle("Enter Contact Number");

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

                final UserDataDO userItem = new UserDataDO();
                userItem.setUserId(uniqueUserID);
                userItem.setFriend3(friends[2]);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(userItem);

                    }
                }).start();
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
        builder.setTitle("Enter Contact Number");

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

                final UserDataDO userItem = new UserDataDO();
                userItem.setUserId(uniqueUserID);
                userItem.setFriend4(friends[3]);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dynamoDBMapper.save(userItem);

                    }
                }).start();
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
