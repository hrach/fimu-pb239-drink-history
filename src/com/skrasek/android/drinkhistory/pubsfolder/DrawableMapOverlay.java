package com.skrasek.android.drinkhistory.pubsfolder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class DrawableMapOverlay extends Overlay {

	  private static final double MAX_TAP_DISTANCE_KM = 3;
	  // Rough approximation - one degree = 50 nautical miles
	  private static final double MAX_TAP_DISTANCE_DEGREES = MAX_TAP_DISTANCE_KM * 0.5399568 * 50;
	  private final GeoPoint geoPoint;
	  private final Context context;
	  private final int drawable;
	  /**
	   * @param context the context in which to display the overlay
	   * @param geoPoint the geographical point where the overlay is located
	   * @param drawable the ID of the desired drawable
	   */
	  public DrawableMapOverlay(Context context, GeoPoint geoPoint, int drawable) {
	    this.context = context;
	    this.geoPoint = geoPoint;
	    this.drawable = drawable;
	  
	  }

	  @Override
	  public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when) {
	    super.draw(canvas, mapView, shadow);

	    // Convert geo coordinates to screen pixels
	    Point screenPoint = new Point();
	    mapView.getProjection().toPixels(geoPoint, screenPoint);

	    // Read the image
	    Bitmap markerImage = BitmapFactory.decodeResource(context.getResources(), drawable);

	    // Draw it, centered around the given coordinates
	    canvas.drawBitmap(markerImage,
	        screenPoint.x - markerImage.getWidth() / 2,
	        screenPoint.y - markerImage.getHeight() / 2, null);
	    return true;
	  }

	  boolean MoveMap;
	  
	  @Override
	  public boolean onTouchEvent(android.view.MotionEvent arg0, MapView arg1) {
		
		  /*
		  int Action = arg0.getAction();
		  if (Action == MotionEvent.ACTION_UP){

		  if(!MoveMap)
		  {
		  Projection proj = arg1.getProjection();
		  GeoPoint loc = proj.fromPixels((int)arg0.getX(), (int)arg0.getY());
		    
		  //remove the last marker
		  arg1.getOverlays().remove(0);
		    
		  //CenterLocation(loc);
		  }

		  }
		  else if (Action == MotionEvent.ACTION_DOWN){

		  MoveMap = false;

		  }
		  else if (Action == MotionEvent.ACTION_MOVE){
		  MoveMap = true;
		  }

		  return super.onTouchEvent(arg0, arg1);
		  */return false;
		  
		  
		  
	  }
	  
	  
	  @Override
	  public boolean onTap(GeoPoint p, MapView mapView) {
	    // Handle tapping on the overlay here
	    return true;
	  }
	}
