package game;

import game.characters.*;
import game.camera.*;
import game.ai.*;
import game.projectiles.*;

import sage.app.BaseGame;
import sage.display.*;
import sage.camera.*;
import sage.input.*;
import sage.scene.*;
import sage.scene.shape.*;
import sage.scene.state.*;
import sage.terrain.*;
import sage.texture.*;
import sage.model.loader.OBJLoader;
import sage.model.loader.ogreXML.OgreXMLParser;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import sage.audio.*;
import com.jogamp.openal.ALFactory;

import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.awt.Color;

import net.java.games.input.Component.*;

public class TheGame extends BaseGame
{

	IDisplaySystem display;
	ICamera camera;
	Camera3Pcontroller camController;
	IInputManager im;
	String kbName, gpName;
	Group rootNode, manModel, golemModel;
	SkyBox skybox;
	Avatar player1;
	Monster golems[];
	
	//HUD Variables
	HUDImage life1, life2, life3;
	HUDImage gameOver;
	HUDString health = new HUDString("HEALTH ");
	int scoreValue = 0;
	HUDString score = new HUDString("SCORE " + scoreValue);

	GolemController golemControllers[];
	ProjectileController projectileController;
	
	IAudioManager audioMgr;
	Sound testSound, testSound2;

	int endOfWorld = 20;

	TriMesh gateCenterL, gateCenterR;
	Point3D gateCenterLLoc, gateCenterRLoc;

	private float HEIGHT = 0.0f;
	private float SPEED = 0.01f;
	private float GRAVITY = 0.5f;
	private int NUM_ENEMIES = 5;

	private Group projectiles;
	
	protected void initGame()
	{
		im = getInputManager();
		gpName = im.getFirstGamepadName();
		kbName = im.getKeyboardName();
		initDisplay();
		initSkyBox();
		initTerrain();
		initGameObjects();
		initPlayers();
		initMovementControls();
		initAudio();
		initHUD();
	}

	private void initDisplay()
	{
		display = getDisplaySystem();
		display.setTitle("The Game");
	}
	
	private void initHUD()
	{
		
		// HUD stuff
		
		// Set life bars
		life1 = new HUDImage("./life.png");
		life1.setLocation(-0.9,0.8);
		life1.scale(0.1f,0.1f,0.1f);
		addGameWorldObject(life1);
		
		life2 = new HUDImage("./life.png");
		life2.setLocation(-0.8,0.8);
		life2.scale(0.1f,0.1f,0.1f);
		addGameWorldObject(life2);
		
		life3 = new HUDImage("./life.png");
		life3.setLocation(-0.7,0.8);
		life3.scale(0.1f,0.1f,0.1f);
		addGameWorldObject(life3);
		
		gameOver = new HUDImage("./gameOver.png");
		gameOver.rotateImage(180f);
		
		// Set 'HEALTH' Lable
		health.setLocation(0.03, 0.94);
		health.scale(1.5f,1.5f,1.5f);
		addGameWorldObject(health);
		
		// Set 'SCORE' Lable
		score.setLocation(0.9, 0.94);
		score.scale(1.5f,1.5f,1.5f);
		addGameWorldObject(score);
		
		
		
	}

	private void initSkyBox()
	{
		skybox = new SkyBox("SkyBox", 24.0f, 15.0f, 50.0f);
		Texture background = TextureManager.loadTexture2D("./textures/skybox/nightsky_ft.jpg");
		skybox.setTexture(SkyBox.Face.North, background);
		addGameWorldObject(skybox);
	}

	private void initTerrain()
	{
		for(int i=0; i<5; i++)
		{	
			Rectangle ground = createGroundPanel();
			Matrix3D groundMat = ground.getLocalTranslation();
			groundMat.translate(endOfWorld-20,0,0);
			ground.setLocalTranslation(groundMat);
			addGameWorldObject(ground);
			endOfWorld -= 40;

			Rectangle hazard = createHazardPanel();
			Matrix3D hazardMat = hazard.getLocalTranslation();
			hazardMat.translate(endOfWorld,0,0);
			hazard.setLocalTranslation(hazardMat);
			addGameWorldObject(hazard);
			endOfWorld -= 5;
		}

		System.out.println("End of world = " + endOfWorld);
	}

	private void initGameObjects()
	{
		golems = new Monster[NUM_ENEMIES];
		golemControllers = new GolemController[NUM_ENEMIES];

		for(int i=0; i<NUM_ENEMIES; i++)
		{
			Random rand = new Random();
			//Get a random spawn location
			int spawn_loc = rand.nextInt(Math.abs(endOfWorld)) + 20;
			System.out.println("Spawn location = " + -spawn_loc);
			golems[i] = new Monster(new Point3D(-spawn_loc,2,0), new Vector3D(1,0,0), 90);
			golems[i].rotate(180, new Vector3D(0,0,1));
			applyTexture(golems[i], "golem.png");
			addGameWorldObject(golems[i]);
			golemControllers[i] = new GolemController(this, golems[i]);
		}


		// Add Group which we will use to store projectiles
		projectiles = new Group();
		addGameWorldObject(projectiles);
		projectileController = new ProjectileController();

		OBJLoader loader = new OBJLoader();	

		// Gate center - left side
		gateCenterL = loader.loadModel("./models/gate_wood.obj");
		Texture woodTex = TextureManager.loadTexture2D("./textures/light_wood.png");
		TextureState woodTexState = (TextureState)display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		woodTexState.setTexture(woodTex);
		woodTexState.setEnabled(true);
		gateCenterL.setRenderState(woodTexState);
		gateCenterL.rotate(90,new Vector3D(0,1,0));
		gateCenterL.scale(1,.75f,1);
		gateCenterL.translate(10,10,0);
		addGameWorldObject(gateCenterL);
		gateCenterLLoc = new Point3D(10,10,0);


		// Gate center - right side
		gateCenterR = loader.loadModel("./models/gate_wood.obj");
		gateCenterR.setRenderState(woodTexState);
		gateCenterR.rotate(90,new Vector3D(0,1,0));
		gateCenterR.scale(1,.75f,1);
		gateCenterR.translate(endOfWorld+10,10,0);
		addGameWorldObject(gateCenterR);
		gateCenterRLoc = new Point3D(endOfWorld+10,10,0);


		// Gate pillars - left side
		TriMesh gateFrameL = loader.loadModel("./models/gate_stone.obj");
		Texture stoneTex = TextureManager.loadTexture2D("./textures/stone.jpg");
		TextureState stoneTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		stoneTexState.setTexture(stoneTex);
		stoneTexState.setEnabled(true);
		gateFrameL.setRenderState(stoneTexState);
		gateFrameL.rotate(90,new Vector3D(0,1,0));
		gateFrameL.scale(1,.75f,1);
		gateFrameL.translate(10,0,0);
		addGameWorldObject(gateFrameL);

		// Gate pillars - right side
		TriMesh gateFrameR = loader.loadModel("./models/gate_stone.obj");
		gateFrameR.setRenderState(stoneTexState);
		gateFrameR.rotate(90,new Vector3D(0,1,0));
		gateFrameR.scale(1,.75f,1);
		gateFrameR.translate(endOfWorld+10,0,0);
		addGameWorldObject(gateFrameR);


		Texture steelTex = TextureManager.loadTexture2D("./textures/gray.png");
		TextureState steelTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		steelTexState.setTexture(steelTex);
		steelTexState.setEnabled(true);


		// Create the background gate
		int wallLoc = 10;
		while(wallLoc > endOfWorld)
		{
			// Background wall
			TriMesh wallStone = loader.loadModel("./models/wall_stone.obj");
			wallStone.setRenderState(stoneTexState);
			wallStone.rotate(90, new Vector3D(0,1,0));
			wallStone.translate(wallLoc,0,40);
			addGameWorldObject(wallStone);

			// Background steel gate
			TriMesh gateSteel = loader.loadModel("./models/gate_steel.obj");
			gateSteel.setRenderState(steelTexState);
			gateSteel.rotate(90, new Vector3D(0,1,0));
			gateSteel.translate(wallLoc,2,40);
			addGameWorldObject(gateSteel);

			wallLoc -= 15;
		}


		// Axial lines
		Point3D origin = new Point3D(0,0,0);
		Point3D xEnd = new Point3D(100,0,0);
		Point3D yEnd = new Point3D(0,100,0);
		Point3D zEnd = new Point3D(0,0,100);

		Line xAxis = new Line(origin, xEnd, Color.red, 2);
		Line yAxis = new Line(origin, yEnd, Color.green, 2);
		Line zAxis = new Line(origin, zEnd, Color.blue, 2);

		addGameWorldObject(xAxis);
		addGameWorldObject(yAxis);
		addGameWorldObject(zAxis);
	}

	private void initPlayers()
	{
		OBJLoader loader = new OBJLoader();

		player1 = new Human(new Point3D(0,2,0), new Vector3D(0,1,0), 90);
		applyTexture(player1, "./textures/man.png");
		//player1 = new Human();
		
		//player1.rotate(90, new Vector3D(0,1,0));
		player1.scale(2,2,2);
		//Matrix3D player1Mat = player1.getLocalTranslation();
		//player1Mat.translate(0,2,0);
		//player1.setLocalTranslation(player1Mat);
		addGameWorldObject(player1);

/*
		golemModel = getMonsterAvatar();
		golemModel.translate(0,4,0);

		Iterator<SceneNode> itr = golemModel.getChildren();
		while(itr.hasNext())
		{
			Model3DTriMesh mesh = ((Model3DTriMesh)itr.next());
			mesh.startAnimation("Walk");
		}
*/
/*
		Group model = ((Human)player1).getModel();

		Iterator<SceneNode> itr = model.getChildren();
		while (itr.hasNext())
		{ 
			Model3DTriMesh mesh = ((Model3DTriMesh)itr.next());
			//mesh.startAnimation("Legs_walk");
		}
*/
		//player1.startAnimation("Legs_walk");
		//player1.startAnimation("Arms_walk");


		// Create camera controller
		camera = display.getRenderer().getCamera();
		camera.setPerspectiveFrustum(45, 1, 0.01, 1000);
		camera.setLocation(new Point3D(0,0,0));

		camController = new Camera3Pcontroller(camera, player1, im, kbName);
	}

	private void initMovementControls()
	{
		// Keyboard actions
		MoveAction mvLeft = new MoveAction(player1, "LEFT");
		MoveAction mvRight = new MoveAction(player1, "RIGHT");
		MoveAction jump = new MoveAction(player1, "JUMP");
		AttackAction fire = new AttackAction((Human)player1, this);

		// Keyboard key bindings
		im.associateAction(kbName, Identifier.Key.A, mvLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, Identifier.Key.D, mvRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, Identifier.Key.SPACE, fire, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
		im.associateAction(kbName, Identifier.Key.LALT, jump, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		// Gamepad bindings
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Button._4, mvLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Button._5, mvRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(gpName, net.java.games.input.Component.Identifier.Button._0, fire, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	}

	protected void update(float elapsedTimeMS)
	{
		Point3D camLoc = camera.getLocation();
		Matrix3D camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);
		camController.update(elapsedTimeMS);

		for(int i=0; i<NUM_ENEMIES; i++)
		{
			golemControllers[i].update(elapsedTimeMS);
			golems[i].updateAnimation(elapsedTimeMS);
			golems[i].update(elapsedTimeMS);
		}

		testSound.setLocation(new Point3D(golems[1].getWorldTranslation().getCol(3)));
		
		setEarParameters();
		
		redrawHealth();
		
		int h = ((Human)player1).getHealth();
		
		// If health is 0, then game is over
		if(h<=0)
		{	
			// Release audio
			testSound.release(audioMgr);
			testSound2.release(audioMgr);
			resource1.unload();
			resource2.unload();
			audioMgr.shutdown();
			
			// Set 'GAME OVER' Lable
			gameOver.setLocation(0.0,0.3);
			gameOver.scale(0.8f,0.8f,0.8f);
			addGameWorldObject(gameOver);	
		}

		// Close the gate over a period of about 5 seconds
		if(gateCenterLLoc.getY() > 0)
		{
			Vector3D loc = new Vector3D(gateCenterLLoc);
			Vector3D dir = new Vector3D(0,1,0);
			dir.scale(-0.097);
			loc = loc.add(dir);
			gateCenterLLoc = new Point3D(loc);
			Matrix3D mat = new Matrix3D();
			mat.translate(gateCenterLLoc.getX(), gateCenterLLoc.getY(), gateCenterLLoc.getZ());
			gateCenterL.setLocalTranslation(mat);
		}
		else
		{
			checkHitDetection();
		}

		if(gateCenterRLoc.getY() > 0)
		{
			Vector3D loc = new Vector3D(gateCenterRLoc);
			Vector3D dir = new Vector3D(0,1,0);
			dir.scale(-0.07);
			loc = loc.add(dir);
			gateCenterRLoc = new Point3D(loc);
			Matrix3D mat = new Matrix3D();
			mat.translate(gateCenterRLoc.getX(), gateCenterRLoc.getY(), gateCenterRLoc.getZ());
			gateCenterR.setLocalTranslation(mat);
		}

		super.update(elapsedTimeMS);
	}

	private Rectangle createGroundPanel()
	{
		Rectangle ground = new Rectangle();
		ground.scale(40,80,1);
		Texture groundTex = TextureManager.loadTexture2D("./textures/seamless_brick_dark.png");
		TextureState groundTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		groundTexState.setTexture(groundTex);
		groundTexState.setEnabled(true);
		ground.setRenderState(groundTexState);
		ground.rotate(90,new Vector3D(1,0,0));

		return ground;
	}

	private Rectangle createHazardPanel()
	{
		Rectangle hazard = new Rectangle();
		hazard.scale(10,80,1);
		Texture hazardTex = TextureManager.loadTexture2D("./textures/lava.png");
		TextureState hazardTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		hazardTexState.setTexture(hazardTex);
		hazardTexState.setEnabled(true);
		hazard.setRenderState(hazardTexState);
		hazard.rotate(90,new Vector3D(1,0,0));

		return hazard;
	}

	private void applyTexture(Avatar c, String file)
	{
		Texture texture = TextureManager.loadTexture2D(file);
		TextureState textureState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		textureState.setTexture(texture);
		textureState.setEnabled(true);
		c.setRenderState(textureState);
		c.updateRenderStates();
	}

	private Group getPlayerAvatar()
	{
		Group model = null;
		OgreXMLParser loader = new OgreXMLParser();

		try
		{
			String slash = File.separator;
			model = loader.loadModel("models" + slash + "Cube.001.mesh.xml",
			"models" + slash + "man_skin.material",
			"models" + slash + "Cube.001.skeleton.xml");
			model.updateGeometricState(0, true);
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
			System.exit(1);
		}
		
		return model;
	}

	private Group getMonsterAvatar()
	{
		Group model = null;
		OgreXMLParser loader = new OgreXMLParser();
		loader.setVerbose(true);

		try
		{
			String slash = File.separator;
			model = loader.loadModel("models" + slash + "golem.mesh.xml",
			"materials" + slash + "golem_mat.material",
			"models" + slash + "golem.skeleton.xml");
			model.updateGeometricState(0, true);
		}
		catch (Exception e)
		{ 
			e.printStackTrace();
			System.exit(1);
		}
		
		return model;
	}

	public boolean checkNearbyMonsters(Monster golem)
	{
			if(Math.abs(player1.getLocation().getX() - golem.getLocation().getX()) <= 10)
				return true;
			else
				return false;
	}

	private TerrainBlock createTerBlock(AbstractHeightMap heightMap, int blockNum)
	{
		float heightScale = 0.005f;
		Vector3D terrainScale = new Vector3D(.1, heightScale, .1);

		// use the size of the height map as size of terrain
		int terrainSize = heightMap.getSize();

		//specify terrain origin so heightmap (0,0) is at world origin
		float cornerHeight = heightMap.getTrueHeightAtPoint(0,0) * heightScale;
		Point3D terrainOrigin = new Point3D((blockNum*(-80)), -cornerHeight, 0);

		// create terrain block using the height map
		String name = "Terrain:" + heightMap.getClass().getSimpleName();
		TerrainBlock tb = new TerrainBlock(name, terrainSize, terrainScale, heightMap.getHeightData(), terrainOrigin);
		return tb;
	}
	
	public void initAudio(){
		
		AudioResource resource1, resource2;
		
		audioMgr = AudioManagerFactory.createAudioManager("sage.audio.joal.JOALAudioManager");
		
		if(!audioMgr.initialize())
		{ System.out.println("Audio Manager failed to initialize!");
			return;
		}
		
		resource1 = audioMgr.createAudioResource("roar.wav", AudioResourceType.AUDIO_SAMPLE);
		resource2 = audioMgr.createAudioResource("order.wav", AudioResourceType.AUDIO_SAMPLE);
		
		testSound =  new Sound(resource1, SoundType.SOUND_EFFECT, 3, true);
		testSound.initialize(audioMgr);
		testSound.setMaxDistance(50.0f);
		testSound.setMinDistance(3.0f);
		testSound.setRollOff(5.0f);
		testSound.setLocation(new Point3D(golems[1].getWorldTranslation().getCol(3)));
		
		testSound2 =  new Sound(resource2, SoundType.SOUND_MUSIC, 1, true);
		testSound2.initialize(audioMgr);
		testSound2.setMaxDistance(50.0f);
		testSound2.setMinDistance(3.0f);
		testSound2.setRollOff(5.0f);
		
		
		setEarParameters();
		
		
		testSound.play();
		testSound2.play();
		
	}
	
	public void setEarParameters(){
		
		Matrix3D avDir = (Matrix3D) (player1.getWorldRotation().clone());
		//float camAz = camController.getAzimuth();
		avDir.rotateY(180.0f);
		Vector3D camDir = new Vector3D(0,0,1);
		camDir = camDir.mult(avDir);
		
		audioMgr.getEar().setLocation(camera.getLocation());
		audioMgr.getEar().setOrientation(camDir, new Vector3D(0,1,0));
		
	}
	
	public Avatar getPlayer()
	{
		return player1;
	}

	public void createProjectile(Projectile p)
	{
		projectiles.addChild(p);
		p.addController(projectileController);
		projectileController.addControlledNode(p);
	}
	
	public void redrawHealth()
	{
		int h = ((Human)player1).getHealth();
		
		if (h==3)
		{
			life1.setLocation(-0.9,0.8);
			life2.setLocation(-0.8,0.8);
			life3.setLocation(-0.7,0.8);
		}else if (h==2){
			life1.setLocation(-0.9,0.8);
			life2.setLocation(-0.8,0.8);
			life3.setLocation(-0.7,1.5);
		}else if (h==1){
			life1.setLocation(-0.9,0.8);
			life2.setLocation(-0.8,1.5);
			life3.setLocation(-0.7,1.5);
		}else{
			life1.setLocation(-0.9,1.5);
			life2.setLocation(-0.8,1.5);
			life3.setLocation(-0.7,1.5);
		}
		
	}

	public void checkHitDetection()
	{
		/*ArrayList<SceneNode> deleteList = new ArrayList<SceneNode>();
		Iterator<SceneNode> itr = projectiles.getChildren();

		while(itr.hasNext())
		{
			SceneNode p = itr.next();
			if(p instanceof Projectile)
			{
				p.updateWorldBound();

			}
		}*/
		
		Iterator<SceneNode>	itemList = projectiles.getChildren();
		//Iterator<SceneNode>	monList = monsters.getChildren();
		
		player1.updateWorldBound();

		for(int i=0; i<NUM_ENEMIES; i++)
		{
			golems[i].updateWorldBound();
		}

		while(itemList.hasNext())
		{
			SceneNode item = itemList.next();
			if(item instanceof Projectile)
			{
			  //Point3D p1Point = new Point3D(golem.getWorldTranslation().getCol(3));
			  //System.out.println(p1Point.getX() + " " + p1Point.getY() + " " + p1Point.getZ());
			  
				item.updateWorldBound();
				  
				// Check to see if projectile collided with a golem
				for(int i=0; i<NUM_ENEMIES; i++)
				{
				  	if((item.getWorldBound().intersects(golems[i].getWorldBound())) && golems[i].isAlive())
				  	{
						itemList.remove();
					  	((Monster)golems[i]).setHealth(((Monster)golems[i]).getHealth()-1);
					  	if(((Monster) golems[i]).getHealth() == 0)
					  	{
					  		golems[i].setAlive(false);
					  		golems[i].setLocation(new Point3D(0,-20,0));	
					  		scoreValue++;
					  	}
				  	}
				}

			}
		}// end of while 

		for(int i=0; i<NUM_ENEMIES; i++)
		{
			if(player1.getWorldBound().intersects(golems[i].getWorldBound()))
			{
				System.out.println("hit you!");
				((Human)player1).setHealth(((Human)player1).getHealth()-1);
			}
		}
	}
	
}