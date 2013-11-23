package com.example.giveaway;

import android.graphics.Bitmap;

public class Item {

	private double dLatitude;
	private double dLongitude;
	private String strEmail;
	private String strPhone;
	private Bitmap btmpImage;
	
	public Item (double dLat, double dLong, String strE, String strP, Bitmap bMap)
	{
		dLatitude = dLat;
		dLongitude=dLong;
		strEmail=strE;
		strPhone=strP;
		btmpImage=bMap;
	}
	
	public double GetLatitude(){
		return dLatitude;
	}
	
	public double GetLongitude(){
		return dLongitude;
	}
	
	public String GetEmail(){
		return strEmail;
	}
	
	public String GetPhone(){
		return strPhone;
	}
	
	public Bitmap GetImage(){
		return btmpImage;
	}
}
