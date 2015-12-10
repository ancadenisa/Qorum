package com.qorum.com.qorumapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FollowsFragment extends Fragment {



    private ListView homeView;
    private String [] dummyList = {"Mercury", "Venus", "Earth", "Mars",
            "Jupiter", "Saturn", "Uranus", "Neptune"};
    private ArrayAdapter<String> listAdapter ;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<String> follow_listHeader;
    HashMap<String, List<String>> follow_listChild;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_follows, container, false);

        expandableListView = (ExpandableListView) v.findViewById(R.id.folowy);
        //homeView = (ListView) v.findViewById(R.id.folowy);
/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(), android.R.layout.simple_list_item_1,
                dummyList);
*/
        //homeView.setAdapter(adapter);
        randomData();
        expandableListAdapter = new CustomArrayAdapter(v.getContext(),follow_listHeader,follow_listChild);
        expandableListView.setAdapter(expandableListAdapter);
        return v;
    }


    public void randomData(){
        follow_listHeader = new ArrayList<String>();
        follow_listChild = new HashMap<String, List<String>>();


        follow_listHeader.add("Apple");
        follow_listHeader.add("Google");
        follow_listHeader.add("Microsoft");

        List<String> appleTopics = new ArrayList<String>();
        appleTopics.add("How to debug a C program");
        appleTopics.add("Swift tips and tricks");
        appleTopics.add("OpenGL debugging");

        List<String> googleTopics = new ArrayList<String>();
        googleTopics.add("Android best practises");
        googleTopics.add("RelativeLayout Problem");
        googleTopics.add("Google Maps API question");
        googleTopics.add("Android Xamarin");
        googleTopics.add("Google Drive integration problems");

        List<String> microsoftTopics = new ArrayList<String>();
        microsoftTopics.add("C#/C interop tips");
        microsoftTopics.add("WPF and OpenGL");
        follow_listChild.put(follow_listHeader.get(0),appleTopics);
        follow_listChild.put(follow_listHeader.get(1),googleTopics);
        follow_listChild.put(follow_listHeader.get(2),microsoftTopics);
    }



    public static FollowsFragment newInstance(String text) {

        FollowsFragment f = new FollowsFragment();
        Bundle b = new Bundle();

        return f;
    }
}
