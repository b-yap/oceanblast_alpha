/**
* BaseScene.java
* Feb 24, 2013
* 6:09:05 PM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

import android.softeng.project.oceanblast.ConstantsList;
import android.softeng.project.oceanblast.managers.ResourcesManager;



public abstract class BaseScene extends Scene {
	  //---------------------------------------------
    // VARIABLES
    //---------------------------------------------
    
    protected Engine engine;
    protected BaseGameActivity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vboManager;
    protected Camera camera;
    
    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------
    
    public BaseScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vboManager = resourcesManager.vboManager;
        this.camera = resourcesManager.camera;
        
        createScene();
    }
    
    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------
    
    public abstract void createScene();
    
    public abstract void onBackKeyPressed();
    
    public abstract ConstantsList.SceneType getSceneType();
    
    public abstract void disposeScene();
}

