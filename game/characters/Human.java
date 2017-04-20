package game.characters;

import sage.scene.TriMesh;
import sage.scene.shape.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

public class Human extends Avatar
{
	private float X_VELOCITY = 0.01f;
	private float Y_VELOCITY = 0;
	private float GRAVITY = 0.5f;

	public Human()
	{
		super();
	}

	public Human(Point3D loc)
	{
		super(loc);
	}

	public void moveRight(float time)
	{
		super.moveRight(X_VELOCITY, time);
	}

	public void moveLeft(float time)
	{
		super.moveLeft(X_VELOCITY, time);
	}

	public void jump(float time)
	{
		Y_VELOCITY = 1.0f;
	}

	public void update(float time)
	{
		/*
		Vector3D loc = new Vector3D(getLocation());
		direction = new Vector3D(0,1,0);
		direction.scale(Y_VELOCITY * time);
		loc = loc.add(direction);
		setLocation(new Point3D(loc));
		updateTranslation();
		if(getLocation().getY() > 2)
				Y_VELOCITY -= GRAVITY * time;
		else
			Y_VELOCITY = 0;

		System.out.println("Y_VELOCITY = " + Y_VELOCITY);
		*/
	}
}