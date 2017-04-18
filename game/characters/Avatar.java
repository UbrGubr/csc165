package game.characters;

import sage.scene.shape.*;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;


public class Avatar extends Cylinder
{
	private float SPEED = 0.01f;

	Point3D location;
	Vector3D direction;
	Matrix3D rotation;

	public Avatar()
	{
		super(true);
		this.location = new Point3D(0,0,0);
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

	public void moveRight(double amt)
	{
		rotation = this.getLocalRotation();
		direction = new Vector3D(1,0,0);
		direction = direction.mult(rotation);
		direction.scale(amt);
		this.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
	}

	public void moveLeft(double amt)
	{
		rotation = this.getLocalRotation();
		direction = new Vector3D(1,0,0);
		direction = direction.mult(rotation);
		direction.scale(amt);
		this.translate((float)direction.getX(),(float)direction.getY(),(float)direction.getZ());
	}

	public void updateTranslation()
	{
		Matrix3D mat = new Matrix3D();
		mat.translate(location.getX(), location.getY(), location.getZ());
		setLocalTranslation(mat);
	}
}