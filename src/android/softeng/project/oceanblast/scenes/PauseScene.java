/**
* PauseScene.java
* Mar 3, 2013
* 12:40:57 PM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.region.TextureRegion;

import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.ResourcesManager;
import android.softeng.project.oceanblast.managers.SceneManager;



public class PauseScene extends BaseScene {

	@Override
	public void createScene() {
		engine.registerUpdateHandler(new FPSLogger());
		final float centerX = (ConstantsList.CAMERA_WIDTH - resourcesManager.pause_btnHolderRegion.getWidth()) / 2;
		final float centerY = (ConstantsList.CAMERA_HEIGHT - resourcesManager.pause_btnHolderRegion.getHeight()) / 2;
		
		
		final Sprite messageBox = createPauseSprite(centerX, centerY,resourcesManager.pause_btnHolderRegion,0);
		this.attachChild(messageBox);
		
		final Sprite resumeButton = createPauseSprite((messageBox.getWidth() - resourcesManager.pause_btnResumeRegion.getWidth())/2-100,
				(messageBox.getWidth()-resourcesManager.pause_btnResumeRegion.getHeight())/2-70,resourcesManager.pause_btnResumeRegion, 1);
		this.registerTouchArea(resumeButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		messageBox.attachChild(resumeButton);
		
		final Sprite menuButton = createPauseSprite((messageBox.getWidth() - resourcesManager.pause_btnResumeRegion.getWidth())/2-100,
				(messageBox.getWidth()-resourcesManager.pause_btnResumeRegion.getHeight())/2-10,resourcesManager.pause_btnMenuRegion, 2);
		this.registerTouchArea(menuButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		messageBox.attachChild(menuButton);
		/* Makes the paused Game look through. */
		this.setBackgroundEnabled(false);	
	}

	@Override
	public void onBackKeyPressed() {
		SceneManager.getInstance().loadMenuScene();
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.PAUSE;
	}

	@Override
	public void disposeScene() {
			
	}
	
	 private Sprite createPauseSprite(float centerX, float centerY, TextureRegion button, int button_id) {
	    		
		 /* LISTENERS */
		 	OnClickListener resumeListener = new OnClickListener() {				
				@Override
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
						GameManager.getInstance().gameHandler.resume();
							SceneManager.getInstance().setCurrentScene(SceneType.GAME);				
				}
		 	};

	    	OnClickListener menuListener = new OnClickListener() {				
				@Override
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
					SceneManager.getInstance().loadMenuScene();				
				}
			};
		 
			// Creating the buttons
		 	final ButtonSprite pauseButton = new ButtonSprite(centerX, centerY,	button,vboManager);
	    	
	    	switch(button_id){
	    		case 1: pauseButton.setOnClickListener(resumeListener); break;
	    		case 2: pauseButton.setOnClickListener(menuListener); break;
	    	}
	    				
		return pauseButton;
	    }

	
}
