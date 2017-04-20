package game.characters;

import sage.scene.shape.*;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;


public class Avatar extends Cylinder
{
	//private float SPEED = 0.01f;

	Point3D location;
	Vector3D direction;
	Matrix3D rotation;

	public Avatar()
	{
		super(true);
		this.location = new Point3D(0,2,0);
	}

	public Avatar(Point3D loc)
	{
		super(true);
		this.location = loc;
	}

	public void setLocation(Point3D loc)
	{
		this.location = loc;
	}

	public Point3D getLocation()
	{
		return this.location;
	}

	public void moveRight(float speed, float time)
	{
		Vector3D loc = new Vector3D(location);
		//rotation = this.getLocalRotation();
		direction = new Vector3D(1,0,0);
		//direction = direction.mult(rotation);
		direction.scale(-(speed * time));
		loc = loc.add(direction);
		location = new Point3D(loc);
		//this.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
		updateTranslation();
		//System.out.println("xLoc = "+location.getX());
	}

	public void moveLeft(float speed, float time)
	{
		Vector3D loc = new Vector3D(location);
		//rotation = this.getLocalRotation();
		direction = new Vector3D(1,0,0);
		//direction = direction.mult(rotation);
		direction.scale(speed * time);
		loc = loc.add(direction);
		location = new Point3D(loc);
		//this.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
		updateTranslation();
		//System.out.println("xLoc = "+location.getX());
	}
/*
	public void jump(float speed, float time)
	{
		Vector3D loc = new Vector3D(location);
		direction = new Vector3D(0,1,0);
		direction.scale(speed * time);
		loc = loc.add(direction);
		location = new Point3D(loc);
		updateTranslation();
	}
*/
	public void updateTranslation()
	{
		Matrix3D mat = new Matrix3D();
		mat.translate(location.getX(), location.getY(), location.getZ());
		setLocalTranslation(mat);
	}
}