package game.ai;

import game.TheGame;
import game.network.GameServer;
import game.characters.Monster;

import graphicslib3D.Point3D;
import sage.ai.behaviortrees.BTCondition;

public class PlayerNear extends BTCondition
{
	private TheGame game;
	private NPCController controller;
	private Monster golem;

	public PlayerNear(TheGame g, NPCController c, Monster m, boolean toNegate)
	{
		super(toNegate);
		game = g;
		controller = c;
		golem = m;
	}

	protected boolean check()
	{
		Point3D golemPos = new Point3D(golem.getLocation().getX(),golem.getLocation().getY(),golem.getLocation().getZ()); 
		controller.setNearFlag(game.checkNearbyMonsters(golem));
		//System.out.println("Near player: " + controller.getNearFlag());
		return controller.getNearFlag();
	}
}