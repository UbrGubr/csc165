package game;

import game.characters.*;
import game.camera.*;

import sage.app.BaseGame;
import sage.display.*;
import sage.camera.*;
import sage.input.*;
import sage.scene.*;
import sage.scene.shape.*;
import sage.scene.state.*;
import sage.terrain.*;
import sage.texture.*;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import java.awt.Color;

import net.java.games.input.Component.*;

public class TheGame extends BaseGame
{

	IDisplaySystem display;
	ICamera camera;
	Camera3Pcontroller camController;
	IInputManager im;
	String kbName;
	Group rootNode;
	SkyBox skybox;
	Human player1;

	Matrix3D rotation;
	Vector3D direction = new Vector3D(0,1,0);

	private float HEIGHT = 0.0f;
	private float SPEED = 0.01f;
	private float GRAVITY = 0.5f;
	
	protected void initGame()
	{
		im = getInputManager();
		kbName = im.getKeyboardName();
		initDisplay();
		initTerrain();
		//initTerrainHeightMap();
		initGameObjects();
		initMovementControls();
	}

	private void initDisplay()
	{
		display = getDisplaySystem();
		display.setTitle("The Game");
	}

	private void initTerrain()
	{

		//rootNode = new Group("Root Node");
		skybox = new SkyBox("SkyBox", 24.0f, 15.0f, 50.0f);
		Texture background = TextureManager.loadTexture2D("./textures/skybox/nightsky_ft.jpg");
		skybox.setTexture(SkyBox.Face.North, background);
		//skybox.translate(0,-20,0);
		//rootNode.addChild(skybox);
		addGameWorldObject(skybox);

		Rectangle[] terrain = new Rectangle[5];
		for(int i=0; i<5; i++)
		{
			terrain[i] = createGroundPanel(i);
			addGameWorldObject(terrain[i]);
		}

		/*
		Rectangle underGround = new Rectangle();
		Texture underGroundTex = TextureManager.loadTexture2D("stone.jpg");
		TextureState underGroundTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		underGroundTexState.setTexture(underGroundTex);
		underGroundTexState.setEnabled(true);
		underGround.setRenderState(underGroundTexState);
		Matrix3D underGroundMat = underGround.getLocalTranslation();
		underGroundMat.translate(0,-8,-6);
		underGround.setLocalTranslation(underGroundMat);
		underGround.scale(100,10,1);
		addGameWorldObject(underGround);
		*/
	}

	private void initTerrainHeightMap()
	{
		skybox = new SkyBox("SkyBox", 20.0f, 20.0f, 20.0f);
		Texture background = TextureManager.loadTexture2D("./textures/background.png");
		skybox.setTexture(SkyBox.Face.North, background);
		//rootNode.addChild(skybox);
		addGameWorldObject(skybox);

		TerrainBlock[] terrain = new TerrainBlock[3];
		ImageBasedHeightMap myHeightMap = new ImageBasedHeightMap("./textures/height.jpg");
		for(int i=0; i<3; i++)
		{
			terrain[i] = createTerBlock(myHeightMap, i+1);
		}

		// create texture state to color terrain 
		TextureState groundState;
		Texture groundTexture = TextureManager.loadTexture2D("./textures/sand.bmp");
		groundTexture.setApplyMode(Texture.ApplyMode.Replace);
		groundState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture); // TODO - condense this?
		groundState.setTexture(groundTexture, 0);
		groundState.setEnabled(true);

		// apply texture to the terrain
		for(int i=0; i<3; i++)
		{
			terrain[i].setRenderState(groundState);
			addGameWorldObject(terrain[i]);
		}
	}

	private void initGameObjects()
	{
		camera = display.getRenderer().getCamera();
		camera.setPerspectiveFrustum(45, 1, 0.01, 1000);
		camera.setLocation(new Point3D(0,0,0));

		// Players will be cylinders
		player1 = new Human();
		player1.setSlices(50);
		player1.rotate(90, new Vector3D(1,0,0));
		player1.scale(1,3,1);
		Matrix3D player1Mat = player1.getLocalTranslation();
		player1Mat.translate(0,2,0);
		player1.setLocalTranslation(player1Mat);
		addGameWorldObject(player1);

		// Create camera controller
		camController = new Camera3Pcontroller(camera, player1, im, kbName);

		// Ground will be a rectangle
		/*
		Ground ground = new Ground();
		Matrix3D groundMat = ground.getLocalTranslation();
		groundMat.translate(0,-10,-8);
		ground.setLocalTranslation(groundMat);
		ground.scale(100,10,1);
		addGameWorldObject(ground);
		*/


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

	private void initMovementControls()
	{
		// Keyboard actions
		//MoveAction mvForward = new MoveAction(player1, "FORWARD");
		//MoveAction mvBackward = new MoveAction(player1, "BACKWARD");
		MoveAction mvLeft = new MoveAction(player1, "LEFT");
		MoveAction mvRight = new MoveAction(player1, "RIGHT");
		MoveAction jump = new MoveAction(player1, "JUMP");
		//MoveAction yawRight = new MoveAction(player1, "YAW_RIGHT");
		//MoveAction yawLeft = new MoveAction(player1, "YAW_LEFT");
		//MoveAction pitchUp = new MoveAction(player1, "PITCH_UP");
		//MoveAction pitchDown = new MoveAction(player1, "PITCH_DOWN");
		//MoveAction rollRight = new MoveAction(player1, "ROLL_RIGHT");
		//MoveAction rollLeft = new MoveAction(player1, "ROLL_LEFT");

		// Keyboard key bindings
		//im.associateAction(kbName, Identifier.Key.W, mvForward, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.S, mvBackward, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, Identifier.Key.A, mvLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, Identifier.Key.D, mvRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(kbName, Identifier.Key.LALT, jump, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.RIGHT, yawRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.LEFT, yawLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.UP, pitchUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.DOWN, pitchDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.E, rollRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.Q, rollLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.ESCAPE, quit, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

	}

	protected void update(float elapsedTimeMS)
	{
		Point3D camLoc = camera.getLocation();
		//System.out.println("camera.X=" + camLoc.getX() + " camera.Y=" + camLoc.getY() + " camera.Z=" + camLoc.getZ());1
		Matrix3D camTranslation = new Matrix3D();
		camTranslation.translate(camLoc.getX(), camLoc.getY(), camLoc.getZ());
		skybox.setLocalTranslation(camTranslation);

		camController.update(elapsedTimeMS);
		super.update(elapsedTimeMS);
	}

	private Rectangle createGroundPanel(int panelNum)
	{
		Rectangle ground = new Rectangle();
		ground.scale(40,40,1);
		Texture groundTex = TextureManager.loadTexture2D("./textures/seamless_brick_dark.png");
		TextureState groundTexState = (TextureState) display.getRenderer().createRenderState(RenderState.RenderStateType.Texture);
		groundTexState.setTexture(groundTex);
		groundTexState.setEnabled(true);
		ground.setRenderState(groundTexState);
		Matrix3D groundMat = ground.getLocalTranslation();
		groundMat.translate((-40*panelNum),0,0);
		ground.setLocalTranslation(groundMat);
		ground.rotate(90,new Vector3D(1,0,0));

		return ground;
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

	public Human getPlayer()
	{
		return player1;
	}
}