package com.chronicweirdo.engage;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SaveActivity extends Activity {
	
	

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
		intent.putExtra(FileChoser.ALLOW_FOLDER_SELECTION, false);
		intent.putExtra(FileChoser.LOCATION, "/");
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

	public void save(View view) {
		// Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.file_name);
		String fileName = editText.getText().toString();

		// save to disk
		// File file = new File(getFilesDir(), fileName);
		File file = new File(/*Environment.getExternalStorageDirectory(), */fileName);
		//File file = new File(fileName);
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			outputStream.write(text.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
