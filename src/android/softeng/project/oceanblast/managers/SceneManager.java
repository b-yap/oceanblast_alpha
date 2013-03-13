/**
 * SceneManager.java
 * Jan 11, 2013
 * 2:04:14 PM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package android.softeng.project.oceanblast.managers;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;
import android.content.res.Resources;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.GameTimerHandler;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.ResourcesManager;
import android.softeng.project.oceanblast.scenes.BaseScene;
import android.softeng.project.oceanblast.scenes.ClearedScene;
import android.softeng.project.oceanblast.scenes.GameOverScene;
import android.softeng.project.oceanblast.scenes.GameScene;
import android.softeng.project.oceanblast.scenes.LoadingScene;
import android.softeng.project.oceanblast.scenes.MenuScene;
import android.softeng.project.oceanblast.scenes.PauseScene;
import android.softeng.project.oceanblast.scenes.ScoreScene;
import android.softeng.project.oceanblast.scenes.SplashScene;
import android.util.Log;

public class SceneManager {

	private static final SceneManager INSTANCE = new SceneManager();	
	private Engine engine =ResourcesManager.getInstance().engine;

	//scenes
	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene gameOverScene;
	private BaseScene pauseScene;
	private BaseScene loadingScene;
	private BaseScene clearedScene;
	private BaseScene scoreScene;
	private BaseScene currentScene;
	
	
	public BaseScene getCurrentScene() {
		return currentScene;
	}	

	//Method allows you to set the currently active scene
	public void setCurrentScene(ConstantsList.SceneType scene) {
	
		switch (scene)
		{
			case SPLASH:{
				engine.setScene(splashScene);
				 Log.d("set Splash", " ");
				 currentScene = splashScene;
			break;
			}	
			case MENU:{		
				menuScene.clearChildScene();
				engine.setScene(menuScene);			
				Log.d("set Menu", " ");
				currentScene = menuScene;
			break;
			}	
			case GAME:{
				gameScene.setIgnoreUpdate(false);
				gameScene.clearChildScene();
				gameScene.setChildScene(ResourcesManager.getInstance().game_analogControl.getAnalogControl());	
				
				engine.setScene(gameScene);	
				Log.d("set game", " ");
				currentScene = gameScene; 
			break;
			}	
			case SCORE:{
				menuScene.setChildScene(scoreScene);
				Log.d("set high score ", " ");
				currentScene =scoreScene;
			break;
			}
			case PAUSE:{	
				gameScene.setIgnoreUpdate(true);
				gameScene.setChildScene(pauseScene,false, true,true);
				Log.d("paused", "done");
				currentScene =pauseScene;
			break;
			}
			case LOADING:{
				engine.setScene(loadingScene);
				Log.d("loading", " ");

				currentScene =loadingScene;
			break;
			}
			case GAMEOVER:{
				gameScene.setIgnoreUpdate(true);
				gameScene.setChildScene(gameOverScene,false, true,true);
				Log.d("Game Over", " ");

				currentScene =gameOverScene;
			break;
			}
			case LEVEL_CLEARED:{
				gameScene.setIgnoreUpdate(true);
				gameScene.setChildScene(clearedScene);

				currentScene =clearedScene;
			break;	
			}
		}
	}
	
	public static SceneManager getInstance(){
		return INSTANCE;
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback){

		splashScene = new SplashScene();
		currentScene =splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);		
	}
	
	private void disposeSplashScene(){
		ResourcesManager.getInstance().unloadSplashScene();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	public void createMenuScene(){
        ResourcesManager.getInstance().loadHighScoreResources();
		ResourcesManager.getInstance().loadMenuResources();	
		menuScene = new MenuScene();
		ResourcesManager.getInstance().loadLoadingSceneResources();
		loadingScene = new LoadingScene();		
		ResourcesManager.getInstance().loadPauseResources();
		pauseScene = new PauseScene();
        gameOverScene = new GameOverScene();
        clearedScene = new ClearedScene();
        scoreScene = new ScoreScene();
       ResourcesManager.getInstance().loadGameResources();
		setCurrentScene(ConstantsList.SceneType.MENU);
		disposeSplashScene();
		
	}
	
	/********************* WHILE LOADING *************************************/
	
	public void loadGameScene()
	{	
	    setCurrentScene(SceneType.LOADING);
	    engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	    	
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	    	    	    ResourcesManager.getInstance().unloadMenuScene();	
	            engine.unregisterUpdateHandler(pTimerHandler);                    
	            if (GameManager.getInstance().counter<=GameManager.getInstance().num_of_levels )
	            {           	
		            GameManager.getInstance().chooseLevel(GameManager.getInstance().counter);
		            gameScene = new GameScene();
		            setCurrentScene(SceneType.GAME);
		           }
	            else
	            	loadMenuScene();
	           
	            
	        }
	    }));
	}
	
	public void loadMenuScene()
	{
	    setCurrentScene(SceneType.LOADING);
	    engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() 
	    {
	        public void onTimePassed(final TimerHandler pTimerHandler) 
	        {
	            engine.unregisterUpdateHandler(pTimerHandler);
	            gameScene.disposeScene();
	    	    ResourcesManager.getInstance().unloadGameScene();
	            ResourcesManager.getInstance().loadMenuScene();
	            scoreScene = new ScoreScene();
	            
	            setCurrentScene(SceneType.MENU);
	            
	        }
	    }));
	}
	
	
	
	
}



