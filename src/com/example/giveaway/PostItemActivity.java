package com.example.giveaway;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.example.giveaway.MainActivity;
import com.example.giveaway.MyLocation.LocationResult;
import com.example.giveaway.posteditemendpoint.Posteditemendpoint;
import com.example.giveaway.posteditemendpoint.model.GeoPt;
import com.example.giveaway.posteditemendpoint.model.PostedItem;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;

public class PostItemActivity extends Activity {

	TextView textTargetUri;
	ImageView targetImage;
	MyLocation myLocation = new MyLocation();
	Location currentLocation;
	Bitmap bmpCurrentBitmap;
	EditText edtextEmail;
	EditText edtextPhone;
	Button myPostButton;
	Uri targetUri;
	String sResultingURL;
	
	private String MyAppURL = "http://giveawayprealpha.appspot.com/";
	
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
		edtextEmail = (EditText)findViewById(R.id.editTextEmail);
		edtextPhone = (EditText)findViewById(R.id.editTextPhoneNumber);

		// Show the Up button in the action bar.
		setupActionBar();
	}

	public void onClickBrowseImages(View arg0)
	{		
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, 0);
		//myLocation.getLocation(this, locationResult);
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
	     targetUri = data.getData();
	     //textTargetUri.setText(targetUri.toString());
	     try {
	      bmpCurrentBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
	      targetImage.setImageBitmap(bmpCurrentBitmap);
	     } catch (FileNotFoundException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	     }	         	    	
	    }
	 }
	 
	 public void PostItem(View view){	
		 
		//Item myItem = new Item(currentLocation.getLatitude(), currentLocation.getLongitude(), 
			//	 edtextEmail.getText().toString(), edtextPhone.getText().toString(), bmpCurrentBitmap);
		
		//PostedItem pItem = new com.example.giveaway.posteditemendpoint.model.PostedItem();
		/*pItem.setEmail(myItem.GetEmail());
		pItem.setImageUrl("");
		pItem.setLatitude(myItem.GetLatitude());
		pItem.setLongitude(myItem.GetLongitude());
		pItem.setPhone(myItem.GetPhone());
		pItem.setPostingDate(new DateTime(new Date()));*/
		/*
		pItem.setEmail("a@b.com");//myItem.GetEmail());
		pItem.setImageUrl("myurl");
		//pItem.setLatitude(5d);
		//pItem.setLongitude(6d);
		pItem.setPhone("1234567890");//myItem.GetPhone());
		//pItem.setPostingDate(new DateTime(new Date()));		
		
		
		Posteditemendpoint.Builder builder = new Posteditemendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), 
				new JacksonFactory(), 
				null);
		
		builder = CloudEndpointUtils.updateBuilder(builder);
		
		Posteditemendpoint endpoint = builder.build();*/
		//try {
			//endpoint.insertPostedItem(pItem).execute();
			//textTargetUri.setText("Hola mundo");
		//} catch (Exception e) {//IOException
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//textTargetUri.setText(e.toString());
		//}
		 
		 //new PostItemTask().execute();
		 new UploadImageTask().execute(targetUri.toString());
		 myPostButton = (Button)findViewById(R.id.btnPost);
		 myPostButton.setEnabled(false);		 
	 }
	 
	 /*
	 private void X(){
		 DefaultHttpClient http_client = new DefaultHttpClient();
		 HttpGet http_get = new HttpGet(Config.BASE_URL + "bloburl");
		 HttpResponse response = http_client.execute(http_get);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		 String first_line = reader.readLine();
		 Log.w(TAG, "blob_url: " + first_line);

		 HttpPost post = new HttpPost(first_line);
		 http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		 MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );

		 mime_type = "application/zip";
		 File file = new File( context.getFilesDir(), filename );
		 entity.addPart( "file", new FileBody( file, mime_type));
		 post.setEntity( entity );

		 String result = EntityUtils.toString( http_client.execute(post).getEntity(), "UTF-8");
		 Log.i(TAG, result);
	 }
	 */
	 
  /**
   * AsyncTask for calling Mobile Assistant API for posting an item
   */
  private class PostItemTask extends AsyncTask<Void, Void, Long> {

    /**
     * Calls appropriate CloudEndpoint to indicate that user checked into a place.
     *
     * @param params the place where the user is checking in.
     */
    @Override
    protected Long doInBackground(Void... params) {
      

		//Item myItem = new Item(currentLocation.getLatitude(), currentLocation.getLongitude(), 
			//	 edtextEmail.getText().toString(), edtextPhone.getText().toString(), bmpCurrentBitmap);

		Item myItem = new Item((float)1, (float)1, 
				 edtextEmail.getText().toString(), edtextPhone.getText().toString(), bmpCurrentBitmap);		
		
		PostedItem pItem = new com.example.giveaway.posteditemendpoint.model.PostedItem();
		GeoPt myPt = new com.example.giveaway.posteditemendpoint.model.GeoPt();
		myPt.setLatitude((float)1);//currentLocation.getLatitude());
		myPt.setLongitude((float)1);//currentLocation.getLongitude());
		pItem.setEmail(myItem.GetEmail());
		pItem.setImageUrl(sResultingURL);
		pItem.setLocation(myPt);
		//pItem.setLatitude(5d);
		//pItem.setLongitude(6d);
		pItem.setPhone(myItem.GetPhone());
		pItem.setPostingDate(new DateTime(new Date()));
      
		Posteditemendpoint.Builder builder = new Posteditemendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), 
				new JacksonFactory(), 
				null);
		
		builder = CloudEndpointUtils.updateBuilder(builder);
		
		Posteditemendpoint endpoint = builder.build();		
		
		try {
			endpoint.insertPostedItem(pItem).execute();
			//textTargetUri.setText("Hola mundo");
		} catch (Exception e) {//IOException
			// TODO Auto-generated catch block
			e.printStackTrace();
			//textTargetUri.setText(e.toString());
			return (long)0;
		}

      return (long) 1;
    }
    
    protected void onPostExecute(Long result){
    	if (result == 1){
		 myPostButton = (Button)findViewById(R.id.btnPost);
		 myPostButton.setEnabled(true);		 
    	}
    }
    
  }
  
  
  public String getRealPathFromURI(Uri contentUri) {
	  Cursor cursor = null;
	  try { 
	    String[] proj = { MediaStore.Images.Media.DATA };
	    cursor = getContentResolver().query(contentUri,  proj, null, null, null);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	  } finally {
	    if (cursor != null) {
	      cursor.close();
	    }
	  }
	}

  private class UploadImageTask extends AsyncTask<String, Void, Long> {

	    @SuppressWarnings("deprecation")
		protected Long doInBackground(String... sPicturePath) {
	    	
	    	try{
	    		
	  	  DefaultHttpClient http_client = new DefaultHttpClient();
	  	  HttpGet http_get = new HttpGet("http://giveawayprealpha.appspot.com/UploadImageHandler");
		  HttpResponse response = http_client.execute(http_get);
		  BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		  String first_line = reader.readLine();
		  Log.w("PostPicture", "blob_url: " + first_line);
		  sResultingURL = first_line;	  	  
	  	  
		  HttpPost post = new HttpPost(first_line);
          //http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);          
		  //MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
		  //multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		  //File file = new File(sPicturePath[0]);
		  //multipartEntity.addPart("file", new FileBody(file));
		  //post.setEntity(multipartEntity.build());	

		  MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );
		  String mime_type = "image/jpeg";
		  File file = new File(getRealPathFromURI(targetUri));
		  
		  entity.addPart("file", new FileBody( file, mime_type));
		  //multipartEntity.addPart("file", new FileBody(file));
		  post.setEntity( entity );
		  //post.setEntity(multipartEntity.build());			  
		  
		  String res = EntityUtils.toString( http_client.execute(post).getEntity(), "UTF-8");
		  Log.i("PostPicture", res); 
		  
		  /*
	  	  HttpGet http_get = new HttpGet(MyAppURL + "bloburl");
		  HttpResponse response = http_client.execute(http_get);
		  BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		  String first_line = reader.readLine();
		  Log.w("PostPicture", "blob_url: " + first_line);
		  sResultingURL = first_line;
*/
	    	/*
		  HttpPost post = new HttpPost(first_line);
		  http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	*/
/*
          HttpPost post = new HttpPost("http://giveawayprealpha.appspot.com/");
          http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


          String result = EntityUtils.toString( http_client.execute(post).getEntity(), "UTF-8");
          String actualURL = result.substring(result.indexOf("http://"), result.indexOf("\" method"));
          Log.w("PostPicture", "url " + actualURL );
          sResultingURL = actualURL;
		  
          
          post = new HttpPost(actualURL);
          http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);           
          
		  MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create(); 
		  multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		  //MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );

		 //String mime_type = "image/jpeg";
		  File file = new File(sPicturePath[0]);
		  //entity.addPart("file", new FileBody( file, mime_type));
		  multipartEntity.addPart("file", new FileBody(file));
		  //post.setEntity( entity );
		  post.setEntity(multipartEntity.build());	

		  String res = EntityUtils.toString( http_client.execute(post).getEntity(), "UTF-8");
		  Log.i("PostPicture", res);*/
		  
		  return (long)1;
	    	}catch (Exception e){
	    		e.printStackTrace();	    		
	    		return (long)0;
	    	}
	    	
	    }

	    protected void onPostExecute(Long uploadResult) {
	    	
	    	if (uploadResult == 1){
	    		new PostItemTask().execute();
	    	}
	        /*myBitmap = returnedBitmap;
			ImageView i = (ImageView) findViewById(R.id.ivPostedItem);		        
	        if (myBitmap != null){
				i.setImageBitmap(myBitmap);					
	        }
	        else{
	        	i.setImageResource(R.drawable.na_1);		        	
	        }*/
	    }
	}  
  
  /*
  private void PostPicture(){
	  
	  DefaultHttpClient http_client = new DefaultHttpClient();
	  HttpGet http_get = new HttpGet(MyAppURL + "bloburl");
	  HttpResponse response = http_client.execute(http_get);
	  BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	  String first_line = reader.readLine();
	  Log.w("PostPicture", "blob_url: " + first_line);

	  HttpPost post = new HttpPost(first_line);
	  http_client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	  MultipartEntity entity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE );

	  String filename = null;
	  String mime_type = "application/zip";
	  File file = new File( context.getFilesDir(), filename );
	  entity.addPart( "file", new FileBody( file, mime_type));
	  post.setEntity( entity );

	  String result = EntityUtils.toString( http_client.execute(post).getEntity(), "UTF-8");
	  Log.i("PostPicture", result);
	  	  	  
  }
	*/  
}