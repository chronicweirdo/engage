package com.chronicweirdo.engage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FileChoser extends Activity implements OnItemClickListener {

	private File current;
	
	private boolean allowFolderSelection = true;
	
	private FileArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		// TODO: check if we get a predefined location and set it as current folder
		// Log.i("#####", intent.getExtras().get("parentParam1").toString());
		setContentView(R.layout.activity_file_choser);
		current = Environment.getExternalStorageDirectory();
		fill(current);
	}
	
	private void fill(File f) {
		// select and order files and folders
		File[] dirs = f.listFiles();
		this.setTitle("Curret Dir: " + f.getName());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		
		try {
			for (File ff: dirs) {
				if (ff.isDirectory()) {
					dir.add(new Option(ff.getName(), Option.Type.FOLDER, ff.getAbsolutePath()));
				} else {
					fls.add(new Option(ff.getName(), Option.Type.FILE, ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {}
		
		Collections.sort(dir);
		Collections.sort(fls);
		
		dir.addAll(fls);
		
		Log.i("#####", "f path: " + f.getAbsolutePath());
		Log.i("#####", "root path: " + Environment.getRootDirectory().getAbsolutePath());
		if (!f.getAbsolutePath().equalsIgnoreCase("/")) {
			dir.add(0, new Option("..", Option.Type.RETURN, f.getParent()));
		}
		
		// build list
		adapter = new FileArrayAdapter(FileChoser.this, R.layout.file_view, dir);
		ListView listView = ((ListView) this.findViewById(R.id.fileList));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
		Option o = adapter.getItem(position);
		if (o.getType() == Option.Type.FOLDER ||
				o.getType() == Option.Type.RETURN) {
			current = new File(o.getPath());
			fill(current);
			Toast.makeText(this, "Current: " + o.getPath(), Toast.LENGTH_SHORT).show();
		} else {
			onFileClick(o);
		}
	}
	
	private void onFileClick(Option o) {
		Toast.makeText(this, "File Clicked: " + o.getName(), Toast.LENGTH_SHORT).show();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("selectedFile", o.getPath());
		setResult(RESULT_OK, resultIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_choser, menu);
		return true;
	}

	public void select(View view) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("selectedFile", current.getAbsolutePath());
		setResult(RESULT_OK, resultIntent);
		finish();
	}

}
