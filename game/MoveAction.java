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
	//private float SPEED = 0.01f;
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
			/*
			case "FORWARD":
				rotation = player.getLocalRotation();
				direction = new Vector3D(0,0,1);
				direction = direction.mult(rotation);
				direction.scale((double)(SPEED * time));
				player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				break;
			case "BACKWARD":
				rotation = player.getLocalRotation();
				direction = new Vector3D(0,0,1);
				direction = direction.mult(rotation);
				direction.scale(-(double)(SPEED * time));
				player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				break;
			*/
			case "RIGHT":
				((Human)player).moveRight(time);
				//rotation = player.getLocalRotation();
				//direction = new Vector3D(1,0,0);
				//direction = direction.mult(rotation);
				//direction.scale(-(double)(SPEED * time));
				//player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				break;
			case "LEFT":
				((Human)player).moveLeft(time);
				//rotation = player.getLocalRotation();
				//direction = new Vector3D(1,0,0);
				//direction = direction.mult(rotation);
				//direction.scale((double)(SPEED * time));
				//player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				break;
			case "JUMP":
				((Human)player).jump(time);
				//direction = new Vector3D(0,1,0);
				//Y_VELOCITY += GRAVITY * time;
				//direction.scale((double)((2*SPEED) * time));
				//player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				/*
				((Human)player).yVelocity = -12.0f;
				System.out.println("TIME = " + time);
				*/
				break;
			/*
			case "YAW_RIGHT":
				player.rotate(1, new Vector3D(0,1,0));
				break;
			case "YAW_LEFT":
				player.rotate(-1, new Vector3D(0,1,0));
				break;
			case "PITCH_UP":
				viewDir = camera.getViewDirection();
				upAxis = camera.getUpAxis();
				rotation = new Matrix3D();
				rotation.rotate(ROTATE_AMOUNT, camera.getRightAxis());
				viewDir = viewDir.mult(rotation);
				upAxis = upAxis.mult(rotation);
				camera.setUpAxis(upAxis.normalize());
				camera.setViewDirection(viewDir.normalize());
				break;
			case "PITCH_DOWN":
				viewDir = camera.getViewDirection();
				upAxis = camera.getUpAxis();
				rotation = new Matrix3D();
				rotation.rotate(-ROTATE_AMOUNT, camera.getRightAxis());
				viewDir = viewDir.mult(rotation);
				upAxis = upAxis.mult(rotation);
				camera.setUpAxis(upAxis.normalize());
				camera.setViewDirection(viewDir.normalize());
				break;
			case "ROLL_RIGHT":
				viewDir = camera.getViewDirection();
				upAxis = camera.getUpAxis();
				rightAxis = camera.getRightAxis();
				rotation = new Matrix3D();
				rotation.rotate(ROTATE_AMOUNT, camera.getViewDirection());
				rightAxis = rightAxis.mult(rotation);
				upAxis = upAxis.mult(rotation);
				camera.setRightAxis(rightAxis.normalize());
				camera.setUpAxis(upAxis.normalize());
				break;
			case "ROLL_LEFT":
				viewDir = camera.getViewDirection();
				upAxis = camera.getUpAxis();
				rightAxis = camera.getRightAxis();
				rotation = new Matrix3D();
				rotation.rotate(-ROTATE_AMOUNT, camera.getViewDirection());
				rightAxis = rightAxis.mult(rotation);
				upAxis = upAxis.mult(rotation);
				camera.setRightAxis(rightAxis.normalize());
				camera.setUpAxis(upAxis.normalize());
				break;
			case "X_AXIS":
				if(e.getValue() < -0.2)
				{
					rotation = player.getLocalRotation();
					direction = new Vector3D(0,0,1);
					direction = direction.mult(rotation);
					direction.scale(-(double)(SPEED * time));
					player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				}
				else if (e.getValue() > 0.2)
				{
					rotation = player.getLocalRotation();
					direction = new Vector3D(0,0,1);
					direction = direction.mult(rotation);
					direction.scale((double)(SPEED * time));
					player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				}
				break;
			case "Y_AXIS":
				if(e.getValue() < -0.2)
				{
					rotation = player.getLocalRotation();
					direction = new Vector3D(1,0,0);
					direction = direction.mult(rotation);
					direction.scale((double)(SPEED * time));
					player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				}
				else if (e.getValue() > 0.2)
				{
					rotation = player.getLocalRotation();
					direction = new Vector3D(1,0,0);
					direction = direction.mult(rotation);
					direction.scale(-(double)(SPEED * time));
					player.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
				}
				break;
			case "RX_AXIS":
				if(e.getValue() < -0.2)
				{
					player.rotate(1, new Vector3D(0,1,0));
				}
				else if (e.getValue() > 0.2)
				{
					player.rotate(-1, new Vector3D(0,1,0));
				}
				break;
			case "RY_AXIS":
				if(e.getValue() < -0.2)
				{
					player.rotate(1, new Vector3D(0,1,0));
				}
				else if (e.getValue() > 0.2)
				{
					player.rotate(-1, new Vector3D(0,1,0));
				}
			*/
		}

/*
		Vector3D viewDir = camera.getViewDirection().normalize();
		Vector3D curLocVector = new Vector3D(camera.getLocation());
		Vector3D newLocVector = curLocVector.add(viewDir.mult(moveAmount));
		double newX = newLocVector.getX();
		double newY = newLocVector.getY();
		double newZ = newLocVector.getZ();
*/
		/*
		if(move)
		{
			Point3D newLoc = new Point3D(newX, newY, newZ);
			camera.setLocation(newLoc);
		}
		*/
		/*
		if(rotate)
		{
			viewDir = viewDir.mult(rotation);
			rightAxis = rightAxis.mult(rotation);
			camera.setRightAxis(rightAxis.normalize());
			camera.setViewDirection(viewDir.normalize());
		}
		*/
	}
}