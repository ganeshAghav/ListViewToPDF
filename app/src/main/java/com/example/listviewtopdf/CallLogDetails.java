package com.example.listviewtopdf;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogDetails {

    public static String ReportName="MSD Computer Solution Report";
    public static String ReportTitle="Call Log Details";

    static DateFormat df = new android.text.format.DateFormat();
    public static String ReportDate=df.format("dd-MM-yyyy", new java.util.Date()).toString();

    static Date d=new Date();
    static SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
    public static String ReportTime=sdf.format(d);

    public static String CALLLOG_CAL_LOG_ID="Customer ID";
    public static String CALLLOG_CAL_LOG_CUST_NAME="Customer Name";
    public static String CALLLOG_CAL_LOG_MOBILE="Mobile";
    public static String CALLLOG_CAL_LOG_DEVICE_CAT="Device Category";
    public static String CALLLOG_CAL_LOG_PROB_BYUSER="Problem By User";
    public static String CALLLOG_CAL_LOG_ADDRESS="Address";
    public static String CALLLOG_CAL_LOG_CREATED_DATE="Created Date";
    public static String CALLLOG_CAL_LOG_CREATED_TIME="Created Time";
    public static String CALLLOG_CAL_CALL_ASSGIN_STATUS="Call Cog Status";
    public static String CALLLOG_CAL_CALL_ASSGIN_DATE="Call Assgin Date";
    public static String CALLLOG_CAL_CALL_ASSGIN_TIME="Call Assgin Time";

}
