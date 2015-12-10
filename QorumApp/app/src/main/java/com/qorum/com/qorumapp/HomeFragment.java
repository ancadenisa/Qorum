package com.qorum.com.qorumapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private ListView homeView;
    private String [] dummyList = {"Mercury", "Venus", "Earth", "Mars",
            "Jupiter", "Saturn", "Uranus", "Neptune", "Neptune", "Neptune", "Neptune", "Neptune", "Neptune", "Neptune", "Neptune", "Neptune"};


    private String [] homePostTitle = {"CELL BE tweak thread","iOS: A brand new adventure"};
    private String [] homePostSnippet = {"A great start for CELL BE","Jump into iOS development by following this guide!"};


    private ArrayAdapter<String> listAdapter ;
    private List<Map<String, String>> homeData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        homeView = (ListView) v.findViewById(R.id.hotTopicsssss);
        ArrayList ls = new ArrayList();
        ls.addAll(Arrays.asList(dummyList));

        homeData = new ArrayList<Map<String, String>>();



        for (int i =0; i < homePostTitle.length; i++) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("title", homePostTitle[i]);
            datum.put("snippet",  homePostSnippet[i]);
            homeData.add(datum);
        }
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                ls);
*/
        SimpleAdapter adapter = new SimpleAdapter(inflater.getContext(), homeData,
                android.R.layout.simple_list_item_2,
                new String[] {"title", "snippet"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        homeView.setAdapter(adapter);



        return v;
    }
    public static HomeFragment newInstance(String text) {

        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();


        return f;
    }

}
