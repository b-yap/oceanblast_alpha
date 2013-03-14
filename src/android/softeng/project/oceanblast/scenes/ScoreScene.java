/**
* ScoreScene.java
* Mar 9, 2013
* 2:41:57 AM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;

import android.graphics.Color;
import android.opengl.GLES20;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.SceneManager;


public class ScoreScene extends BaseScene{

	private Sprite obackground;
	private Text scoreText;
	private Text userText;
	@Override
	public void createScene() {
		engine.registerUpdateHandler(new FPSLogger());
		final float centerX = (ConstantsList.CAMERA_WIDTH - resourcesManager.score_bgroundRegion.getWidth()) / 2;
		final float centerY = (ConstantsList.CAMERA_HEIGHT - resourcesManager.score_bgroundRegion.getHeight()) / 2;
		
		obackground = new Sprite(centerX,centerY,resourcesManager.score_bgroundRegion,vboManager);
		this.attachChild(obackground);
		
		int score; String user;
		if(GameManager.getInstance().mScoreDb.contains("HIGHSCORE_LABEL") && GameManager.getInstance().mScoreDb.contains("HIGHSCORE_USERNAME"))
		{
			user =GameManager.getInstance().loadHighScoreUserName();
			score=GameManager.getInstance().loadHighScore();
		}
		else
		{
			user =GameManager.getInstance().user_name;
			score=GameManager.getInstance().currentScore;
		}
			
		
		
		scoreText = new Text(centerX+250, centerY+150,resourcesManager.font, Integer.toString(score),
				"xxxxxxxxxxxx".length(),vboManager);
		scoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.attachChild(scoreText);
		
		
		userText = new Text(centerX+250, centerY+250,resourcesManager.font, user,
				"xxxxxxxxxxxxxxxxxxxxx".length(), vboManager);
		scoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.attachChild(userText);
		
		this.setBackgroundEnabled(false);
		
	}

	@Override
	public void onBackKeyPressed() {
		this.disposeScene();
		SceneManager.getInstance().setCurrentScene(SceneType.MENU);
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCORE;
	}

	@Override
	public void disposeScene() {
		scoreText.detachSelf();
		scoreText.dispose();
		userText.detachSelf();
		userText.dispose();
		obackground.detachSelf();
		obackground.dispose();
		this.detachSelf();
		this.dispose();	
		
	}

}
