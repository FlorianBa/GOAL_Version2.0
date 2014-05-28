package com.projekt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



@SuppressLint("DefaultLocale")
public class FileChooserActivity extends ListActivity {

	private static final String TAG = "FileChooser";
	private List<String> item = null;
	private List<String> path = null;
	private String root="/storage";
	private TextView myPath;
	private String currentParent = root;
	private Context con = this;
	
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_chooser);

		myPath = (TextView)findViewById(R.id.path);
		getDir(root);
	}


	private void getDir(String dirPath){

		myPath.setText("Location: " + dirPath);

		item = new ArrayList<String>();
		path = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();
		currentParent = f.getParent();

		if(!dirPath.equals(root)){
			item.add(root);
			path.add(root);

			item.add("../");
			path.add(f.getParent());
		}


		for(int i=0; i < files.length; i++){

			File file = files[i];
			path.add(file.getPath());

			if(file.isDirectory())
				item.add(file.getName() + "/");
			else
				item.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.row, item);
		setListAdapter(fileList);
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final File file = new File(path.get(position));
		final String filePath = file.getAbsolutePath();
		String fileTyp = "";

		// Filter File Type 
		if(filePath.contains(".")){
			fileTyp = filePath.toLowerCase().substring(filePath.lastIndexOf(".")+1);
		}

		// Check the File
		if (file.isDirectory()){

			if(file.canRead())
				getDir(path.get(position));
			else{
				// Dialog for folder can not read
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "] folder can't be read!")
				.setPositiveButton("OK", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do something when folder is not readable
					}

				}).show();
			}
		}

		else{
			if(fileTyp.equals("csv")){
				// Dialog for selected File is a csv-File
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "]")
				.setPositiveButton("OK", 
						new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// For csv-Files
						Log.d(TAG, filePath);
						
						Intent i = new Intent();
						i.setClass(con, MainActivity.class);
						i.putExtra("File", file.getName());
						startActivity(i);

					}
				})
				.setNegativeButton("Cancel", 
						new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Action for Cancel-Button
					}
				})
				.show();
			}
			else{
				// Dialog for selected Folder/File is not a csv-File
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("No csv-File seleceted!")
				.setPositiveButton("Cancel", 
						new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// For ohter Files
						Log.d(TAG, filePath);
					}
				}).show();
			}
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Stop going back when root Directory is entered
			if(!currentParent.equals(new File(root).getParent())){
				getDir(currentParent);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
