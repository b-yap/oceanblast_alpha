/**
 * AnalogControls.java
 * Feb 24, 2013
 * 10:06:30 AM
 *
 * @author B. Carla Yap 
 * email: bcarlayap@ymail.com
 *
 */

package android.softeng.project.oceanblast.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.opengl.GLES20;
import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.managers.ResourcesManager;

public class AnalogControls {
	
	//controls
	private BitmapTextureAtlas onScreenControlAtlas;
	private ITextureRegion onScreenControlBaseRegion;
	private ITextureRegion onScreenControlKnobRegion;
	private VertexBufferObjectManager vboManager;
	private PhysicsHandler mPhysicsHandler;
	private AnalogOnScreenControl analogOnScreenControl;
	private Sprite userPlayer;
	
	
	private Camera mCamera;
	private BaseGameActivity mActivity;
	
	
	public AnalogControls(){
	this.mActivity=ResourcesManager.getInstance().activity;
	this.mCamera=ResourcesManager.getInstance().camera;
	
	this.vboManager = ResourcesManager.getInstance().vboManager;
	}
	
		public void setPlayerPhysicsHandler(Sprite player,PhysicsHandler physicsHandler){
		this.mPhysicsHandler = physicsHandler;
		this.userPlayer =player;
	}
	
	public void loadAnalogControlResources(){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//control images
				onScreenControlAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
					onScreenControlBaseRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(onScreenControlAtlas, 
												mActivity, "onscreen_control_base.png", 0, 0);
					onScreenControlKnobRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(onScreenControlAtlas, 
												mActivity, "onscreen_control_knob.png", 128, 0);
				onScreenControlAtlas.load();
				
	}
	
	public void setAnalogControl(){
		
		this.analogOnScreenControl = new AnalogOnScreenControl(15, ConstantsList.CAMERA_HEIGHT -  
				this.onScreenControlBaseRegion.getHeight()-15, this.mCamera, 
				this.onScreenControlBaseRegion, this.onScreenControlKnobRegion, 
				0.1f, 200, this.vboManager, new IAnalogOnScreenControlListener() {

					public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,float pValueX, float pValueY) {
						mPhysicsHandler.setVelocity(pValueX * 250, pValueY * 250);
						
						// setting boundaries
						int zeroA = 0;
						int zeroB = (int) userPlayer.getHeight();
						float velX = mPhysicsHandler.getVelocityX();
						float velY = mPhysicsHandler.getVelocityY();
        
						if (userPlayer.getX() < zeroA) {
                
							if (userPlayer.getY() < zeroA) {
								if (velX < 0)
									mPhysicsHandler.setVelocityX(0.0f);
								if (velY < 0)
									mPhysicsHandler.setVelocityY(0.0f);
							} 
							else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
								if (velX < 0)
									mPhysicsHandler.setVelocityX(0.0f);
								if (velY > 0)
									mPhysicsHandler.setVelocityY(0.0f);
							} else {
								if (velX < 0)
									mPhysicsHandler.setVelocityX(0.0f);
							}
							
						} else if (userPlayer.getX() > ConstantsList.CAMERA_WIDTH - zeroB) {
							
							if (userPlayer.getY() < zeroA) {
								if (velX > 0)
									mPhysicsHandler.setVelocityX(0.0f);
								if (velY < 0)
									mPhysicsHandler.setVelocityY(0.0f);

							} else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
								if (velX > 0)
									mPhysicsHandler.setVelocityX(0.0f);
								if (velY > 0)
									mPhysicsHandler.setVelocityY(0.0f);

							} else {
                                if (velX > 0)
                                mPhysicsHandler.setVelocityX(0.0f);
							  }
						
						} else {
								if (userPlayer.getY() < zeroA) {
                      
									if (velY < 0)
										mPhysicsHandler.setVelocityY(0.0f);

								} else if (userPlayer.getY() > ConstantsList.CAMERA_HEIGHT - zeroB) {
                                     if (velY > 0)
                                    	 mPhysicsHandler.setVelocityY(0.0f);

								} 
						}		
				} // end of onControlChange

					public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
						userPlayer.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.25f, 1, 1.5f), new ScaleModifier(0.25f, 1.5f, 1)));
						
					}});
					
		analogOnScreenControl.getControlBase().setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setAlpha(0.5f);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
		analogOnScreenControl.getControlBase().setScale(1.25f);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		analogOnScreenControl.refreshControlKnobPosition();		
		
	}
	public AnalogOnScreenControl getAnalogControl(){
		return this.analogOnScreenControl;
	}

}

