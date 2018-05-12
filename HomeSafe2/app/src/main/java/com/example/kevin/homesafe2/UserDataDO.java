package com.example.kevin.homesafe2;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "homesafe-mobilehub-164224738-user-data")

public class UserDataDO {
    private String _userId;
    private String _friend1;
    private String _friend2;
    private String _friend3;
    private String _friend4;
    private boolean isOut;
    private int hour, min;
    private String name;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "friend1")
    public String getFriend1() {
        return _friend1;
    }

    public void setFriend1(final String _friend1) {
        this._friend1 = _friend1;
    }
    @DynamoDBAttribute(attributeName = "friend2")
    public String getFriend2() {
        return _friend2;
    }

    public void setFriend2(final String _friend2) {
        this._friend2 = _friend2;
    }
    @DynamoDBAttribute(attributeName = "friend3")
    public String getFriend3() {
        return _friend3;
    }

    public void setFriend3(final String _friend3) {
        this._friend3 = _friend3;
    }
    @DynamoDBAttribute(attributeName = "friend4")
    public String getFriend4() {
        return _friend4;
    }

    public void setFriend4(final String _friend4) {
        this._friend4 = _friend4;
    }

    @DynamoDBAttribute(attributeName = "isOut")
    public boolean getIsOut() {
        return isOut;
    }

    public void setIsOut(final boolean isOut) {
        this.isOut = isOut;
    }

    @DynamoDBAttribute(attributeName = "hour")
    public int getHour() {
        return hour;
    }

    public void setHour(final int hour) {
        this.hour = hour;
    }

    @DynamoDBAttribute(attributeName = "min")
    public int getMin() {
        return min;
    }

    public void setMin(final int min) {
        this.min = min;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }



}
