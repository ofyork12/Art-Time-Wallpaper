package com.Color.My.Math;

//Art of Time Quantitative Clock by Edward Hughes

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSCounter;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.ui.livewallpaper.BaseLiveWallpaperService;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import android.content.res.AssetManager;
import android.hardware.SensorManager;
import android.view.MotionEvent;

public class Counting extends BaseLiveWallpaperService
		implements IAccelerationListener, IOnSceneTouchListener, IOnMenuItemClickListener, IOnAreaTouchListener {
	// ===========================================================
	// Constants extends BaseGameActivity
	// ===========================================================

	private static final int CAMERA_WIDTH = 360;
	private static final int CAMERA_HEIGHT = 640;

	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(.5f, 1f, 0f);
	// ===========================================================
	// Fields
	// ===========================================================
	private Scene mScene;
	private PhysicsWorld mPhysicsWorld;
	private Body body;

	private TextureRegion mCircle_1_min_TextureRegion1;
	private TextureRegion mCircle_2_min_TextureRegion2;
	private TextureRegion mCircle_3_min_TextureRegion3;
	private TextureRegion mCircle_4_min_TextureRegion4;
	private TextureRegion mCircle_5_min_TextureRegion5;
	private TextureRegion mCircle_6_min_TextureRegion6;
	private TextureRegion mCircle_7_min_TextureRegion7;
	private TextureRegion mCircle_8_min_TextureRegion8;
	private TextureRegion mCircle_9_min_TextureRegion9;

	private TextureRegion mTriangleFaceTextureRegion10;
	private TextureRegion mTriangleFaceTextureRegion20;
	private TextureRegion mTriangleFaceTextureRegion30;
	private TextureRegion mTriangleFaceTextureRegion40;
	private TextureRegion mTriangleFaceTextureRegion50;
	private TextureRegion mCircleTextureRegion_1_Hour;
	private TextureRegion mCircleTextureRegion_2_Hour;
	private TextureRegion mCircleTextureRegion_3_Hour;
	private TextureRegion mCircleTextureRegion_4_Hour;
	private TextureRegion mCircleTextureRegion_5_Hour;
	private TextureRegion mCircleTextureRegion_6_Hour;
	private TextureRegion mCircleTextureRegion_7_Hour;
	private TextureRegion mCircleTextureRegion_8_Hour;
	private TextureRegion mCircleTextureRegion_9_Hour;
	private TextureRegion mTriangleFaceTextureRegion_10_Hour;
	private TextureRegion mTriangleFaceTextureRegion_11_Hour;
	private TextureRegion mTriangleFaceTextureRegion_12_Hour;

	private Sprite[] nPC9s;
	private int[] npx9s;
	private int[] npy9s;
	private Sprite[] nPC10s;
	private int[] npx10s;
	private int[] npy10s;
	private Sprite[] nPC30sHour;
	private int[] npx30sHour;
	private int[] npy30sHour;

	private int mFaceMin = 1;
	private int mFaceHour = 1;
	private int min;
	private int hour;
	protected int mFaceChange;

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(),
				new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT));
	}

	@Override
	public org.andengine.engine.Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new org.andengine.engine.FixedStepEngine(pEngineOptions, 10);
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
		TextureManager tm = mEngine.getTextureManager();
		BitmapTextureAtlas texture1single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture2single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture3single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture4single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture5single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture6single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture7single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture8single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture9single = new BitmapTextureAtlas(tm, 32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		BitmapTextureAtlas texture10 = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture20 = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture30 = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture40 = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture50 = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		BitmapTextureAtlas texture_1_Hour = new BitmapTextureAtlas(tm, 64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_2_Hour = new BitmapTextureAtlas(tm, 128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_3_Hour = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_4_Hour = new BitmapTextureAtlas(tm, 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_5_Hour = new BitmapTextureAtlas(tm, 256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_6_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_7_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_8_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_9_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_10_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_11_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlas texture_12_Hour = new BitmapTextureAtlas(tm, 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		load_Single_Minutes_Coordinates();
		load_10_Minute_Triangle_Coordinates();
		load_Hour_Coordinates();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		AssetManager am = getAssets();

		this.mCircle_1_min_TextureRegion1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture1single, am,
				"a_red.png", 0, 0); // Box 64x32
		this.mCircle_2_min_TextureRegion2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture2single, am,
				"a_orange.png", 0, 0); //
		this.mCircle_3_min_TextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture3single, am,
				"a_yellow.png", 0, 0); //
		this.mCircle_4_min_TextureRegion4 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture4single, am,
				"a_green.png", 0, 0); //
		this.mCircle_5_min_TextureRegion5 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture5single, am,
				"a_blue.png", 0, 0); //
		this.mCircle_6_min_TextureRegion6 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture6single, am,
				"a_purple.png", 0, 0); //
		this.mCircle_7_min_TextureRegion7 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture7single, am,
				"a_brown.png", 0, 0); //
		this.mCircle_8_min_TextureRegion8 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture8single, am,
				"a_pink.png", 0, 0); //
		this.mCircle_9_min_TextureRegion9 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture9single, am,
				"a_skyblue.png", 0, 0); //

		this.mTriangleFaceTextureRegion10 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture10, am,
				"ten_red.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion20 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture20, am,
				"ten_orange.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion30 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture30, am,
				"ten_yellow.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion40 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture40, am,
				"ten_green.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion50 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture50, am,
				"ten_blue.png", 0, 0); // 64x32

		this.mCircleTextureRegion_1_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_1_Hour, am,
				"a_group_one.png", 0, 0); // Box 64x32
		this.mCircleTextureRegion_2_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_2_Hour, am,
				"a_group_two.png", 0, 0); //
		this.mCircleTextureRegion_3_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_3_Hour, am,
				"a_group_three.png", 0, 0); //
		this.mCircleTextureRegion_4_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_4_Hour, am,
				"a_group_four.png", 0, 0); //
		this.mCircleTextureRegion_5_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_5_Hour, am,
				"a_group_five.png", 0, 0); //
		this.mCircleTextureRegion_6_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_6_Hour, am,
				"a_group_six.png", 0, 0); //
		this.mCircleTextureRegion_7_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_7_Hour, am,
				"a_group_seven.png", 0, 0); //
		this.mCircleTextureRegion_8_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_8_Hour, am,
				"a_group_eight.png", 0, 0); //
		this.mCircleTextureRegion_9_Hour = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture_9_Hour, am,
				"a_group_nine.png", 0, 0); //
		this.mTriangleFaceTextureRegion_10_Hour = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(texture_10_Hour, am, "a_group_ten.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion_11_Hour = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(texture_11_Hour, am, "a_group_eleven.png", 0, 0); // 64x32
		this.mTriangleFaceTextureRegion_12_Hour = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(texture_12_Hour, am, "a_group_twelve.png", 0, 0); // 64x32

		this.mEngine.getTextureManager().loadTexture(texture1single);
		this.mEngine.getTextureManager().loadTexture(texture2single); // orange
		this.mEngine.getTextureManager().loadTexture(texture3single); // yellow
		this.mEngine.getTextureManager().loadTexture(texture4single); // green
		this.mEngine.getTextureManager().loadTexture(texture5single); // blue
		this.mEngine.getTextureManager().loadTexture(texture6single);
		this.mEngine.getTextureManager().loadTexture(texture7single);
		this.mEngine.getTextureManager().loadTexture(texture8single);
		this.mEngine.getTextureManager().loadTexture(texture9single);

		this.mEngine.getTextureManager().loadTexture(texture10);
		this.mEngine.getTextureManager().loadTexture(texture20);
		this.mEngine.getTextureManager().loadTexture(texture30);
		this.mEngine.getTextureManager().loadTexture(texture40);
		this.mEngine.getTextureManager().loadTexture(texture50);

		this.mEngine.getTextureManager().loadTexture(texture_1_Hour); // red
		this.mEngine.getTextureManager().loadTexture(texture_2_Hour); // orange
		this.mEngine.getTextureManager().loadTexture(texture_3_Hour); // yellow
		this.mEngine.getTextureManager().loadTexture(texture_4_Hour); // green
		this.mEngine.getTextureManager().loadTexture(texture_5_Hour); // blue
		this.mEngine.getTextureManager().loadTexture(texture_6_Hour);
		this.mEngine.getTextureManager().loadTexture(texture_7_Hour);
		this.mEngine.getTextureManager().loadTexture(texture_8_Hour);
		this.mEngine.getTextureManager().loadTexture(texture_9_Hour);
		this.mEngine.getTextureManager().loadTexture(texture_10_Hour); // 10Red
		this.mEngine.getTextureManager().loadTexture(texture_11_Hour);
		this.mEngine.getTextureManager().loadTexture(texture_12_Hour);

		this.enableAccelerationSensor(this);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {

		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.mScene = new Scene();
		createSpriteSpawnTimeHandler();

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.SENSOR_STATUS_ACCURACY_HIGH), false);
		VertexBufferObjectManager vbom = mEngine.getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0, CAMERA_HEIGHT, CAMERA_WIDTH, 1, vbom);
		final Rectangle roof = new Rectangle(0, 30, CAMERA_HEIGHT, 1, vbom);
		final Rectangle left = new Rectangle(-1, 0, 1, CAMERA_HEIGHT, vbom);
		final Rectangle right = new Rectangle(CAMERA_WIDTH, 0, 2, CAMERA_HEIGHT, vbom);
		final Rectangle square = new Rectangle(CAMERA_WIDTH / 2 - 59, CAMERA_HEIGHT / 2 - 72, 20, 20, vbom);
		final Rectangle square2 = new Rectangle(CAMERA_WIDTH / 2 + 40, CAMERA_HEIGHT / 2 - 72, 20, 20, vbom);

		ground.setColor(1, 0, 0, 1);
		roof.setColor(1, 1, 0, 1);
		right.setColor(0, 0, 1, 1);
		left.setColor(1, 0, 0, 1);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(1f, 1.f, 0f);

		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, square, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mPhysicsWorld, square2, BodyType.StaticBody, wallFixtureDef);

		// mScene.getBottomLayer().addEntity(ground);
		// mScene.getBottomLayer().addEntity(roof);
		// mScene.getBottomLayer().addEntity(left);
		// mScene.getBottomLayer().addEntity(right);
		// mScene.getBottomLayer().addEntity(square);
		// mScene.getBottomLayer().addEntity(square2);
		mScene.attachChild(ground);
		mScene.attachChild(roof);
		mScene.attachChild(left);
		mScene.attachChild(right);
		mScene.attachChild(square);
		mScene.attachChild(square2);

		mScene.registerUpdateHandler(this.mPhysicsWorld);

		mScene.setOnSceneTouchListener(this);

		final FPSCounter fpsCounter = new FPSCounter();
		this.mEngine.registerUpdateHandler(fpsCounter);

		mScene.registerUpdateHandler(new TimerHandler(1 / 20f, true, new ITimerCallback() {

			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
			}
		}));
		pOnCreateSceneCallback.onCreateSceneFinished(mScene);
	}

	public void onLoadComplete() {

	}

	@Override
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		if (this.mPhysicsWorld != null) {
			if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
				mScene.postRunnable(new Runnable() {
					@Override
					public void run() {
						resetTime();
						/* Now it is save to remove the entity! */

					}
				});
				return true;
			}
		}
		return false;
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) {
		// TODO Auto-generated method stub

		resetTime();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public boolean onSceneTouchEvent(final Scene pScene, final
	 * TouchEvent pSceneTouchEvent) {
	 * 
	 * if(this.mPhysicsWorld != null) { if(pSceneTouchEvent.getAction() ==
	 * MotionEvent.ACTION_DOWN) { //spriteTimerHandler.reset();
	 * 
	 * Debug.d("Before HourCount " + this.mFaceHour); Debug.d("Before minCount"
	 * + this.mFaceMin); Debug.d("Hour: " + now("hh"));
	 * 
	 * waiting(25); resetTime();
	 * 
	 * Debug.d("End HourCount " + this.mFaceHour); Debug.d("End minCount " +
	 * this.mFaceMin);
	 * 
	 * Debug.d("time: " + now("hh:mm"));
	 * 
	 * return true; } } return false; }
	 */

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX,
			float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		return false;
	} // ===========================================================

	@Override
	public void onAccelerationChanged(final AccelerationData pAccelerometerData) {
		this.mPhysicsWorld.setGravity(new Vector2(pAccelerometerData.getX() * -1, pAccelerometerData.getY()));
	}

	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		return new SimpleDateFormat(dateFormat, Locale.US).format(cal.getTime());
	}

	/*
	 * public static void waiting (int n){
	 * 
	 * long t0, t1;
	 * 
	 * t0 = System.currentTimeMillis();
	 * 
	 * do{ t1 = System.currentTimeMillis(); } while ((t1 - t0) < (n )); }
	 */
	private void resetTime() {

		hour = Integer.parseInt(now("hh"));
		mFaceHour = (int) hour;
		min = Integer.parseInt(now("mm"));
		mFaceMin = (int) min;

		for (int i = 0; i <= 45; i++) {
			try {
				clearEntities(nPC9s[i]);
			} catch (Exception e) {
			}
		}

		for (int i = 0; i <= 19; i++) {
			try {
				clearEntities(nPC10s[i]);
			} catch (Exception e) {
			}
		}

		for (int i = 0; i <= 2; i++) {
			try {
				clearEntities(nPC30sHour[i]);
			} catch (Exception e) {
			}
		}

		this.addFaceHour();
		this.addFaceMin();

	}

	private void createSpriteSpawnTimeHandler() {
		this.getEngine().registerUpdateHandler(new TimerHandler(1 / 2f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				min = Integer.parseInt(now("mm"));
				mFaceChange = (int) min;
				if (mFaceMin != mFaceChange) {

					resetTime();
				}

			}
		}));
	}

	private void addFaceHour() {
		/*
		 * Debug.d("Hour HourCount " + this.mFaceHour); Debug.d("Min minCount "
		 * + this.mFaceMin);
		 * 
		 * this.mTouchScreenCount++; Debug.d("ScreenTouches: " +
		 * this.mTouchScreenCount); Debug.d("Hour: " + this.mFaceHour);
		 */
		if (this.mFaceHour % 512 == 1) {

			add_1_Hour();

		} else if (this.mFaceHour % 512 == 2) {

			add_2_Hour();
		} else if (this.mFaceHour % 512 == 3) {

			add_3_Hour();
		} else if (this.mFaceHour % 512 == 4) {

			add_4_Hour();
		} else if (this.mFaceHour % 512 == 5) {

			add_5_Hour();
		} else if (this.mFaceHour % 512 == 6) {

			add_6_Hour();
		} else if (this.mFaceHour % 512 == 7) {

			add_7_Hour();
		} else if (this.mFaceHour % 512 == 8) {

			add_8_Hour();
		} else if (this.mFaceHour % 512 == 9) {

			add_9_Hour();
		} else if (this.mFaceHour % 512 == 10) {

			add_10_Hour();
		} else if (this.mFaceHour % 512 == 11) {

			add_11_Hour();
		} else if (this.mFaceHour % 512 == 12) {

			add_12_Hour();

		} else {
		}
	}

	private void addFaceMin() {
		mScene.setBackground(new Background(0, 0, 0));

		/*
		 * Debug.d("mFaceMin: " + this.mFaceMin); Debug.d("min: " + this.min);
		 * this.mTouchScreenCount++; Debug.d("ScreenTouches: " +
		 * this.mTouchScreenCount);
		 */
		if (this.mFaceMin % 512 == 1) {
			add_1_min();
		} else if (this.mFaceMin % 512 == 2) {
			add_2_min();
		} else if (this.mFaceMin % 512 == 3) {
			add_3_min();
		} else if (this.mFaceMin % 512 == 4) {
			add_4_min();
		} else if (this.mFaceMin % 512 == 5) {
			add_5_min();
		} else if (this.mFaceMin % 512 == 6) {
			add_6_min();
		} else if (this.mFaceMin % 512 == 7) {
			add_7_min();
		} else if (this.mFaceMin % 512 == 8) {
			add_8_min();
		} else if (this.mFaceMin % 512 == 9) {
			add_9_min();
		} else if (this.mFaceMin % 512 == 10) {
			add_10_min();
		} else if (this.mFaceMin % 512 == 11) {
			add_10_min();
			add_1_min();
		} else if (this.mFaceMin % 512 == 12) {
			add_10_min();
			add_2_min();
		} else if (this.mFaceMin % 512 == 13) {
			add_10_min();
			add_3_min();
		} else if (this.mFaceMin % 512 == 14) {
			add_10_min();
			add_4_min();
		} else if (this.mFaceMin % 512 == 15) {
			add_10_min();
			add_5_min();
		} else if (this.mFaceMin % 512 == 16) {
			add_10_min();
			add_6_min();
		} else if (this.mFaceMin % 512 == 17) {
			add_10_min();
			add_7_min();
		} else if (this.mFaceMin % 512 == 18) {
			add_10_min();
			add_8_min();
		} else if (this.mFaceMin % 512 == 19) {
			add_10_min();
			add_9_min();
		} else if (this.mFaceMin % 512 == 20) {
			add_20_min();
		} else if (this.mFaceMin % 512 == 21) {
			add_20_min();
			add_1_min();
		} else if (this.mFaceMin % 512 == 22) {
			add_20_min();
			add_2_min();
		} else if (this.mFaceMin % 512 == 23) {
			add_20_min();
			add_3_min();
		} else if (this.mFaceMin % 512 == 24) {
			add_20_min();
			add_4_min();
		} else if (this.mFaceMin % 512 == 25) {
			add_20_min();
			add_5_min();
		} else if (this.mFaceMin % 512 == 26) {
			add_20_min();
			add_6_min();
		} else if (this.mFaceMin % 512 == 27) {
			add_20_min();
			add_7_min();
		} else if (this.mFaceMin % 512 == 28) {
			add_20_min();
			add_8_min();
		} else if (this.mFaceMin % 512 == 29) {
			add_20_min();
			add_9_min();

		} else if (this.mFaceMin % 512 == 30) {
			add_30_min();
		} else if (this.mFaceMin % 512 == 31) {
			add_30_min();
			add_1_min();
		} else if (this.mFaceMin % 512 == 32) {
			add_30_min();
			add_2_min();
		} else if (this.mFaceMin % 512 == 33) {
			add_30_min();
			add_3_min();
		} else if (this.mFaceMin % 512 == 34) {
			add_30_min();
			add_4_min();
		} else if (this.mFaceMin % 512 == 35) {
			add_30_min();
			add_5_min();
		} else if (this.mFaceMin % 512 == 36) {
			add_30_min();
			add_6_min();
		} else if (this.mFaceMin % 512 == 37) {
			add_30_min();
			add_7_min();
		} else if (this.mFaceMin % 512 == 38) {
			add_30_min();
			add_8_min();
		} else if (this.mFaceMin % 512 == 39) {
			add_30_min();
			add_9_min();
		} else if (this.mFaceMin % 512 == 40) {
			add_40_min();
		} else if (this.mFaceMin % 512 == 41) {
			add_40_min();
			add_1_min();
		} else if (this.mFaceMin % 512 == 42) {
			add_40_min();
			add_2_min();
		} else if (this.mFaceMin % 512 == 43) {
			add_40_min();
			add_3_min();
		} else if (this.mFaceMin % 512 == 44) {
			add_40_min();
			add_4_min();
		} else if (this.mFaceMin % 512 == 45) {
			add_40_min();
			add_5_min();
		} else if (this.mFaceMin % 512 == 46) {
			add_40_min();
			add_6_min();
		} else if (this.mFaceMin % 512 == 47) {
			add_40_min();
			add_7_min();
		} else if (this.mFaceMin % 512 == 48) {
			add_40_min();
			add_8_min();
		} else if (this.mFaceMin % 512 == 49) {
			add_40_min();
			add_9_min();
		} else if (this.mFaceMin % 512 == 50) {
			add_50_min();
		} else if (this.mFaceMin % 512 == 51) {
			add_50_min();
			add_1_min();
		} else if (this.mFaceMin % 512 == 52) {
			add_50_min();
			add_2_min();
		} else if (this.mFaceMin % 512 == 53) {
			add_50_min();
			add_3_min();
		} else if (this.mFaceMin % 512 == 54) {
			add_50_min();
			add_4_min();
		} else if (this.mFaceMin % 512 == 55) {
			add_50_min();
			add_5_min();
		} else if (this.mFaceMin % 512 == 56) {
			add_50_min();
			add_6_min();
		} else if (this.mFaceMin % 512 == 57) {
			add_50_min();
			add_7_min();
		} else if (this.mFaceMin % 512 == 58) {
			add_50_min();
			add_8_min();
		} else if (this.mFaceMin % 512 == 59) {
			add_50_min();
			add_9_min();

		} else if (this.mFaceMin % 512 == 60) {
		} else {
		}

	}

	private void clearEntities(final Sprite face1r) {
		face1r.clearUpdateHandlers();
		// TODO: see if we fixed this above
		face1r.setIgnoreUpdate(true);
		final Scene mScene = this.mEngine.getScene();
		final PhysicsConnector facePhysicsConnector = this.mPhysicsWorld.getPhysicsConnectorManager()
				.findPhysicsConnectorByShape(face1r);

		// this.mPhysicsWorld.unregisterPhysicsConnector(new
		// PhysicsConnector(face1r, body, true, true, false, false));
		mEngine.getVertexBufferObjectManager().onUnloadVertexBufferObject(face1r.getVertexBufferObject());
		// TODO: does the above replace this?
		// BufferObjectManager.getActiveInstance().unloadBufferObject(face1r.getVertexBuffer());

		this.mPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
		this.mPhysicsWorld.destroyBody(facePhysicsConnector.getBody());

		mScene.unregisterTouchArea(face1r);
		// TODO Figure out if this
		// mScene.getTopLayer().removeEntity(face1r);
		// can be replace by this
		mScene.detachChild(face1r);

	}

	private void load_Single_Minutes_Coordinates() {
		nPC9s = new Sprite[27];

		npx9s = new int[27];
		npy9s = new int[27];

		npx9s[0] = (int) (CAMERA_WIDTH / 2 - 18f);
		npy9s[0] = (int) (CAMERA_HEIGHT - 164f);

		npx9s[1] = (int) (npx9s[0] - 33f);
		npy9s[1] = (int) (npy9s[0]);

		npx9s[2] = (int) (npx9s[0] + 33f);
		npy9s[2] = (int) (npy9s[0]); // end of highest row of nine

		npx9s[3] = (int) (npx9s[0]);
		npy9s[3] = (int) (npy9s[0] + 33f); // middle missing of eight

		npx9s[4] = (int) (npx9s[0] - 33f);
		npy9s[4] = (int) (npy9s[0] + 33f);

		npx9s[5] = (int) (npx9s[0] + 33f);// end of middle row of nine
		npy9s[5] = (int) (npy9s[0] + 33f);

		npx9s[6] = (int) (npx9s[0]);
		npy9s[6] = (int) (npy9s[0] + 66f);

		npx9s[7] = (int) (npx9s[0] - 33f);
		npy9s[7] = (int) (npy9s[0] + 66f);

		npx9s[8] = (int) (npx9s[0] + 33f);
		npy9s[8] = (int) (npy9s[0] + 66f); // end of lowest row of nine

		// beginning of eight
		npx9s[9] = (int) (npx9s[0]);
		npy9s[9] = (int) (npy9s[0] - 32f);

		npx9s[10] = (int) (npx9s[0]);
		npy9s[10] = (int) (npy9s[0]);

		npx9s[11] = (int) (npx9s[0] + 16f);
		npy9s[11] = (int) (npy9s[0] + 3f);

		npx9s[12] = (int) (npx9s[0] - 3f);
		npy9s[12] = (int) (npy9s[0] - 16f);

		npx9s[13] = (int) (npx9s[0] + 32f);
		npy9s[13] = (int) (npy9s[0]);

		npx9s[14] = (int) (npx9s[0] + 35f);
		npy9s[14] = (int) (npy9s[0] - 16f);

		npx9s[15] = (int) (npx9s[0] + 32f);
		npy9s[15] = (int) (npy9s[0] - 32f);

		npx9s[16] = (int) (npx9s[0] + 16);
		npy9s[16] = (int) (npy9s[0] - 35f);

		npx9s[17] = (int) (npx9s[0]);
		npy9s[17] = (int) (npy9s[0] - 32f);
		// end of eight

		// last right top five
		npx9s[18] = (int) (npx9s[0] + 24f);
		npy9s[18] = (int) (npy9s[0] - 16f);

		npx9s[19] = (int) (npx9s[0] + 16f);
		npy9s[19] = (int) (npy9s[0] - 30f);

		// beginning of seven
		// middle three
		npx9s[20] = (int) (npx9s[0]);
		npy9s[20] = (int) (npy9s[0] - 16f);

		npx9s[21] = (int) (npx9s[20] + 16f);
		npy9s[21] = (int) (npy9s[20]);

		npx9s[22] = (int) (npx9s[20] + 32f);
		npy9s[22] = (int) (npy9s[20]);
		// bottom two
		npx9s[23] = (int) (npx9s[20] + 8f);
		npy9s[23] = (int) (npy9s[20] + 16f); // three i = 0,1, 4

		npx9s[24] = (int) (npx9s[20] + 24f);
		npy9s[24] = (int) (npy9s[20] + 16f);
		// top two
		npx9s[25] = (int) (npx9s[20] + 8f);
		npy9s[25] = (int) (npy9s[20] - 16f); // three i = 0,1, 4

		npx9s[26] = (int) (npx9s[20] + 24f);
		npy9s[26] = (int) (npy9s[20] - 16f);
	}

	private void add_1_min() {
		for (int i = 0; i <= 0; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_1_min_TextureRegion1,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));

			mScene.attachChild(nPC9s[i]);
		}
	}

	private void add_2_min() {
		for (int i = 0; i <= 1; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_2_min_TextureRegion2,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));

			mScene.attachChild(nPC9s[i]);
		}
	}

	private void add_3_min() {
		for (int i = 0; i <= 0; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_3_min_TextureRegion3,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 3; i <= 4; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i] - 3f, this.mCircle_3_min_TextureRegion3,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));

			mScene.attachChild(nPC9s[i]);
		}
	}

	private void add_4_min() {
		for (int i = 0; i <= 1; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_4_min_TextureRegion4,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 3; i <= 4; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_4_min_TextureRegion4,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
	}

	private void add_5_min() {
		for (int i = 0; i <= 1; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_5_min_TextureRegion5,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 3; i <= 5; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i] - 5f, this.mCircle_5_min_TextureRegion5,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}

	}

	void add_6_min() {
		for (int i = 0; i <= 0; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_6_min_TextureRegion6,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 3; i <= 4; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_6_min_TextureRegion6,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 6; i <= 8; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_6_min_TextureRegion6,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
	}

	void add_7_min() {
		for (int i = 0; i <= 1; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_7_min_TextureRegion7,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 3; i <= 5; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_7_min_TextureRegion7,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
		for (int i = 6; i <= 7; i++) {
			nPC9s[i] = new Sprite(npx9s[i] + 16f, npy9s[i], this.mCircle_7_min_TextureRegion7,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
			mScene.attachChild(nPC9s[i]);
		}
	}

	void add_8_min() {
		for (int i = 0; i <= 2; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_8_min_TextureRegion8,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			nPC9s[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC9s[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
		}
		for (int i = 4; i <= 8; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_8_min_TextureRegion8,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			nPC9s[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC9s[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
		}
	}

	void add_9_min() {
		for (int i = 0; i <= 8; i++) {
			nPC9s[i] = new Sprite(npx9s[i], npy9s[i], this.mCircle_9_min_TextureRegion9,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC9s[i], BodyType.DynamicBody, FIXTURE_DEF);
			nPC9s[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC9s[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC9s[i], body, true, true));
		}

	}

	private void load_10_Minute_Triangle_Coordinates() {
		nPC10s = new Sprite[20];
		npx10s = new int[20];
		npy10s = new int[20];

		npx10s[0] = (int) (CAMERA_WIDTH / 2 - 58f);
		npy10s[0] = (int) (CAMERA_HEIGHT / 2 + 52f);// one i = 0

		npx10s[1] = (int) (npx10s[0] - 112f);
		npy10s[1] = (int) (npy10s[0]); // two i = 0,1

		npx10s[2] = (int) (npx10s[0] + 112f);
		npy10s[2] = (int) (npy10s[0]);

		npx10s[3] = (int) (npx10s[0] - 56f);// four i = 0,1,2,3
		npy10s[3] = (int) (npy10s[0] - 93f);

		npx10s[4] = (int) (npx10s[0] + 56f);
		npy10s[4] = (int) (npy10s[0] - 93f);

		npx10s[5] = (int) (npx10s[0]);// three i = 0,1,5
		npy10s[5] = (int) (npy10s[0]);

		npx10s[6] = (int) (npx10s[0] - 112f);// five i = 0,1,4,5,6
		npy10s[6] = (int) (npy10s[0]);

		npx10s[7] = (int) (npx10s[0] + 83f);// six i = 0,1, 4,5,6,7
		npy10s[7] = (int) (npy10s[0] - 140f);

		npx10s[8] = (int) (npx10s[0]);// top left of different five i = 0,1,
										// 5,7,8
		npy10s[8] = (int) (npy10s[0] - 140f);

	}

	private void add_10_min() {
		for (int i = 0; i <= 0; i++) {
			nPC10s[i] = new Sprite(npx10s[i], npy10s[i] - 32, this.mTriangleFaceTextureRegion10,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
	}

	void add_20_min() {
		for (int i = 3; i <= 4; i++) {
			nPC10s[i] = new Sprite(npx10s[i], npy10s[i] + 64, this.mTriangleFaceTextureRegion20,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
	}

	void add_30_min() {
		for (int i = 0; i <= 1; i++) {

			nPC10s[i] = new Sprite(npx10s[i] + 56f, npy10s[i], this.mTriangleFaceTextureRegion30,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
		for (int i = 3; i <= 3; i++) {

			nPC10s[i] = new Sprite(npx10s[i] + 56f, npy10s[i], this.mTriangleFaceTextureRegion30,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
	}

	void add_40_min() {

		for (int i = 0; i <= 1; i++) {
			nPC10s[i] = new Sprite(npx10s[i] + 56f, npy10s[i], this.mTriangleFaceTextureRegion40,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
		for (int i = 5; i <= 6; i++) {
			nPC10s[i] = new Sprite(npx10s[i] + 56f, npy10s[i] - 93f, this.mTriangleFaceTextureRegion40,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}
	}

	void add_50_min() {
		for (int i = 0; i <= 4; i++) {
			nPC10s[i] = new Sprite(npx10s[i], npy10s[i], this.mTriangleFaceTextureRegion50,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC10s[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC10s[i], body, true, true));
			mScene.attachChild(nPC10s[i]);
		}

	}

	private void load_Hour_Coordinates() {
		nPC30sHour = new Sprite[1];
		npx30sHour = new int[1];
		npy30sHour = new int[1];

		npx30sHour[0] = (int) (CAMERA_WIDTH / 4f + 4);
		npy30sHour[0] = (int) (64);
	}

	private void add_1_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] + 60, npy30sHour[i] + 64, this.mCircleTextureRegion_1_Hour,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody,
					FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_2_Hour() {
		for (int i = 0; i <= 0; i++) {

			nPC30sHour[i] = new Sprite(npx30sHour[i] + 24, npy30sHour[i] + 64, this.mCircleTextureRegion_2_Hour,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_3_Hour() {

		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] + 24, npy30sHour[i] + 42, this.mCircleTextureRegion_3_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_4_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] + 24, npy30sHour[i] + 16, this.mCircleTextureRegion_4_Hour,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_5_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i], npy30sHour[i] + 42, this.mCircleTextureRegion_5_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createHexagonBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	void add_6_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i], npy30sHour[i], this.mCircleTextureRegion_6_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	void add_7_Hour() {
		for (int i = 0; i <= 0; i++)
		// for (int i=13; i <= 19; i++)
		{
			nPC30sHour[i] = new Sprite(npx30sHour[i] + 4, npy30sHour[i], this.mCircleTextureRegion_7_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createHexagonBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			nPC30sHour[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC30sHour[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
		}
	}

	void add_8_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i], npy30sHour[i], this.mCircleTextureRegion_8_Hour,
					mEngine.getVertexBufferObjectManager());
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			nPC30sHour[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC30sHour[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
		}
	}

	void add_9_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i], npy30sHour[i], mCircleTextureRegion_9_Hour,
					mEngine.getVertexBufferObjectManager());// npx30sHour[i],
															// npy30sHour[i] ,
															// mCircle_9_min_TextureRegion
			body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			// nPC30sHour[i].animate(1000);
			nPC30sHour[i].setIgnoreUpdate(true);
			mScene.attachChild(nPC30sHour[i]);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
		}
	}

	private void add_10_Hour() {// add_4_Green_Dots_Hour();
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] - 16, npy30sHour[i] - 28, this.mTriangleFaceTextureRegion_10_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_11_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] - 16, npy30sHour[i] - 28, this.mTriangleFaceTextureRegion_11_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	private void add_12_Hour() {
		for (int i = 0; i <= 0; i++) {
			nPC30sHour[i] = new Sprite(npx30sHour[i] - 16, npy30sHour[i] - 28, this.mTriangleFaceTextureRegion_12_Hour,
					mEngine.getVertexBufferObjectManager());
			body = createTriangleBody(this.mPhysicsWorld, nPC30sHour[i], BodyType.DynamicBody, FIXTURE_DEF);
			this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(nPC30sHour[i], body, true, true));
			mScene.attachChild(nPC30sHour[i]);
		}
	}

	/**
	 * Creates a {@link Body} based on a {@link PolygonShape} in the form of a
	 * hexagon:
	 * 
	 * <pre>
	 *  /\
	 * /  \
	 * |  |
	 * |  |
	 * \  /
	 *  \/
	 * </pre>
	 */
	private static Body createHexagonBody(final PhysicsWorld pPhysicsWorld, final Shape pShape,
			final BodyType pBodyType, final FixtureDef pFixtureDef) {
		/*
		 * Remember that the vertices are relative to the center-coordinates of
		 * the Shape.
		 */
		final float halfWidth = pShape.getScaleX() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float halfHeight = pShape.getScaleY() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;

		/*
		 * The top and bottom vertex of the hexagon are on the bottom and top of
		 * hexagon-sprite.
		 */
		final float top = -halfHeight;
		final float bottom = halfHeight;

		final float centerX = 0;

		/*
		 * The left and right vertices of the heaxgon are not on the edge of the
		 * hexagon-sprite, so we need to inset them a little.
		 */
		final float left = -halfWidth + 2.5f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float right = halfWidth - 2.5f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float higher = top + 8.25f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float lower = bottom - 8.25f / PIXEL_TO_METER_RATIO_DEFAULT;

		final Vector2[] vertices = { new Vector2(centerX, top), new Vector2(right, higher), new Vector2(right, lower),
				new Vector2(centerX, bottom), new Vector2(left, lower), new Vector2(left, higher) };

		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pShape, vertices, pBodyType, pFixtureDef);
	}

	/**
	 * Creates a {@link Body} based on a {@link PolygonShape} in the form of a
	 * triangle:
	 * 
	 * <pre>
	 *  /\
	 * /__\
	 * </pre>
	 */
	private static Body createTriangleBody(final PhysicsWorld pPhysicsWorld, final Shape pShape,
			final BodyType pBodyType, final FixtureDef pFixtureDef) {
		/*
		 * Remember that the vertices are relative to the center-coordinates of
		 * the Shape.
		 */
		final float halfWidth = pShape.getScaleX() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
		final float halfHeight = pShape.getScaleY() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;

		final float top = -halfHeight;
		final float bottom = halfHeight;
		final float left = -halfHeight;
		final float centerX = 0;
		final float right = halfWidth;

		final Vector2[] vertices = { new Vector2(centerX, top), new Vector2(right, bottom), new Vector2(left, bottom) };

		return PhysicsFactory.createPolygonBody(pPhysicsWorld, pShape, vertices, pBodyType, pFixtureDef);
	}
}
