package game.characters;

import java.util.UUID;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;

public class GhostAvatar extends Human
{
	UUID id;
	
	public GhostAvatar(UUID ghostID, Point3D ghostPosition)
	{
		super(new Point3D(0,2,0), new Vector3D(0,1,0), 90);
		this.scale(2,2,2);
		id = ghostID;
		this.setLocation(ghostPosition);
	}

	public void move(Point3D ghostPosition)
	{
		this.setLocation(ghostPosition);
	}

	public void setLocation(Point3D ghostPosition)
	{
		super.setLocation(ghostPosition);
	}
}