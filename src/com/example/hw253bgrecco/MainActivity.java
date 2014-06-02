package com.example.hw253bgrecco;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	
	private int NOTIFICATION_ID = 818;
	
	Boolean bServiceStarted = false;
	Button startStopButton;
	SharedPreferences prefs = null;
	Boolean bIsInForegroundMode = false;
	NotificationManager mNotificationManager;
	Notification mNotification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set the listener for the start beep / end beep button
		startStopButton = (Button) findViewById(R.id.StartStopButton);
		startStopButton.setOnClickListener(this);
		
		// find our late beeping state
		prefs = this.getSharedPreferences("com.example.hw253bgrecco", Context.MODE_PRIVATE);
		
		// Get the NotificationManager
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// Create a PendingIntent so that the Notification knows who to call...
		Intent mainIntent = new Intent(this, MainActivity.class);
		PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent, 0);

		
		// Create the Normal Notification
		mNotification =  new Notification.Builder(this)
			.setContentIntent(mainPendingIntent)
			.setContentTitle("Beeper App")
			.setContentText("Change beep Status")
			.setSmallIcon(R.drawable.ic_launcher)
			.getNotification();		

	}

	@Override
	protected void onResume() {
		// Get the last beeper state
		if (prefs == null){
			prefs = this.getSharedPreferences("com.example.hw253bgrecco", Context.MODE_PRIVATE);				
		}
		// Start the service if needed
		bServiceStarted = prefs.getBoolean("timerState", false);
		setServiceState();
		
		// note that we are now in the foreground
		bIsInForegroundMode = true;	
		// hide the icon in the notification bar
		mNotificationManager.cancel(NOTIFICATION_ID);
		
		super.onResume();
	}

	@Override
	protected void onPause() {
		// Note we are no longer in the foreground
		bIsInForegroundMode = false;
		// show the icon in the notification bar
		mNotificationManager.notify(NOTIFICATION_ID, mNotification);		
		super.onPause();
	}

	void setServiceState(){
		// Show the correct button state (off/on)
		if (bServiceStarted){
			startStopButton.setText(R.string.Stop);
			startService(new Intent(getBaseContext(),TimerService.class));	
		}
		else {
			startStopButton.setText(R.string.Start);
			stopService(new Intent(getBaseContext(),TimerService.class));
		}
	}
	@Override
    public void onClick(View v) {
	
		if (bServiceStarted){
			bServiceStarted = false;
		}
		else {
			bServiceStarted = true;
		}
		setServiceState();
		//Save Value in shared preferences
		prefs.edit().putBoolean("timerState", bServiceStarted).commit();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
