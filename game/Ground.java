package game;

import sage.scene.*;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Ground extends TriMesh
{
	private static float[] verts = new float[] { 	1,0,.5f,	1,1,.5f, 	-1,0,.5f, 	// front right
													-1,0,.5f, 	1,1,.5f,	-1,1,.5f,	// front left
													-1,1,.5f,	1,1,.5f,	1,1,-.5f,	// top right
													-1,1,.5f,	1,1,-.5f,	-1,1,-.5f	// top left
												};

	private static float[] colors = new float[] {	0.9f,0.5f,0.3f,1,		0,1,0,1,	0.9f,0.5f,0.3f,1,	// brown, green, brown
													0.9f,0.5f,0.3f,1,		0,1,0,1,	0,1,0,1,		// brown, green, green
													0,1,0,1,			0,1,0,1,	0,1,0,1,		// green, green, green
													0,1,0,1,			0,1,0,1,	0,1,0,1
												};

	private static int[] triangles = new int[] {0,1,2, 2,1,3, 3,1,4, 3,4,5};

	public Ground()
	{
		FloatBuffer vertBuffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(verts);
		FloatBuffer colorBuffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(colors);
		IntBuffer triangleBuffer = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);

		this.setVertexBuffer(vertBuffer);
		this.setColorBuffer(colorBuffer);
		this.setIndexBuffer(triangleBuffer);
	}


}