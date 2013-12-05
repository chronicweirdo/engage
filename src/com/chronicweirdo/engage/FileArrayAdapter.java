package com.chronicweirdo.engage;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<Option> {

	private Context context;
	private int id;
	private List<Option> items;
	
	public FileArrayAdapter(Context context, int textViewResourceId, List<Option> objects) {
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.id = textViewResourceId;
		this.items = objects;
	
	}
	
	public Option getItem(int i) {
		return items.get(i);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(id, null);
		}
		final Option o = items.get(position);
		if (o != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.fileIcon);
			TextView t1 = (TextView) v.findViewById(R.id.TextView01);
			//TextView t2 = (TextView) v.findViewById(R.id.TextView02);
			
			if (iv != null) {
				if (o.getType() == Option.Type.FOLDER) {
					iv.setImageResource(R.drawable.ic_folder);
				} else if (o.getType() == Option.Type.FILE){
					iv.setImageResource(R.drawable.ic_file);
				} else if (o.getType() == Option.Type.RETURN) {
					iv.setImageResource(R.drawable.ic_parent);
				}
			}
			if (t1 != null) t1.setText(o.getName());
		}
		return v;
	}
	
	

}
