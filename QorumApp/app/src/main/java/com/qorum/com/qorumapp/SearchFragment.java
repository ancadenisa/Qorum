package com.qorum.com.qorumapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

/*
        homeView = (ListView) v.findViewById(R.id.hotTopics);
        ArrayList<String> planetList = new ArrayList<String>();
        planetList.addAll(Arrays.asList(dummyList));
        listAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.fragment_follows);
        homeView.setAdapter(listAdapter);
*/

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }
    public static SearchFragment newInstance(String text) {

        SearchFragment f = new SearchFragment();
        Bundle b = new Bundle();

        return f;
    }
}
