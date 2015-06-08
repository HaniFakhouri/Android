package com.example.hani.job;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends Activity {

    private NotificationManager notificationManager;

    private Button btnNotification1, btnCalendar;
    private Button btnFrag1, btnFrag2, btnFrag3, btnFrag4;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        btnNotification1 = (Button)findViewById(R.id.btnNot1);
        btnCalendar = (Button)findViewById(R.id.btnCalendar);
        btnFrag1 = (Button)findViewById(R.id.btnF1);
        btnFrag2 = (Button)findViewById(R.id.btnF2);
        btnFrag3 = (Button)findViewById(R.id.btnF3);
        btnFrag4 = (Button)findViewById(R.id.btnF4);

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calendar();
                //showCalEventAtEpochMillis(1435218300000L);
                insertEventIntoCalendar();
            }
        });

        btnNotification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification1();
            }
        });

        btnFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTranscation(new FragmentOne());
            }
        });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTranscation(new FragmentTwo());
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTranscation(new FragmentThree());
            }
        });

        btnFrag4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTranscation(new FragmentFour());
            }
        });

    }

    private void doTranscation(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getApplicationContext(), "RES CODE: " + resultCode, Toast.LENGTH_SHORT).show();
    }

        private void insertEventIntoCalendar() {

        final int JUNE = 5;

        long calId = 1;
        Calendar calStartTime = Calendar.getInstance();
        calStartTime.set(2015, JUNE, 15, 21, 0, 0);
        Calendar calEndTime = Calendar.getInstance();
        calEndTime.set(2015, JUNE, 15, 22, 0, 0);

        long startMillis = calStartTime.getTimeInMillis();
        long endMillis = calEndTime.getTimeInMillis();

        ContentValues values = new ContentValues();

        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "A TITLE");
        values.put(CalendarContract.Events.DESCRIPTION, "A DESCRIPTION");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        values.put(CalendarContract.Events.CALENDAR_ID, calId);

        ContentResolver contentResolver = getContentResolver();
        Uri cal_events_uri = CalendarContract.Events.CONTENT_URI;
        Uri uri = contentResolver.insert(cal_events_uri, values);

        long eventID = Long.parseLong(uri.getLastPathSegment());

        Toast.makeText(getApplicationContext(), "Inserted the event. Got ID: " + eventID, Toast.LENGTH_SHORT).show();

    }


    /**
     * If you want to see all calendars that a user has viewed,
     * not just calendars the user owns, omit the OWNER_ACCOUNT
     */
    private void calendar() {

        info = (TextView)findViewById(R.id.txtInfo);

        // Calendars Table

        // Projection (SQL: SELECT FROM TABLE (*,*,*,*))
        final String[] PROJECTION = new String[]
                {
                        /* 0 */ CalendarContract.Calendars._ID,
                        /* 1 */ CalendarContract.Calendars.ACCOUNT_NAME,
                        /* 2 */ CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                        /* 3 */ CalendarContract.Calendars.OWNER_ACCOUNT
                };

        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        final int PROJECTION_DISPLAY_NAME_INDEX = 2;
        final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

        // Selection i.e. criteria for the query (SQL: WHERE (* AND * AND *))
        final String SELECTION = "((" + CalendarContract.Calendars.ACCOUNT_NAME +  " = ? ) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE +  " = ? ) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ? ))";

        final String ACCOUNT_NAME = "hani@fakhouri.eu";
        final String ACCOUNT_TYPE = "com.google";
        final String OWNER_ACCOUNT = "hani@fakhouri.eu";

        // Selection arguments
        final String[] SELECTION_ARGS = new String[]
                {
                        ACCOUNT_NAME,
                        ACCOUNT_TYPE,
                        OWNER_ACCOUNT
                };

        // Run query
        ContentResolver contentResolver = getContentResolver();
        Uri cal_content_uri = CalendarContract.Calendars.CONTENT_URI;
        Cursor cursor = contentResolver.query(cal_content_uri, PROJECTION, SELECTION, SELECTION_ARGS, null);

        info.setText("");

        while (cursor.moveToNext()) {

            long calID = cursor.getLong(PROJECTION_ID_INDEX);
            String displayName = cursor.getString(PROJECTION_DISPLAY_NAME_INDEX);
            String accountName = cursor.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = cursor.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            info.append(calID + ":" + displayName + ":" + accountName + ":" + ownerName + "\n");

        }

        cursor.close();

    }

    private void showCalEventAtEpochMillis(long startMillis) {
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    private void showNotification1() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setColor(Color.BLUE);
        builder.setContentText("Content Text");
        builder.setContentTitle("Content Title");
        builder.setContentInfo("Content Info");
        builder.setSubText("Sub Text");
        builder.setAutoCancel(true);
        builder.setCategory(Notification.CATEGORY_RECOMMENDATION);
        builder.setOnlyAlertOnce(true);
        builder.setSmallIcon(R.drawable.ic_launcher);

        builder.setVibrate(new long[]{0});
        builder.setPriority(Notification.PRIORITY_HIGH);

        Intent next = new Intent(this, NextActivity.class);
        Intent prev = new Intent(this, PreviousActivity.class);

        PendingIntent pNext = PendingIntent.getActivity(this, 0, next, PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pPrev = PendingIntent.getActivity(this, 0, prev, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.addAction(R.drawable.ic_prev, "Previous", pPrev);
        builder.addAction(R.drawable.ic_next, "Next", pNext);

        Intent main = new Intent(this, MainContent.class);
        PendingIntent pMain = PendingIntent.getActivity(this, 0, main, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pMain);

        Notification notification = builder.build();

        notificationManager.notify("TAG", 0, notification);
    }

}
