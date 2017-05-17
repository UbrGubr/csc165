package game.projectiles;

import game.*;
import game.projectiles.*;

import sage.scene.*;

public class ProjectileController extends Controller
{
	long lastTime;
	
	public ProjectileController()
	{
		lastTime = System.currentTimeMillis();
	}

	public void update(double time)
	{
		long currentTime = System.currentTimeMillis();
		long t = currentTime - lastTime;
		lastTime = currentTime;

		double dist;
		for(SceneNode node : controlledNodes)
		{
			if(node instanceof Projectile)
			{
				Projectile p = (Projectile) node;

				if(p.getDirection() == Direction.RIGHT)
					dist = -(p.getVelocity() * t);
				else
					dist = p.getVelocity() * t;
				
				p.travel(dist);
			}
		}
	}
}