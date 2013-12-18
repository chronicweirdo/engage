package com.chronicweirdo.engage;

import java.io.File;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.chronicweirdo.engage.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            openSearch();
	            return true;
	        case R.id.action_settings:
	            openSettings();
	            return true;
	        case R.id.action_open:
	        	openFile();
	        	return true;
	        case R.id.action_save:
	        	saveFile();
	        	return true;
	        case R.id.action_close:
	        	closeFile();
	        	return true;
	        case R.id.action_new:
	        	newFile();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void openFile() {
		
	}
	
	private void saveFile() {
		Intent intent = new Intent(this, SaveActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
	private void closeFile() {
		
	}
	
	private void newFile() {
		Uri uri = Uri.fromFile(new File("/storage/sdcard0/mydata/cumparaturi.txt"));
		Intent openIntent = new Intent(Intent.ACTION_EDIT, uri);
		//openIntent.setAction(Intent.ACTION_EDIT);
		openIntent.setType("text/plain");
		
		//openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//openIntent.addCategory(Intent.CATEGORY_OPENABLE);
		//openIntent.addCategory(Intent.CATEGORY_BROWSABLE);
		//openIntent.addCategory(Intent.CATEGORY_DEFAULT);
		//openIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		Log.w(":::: ", uri.toString());
		//openIntent.setData(uri);
		startActivity(openIntent);
		
		Intent intent = new Intent(Intent.ACTION_VIEW,
				/*Uri.parse("http://www.ebookfrenzy.com")*/ uri);
    	
    	//startActivity(intent);
	}
	
	private void openSearch() {
		
	}
	
	private void openSettings() {
		
	}

	/** Called when the user clicks the Send button */
	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
	
}
