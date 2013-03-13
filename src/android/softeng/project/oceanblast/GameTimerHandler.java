/**
* enemyTimeHandler.java
* Feb 24, 2013
* 2:48:46 PM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import android.util.Log;

public class GameTimerHandler extends TimerHandler {
	 
	 private boolean mPause = false;
	  
	 public GameTimerHandler(float pTimerSeconds, boolean pAutoReset,
	   ITimerCallback pTimerCallback) {
	  super(pTimerSeconds, pAutoReset, pTimerCallback);
	 }
	  
	 public GameTimerHandler (float pTimerSeconds, ITimerCallback pTimerCallback){
		 super(pTimerSeconds, pTimerCallback);
	 }
	 public void pause() {
		 Log.d("handler paused.", " ");
	  this.mPause = true;
	 }
	  
	 public void resume() {
		 Log.d("handler resumed.", " ");
	  this.mPause = false;
	 }
	 
	 @Override
	 public void onUpdate(float pSecondsElapsed) {
	  if(!this.mPause) {
	   super.onUpdate(pSecondsElapsed);
	  }
	 }
	}