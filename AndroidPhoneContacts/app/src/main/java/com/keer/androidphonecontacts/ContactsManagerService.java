package com.keer.androidphonecontacts;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsManagerService extends IntentService {

    private static final String LOG_TAG = "keer_ContactsManagerService";

    private static final int MIN_NR_TIMES_CONTACTED = 1;

    private static final String READ_CONTACTS =
            "com.keer.action.READ_CONTACTS";
    private static final String READ_CONTACTS_NUMBERS_EMAILS =
            "com.keer.action.READ_CONTACTS_NUMBERS_EMAILS";

    public static void readContacts(Context context) {
        Intent intent = new Intent(context, ContactsManagerService.class);
        intent.setAction(READ_CONTACTS);
        context.startService(intent);
    }

    public static void readContactNumberAndEmail(Context context) {
        Intent intent = new Intent(context, ContactsManagerService.class);
        intent.setAction(READ_CONTACTS_NUMBERS_EMAILS);
        context.startService(intent);
    }

    public ContactsManagerService() {
        super("ContactsManagerService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (READ_CONTACTS.equals(action)) {
                readContacts();
            } else if (READ_CONTACTS_NUMBERS_EMAILS.equals(action)) {
                for (Long contactID : getContactsIDs(MIN_NR_TIMES_CONTACTED)) {
                    readContactNumberAndEmail(contactID.intValue());
                }
            }
        }
    }

    private List<Long> getContactsIDs(int min_nrs_contacted) {

        List<Long> ids = new ArrayList<>();

        Context mContext = getApplicationContext();
        ContentResolver mContentResolver = mContext.getContentResolver();

        // Contacts Table

        // Projection (SQL: SELECT FROM TABLE (*,*,*,*))
        final String[] PROJECTION =
                {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER,
                        ContactsContract.Contacts.TIMES_CONTACTED,
                };

        final int PROJECTION_INDEX_ID = 0;
        final int PROJECTION_INDEX_HAS_PHONE_NUMBER = 1;
        final int PROJECTION_INDEX_TIMES_CONTACTED = 2;

        final String SELECTION =
                "((" +
                        ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ? AND " +
                        ContactsContract.Contacts.TIMES_CONTACTED + " >= ?" +
                "))";

        final String[] SELECTION_ARGS = {"1", String.valueOf(min_nrs_contacted)};

        Uri contacts_content_uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = mContentResolver.query(
                contacts_content_uri,
                PROJECTION, SELECTION,
                SELECTION_ARGS,
                null);

        while (cursor.moveToNext()) {
            long _id = cursor.getLong(PROJECTION_INDEX_ID);
            ids.add(_id);
        }

        cursor.close();

        return ids;
    }

    private void readContacts() {

        Context mContext = getApplicationContext();
        ContentResolver mContentResolver = mContext.getContentResolver();

        // Contacts Table

        // Projection (SQL: SELECT FROM TABLE (*,*,*,*))
        final String[] PROJECTION =
                {
                        /* 0 long */    ContactsContract.Contacts._ID,
                        /* 1 long */    ContactsContract.Contacts.NAME_RAW_CONTACT_ID,
                        /* 2 String */  ContactsContract.Contacts.DISPLAY_NAME,
                        /* 3 int */     ContactsContract.Contacts.HAS_PHONE_NUMBER,
                        /* 4 long */    ContactsContract.Contacts.LAST_TIME_CONTACTED,
                        /* 5 int */     ContactsContract.Contacts.TIMES_CONTACTED,
                        /* 6 int */     ContactsContract.Contacts.STARRED,
                };

        final int PROJECTION_INDEX_ID = 0;
        final int PROJECTION_INDEX_RAW_CONTACT_ID = 1;
        final int PROJECTION_INDEX_DISPLAY_NAME = 2;
        final int PROJECTION_INDEX_HAS_PHONE_NUMBER = 3;
        final int PROJECTION_INDEX_LAST_TIME_CONTACTED = 4;
        final int PROJECTION_INDEX_TIMES_CONTACTED = 5;
        final int PROJECTION_INDEX_IS_STARRED = 6;

        // Selection i.e. criteria for the query (SQL: WHERE (* AND * AND *))
        final String SELECTION =
                "((" +
                        ContactsContract.Contacts.HAS_PHONE_NUMBER + " = ? AND " +
                        ContactsContract.Contacts.TIMES_CONTACTED + " >= ?" +
                "))";

        final String[] SELECTION_ARGS = {"1", String.valueOf(MIN_NR_TIMES_CONTACTED)};

        // Run query
        Uri contacts_content_uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = mContentResolver.query(
                contacts_content_uri,
                PROJECTION, SELECTION,
                SELECTION_ARGS,
                null);

        while (cursor.moveToNext()) {
            long _id = cursor.getLong(PROJECTION_INDEX_ID);
            long _raw_id = cursor.getLong(PROJECTION_INDEX_RAW_CONTACT_ID);
            boolean _has_number = (cursor.getInt(PROJECTION_INDEX_HAS_PHONE_NUMBER)==1?true:false);
            String _name = cursor.getString(PROJECTION_INDEX_DISPLAY_NAME);
            int _times_contacted = cursor.getInt(PROJECTION_INDEX_TIMES_CONTACTED);
            long _last_time_contacted = cursor.getLong(PROJECTION_INDEX_LAST_TIME_CONTACTED);
            boolean _is_starred = (cursor.getInt(PROJECTION_INDEX_IS_STARRED)==1?true:false);

            String readableTime = getReadableDateTime(_last_time_contacted, mContext);
            String log = getStringSeperatedByCommas(_id, _raw_id, _has_number, _name, _times_contacted,
                    _is_starred, readableTime);
            Log.d(LOG_TAG, log);
        }

        cursor.close();
    }

    private void readContactNumberAndEmail(int contactID) {

        Context mContext = getApplicationContext();
        ContentResolver mContentResolver = mContext.getContentResolver();

        // Data table

        // Projection (SQL: SELECT FROM TABLE (*,*,*,*))
        final String[] PROJECTION =
                {
                        ContactsContract.Data._ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.LABEL,
                        ContactsContract.CommonDataKinds.Email.ADDRESS,
                        ContactsContract.CommonDataKinds.Email.LABEL
                };

        // Selection i.e. criteria for the query (SQL: WHERE (* AND * AND *))
        final String SELECTION =
                "((" +
                        ContactsContract.Data.CONTACT_ID + "=?" + " AND " +
                        ContactsContract.Data.MIMETYPE + "='" +
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE +
                "))";

        final String[] SELECTION_ARGS = {String.valueOf(contactID)};

        Uri data_content_uri = ContactsContract.Data.CONTENT_URI;
        Cursor cursor = mContentResolver.query(
                data_content_uri,
                PROJECTION,
                SELECTION,
                SELECTION_ARGS,
                null);

        while (cursor.moveToNext()) {
            int _data_id = cursor.getInt(0);
            String _num = cursor.getString(1);
            String _type = cursor.getString(2);
            String _label = cursor.getString(3);
            String _email = cursor.getString(4);
            String _email_label = cursor.getString(5);

            String log = getStringSeperatedByCommas(_data_id, _num, _type, _label, _email, _email_label);

            Log.d(LOG_TAG, log);
        }

        cursor.close();
    }

    private static String getStringSeperatedByCommas(Object...args) {
        StringBuilder sb = new StringBuilder(2 * args.length);
        for                     (Object o : args) {
            sb.append(o.toString() + ",");
        }
        return sb.toString();
    }

    private static String getReadableDateTime(long milliseconds, Context context) {
        String _time = android.text.format.DateFormat.
                getTimeFormat(context).format(new Date(milliseconds));
        String _date = android.text.format.DateFormat.
                getLongDateFormat(context).format(new Date(milliseconds));
        return _time + " " + _date;
    }

}
