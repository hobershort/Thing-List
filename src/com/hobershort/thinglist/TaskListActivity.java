package com.hobershort.thinglist;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.UUID;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TaskListActivity extends TabActivity {
	
	
	private static final String NUMLISTS = "numberOfLists";
	private static final String LISTSFILE = "lists";
	LinkedList<TabSpec> tabSpecs = new LinkedList<TabSpec>();
	LinkedList<String> uuids = new LinkedList<String>();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int numberOfPreviousLists = prefs.getInt(NUMLISTS, 3);
        
        for(int i = 0; i < numberOfPreviousLists; i++)
        	addList();
        
        
//        TabHost tabhost = getTabHost();
//        tabhost.setCurrentTab(0);
    }

	private void addList() {
		Resources res = getResources();
        TabHost tabhost = getTabHost();
		TabHost.TabSpec tabspec;
		Intent intent;
		int newListNumber = tabSpecs.size() + 1;
		intent = new Intent().setClass(this, TaskActivity.class);
		
		String uuid = UUID.randomUUID().toString();
		
        intent.putExtra("uuid", uuid);
        intent.putExtra("listName", (String) null);

        tabspec = tabhost.newTabSpec("").setIndicator("Untitled", res.getDrawable(R.drawable.ic_tab_write)).setContent(intent);
        tabhost.addTab(tabspec);
        tabSpecs.add(tabspec);
        uuids.add(uuid);
	}
	
	private void renameList() {
		TabHost tabhost = getTabHost();
		TaskActivity currentActivity = (TaskActivity) tabhost.getCurrentTabView().getContext();
		TabSpec currentTab = tabSpecs.get(tabhost.getCurrentTab());
		currentTab.setIndicator("asdf");
	}

	private void deleteList(){
//		Resources res = getResources();
        TabHost tabhost = getTabHost();
        int currentTab = tabhost.getCurrentTab();
        int newTab = (currentTab == 0) ? 0 : currentTab-1;
        tabhost.setCurrentTab(0);
        tabSpecs.remove(currentTab);
        uuids.remove(currentTab);
        tabhost.clearAllTabs();
        for(TabSpec tabSpec : tabSpecs){
        	tabhost.addTab(tabSpec);
        }
        tabhost.setCurrentTab(newTab);
//        View view = tabhost.getCurrentView();
//        //view.getContext() to get the view;
//        tabhost.removeView(view);
//        tabhost.invalidate();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.optionsmenu, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()){
    	case R.id.addlist:
    		addList();
    		return true;
    	case R.id.renamelist:
    		renameList();
    		return true;
    	case R.id.deletelist:
    		deleteList();
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);

    	}
    }
    
    @Override
    protected void onPause() {
    	SharedPreferences prefs = getPreferences(MODE_PRIVATE);
    	Editor editor = prefs.edit();
    	TabHost tabhost = getTabHost();
    	editor.putInt(NUMLISTS, tabSpecs.size());
    	editor.commit();
    	
    	FileOutputStream fos;
		try {
			fos = openFileOutput(LISTSFILE, Context.MODE_PRIVATE);
			BufferedWriter outf = new BufferedWriter(new OutputStreamWriter(fos));
			for(int i = 0; i < tabSpecs.size(); i++){
				StringBuilder sb = new StringBuilder();
				sb.append()
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	super.onPause();
    }
}