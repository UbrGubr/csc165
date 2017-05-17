package game;

import game.characters.*;

import sage.input.action.*;
import sage.camera.*;
import sage.scene.SceneNode;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import net.java.games.input.Event;

public class MoveAction extends AbstractInputAction
{
	private ICamera camera;
	private SceneNode player;
	String moveDirection;
	private float MOVE_AMOUNT = 0.1f;
	private float ROTATE_AMOUNT = 0.2f;
	private float HEIGHT = 0.0f;
	private float GRAVITY = 0.5f;
	private float Y_VELOCITY = 0.0f;

	public MoveAction(SceneNode p, String d)
	{
		player = p;
		moveDirection = d;
	}

	public void performAction(float time, Event e)
	{
		Vector3D viewDir;
		Vector3D curLocVector;
		Vector3D newLocVector;
		Vector3D rightAxis;
		Vector3D upAxis;

		double newX;
		double newY;
		double newZ;

		Point3D newLoc;

		Matrix3D rotation;
		Vector3D direction;

		boolean move = false;
		boolean rotate = false;

		switch(moveDirection)
		{
			case "RIGHT":
				((Human)player).moveRight(time);
				break;
			case "LEFT":
				((Human)player).moveLeft(time);
				break;
			case "JUMP":
				((Human)player).jump(time);
				break;
		}
	}
}