package game;

import sage.scene.TriMesh;

import com.jogamp.common.nio.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Human extends TriMesh
{
	private static float[] verts = new float[] {0,1,0,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,-1};
	private static float[] colors = new float[] {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,0,1,1};
	private static int[] triangles = new int[]{0,1,2,0,2,3,0,3,4,0,4,1,1,4,2,4,3,2};

	public Human()
	{
		FloatBuffer vertBuffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(verts);
		FloatBuffer colorBuffer = com.jogamp.common.nio.Buffers.newDirectFloatBuffer(colors);
		IntBuffer triangleBuffer = com.jogamp.common.nio.Buffers.newDirectIntBuffer(triangles);

		this.setVertexBuffer(vertBuffer);
		this.setColorBuffer(colorBuffer);
		this.setIndexBuffer(triangleBuffer);
	}
}