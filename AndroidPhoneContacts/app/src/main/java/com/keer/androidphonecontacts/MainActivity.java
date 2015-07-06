package com.keer.androidphonecontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContactsManagerService.readContacts(getApplicationContext());
        ContactsManagerService.readContactNumberAndEmail(getApplicationContext());

    }

}
