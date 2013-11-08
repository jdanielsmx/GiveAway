package com.example.giveaway;


import java.io.FileNotFoundException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.giveaway.MainActivity;
import com.example.giveaway.MyLocation.LocationResult;

public class PostItemActivity extends Activity {

	TextView textTargetUri;
	ImageView targetImage;
	MyLocation myLocation = new MyLocation();
	Location currentLocation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		TextView textView = new TextView(this);
		textView.setTextSize(10);
		textView.setText(message);
		
		setContentView(R.layout.activity_post_item);
		//setContentView(textView);
		
		//Button buttonLoadImage = (Button)findViewById(R.id.btnLoadImage);
		textTargetUri = (TextView)findViewById(R.id.textViewTargetImage);
		targetImage = (ImageView)findViewById(R.id.imageViewTargetImage);				

		// Show the Up button in the action bar.
		setupActionBar();
	}

	public void onClickBrowseImages(View arg0)
	{		
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);
		myLocation.getLocation(this, locationResult);
	}
	
	public LocationResult locationResult = new LocationResult(){
		@Override
		public void gotLocation(final Location location){
			//Got the location!
			if (location != null){
				currentLocation = location;
				textTargetUri.setText("Lat:"+currentLocation.getLatitude()+"Lon:"+currentLocation.getLongitude());
			}
			else{
				textTargetUri.setText("location unknown");
			}
		};
	};
	
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
		getMenuInflater().inflate(R.menu.post_item, menu);
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

	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // TODO Auto-generated method stub
	    super.onActivityResult(requestCode, resultCode, data);

	    if (resultCode == RESULT_OK){
	     Uri targetUri = data.getData();
	     //textTargetUri.setText(targetUri.toString());
	     Bitmap bitmap;
	     try {
	      bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
	      targetImage.setImageBitmap(bitmap);
	     } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }	         	    	
	    }
	 }	
}
