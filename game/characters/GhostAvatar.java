package game.characters;

import java.util.UUID;

import graphicslib3D.Point3D;

public class GhostAvatar extends Human
{
	UUID id;
	
	public GhostAvatar(UUID ghostID, Point3D ghostPosition)
	{
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