package game;

import game.characters.*;
import game.projectiles.*;

import sage.input.action.*;

import net.java.games.input.Event;

public class AttackAction extends AbstractInputAction
{
	Human player;
	TheGame game;

	private long timeLastFired;

	public AttackAction(Human p, TheGame g)
	{
		player = p;
		game = g;
		timeLastFired = System.currentTimeMillis();
	}

	public void performAction(float time, Event e)
	{
		if((System.currentTimeMillis() - timeLastFired) >= player.getFireRate())
		{
			timeLastFired = System.currentTimeMillis();
			Projectile p = player.fire();
			game.createProjectile(p);
		}
	}
}