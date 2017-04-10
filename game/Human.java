package game;

import sage.scene.TriMesh;
import sage.scene.shape.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import graphicslib3D.Point3D;

public class Human extends Cylinder
{
	Point3D position;
	float yVelocity;
	float yPosition;

	public Human()
	{
		super();
		position = new Point3D(0,0,0);
		yVelocity = 0;
		yPosition = 0;
	}

	Human(boolean solid)
	{
		super(solid);
		position = new Point3D(0,0,0);
		yVelocity = 0;
		yPosition = 0;
	}

	public void setLocation(Point3D pos)
	{
		this.position = pos;
	}

	public Point3D getLocation()
	{
		System.out.println("Getting player location");
		return position;
	}
}