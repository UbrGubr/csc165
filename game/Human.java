package game;

import sage.scene.TriMesh;
import sage.scene.shape.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Human extends Cylinder
{
	float yVelocity;
	float yPosition;

	public Human()
	{
		super();
		yVelocity = 0;
		yPosition = 0;
	}

	Human(boolean solid)
	{
		super(solid);
		yVelocity = 0;
		yPosition = 0;
	}
}