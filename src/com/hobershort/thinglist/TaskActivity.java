package com.hobershort.thinglist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.hobershort.tasklist.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TaskActivity extends ListActivity {
	
	ArrayAdapter<String> adapter;
	private int window;
	

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		window = getIntent().getExtras().getInt("window");
		
		final Resources res = getResources();
		final Context context = this;
		
		adapter = new ArrayAdapter<String>(this, R.layout.list_item);
		
		setListAdapter(adapter);
		adapter.add(res.getString(R.string.create_new));
		
		try {
			FileInputStream inf = openFileInput(Integer.toString(window));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inf));
			String line = null;
			while((line = reader.readLine()) != null){
				adapter.add(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(lv);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				String selected = (String) parent.getItemAtPosition(position);
				if(selected.equals(res.getString(R.string.create_new))){
					popUpAddDialog(context, null);
				}
				else {
					view.performLongClick();
				}
			}
		});
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextmenu, menu);
		
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		TextView x = (TextView) info.targetView;
		switch(item.getItemId()){
			case R.id.delete:
				adapter.remove((String) x.getText());
				return true;
			case R.id.edit:
				popUpAddDialog(x.getContext(), x.getText());
				adapter.remove((String) x.getText());
				return true;
			default:
				return false;
		}
	}
	
	private void popUpAddDialog(final Context context, CharSequence charSequence) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("New list item");
		alert.setMessage("Enter new to-do item");
		final EditText textInput = new EditText(context);
		textInput.setText(charSequence);
		alert.setView(textInput);
		alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String value = textInput.getText().toString();
				adapter.add(value);
			}
		});
		
		alert.show();
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			FileOutputStream fos = openFileOutput(Integer.toString(window), Context.MODE_PRIVATE);
			BufferedWriter outf = new BufferedWriter(new OutputStreamWriter(fos));
			String item;
			for(int i = 0; i < adapter.getCount(); i++){
				item = adapter.getItem(i);
				if(item != getResources().getString(R.string.create_new)){
					item += "\n";
					outf.write(item.toCharArray());
				}
			}
			
			
			outf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
