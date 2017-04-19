package game.characters;

import sage.scene.TriMesh;
import sage.scene.shape.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import graphicslib3D.Point3D;

public class Human extends Avatar
{
	private float X_SPEED = 0.01f;
	private float Y_SPEED = 0.01f;

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
		super.moveRight(X_SPEED, time);
	}

	public void moveLeft(float time)
	{
		super.moveLeft(X_SPEED, time);
	}

	public void jump(float time)
	{
		super.jump(Y_SPEED, time);
	}
}