package game;

import sage.app.BaseGame;
import sage.display.*;
import sage.camera.*;
import sage.input.*;
import sage.scene.shape.*;

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
	Cylinder player1;
	
	protected void initGame()
	{
		im = getInputManager();
		kbName = im.getKeyboardName();
		initGameObjects();
		initMovementControls();
	}

	private void initGameObjects()
	{
		display = getDisplaySystem();
		display.setTitle("The Game");
		camera = display.getRenderer().getCamera();
		camera.setPerspectiveFrustum(45, 1, 0.01, 1000);
		camera.setLocation(new Point3D(1,1,20));

		// Players will be cylinders
		player1 = new Cylinder(true);
		player1.setSlices(50);
		player1.rotate(90, new Vector3D(1,0,0));
		player1.scale(1,3,1);
		Matrix3D player1Mat = player1.getLocalTranslation();
		player1Mat.translate(0,2,-8);
		player1.setLocalTranslation(player1Mat);
		addGameWorldObject(player1);

		// Create camera controller
		camController = new Camera3Pcontroller(camera, player1, im, kbName);

		// Ground will be a rectangle
		Ground ground = new Ground();
		Matrix3D groundMat = ground.getLocalTranslation();
		groundMat.translate(0,-10,-8);
		ground.setLocalTranslation(groundMat);
		ground.scale(100,10,1);
		addGameWorldObject(ground);

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
		//im.associateAction(kbName, Identifier.Key.RIGHT, yawRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.LEFT, yawLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.UP, pitchUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.DOWN, pitchDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.E, rollRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.Q, rollLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(kbName, Identifier.Key.ESCAPE, quit, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

	}

	public void update(float elapsedTimeMS)
	{
		camController.update(elapsedTimeMS);
		super.update(elapsedTimeMS);
	}
}