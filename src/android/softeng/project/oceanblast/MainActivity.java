/**
 * MainActivity.java
 * Jan 11, 2013
 * 2:03:28 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */
package android.softeng.project.oceanblast;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.controller.MultiTouch;

import org.andengine.ui.activity.BaseGameActivity;


import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.ResourcesManager;
import android.softeng.project.oceanblast.managers.SceneManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity
{
		private Camera camera;
		private boolean mPlaceOnScreenControlsAtDifferentVerticalLocations = false;	
		
		//managers
		private SceneManager sceneManager;
		private ResourcesManager resourcesManager;
		private GameManager gameManager;
		
		public EngineOptions onCreateEngineOptions()
		{
		Log.d("-------onCreateEngineOptions()---------", " ");
				camera = new Camera(0, 0, ConstantsList.CAMERA_WIDTH, ConstantsList.CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		if(MultiTouch.isSupported(this)) {
			if(MultiTouch.isSupportedDistinct(this)) {
				Toast.makeText(this, "MultiTouch detected --> Both controls will work properly!", Toast.LENGTH_SHORT).show();
			} else {
				this.mPlaceOnScreenControlsAtDifferentVerticalLocations = true;
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers." +
						"\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!\n\n(Falling back to SingleTouch.)" +
					"\n\nControls are placed at different vertical locations.", Toast.LENGTH_LONG).show();
			}		
		
		return engineOptions;
		}

		public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
		{
		Log.d("-------onCreateResources()---------", " ");
		
		ResourcesManager.prepareManager(mEngine, this, this.camera, getVertexBufferObjectManager());
		ResourcesManager.getInstance().loadSplashScene();
		        pOnCreateResourcesCallback.onCreateResourcesFinished();
		}

		public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception
		{
		Log.d("-------onCreateScene()---------", " ");
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		}

		public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
		{
		Log.d("-------onPopulateScene()---------", " ");
		mEngine.registerUpdateHandler(new TimerHandler(5f, new ITimerCallback()
		{

					public void onTimePassed(TimerHandler pTimerHandler) {
				  		  	mEngine.unregisterUpdateHandler(pTimerHandler);
				  		  	
				  		  	
				  		  	
				  		  	SceneManager.getInstance().createMenuScene();
			                
					}
		}));
		 
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		}

				
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) 
		{  
		    if (keyCode == KeyEvent.KEYCODE_BACK)
		    {
		       if(SceneManager.getInstance().getCurrentScene().getSceneType().equals(SceneType.MENU))
		    	   onDestroy();
		       else
		    	   SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		    }
		    return false; 
		}

		@Override
		protected void onDestroy()
		{
		    super.onDestroy();
		    System.exit(0);
		}

		
}
