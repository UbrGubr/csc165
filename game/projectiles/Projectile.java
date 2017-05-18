package game.projectiles;

import game.*;

import sage.scene.shape.Sphere;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import java.awt.Color;

public class Projectile extends Sphere
{
	private float X_VELOCITY = 0.02f;
	private Direction direction;
	private Point3D location;

	public Projectile (Point3D loc, Direction d)
	{
		super(.5,6,16,Color.YELLOW);
		this.location = loc;
		this.direction = d;
		translate((float)loc.getX(), (float)loc.getY(), (float)loc.getZ());
	}

	public float getVelocity()
	{
		return X_VELOCITY;
	}

	public Direction getDirection()
	{
		return direction;
	}

	public void travel(double dist)
	{
		Vector3D loc = new Vector3D(location);
		Vector3D dir = new Vector3D(1,0,0);
		dir = dir.mult(dist);
		loc = loc.add(dir);	
		location = new Point3D(loc);
		updateTranslation();
	}

	public void updateTranslation()
	{
		Matrix3D mat = new Matrix3D();
		mat.translate(location.getX(), location.getY(), location.getZ());
		setLocalTranslation(mat);
	}
}