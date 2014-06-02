package com.example.hw253bgrecco;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerService extends Service {

	Beeper beeper = null;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Start the beeper
		if (beeper == null){
			beeper = new Beeper(this);			
		}

		beeper.StartBeeper();
		return START_STICKY;
	}
	
	

	@Override
	public void onDestroy() {
		// Stop the beeper
		if ( beeper != null ){
			beeper.StopBeeper();			
		}
		super.onDestroy();
	}



	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
