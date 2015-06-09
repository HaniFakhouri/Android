package com.keer.inbox;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends Activity
        implements
        AllObjectsFragment.OnFragmentInteractionListener,
        XObjetFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            ArrayList<xObject> xObjects = new ArrayList<>();
            for (int i=0; i<15; i++) {
                xObjects.add(new xObject("Title " + i, i));
            }

            getFragmentManager().beginTransaction()
                    .add(R.id.container, AllObjectsFragment.newInstance(xObjects))
                    .commit();
        }

    }

    @Override
    public void onFragmentInteraction(xObject xObject) {
        doTransaction(XObjetFragment.newInstance(xObject));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void doTransaction(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}
