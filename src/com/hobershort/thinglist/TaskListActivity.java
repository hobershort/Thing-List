package com.hobershort.thinglist;

import com.hobershort.thinglist.R;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TaskListActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources();
        TabHost tabhost = getTabHost();
        TabHost.TabSpec tabspec;
        Intent intent;
        
        intent = new Intent().setClass(this, TaskActivity.class);
        intent.putExtra("window", 1);
        tabspec = tabhost.newTabSpec("list1").setIndicator("List 1", res.getDrawable(R.drawable.ic_tab_write)).setContent(intent);
        tabhost.addTab(tabspec);
        
        intent = new Intent().setClass(this, TaskActivity.class);
        intent.putExtra("window", 2);
        tabspec = tabhost.newTabSpec("list2").setIndicator("List 2", res.getDrawable(R.drawable.ic_tab_write)).setContent(intent);
        tabhost.addTab(tabspec);
        
        intent = new Intent().setClass(this, TaskActivity.class);
        intent.putExtra("window", 3);
        tabspec = tabhost.newTabSpec("list3").setIndicator("List 3", res.getDrawable(R.drawable.ic_tab_write)).setContent(intent);
        tabhost.addTab(tabspec);
        
        tabhost.setCurrentTab(0);
    }
}