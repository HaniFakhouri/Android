package com.keer.inbox;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class XObjetFragment extends Fragment {

    /**
     * Used as a callback to the calling {@link Activity}
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    private OnFragmentInteractionListener mListener;

    private xObject mXObject;

    public static XObjetFragment newInstance(xObject xObject) {
        XObjetFragment fragment = new XObjetFragment();
        fragment.setArguments(xObject.toBundle());
        return fragment;
    }

    public XObjetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mXObject = new xObject(bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_xobject, container, false);

        TextView title = (TextView)rootView.findViewById(R.id.txtTitle);
        title.setText(mXObject.title + ", " + mXObject.id);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
