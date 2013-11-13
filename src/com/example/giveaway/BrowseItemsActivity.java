package com.example.giveaway;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class BrowseItemsActivity extends Activity {
	
	ArrayList<String> myCameraImages;
	int myIndex=0;
	TextView tvEmail;
	TextView tvPhone;
			
	/*
	public static final String CAMERA_IMAGE_BUCKET_NAME =
	        Environment.getExternalStorageDirectory().toString()
	        + "/DCIM/Camera";
	public static final String CAMERA_IMAGE_BUCKET_ID =
	        getBucketId(CAMERA_IMAGE_BUCKET_NAME);

	/**
	 * Matches code in MediaProvider.computeBucketValues. Should be a common
	 * function.
	 *	
	public static String getBucketId(String path) {
	    return String.valueOf(path.toLowerCase().hashCode());
	}		
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse_items);

		
		
		
		
		
		
		// Show the Up button in the action bar.
		myCameraImages = getCameraImages(this);
		tvEmail = (TextView)findViewById(R.id.textviewBrowseEmail);
		tvPhone =(TextView)findViewById(R.id.textviewBrowsePhone);
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse_items, menu);
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
	/*
	public static List<String> getCameraImages(Context context) {
	    final String[] projection = { MediaStore.Images.Media.DATA };
	    final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
	    final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
	    final Cursor cursor = context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, 
	            projection, 
	            selection, 
	            selectionArgs, 
	            null);
	    ArrayList<String> result = new ArrayList<String>(cursor.getCount());
	    if (cursor.moveToFirst()) {
	        final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        do {
	            final String data = cursor.getString(dataColumn);
	            result.add(data);
	        } while (cursor.moveToNext());
	    }
	    cursor.close();
	    return result;
	}	*/
	
	public void goLeft(View view){
		tvPhone.setText(String.valueOf(myIndex)); 
		tvEmail.setText(myCameraImages.get(myIndex));
		LoadImage(myCameraImages.get(myIndex));
		if (myIndex==0)
			myIndex = myCameraImages.size()-1;
		else
			myIndex--;
	}

	public void goRight(View view){
		tvPhone.setText(String.valueOf(myIndex));
		tvEmail.setText(myCameraImages.get(myIndex));
		LoadImage(myCameraImages.get(myIndex));
		if (myIndex == (myCameraImages.size()-1))
			myIndex = 0;
		else
			myIndex++;
	}
	

	public void LoadImage(String path){
		File imgFile = new  File(path);
		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView myImage = (ImageView) findViewById(R.id.ivPostedItem);
		    myImage.setImageBitmap(myBitmap);
		}
	}
	
	public ArrayList<String> getCameraImages(Context context) {

	    // Set up an array of the Thumbnail Image ID column we want
	    String[] projection = {MediaStore.Images.Media.DATA};


	    final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	            projection,
	            null,
	            null,
	            null);

	    ArrayList<String> result = new ArrayList<String>(cursor.getCount());

	    Log.i("cursor.getCount()) :", cursor.getCount() + "");

	    if (cursor.moveToFirst()) {
	        final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        do {
	            final String data = cursor.getString(dataColumn);
	            Log.i ("data :",data );
	            result.add(data);
	        } while (cursor.moveToNext());
	    }
	    cursor.close();

	    return result;

	}	
	
}