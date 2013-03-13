/**
* GameScene_base.java
* Mar 3, 2013
* 7:16:19 AM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.IModifier;

import android.opengl.GLES20;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.GameTimerHandler;
import android.softeng.project.oceanblast.ConstantsList.SceneType;
import android.softeng.project.oceanblast.managers.GameManager;
import android.softeng.project.oceanblast.managers.SceneManager;
import android.util.Log;


public class GameScene extends BaseScene implements IOnSceneTouchListener{

	private Sprite userPlayer;
	private ButtonSprite pauseButton;

	private LinkedList TargetsToBeAdded;
	private LinkedList targetLL; 
	private LinkedList projectileLL;
	private LinkedList projectilesToBeAdded;
	
	private Text scoreText;
			
	private int currentScore; 
	private PhysicsHandler physicsHandler;
	private BaseScene mScene;
	
	private boolean gameOver=false;
	
	@Override
	public void createScene() {
		// initialize the necessities 
		this.mScene = this;	
		TargetsToBeAdded = new LinkedList();
		targetLL = new LinkedList();
		projectilesToBeAdded = new LinkedList();
		projectileLL = new LinkedList();
		//creation of objects 
		createBackground();
		createGameObjects();
		createButtons();
	
		resourcesManager.game_analogControl.setPlayerPhysicsHandler(userPlayer, physicsHandler);
		resourcesManager.game_analogControl.setAnalogControl();
		setOnSceneTouchListener(this);
	 }

	@Override
	public void onBackKeyPressed() {
		GameManager.getInstance().gameHandler.pause();
		
		if(gameOver==false)
			SceneManager.getInstance().setCurrentScene(SceneType.PAUSE);
				
	}

	@Override
	public SceneType getSceneType() {
		return ConstantsList.SceneType.GAME;
	}

	@Override
	public void disposeScene() {
		targetLL.clear();
		TargetsToBeAdded.clear();
		projectileLL.clear();
		projectilesToBeAdded.clear();
		this.detachSelf();
		this.dispose();			
	}
	
	private void createBackground(){
		ITextureRegion back=resourcesManager.game_parallaxLayerBackRegion;
		ITextureRegion front=resourcesManager.game_parallaxLayerFrontRegion;
		
		switch((new Random()).nextInt(3)){
		case 1: back=resourcesManager.game_parallaxLayerBackRegion; front=resourcesManager.game_parallaxLayerFrontRegion; break;
		case 2: back=resourcesManager.game_parallaxLayerBack1Region; front=resourcesManager.game_parallaxLayerFront1Region; break;
		case 3: back=resourcesManager.game_parallaxLayerBack2Region; front=resourcesManager.game_parallaxLayerFront2Region; break;
		
		
		}
		
		//background
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0,0,0,5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.2f, new Sprite(0, ConstantsList.CAMERA_HEIGHT - 
				 back.getHeight(),back, vboManager)));
		 
		 autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(GameManager.getInstance().speed, new Sprite(0, ConstantsList.CAMERA_HEIGHT - 
				 front.getHeight(), front, vboManager)));
		 this.setBackground(autoParallaxBackground);		 
	}
	
	private void createGameObjects(){	
		
		final int centerX= (int) (ConstantsList.CAMERA_WIDTH - resourcesManager.game_playerRegion.getHeight())/2;	
		final int centerY=(int) (ConstantsList.CAMERA_HEIGHT - resourcesManager.game_playerRegion.getHeight())/2;	
	
		//player
		 final AnimatedSprite userPlayer = new AnimatedSprite(centerX, centerY, resourcesManager.game_playerRegion,vboManager);
		userPlayer.animate(200);
		 this.physicsHandler = new PhysicsHandler(userPlayer);
		 userPlayer.registerUpdateHandler(physicsHandler);
		 this.attachChild(userPlayer);
		 this.userPlayer = userPlayer; 
		 
		 createSpriteSpawnTimeHandler();
		 
		 IUpdateHandler detect = new IUpdateHandler() {
			    public void reset() {
			    }

			    public void onUpdate(float pSecondsElapsed) {

			        Iterator<Sprite> targets = targetLL.iterator();
			        Sprite _target;
			        boolean hit = false;

			        while (targets.hasNext()) {
			            _target = targets.next();

			            if(_target.collidesWith(userPlayer)){
		                	hit = true;
		                	removeSprite(userPlayer);
		                	GameManager.getInstance().gameHandler.pause();
		                	if(currentScore > GameManager.getInstance().loadHighScore()){
		                		GameManager.getInstance().saveHighScore(currentScore);
		                	}
		                	GameManager.getInstance().currentScore=0;
		                	SceneManager.getInstance().setCurrentScene(SceneType.GAMEOVER);
		                
		                	gameOver = true;
		                }
			            
			            if (_target.getX() <= -_target.getWidth()) {
			                removeSprite(_target, targets);
			                break;
			            }
			            Iterator<Sprite> projectiles = projectileLL.iterator();
			            Sprite _projectile;
			            while (projectiles.hasNext()) {
			                _projectile = projectiles.next();

			                if (_projectile.getX() >= camera.getWidth()
			                    || _projectile.getY() >= camera.getHeight()
			                    + _projectile.getHeight()
			                    || _projectile.getY() <= -_projectile.getHeight()) {
			                        removeSprite(_projectile, projectiles);
			                        continue;
			                }

			                if (_target.collidesWith(_projectile)) {
			                    removeSprite(_projectile, projectiles);
			                    hit = true;
			                    ++currentScore;
			                    scoreText.setText("Score: " + currentScore );
			                    break;
			                }		              
			            }

			            if (hit) {
			                removeSprite(_target, targets);		                
			                hit = false;
			            }

			        }
			        projectileLL.addAll(projectilesToBeAdded);
			        projectilesToBeAdded.clear();

			        targetLL.addAll(TargetsToBeAdded);
			        TargetsToBeAdded.clear();
			    }
			};

			// display score when hit	 
		currentScore= GameManager.getInstance().currentScore;
		this.scoreText = new Text(5, 5, resourcesManager.font, "Score: "+ currentScore , "Score: XXXX".length(), vboManager);
		
		this.scoreText.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.scoreText.setAlpha(0.5f);
		this.attachChild(this.scoreText);
		
		this.registerUpdateHandler(detect);
	}
	
	private void createButtons(){
		 //listener for the pause button
		 OnClickListener pauseListener = new OnClickListener(){
			
				public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
						float pTouchAreaLocalY) {
					// call the setCurrentScene for pause button
					GameManager.getInstance().gameHandler.pause();
					SceneManager.getInstance().setCurrentScene(SceneType.PAUSE);
				}
			};	
			
		 //pause button
		 pauseButton = new ButtonSprite(740,10,resourcesManager.game_btnPauseRegion,vboManager,pauseListener);
		 this.registerTouchArea(pauseButton);
		 this.setTouchAreaBindingOnActionDownEnabled(true);
		 this.attachChild(pauseButton);
		}
	
	
	/* TIMER ON CREATING MULTIPLE ENEMIES*/
	
	private void createSpriteSpawnTimeHandler() {
	    float mEffectSpawnDelay = GameManager.getInstance().spawnDelay;

	   GameManager.getInstance().gameHandler  = new GameTimerHandler(mEffectSpawnDelay,true, new ITimerCallback() {
			
	    	@Override
	        public void onTimePassed(TimerHandler pTimerHandler) {
	      		Log.d("number of enemies left: " + GameManager.getInstance().num_of_enemies, " ");
	        	
	      		if(GameManager.getInstance().num_of_enemies>0){
	      		addTarget();  
	      		GameManager.getInstance().num_of_enemies--;	
	    	    
	      		}
	      		else{
	      			engine.unregisterUpdateHandler(pTimerHandler);	 
	      			GameManager.getInstance().currentScore = currentScore;
	               	++GameManager.getInstance().counter;
	        		SceneManager.getInstance().setCurrentScene(SceneType.LEVEL_CLEARED);	  
	    		}
    	    }
		});
	
	    engine.registerUpdateHandler(GameManager.getInstance().gameHandler );
	}
	
	/* MULTIPLE ENEMIES CREATED, NAMED AS 'target'  USING deepCopy A.K.A. cloning */
	
	public void addTarget() {
		Log.d("added target ", " ");
		
		Random rand = new Random();

	    int x = (int) camera.getWidth() + (int) resourcesManager.game_enemyGoldfishRegion.getWidth();
	    int minY =(int) resourcesManager.game_enemyGoldfishRegion.getHeight();
	    int maxY = (int) (camera.getHeight() -(int) resourcesManager.game_enemyGoldfishRegion.getHeight());
	    int rangeY = maxY - minY;
	    int y = rand.nextInt(rangeY) + minY;

	    	TiledTextureRegion receiver = resourcesManager.game_enemyGoldfishRegion; 		
	    	switch(rand.nextInt(8)){
	    	
	    	case 1:  receiver = resourcesManager.game_enemyGoldfishRegion; break;
	    	case 2:  receiver = resourcesManager.game_enemyAnglerfishRegion; break;
	    	case 3:  receiver = resourcesManager.game_enemyMutatefishRegion; break;
	    	case 4:  receiver = resourcesManager.game_enemyOctopusRegion; break;
	    	case 5:  receiver = resourcesManager.game_enemyEelRegion; break;
	    	case 6:  receiver = resourcesManager.game_enemyJellyRegion; break;
	    	case 7:  receiver = resourcesManager.game_enemySharkRegion; break;
	    	case 8:  receiver = resourcesManager.game_enemyStingRegion; break;
	    	}
  
	    AnimatedSprite target = new AnimatedSprite(x, y, receiver.deepCopy(), vboManager);
	   target.animate(300);
	    this.attachChild(target);

	    int minDuration = 2;
	    int maxDuration = 4;
	    int rangeDuration = maxDuration - minDuration;
	    int actualDuration = rand.nextInt(rangeDuration) + minDuration;

	    MoveXModifier mod = new MoveXModifier(actualDuration, target.getX(),
	        -target.getWidth());
	    target.registerEntityModifier(mod.deepCopy());

	    TargetsToBeAdded.add(target);
	}
	
	
	/* SPRITE REMOVALS, USED OVERLOADING */
	
	private void removeSprite(final Sprite _sprite, Iterator it) {
	    activity.runOnUpdateThread(new Runnable() {

	        public void run() {
	            mScene.detachChild(_sprite);
	        }
	    });
	    it.remove();
	}
	
	private void removeSprite(final Sprite _sprite) {
	    activity.runOnUpdateThread(new Runnable() {

	         public void run() {
	            mScene.detachChild(_sprite);
	        }
	    });
	}
	
	/* SHOOTING MISSILES IN PROJECTILE MOTION */
	
	private void shootProjectile(final float pX, final float pY) {

		
		int offX = (int) (pX - userPlayer.getX());
	    int offY = (int) (pY - userPlayer.getY());
	    if (offX <= 0)
	        return;
	    
	    TextureRegion chooseMissile;
	    if(GameManager.getInstance().counter>=5)
	    	chooseMissile=resourcesManager.game_missile1Region;
	    else
	    	chooseMissile=resourcesManager.game_missile2Region;
	    
	    	    
	   final Sprite projectile = new Sprite(userPlayer.getX()+100, userPlayer.getY()+50,
	    chooseMissile.deepCopy(), vboManager);
	    this.attachChild(projectile);

	    int realX = (int) (camera.getWidth() + projectile.getWidth() / 2.0f);
	    float ratio = (float) offY / (float) offX;
	    int realY = (int) ((realX * ratio) + projectile.getY());

	    int offRealX = (int) (realX - projectile.getX());
	    int offRealY = (int) (realY - projectile.getY());
	    float length = (float) Math.sqrt((offRealX * offRealX)
	        + (offRealY * offRealY));
	    float velocity = 480.0f / 1.0f; // 480 pixels / 1 sec
	    float realMoveDuration = length / velocity;

	    MoveModifier mod = new MoveModifier(realMoveDuration,
	    projectile.getX(), realX, projectile.getY(), realY);
	    projectile.registerEntityModifier(mod.deepCopy());

	    projectilesToBeAdded.add(projectile);
	}

	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

	    if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
	        final float touchX = pSceneTouchEvent.getX();
	        final float touchY = pSceneTouchEvent.getY();
	        shootProjectile(touchX, touchY);
	        return true;
	    }
	    return false;
	}


}
