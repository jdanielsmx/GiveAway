package com.example.giveaway;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE="com.example.giveaway.POSTITEM";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void openPostItem(View view){
		//do something in response to button
		Intent intent = new Intent(this, PostItemActivity.class);
		Time now = new Time();
		now.setToNow();
		intent .putExtra(EXTRA_MESSAGE, now.toString());
		startActivity(intent);
	}
	
	
	public void openBrowseItems(View view){
		Intent intent = new Intent(this, BrowseItemsActivity.class);
		startActivity(intent);
	}
}
