/**
 * GameManager.java
 * Feb 1, 2013
 * 11:35:55 AM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package android.softeng.project.oceanblast.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.softeng.project.oceanblast.GameTimerHandler;

public class GameManager {
	private static GameManager INSTANCE = new GameManager();
	public int currentScore=0;
	public int num_of_enemies;
	public float speed;
	public float spawnDelay;
	public int counter=1;
	public boolean sounds = true;
	public GameTimerHandler gameHandler;
	public boolean gameOver = false;
	public int num_of_levels =10;
	
	private static final String HIGHSCORE_DB_NAME = "Highscores";
	private static final String HIGHSCORE_LABEL = "score";
	private static final String HIGHSCORE_USERNAME ="highscore_username";
	private static final String USERNAME ="username";
	private static final String TOGGLE_MUSIC ="toggle_music";

	public SharedPreferences mScoreDb = ResourcesManager.getInstance().activity.getSharedPreferences(HIGHSCORE_DB_NAME, Context.MODE_PRIVATE);
	private SharedPreferences.Editor mScoreDbEditor = this.mScoreDb.edit();
	private boolean setMusic = true;
	public String user_name = this.loadUserName();
	
	public GameManager(){}
	
	public static GameManager getInstance(){
		return INSTANCE;
	}
	
	public int getCurrentScore(){
		return this.currentScore;
	}
	
	public void incrementScore(int pIncrementBy){
		currentScore +=pIncrementBy;
	}
	
	public void resetGame(){
		this.currentScore = 0;
	}

	/********************* CHOOSE LEVEL *************************************/
	 
	public void chooseLevel(int level){
		 switch(level){		 
		 case 1: num_of_enemies =5; speed= -10f; spawnDelay =3f; break;
		 case 2: num_of_enemies =10; speed= -15f; spawnDelay =3f; break;
		 case 3: num_of_enemies =15; speed= -20f; spawnDelay =3f; break;
		 case 4: num_of_enemies =25; speed= -30f; spawnDelay =2f; break;
		 case 5: num_of_enemies =35; speed= -35f; spawnDelay =2f; break;
		 case 6: num_of_enemies =45; speed= -40f; spawnDelay =1f;break;
		 case 7: num_of_enemies =60; speed= -40f; spawnDelay =1f;break;
		 case 8: num_of_enemies =75; speed= -55f; spawnDelay =1f;break;
		 case 9: num_of_enemies =80; speed= -60f; spawnDelay =0.8f;break;
		 case 10: num_of_enemies =95; speed= -70f; spawnDelay =0.8f;break;
//		 case 11: num_of_enemies =100; speed= -90f; spawnDelay =0.6f;break;		 
//		 case 12: num_of_enemies =105; speed= -100f; spawnDelay =0.06f;break;
//		 case 13: num_of_enemies =110; speed= -100f; spawnDelay =0.05f;break;
//		 case 14: num_of_enemies =120; speed= -110f; spawnDelay =0.05f;break;
//		 case 15: num_of_enemies =140; speed= -110f; spawnDelay =0.04f;break;
//		 case 16: num_of_enemies =150; speed= -130f; spawnDelay =0.04f;break;
//		 case 17: num_of_enemies =165; speed= -130f; spawnDelay =0.02f;break;
//		 case 18: num_of_enemies =175; speed= -135f; spawnDelay =0.02f;break;
//		 case 19: num_of_enemies =185; speed= -140f; spawnDelay =0.01f;break;
//		 case 20: num_of_enemies =200; speed= -140f; spawnDelay =0.01f;break;
//		 
		 
		 }
	 }

	
	/********************* SAVING DATA *************************************/
	
	public boolean saveHighScore(int highScore) {
        this.mScoreDbEditor.putInt(HIGHSCORE_LABEL, highScore );
        this.mScoreDbEditor.putString(HIGHSCORE_USERNAME, this.loadUserName());
        return this.mScoreDbEditor.commit();
	}

	public boolean saveMusicPreference(boolean setMusic){
		this.mScoreDbEditor.putBoolean(TOGGLE_MUSIC, setMusic);
		return this.mScoreDbEditor.commit();	
	}
	public boolean saveUserName(String placeName){
		this.mScoreDbEditor.putString(USERNAME, placeName);
		return this.mScoreDbEditor.commit();
	}
	
	public String loadUserName(){
		return this.mScoreDb.getString(USERNAME,null);
	}
	
	public String loadHighScoreUserName(){

		return this.mScoreDb.getString(HIGHSCORE_USERNAME,null);
	}
	public int loadHighScore() {
	        return this.mScoreDb.getInt(HIGHSCORE_LABEL, 0);
	}
	
	public boolean loadMusicPreference(){
		return this.mScoreDb.getBoolean(TOGGLE_MUSIC,true);	
	}

}

