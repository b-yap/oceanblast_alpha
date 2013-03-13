/**
* ResourceManager.java
* Feb 24, 2013
* 5:49:46 PM
* 
* @author B. Carla Yap
* @email bcarlayap@ymail.com
**/


package android.softeng.project.oceanblast.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.graphics.Color;
import android.graphics.Typeface;
import android.softeng.project.oceanblast.scenes.AnalogControls;


public class ResourcesManager {
	 
	 /******************** MAINACTIVITY VARIABLES ********************/
	
	private static final ResourcesManager INSTANCE = new ResourcesManager();
		public Engine engine;
		public BaseGameActivity activity;
		public Camera camera;
		public VertexBufferObjectManager vboManager;
	
	
	 /******************** SPLASH SCENE RESOURCES ********************/

		public ITextureRegion 		splashRegion;
		private BitmapTextureAtlas 	splashAtlas; 
		private BitmapTextureAtlas 	splashBubble_Atlas;
		public TiledTextureRegion 	splash_bubbleRegion;
			

	/******************** MENUSCENE RESOURCES ************************/
		 
		//background
		private BitmapTextureAtlas 	menu_bgroundAtlas;
		public TextureRegion 		menu_bgroundRegion;
		
		//menu buttons
		private BitmapTextureAtlas 	menu_btnAtlas;
		public TextureRegion 		menu_btnPlayRegion;
		public TextureRegion 		menu_btnPlayPushedRegion;
		public TextureRegion 		menu_btnOnRegion;
		public TextureRegion 		menu_btnOffRegion;
		public TextureRegion 		menu_btnOptionRegion;
		public TextureRegion		menu_btnOptionPushedRegion;
		public TiledTextureRegion	menu_btnChangeUserRegion;
		
		//music
		public Music menu_music;
		
		
	/******************** HIGHSCORESCENE RESOURCES ************************/

		//background
		private BitmapTextureAtlas 	score_bgroundAtlas;
		public TextureRegion 		score_bgroundRegion;
		
		
	/******************** GAMESCENE RESOURCES ************************/
		
		public AnalogControls game_analogControl;
		
		//parallax background
		private BitmapTextureAtlas game_autoParallaxAtlas;
		public ITextureRegion game_parallaxLayerBackRegion;

		private BitmapTextureAtlas game_autoParallax1Atlas;
		public ITextureRegion game_parallaxLayerBack1Region;

		private BitmapTextureAtlas game_autoParallax2Atlas;
		public ITextureRegion game_parallaxLayerBack2Region;
		
		public ITextureRegion game_parallaxLayerFrontRegion;
		public ITextureRegion game_parallaxLayerFront1Region;
		public ITextureRegion game_parallaxLayerFront2Region;
		

		//objects
		private BitmapTextureAtlas game_objectAtlas;
		public TextureRegion game_missile1Region;
		public TextureRegion game_missile2Region;
		private BuildableBitmapTextureAtlas game_enemyAtlas;
		public TiledTextureRegion game_enemyGoldfishRegion;
		public TiledTextureRegion game_playerRegion;
		private BuildableBitmapTextureAtlas game_enemyAtlas2;
		public TiledTextureRegion game_enemyAnglerfishRegion;	
		private BuildableBitmapTextureAtlas game_enemyAtlas3;
		public TiledTextureRegion game_enemyMutatefishRegion;	
		private BuildableBitmapTextureAtlas game_enemyAtlas4;
		public TiledTextureRegion game_enemyOctopusRegion;	
		private BuildableBitmapTextureAtlas game_enemyAtlas5;
		public TiledTextureRegion game_enemyfRegion;	

		private BuildableBitmapTextureAtlas game_enemyAtlas6;
		public TiledTextureRegion game_enemySharkRegion;
		private BuildableBitmapTextureAtlas game_enemyAtlas7;
		public TiledTextureRegion game_enemyEelRegion;
		private BuildableBitmapTextureAtlas game_enemyAtlas8;
		public TiledTextureRegion game_enemyJellyRegion;
		
		public TiledTextureRegion game_enemyStingRegion;
		private BitmapTextureAtlas game_pauseAtlas;
		public  TextureRegion game_btnPauseRegion;
		
		//music
		public Music game_shootSound;
		
							
	/******************** LOADINGSCENE RESOURCES **********************/
		
		//loading
		public Font font;
		public Font fontBig;
		private BitmapTextureAtlas 	loading_bgroundAtlas;
		public TextureRegion 		loading_bgroundRegion;
		
	/******************** PAUSECENE RESOURCES **********************/
		
		private BitmapTextureAtlas pause_pausedAtlas;
		public TextureRegion pause_btnResumeRegion;
		public TextureRegion pause_btnMenuRegion;
		public TextureRegion pause_btnHolderRegion;
		
		
	/******************** LOGIC AREA **********************************/
		
	/* FONT AREA */ 
			
	private void loadFontResources(){
		FontFactory.setAssetBasePath("font/");
	    final ITexture mainFontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256*2, 256*2, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	    font = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "CHUBBY.TTF", 30, true, Color.WHITE, 2, Color.BLUE);
	    font.load();
	    
	    fontBig = FontFactory.createStrokeFromAsset(activity.getFontManager(), mainFontTexture, activity.getAssets(), "BASKVILL.TTF", 40, true, Color.WHITE, 2, Color.BLUE);
	    fontBig.load();
	}
	
	/* PAUSESCENE AREA */ 
	
	public void loadPauseResources(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		pause_pausedAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 506, 600, TextureOptions.DEFAULT);
		pause_btnResumeRegion = BitmapTextureAtlasTextureRegionFactory		
			    .createFromAsset(pause_pausedAtlas, activity, "resume_button.png",0, 0);
		pause_btnMenuRegion = BitmapTextureAtlasTextureRegionFactory		
			    .createFromAsset(pause_pausedAtlas, activity, "menu_button.png",0,60);
		pause_btnHolderRegion = BitmapTextureAtlasTextureRegionFactory		
			    .createFromAsset(pause_pausedAtlas, activity, "message_box.png",0,120);
		pause_pausedAtlas.load();
	
	}
	
	/* MENUSCENE AREA */ 
	
	public void loadMenuResources(){
		loadMenuGraphics();	
		loadMenuAudio();
		loadFontResources();
	 }
	  
	 private void loadMenuGraphics(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
			
			//background
			menu_bgroundAtlas 				= new BitmapTextureAtlas(activity.getTextureManager(),1000,1000);
				menu_bgroundRegion 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_bgroundAtlas, activity, "menu_screen.png",0,0);
			menu_bgroundAtlas.load();		
			
			//menu buttons
			menu_btnAtlas					= new BitmapTextureAtlas(activity.getTextureManager(),1000,1000);
			
			menu_btnPlayRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"play_bubble.png",0,0);
			menu_btnPlayPushedRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"play_OnP.png",0,96);
			menu_btnOptionRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"high_score.png",0,246);
			menu_btnOptionPushedRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"highscore_OnP.png",0,354);
			menu_btnOnRegion 		= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"soundOn_.png",0,504);
			menu_btnOffRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_btnAtlas, activity,"soundOff_.png",0,598);
			
			menu_btnChangeUserRegion= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menu_btnAtlas, activity,"alertbox.png",0,848,2,1);
			
			menu_btnAtlas.load();
		
	 }
	 
	 private void loadMenuAudio(){
		//add music
			MusicFactory.setAssetBasePath("musix/");
			try	{
					this.menu_music 		= MusicFactory.createMusicFromAsset(activity.getMusicManager(),activity, "mainMenuMusic.mid");
					this.menu_music.setVolume(1f);
					this.menu_music.setLooping(true);
				}
			catch (IOException e)
				{
					e.printStackTrace();
				}		
	 }
	 
	 public void unloadMenuScene(){
	     menu_bgroundAtlas.unload();
	     menu_btnAtlas.unload();
	 }
	     
	 public void loadMenuScene(){
		 menu_bgroundAtlas.load();
	     menu_btnAtlas.load();
	 }
	 
	
	 /* LOADINGSCENE AREA */
	 public void loadLoadingSceneResources(){
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	
			
			//background
			loading_bgroundAtlas 				= new BitmapTextureAtlas(activity.getTextureManager(),1000,1000);
			loading_bgroundRegion 			= BitmapTextureAtlasTextureRegionFactory.createFromAsset(loading_bgroundAtlas, activity, "loading.png",0,0);
			loading_bgroundAtlas.load();		

	 }
	 
	 /* GAMESCENE AREA */
	 
	 public void loadGameResources(){
		 game_analogControl = new AnalogControls();
		 loadGameGraphics();
		 loadGameAudio();
	 }
	 
	 private void loadGameGraphics(){
			
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			  
		 	//parallax background
			game_autoParallaxAtlas 				= new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024);
				game_parallaxLayerFrontRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallaxAtlas,
						activity, "parallax_background_layer_front.png", 0, 0);
				game_parallaxLayerBackRegion 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallaxAtlas,
						activity, "background.png", 0, 188);
			
			game_autoParallaxAtlas.load();	
		
			game_autoParallax1Atlas 				= new BitmapTextureAtlas(activity.getTextureManager(), 1600, 1024);
			game_parallaxLayerFront1Region 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallax1Atlas,
					activity, "cave_details_1.png", 0, 0);
			game_parallaxLayerBack1Region 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallax1Atlas,
					activity, "cave_background_1.png", 0, 490);
		
			game_autoParallax1Atlas.load();	
		
			game_autoParallax2Atlas 				= new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024);
			game_parallaxLayerFront2Region 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallax2Atlas,
					activity, "cave_details_2.png", 0, 0);
			game_parallaxLayerBack2Region 	= BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_autoParallax2Atlas,
					activity, "cave_background_2.png", 0, 490);
		
			game_autoParallax1Atlas.load();	
		
			
			//objects	
			game_objectAtlas 		= new BitmapTextureAtlas(activity.getTextureManager(),40,58);
				game_missile1Region 	=BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.game_objectAtlas,
						activity,"missile.png",0,0);
				game_missile2Region 	=BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.game_objectAtlas,
						activity,"missile2.png",0,26);
				
			game_objectAtlas.load();
			
			//analog controls
			game_analogControl.loadAnalogControlResources();	
				
			//buttons
			game_pauseAtlas = new BitmapTextureAtlas(activity.getTextureManager(),32,32);
				game_btnPauseRegion =BitmapTextureAtlasTextureRegionFactory.createFromAsset(game_pauseAtlas,
						activity, "pause.png", 0, 0);
			game_pauseAtlas.load();
					
			//animated sprite: enemy
			game_enemyAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 506, 210, TextureOptions.NEAREST);
			 																				
			game_enemyGoldfishRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas, 
									 activity, "goldfish_tiled.png",2,1);
			game_playerRegion 	= BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.game_enemyAtlas,
					activity,"submarine_animated.png",2,1);
			
				
				//place here all animated sprites
			game_enemyAtlas2 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.NEAREST);
			game_enemyAnglerfishRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas2, 
									 activity, "f_anglerfish.png",3,1);
			
			game_enemyAtlas3 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.NEAREST);
			game_enemyMutatefishRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas3, 
					 activity, "f_angler2.png",2,1);

			game_enemyAtlas4 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.NEAREST);
			game_enemyOctopusRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas4, 
					 activity, "octopus_.png",2,1);
					
			game_enemyAtlas5 = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 2048, 2048, TextureOptions.NEAREST);
			
			game_enemySharkRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas5, 
					 activity, "shark_animated.png",2,1);
			
			game_enemyAtlas6 = new BuildableBitmapTextureAtlas(activity.getTextureManager(),  2048, 2048, TextureOptions.NEAREST);
			
			game_enemyEelRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas6, 
					 activity, "f_eel.png",2,1);
					 
			game_enemyAtlas7 = new BuildableBitmapTextureAtlas(activity.getTextureManager(),  2048, 2048, TextureOptions.NEAREST);
			

			game_enemyStingRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas7, 
					 activity, "sting.png",2,1);

					 
			game_enemyAtlas8 = new BuildableBitmapTextureAtlas(activity.getTextureManager(),  2048, 2048, TextureOptions.NEAREST);
			
			game_enemyJellyRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(game_enemyAtlas8, 
					 activity, "jelly.png",2,1);
			
			try {
					
					game_enemyAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas.load();
					
					game_enemyAtlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas2.load();
					
					game_enemyAtlas3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas3.load();
					
					game_enemyAtlas4.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas4.load();
					game_enemyAtlas5.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas5.load();
					game_enemyAtlas6.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas6.load();
					game_enemyAtlas7.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas7.load();
					game_enemyAtlas8.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
					game_enemyAtlas8.load();
					
			} catch (TextureAtlasBuilderException e) {
					Debug.e(e);
				}
	 
	 }
	 
	 private void loadGameAudio(){
			//add music
			MusicFactory.setAssetBasePath("musix/");
			try	{
					this.game_shootSound	= MusicFactory.createMusicFromAsset(activity.getMusicManager(),activity, "one_shot.mp3");
					this.game_shootSound.setVolume(1f);
				}
			catch (IOException e)
				{
					e.printStackTrace();
				}		
		 
	 }
	 
	 public void unloadGameScene()
	 {
		 game_autoParallaxAtlas.unload();
		 game_autoParallax1Atlas.unload();
		 game_autoParallax2Atlas.unload();
		 

		 game_parallaxLayerFrontRegion = null;
		 game_parallaxLayerFront1Region = null; 
		 game_parallaxLayerFront2Region = null;
		 game_parallaxLayerBack2Region = null;
		 game_parallaxLayerBackRegion = null;
		 game_parallaxLayerBack1Region = null;
			
		 game_objectAtlas.unload();
		 game_pauseAtlas.unload();
		game_enemyAtlas2.unload();
		game_enemyAtlas3.unload();
		game_enemyAtlas4.unload();
		game_enemyAtlas5.unload();
		game_enemyAtlas6.unload();
		game_enemyAtlas7.unload();
		
		 game_missile1Region = null;
		 game_missile2Region = null;
		 game_enemyGoldfishRegion = null;
		 game_enemyAnglerfishRegion=null;
	    	game_enemyMutatefishRegion=null;
	    	game_enemyOctopusRegion=null;
	    	game_enemyEelRegion=null;
	    	game_enemyJellyRegion=null;
	    	game_enemySharkRegion=null;
	    	game_enemyStingRegion=null;
		
	 }
	 
	 /* SPLASH SCENE AREA */
	 
	 public void loadSplashScene(){
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	        splashAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480, TextureOptions.DEFAULT);
	        splashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashAtlas, activity, "meerusa_splashscreen.png", 0, 0);
	        splashAtlas.load();
	        

	        splashBubble_Atlas = new BitmapTextureAtlas(activity.getTextureManager(), 32, 32, TextureOptions.DEFAULT);
	        splash_bubbleRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(splashBubble_Atlas,activity,"bubbles.png",0,0,1,1);
	        splashBubble_Atlas.load();
	 
	 }
	 
	 public void unloadSplashScene(){
		 splashAtlas.unload();
		 splashRegion =null;
	 }
	 
	 
	 
	 /* HIGHSCORESCENE AREA */
	 public void loadHighScoreResources(){
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	        score_bgroundAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 510, 300, TextureOptions.DEFAULT);
	        score_bgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(score_bgroundAtlas, activity, "small_highscore.png", 0, 0);
	        score_bgroundAtlas.load();
	 }

	 public static void prepareManager(Engine engine, BaseGameActivity activity, Camera camera,
			 VertexBufferObjectManager vbom){
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vboManager =vbom;
	 }
	 
	 public static ResourcesManager getInstance(){
		 return INSTANCE;
	 }

}
