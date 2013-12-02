package com.chronicweirdo.engage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

public class FileChoser extends ListActivity {

	private File currentDir;
	
	private FileArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_file_choser);
		currentDir = Environment.getExternalStorageDirectory();
		fill(currentDir);
	}
	
	private void fill(File f) {
		File[] dirs = f.listFiles();
		this.setTitle("Curret Dir: " + f.getName());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		
		try {
			for (File ff: dirs) {
				if (ff.isDirectory()) {
					dir.add(new Option(ff.getName(), "Folder", ff.getAbsolutePath()));
				} else {
					fls.add(new Option(ff.getName(), "File Size: " + ff.length(), ff.getAbsolutePath()));
				}
			}
		} catch (Exception e) {}
		
		Collections.sort(dir);
		Collections.sort(fls);
		
		dir.addAll(fls);
		
		if (!f.getName().equalsIgnoreCase("sdcard")) {
			dir.add(0, new Option("..", "Parent Directory", f.getParent()));
		}
		
		adapter = new FileArrayAdapter(FileChoser.this, R.layout.file_view, dir);
		this.setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_choser, menu);
		return true;
	}

}
