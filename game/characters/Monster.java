package game.characters;

import sage.scene.*;
import sage.scene.shape.*;
import sage.model.loader.OBJLoader;
import sage.model.loader.ogreXML.OgreXMLParser;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import sage.event.*;


public class Monster extends Avatar implements IEventListener
{
	private float X_VELOCITY = 0.005f;
	private float Y_VELOCITY = 0;
	private float GRAVITY = 0.5f;
	private int HEALTH = 4;

	private Group model;
	private Model3DTriMesh myModel;

	private boolean moving;
	private boolean isAlive = true;
	public boolean facingLeft;

	public Monster()
	{
		super();

		/*
		OBJLoader loader = new OBJLoader();
		TriMesh monster = loader.loadModel("./models/golem.obj");
		addModel(monster);
		*/

		OgreXMLParser loader = new OgreXMLParser();
		try
		{
			model = loader.loadModel("models/golem.mesh.xml", "materials/golem_mat.material", "models/golem.skeleton.xml");
			model.updateGeometricState(0, true);
			Iterator<SceneNode> itr = model.iterator();
			myModel = (Model3DTriMesh) itr.next();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		addModel(myModel);
	}

	public Monster(Point3D loc)
	{
		super(loc);

		OgreXMLParser loader = new OgreXMLParser();
		try
		{
			model = loader.loadModel("models/golem.mesh.xml", "materials/golem_mat.material", "models/golem.skeleton.xml");
			model.updateGeometricState(0, true);
			Iterator<SceneNode> itr = model.iterator();
			myModel = (Model3DTriMesh) itr.next();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		addModel(myModel);
	}

	public Monster(Point3D loc, Vector3D axis, float rot)
	{
		super(loc, rot, axis);

		OgreXMLParser loader = new OgreXMLParser();
		try
		{
			model = loader.loadModel("models/golem.mesh.xml", "materials/golem_mat.material", "models/golem.skeleton.xml");
			model.updateGeometricState(0, true);
			Iterator<SceneNode> itr = model.iterator();
			myModel = (Model3DTriMesh) itr.next();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		addModel(myModel);
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

	public boolean isMoving()
	{
		return moving;
	}

	public void setMoving(boolean mv)
	{
		moving = mv;
	}

	public boolean facingLeft()
	{
		return facingLeft;
	}

	public void switchDirections()
	{
		if(facingLeft)
			facingLeft = false;
		else
			facingLeft = true;

		rotate(180, new Vector3D(0,0,1));
	}

	public void update(float time)
	{
		if(facingLeft)
		{
			moveLeft(time);
		}
		else
		{
			moveRight(time);
		}

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
	
	public void setHealth(int i)
	{
		HEALTH = i;
	}
	
	public int getHealth()
	{
		return HEALTH;
	}
	
	public void setAlive(boolean b)
	{
		isAlive = b;
	}

	public boolean isAlive()
	{
		return isAlive;
	}
	
	public boolean handleEvent(IGameEvent event)
	{
		/*CollectEvent cevent = (CollectEvent) event;
		int collectCount = cevent.getWhichCollect();
		if (collectCount % 2 == 0) this.setColorBuffer(colorBuf);
		else this.setColorBuffer(colorBuf2);
		return true;*/
		
		this.scale(0.5f,0.5f,0.5f);
		return true;
 }

	public void updateLocation()
	{
		// TODO
	}
}