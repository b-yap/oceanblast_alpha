/**
* MenuScene.java
* Mar 3, 2013
* 7:20:58 AM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import org.andengine.audio.music.Music;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;

import android.content.SharedPreferences;
import android.opengl.GLES20;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.InputText;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.ResourcesManager;
import android.softeng.project.oceanblast.managers.SceneManager;
import android.util.Log;
import android.widget.Toast;


public class MenuScene extends BaseScene {

	/******************* VARIABLES *******************/
	//sprites
	private Sprite oBackground;
	private Sprite playButton;
	private Sprite quitButton;
	private Sprite helpButton;
	private Sprite soundOnButton;
	private Sprite soundOffButton;
	private Sprite onButton;
	private Sprite offButton;
	private InputText inputBox;
	private String inputUserName;
	Music music;
    
	
	@Override
	public void createScene() {
		 music= ResourcesManager.getInstance().menu_music;
		createBackground();
		createMenuButtons();
		
		//user data		
			
			Log.d("load data", " ");
			if(GameManager.getInstance().mScoreDb.contains("USERNAME"))
				inputUserName = GameManager.getInstance().loadUserName();
			else
				inputUserName = GameManager.getInstance().user_name;
			
		final Text userName = new Text(15, 15, resourcesManager.font, inputUserName,
				"UserssssssssssssssssName".length(), vboManager);
		userName.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		userName.setAlpha(0.5f);
		
		inputBox = new InputText(10,10,"Name"," ",
				resourcesManager.menu_btnChangeUserRegion,resourcesManager.font,17,19,vboManager,activity, userName);
	
		this.registerTouchArea(inputBox);
		
		this.attachChild(inputBox);
		this.attachChild(userName);	
		
	}

	@Override
	public void onBackKeyPressed() {
		System.exit(0);
		
	}

	@Override
	public SceneType getSceneType() {
		return ConstantsList.SceneType.MENU;
	}

	@Override
	public void disposeScene() {
		
	}
		
	private void createBackground(){
		 oBackground = new Sprite(0,0,resourcesManager.menu_bgroundRegion,resourcesManager.vboManager);
		this.attachChild(oBackground); 
	}
	
	private void createMenuButtons(){
		
		/* LISTENERS */
		
		OnClickListener musicListener = new OnClickListener() {    	
        			
			//toggle Music
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				if(music.isPlaying())	
					music.pause();
				else	
					music.play();
					
				if(soundOnButton.isVisible()==true){
					soundOnButton.setVisible(false);
					soundOffButton.setVisible(true);
				}else{
					soundOnButton.setVisible(true);
					soundOffButton.setVisible(false);	
				}
			}
		}; 
		
		 OnClickListener playListener = new OnClickListener(){			
			 public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
	    	    ResourcesManager.getInstance().loadGameResources();
				SceneManager.getInstance().loadGameScene(); 
			 }
		};
		
		 OnClickListener quitListener = new OnClickListener(){			
			 public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				SceneManager.getInstance().setCurrentScene(SceneType.SCORE);
			 }
		};
		
		//menu buttons
		
		playButton = new ButtonSprite(320,250,resourcesManager.menu_btnPlayRegion, resourcesManager.menu_btnPlayPushedRegion,
				resourcesManager.vboManager, playListener);		
		this.registerTouchArea(playButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(playButton);
		
		quitButton = new ButtonSprite(530,275,resourcesManager.menu_btnOptionRegion,resourcesManager.menu_btnOptionPushedRegion,
				resourcesManager.vboManager, quitListener);		
		this.registerTouchArea(quitButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(quitButton);
		
		soundOnButton = new ButtonSprite(150,290,resourcesManager.menu_btnOnRegion, resourcesManager.menu_btnOnRegion, resourcesManager.vboManager, musicListener);	
		this.registerTouchArea(soundOnButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(soundOnButton);
		
		soundOffButton = new ButtonSprite(150,290,resourcesManager.menu_btnOffRegion,resourcesManager.menu_btnOffRegion, resourcesManager.vboManager, musicListener);		
		this.registerTouchArea(soundOffButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(soundOffButton);
		
		soundOnButton.setVisible(true);
		soundOffButton.setVisible(false);
		music.play();
	}

}
