package com.keer.inbox;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllObjectsFragment extends Fragment
        implements AbsListView.OnItemClickListener,
        AllObjectsListAdapter.OnTipDeletedListener {

    /**
     * Used as a callback to the calling {@link Activity}
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(xObject xObject);
    }

    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;

    private ArrayAdapter mAdapter;

    private List<xObject> mXObjects;

    public static AllObjectsFragment newInstance(ArrayList<xObject> allXObjects) {
        AllObjectsFragment fragment = new AllObjectsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("alltips", allXObjects);
        fragment.setArguments(args);
        return fragment;
    }

    public AllObjectsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mXObjects = bundle.getParcelableArrayList("alltips");
            mAdapter = new AllObjectsListAdapter(getActivity(), new ArrayList(mXObjects), this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xobjects_list, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        return view;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(mXObjects.get(position));
        }
    }

    @Override
    public void onTipDeletedListener(int index) {
        mXObjects.remove(index);
    }

}
