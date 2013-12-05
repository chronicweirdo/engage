package com.chronicweirdo.engage;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SaveActivity extends Activity {
	
	public static final String PATH = "path";

	private String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		// Show the Up button in the action bar.
		setupActionBar();

		Intent intent = getIntent();
		text = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void openBrowser(View view) {
		Intent intent = new Intent(this, FileChoser.class);
		//intent.putExtra(FileChoser.ALLOW_FOLDER_SELECTION, false);
		//intent.putExtra(FileChoser.LOCATION, "/");
		int requestCode = 1; // some random request code
		startActivityForResult(intent, requestCode);
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			String path = data.getStringExtra(FileChoser.PATH);
			EditText editText = (EditText) findViewById(R.id.file_name);
			editText.setText(path);
		}
	}
	
	private void result(String path) {
		Intent r = new Intent();
		r.putExtra(PATH, path);
		setResult(RESULT_OK, r);
		finish();
	}

	public void save(View view) {
		// get file name
		EditText editText = (EditText) findViewById(R.id.file_name);
		String fileName = editText.getText().toString();
		
		// verify it is a file (or does not exist)
		final File file = new File(fileName);
		if (file.isDirectory()) {
			Log.i("#####", "this is a folder, dude!");
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("That's a folder!");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i("#####", "ok clicked");
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			return;
		}
		
		// ask for overwrite
		if (file.exists()) {
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Overwrite?");
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i("#####", "cancel clicked");
				}
			});
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Log.i("#####", "ok clicked");
					save(file);
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			return;
		}
		
		// a new file
		save(file);
	}
	
	private void save(File file) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(text.getBytes());
			outputStream.close();
			result(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
