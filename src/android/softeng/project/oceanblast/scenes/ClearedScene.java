/**
* ClearedScene.java
* Mar 5, 2013
* 11:18:04 PM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/

package android.softeng.project.oceanblast.scenes;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.TextureRegion;

import android.opengl.GLES20;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.SceneManager;


public class ClearedScene extends BaseScene {

	@Override
	public void createScene() {
		engine.registerUpdateHandler(new FPSLogger());
		final float centerX = (ConstantsList.CAMERA_WIDTH - resourcesManager.pause_btnHolderRegion.getWidth()) / 2;
		final float centerY = (ConstantsList.CAMERA_HEIGHT - resourcesManager.pause_btnHolderRegion.getHeight()) / 2;
			
		final Sprite messageBox = createPauseSprite(centerX, centerY,resourcesManager.pause_btnHolderRegion);
		this.attachChild(messageBox);		

		if(GameManager.getInstance().counter<GameManager.getInstance().num_of_levels){
			final Text goodJobText =  new Text((messageBox.getWidth())/2-150,
					(messageBox.getHeight())/2-100, resourcesManager.font, "Good job!", "Good job!".length(), vboManager);
			messageBox.attachChild(goodJobText);				
				
			final Text nextLevelText = new Text((messageBox.getWidth())/2-170,
					(messageBox.getHeight())/2-50, resourcesManager.font, "Next Level", "Next Level".length(), vboManager){
				@Override
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					 switch(pSceneTouchEvent.getAction()) {
	                 case TouchEvent.ACTION_DOWN:
	                         SceneManager.getInstance().loadGameScene();
	                         break;
	                 }
					return super
							.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			};	
			
			nextLevelText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			this.registerTouchArea(nextLevelText);		
			messageBox.attachChild(nextLevelText);
		}
		else{
			final Text goodJobText =  new Text((messageBox.getWidth())/2-150,
					(messageBox.getHeight())/2-100, resourcesManager.font, "Congratulations! You've done it!", "Congratulations! You've done it!".length(), vboManager);
			messageBox.attachChild(goodJobText);				
		
				
				
		}
		final Sprite menuButton = createPauseSprite((messageBox.getWidth() - resourcesManager.pause_btnResumeRegion.getWidth())/2-30,
				(messageBox.getWidth()-resourcesManager.pause_btnResumeRegion.getHeight())/2+30,resourcesManager.pause_btnMenuRegion);
		this.registerTouchArea(menuButton);
		messageBox.attachChild(menuButton);
	
		this.setTouchAreaBindingOnActionDownEnabled(true);
		
		/* Makes the paused Game look through. */
		this.setBackgroundEnabled(false);	
	}

	@Override
	public void onBackKeyPressed() {		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.LEVEL_CLEARED;
	}

	@Override
	public void disposeScene() {		
	}
	
	 private Sprite createPauseSprite(float centerX, float centerY, TextureRegion button) {
	    		
		 /* LISTENERS */
		 	    	OnClickListener menuListener = new OnClickListener() {				
				@Override
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
					SceneManager.getInstance().loadMenuScene();				
				}
			};
		 
			// Creating the buttons
		 	final ButtonSprite pauseButton = new ButtonSprite(centerX, centerY,	button,vboManager);
	    	 	pauseButton.setOnClickListener(menuListener);
	    	
	    				
		return pauseButton;
	    }

	
}
