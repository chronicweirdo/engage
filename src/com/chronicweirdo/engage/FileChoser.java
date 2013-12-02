package com.chronicweirdo.engage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
		
		Log.i("#####", "f path: " + f.getAbsolutePath());
		Log.i("#####", "root path: " + Environment.getRootDirectory().getAbsolutePath());
		if (!f.getAbsolutePath().equalsIgnoreCase("/")) {
			dir.add(0, new Option("..", "Parent Directory", f.getParent()));
		}
		
		adapter = new FileArrayAdapter(FileChoser.this, R.layout.file_view, dir);
		this.setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if (o.getData().equalsIgnoreCase("folder") ||
				o.getData().equalsIgnoreCase("parent directory")) {
			currentDir = new File(o.getPath());
			fill(currentDir);
			Toast.makeText(this, "Current: " + o.getPath(), Toast.LENGTH_SHORT).show();
		} else {
			onFileClick(o);
		}
	}
	
	private void onFileClick(Option o) {
		Toast.makeText(this, "File Clicked: " + o.getName(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.file_choser, menu);
		return true;
	}

}
