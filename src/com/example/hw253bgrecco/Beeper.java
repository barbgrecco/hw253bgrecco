package com.example.hw253bgrecco;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;


	public class Beeper extends TimerTask {

	  Timer timer = null;
	  Boolean bTimerRunning = false;
	  Context beeperContext = null;
	  
	  public Beeper(Context c){
		  beeperContext = c;
	  }
	  
	  public void StartBeeper() {
		  // Create the timer if it doesn't exist
		  if ( timer == null ){
			  timer = new Timer();
		  }
		  if (!bTimerRunning){
			  //Start the timer with initial delay of 0 and interval of 5000ms
			  timer.schedule(this, 0, 5 * 1000);
			  bTimerRunning = true;
		  }
	  }

	  public void StopBeeper(){
		  // Cancel the timer, we are done
		  timer.cancel();
		  bTimerRunning = false;
	  }
	  
	  public void PlayBeep() {
		  // Generate the beep tone
		  final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
		  tg.startTone(ToneGenerator.TONE_PROP_BEEP);
  }
	  
	  public void run() {
		  // 5 seconds has past - BEEP
	      PlayBeep();
	   }

	}

