/**
* LoadingScene.java
* Mar 3, 2013
* 10:39:07 AM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.softeng.project.oceanblast.ConstantsList.SceneType;



public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		createBackground();
	}

	@Override
	public void onBackKeyPressed() {
		return ;
		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground(){
	   Sprite background = new Sprite(0,0,resourcesManager.loading_bgroundRegion,vboManager);
		this.attachChild(background);
		
	}

}
