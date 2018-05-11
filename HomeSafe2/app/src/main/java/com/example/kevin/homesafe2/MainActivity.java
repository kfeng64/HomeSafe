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
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.auth.core.IdentityHandler;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.mobile.api.idxz1n4qn66g.HomeSafeAPIMobileHubClient;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


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
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

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
                    callCloudLogic();
                } else {
                    time.setText("");
                    isOut = false;
                }

                updateUser(friends, isOut);

            }
        });



    }

    public void callCloudLogic() {
        // Create components of api request
        final String method = "GET";

        final String path = "/friends";

        final String body = "";
        final byte[] content = body.getBytes(StringUtils.UTF8);

        final Map parameters = new HashMap<>();
        parameters.put("lang", "en_US");

        final Map headers = new HashMap<>();

        // Use components to create the api request
        ApiRequest localRequest =
                new ApiRequest(apiClient.getClass().getSimpleName())
                        .withPath(path)
                        .withHttpMethod(HttpMethodName.valueOf(method))
                        .withHeaders(headers)
                        .addHeader("Content-Type", "application/json")
                        .withParameters(parameters);

        // Only set body if it has content.
        if (body.length() > 0) {
            localRequest = localRequest
                    .addHeader("Content-Length", String.valueOf(content.length))
                    .withBody(content);
        }

        final ApiRequest request = localRequest;

        // Make network call on background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(LOG_TAG,
                            "Invoking API w/ Request : " +
                                    request.getHttpMethod() + ":" +
                                    request.getPath());

                    final ApiResponse response = apiClient.execute(request);

                    final InputStream responseContentStream = response.getContent();

                    if (responseContentStream != null) {
                        final String responseData = IOUtils.toString(responseContentStream);
                        Log.d(LOG_TAG, "Response : " + responseData);
                    }

                    Log.d(LOG_TAG, response.getStatusCode() + " " + response.getStatusText());

                } catch (final Exception exception) {
                    Log.e(LOG_TAG, exception.getMessage(), exception);
                    exception.printStackTrace();
                }
            }
        }).start();
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

                updateUser(friends, isOut);
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

                updateUser(friends, isOut);
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

                updateUser(friends, isOut);
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

                updateUser(friends, isOut);
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

    private void updateUser(String friends[], boolean isOut) {
        final UserDataDO userItem = new UserDataDO();
        userItem.setUserId(uniqueUserID);
        userItem.setFriend1(friends[0]);
        userItem.setFriend2(friends[1]);
        userItem.setFriend3(friends[2]);
        userItem.setFriend4(friends[3]);
        userItem.setIsOut(isOut);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(userItem);

            }
        }).start();
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
