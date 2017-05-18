package game.characters;

import game.projectiles.*;

import sage.scene.*;
import sage.scene.shape.*;
import sage.model.loader.OBJLoader;
import sage.model.loader.ogreXML.OgreXMLParser;

import com.jogamp.common.nio.Buffers;

import java.io.File;
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
	private float FIRE_RATE = 500.0f;
	private int HEALTH = 3;

	private Model3DTriMesh human;

	public Human()
	{
		super(); 

		OBJLoader loader = new OBJLoader();
		TriMesh human = loader.loadModel("./models/man.obj");
		addModel(human);
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

	public Projectile fire()
	{
		Point3D loc = getLocation();
		Projectile bullet = new Projectile(loc, getFacingDir());
		return bullet;
	}

	public float getFireRate()
	{
		return FIRE_RATE;
	}
	
	public void setHealth(int i)
	{
		HEALTH = i;
	}
	
	public int getHealth()
	{
		return HEALTH;
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