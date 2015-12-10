package com.qorum.com.qorumapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Alpharius on 30-Nov-15.
 */
public class CustomArrayAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> follow_listHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> follow_listChild;


    public CustomArrayAdapter(Context context, List<String> follow_listHeader,
                              HashMap<String, List<String>> follow_listChild){
        this.context = context;
        this.follow_listHeader = follow_listHeader;
        this.follow_listChild = follow_listChild;
    }

    @Override
    public int getGroupCount() {
        return this.follow_listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.follow_listChild.get(this.follow_listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.follow_listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.follow_listChild.get(this.follow_listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.follows_lst_header,null);
        }
        TextView lstHeader = (TextView) convertView.findViewById(R.id.followListHeader);
        lstHeader.setTypeface(null, Typeface.BOLD);
        lstHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition,childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.follows_lst_item,null);
        }
        TextView lstItemChild = (TextView) convertView.findViewById(R.id.followListItem);
        lstItemChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
