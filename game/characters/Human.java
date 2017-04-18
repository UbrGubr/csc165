package game.characters;

import sage.scene.TriMesh;
import sage.scene.shape.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import graphicslib3D.Point3D;

public class Human extends Avatar
{
	float yVelocity;
	float yPosition;

	public Human()
	{
		super();
	}

	public Human(Point3D loc)
	{
		super(loc);
	}
}