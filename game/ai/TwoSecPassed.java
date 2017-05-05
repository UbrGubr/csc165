package game.ai;

import game.characters.Monster;
import sage.ai.behaviortrees.BTCondition;

public class TwoSecPassed extends BTCondition
{
	private NPCController controller;
	private Monster golem;

	private long lastUpdateTime;

	public TwoSecPassed(NPCController c, Monster m, boolean toNegate)
	{
		super(toNegate);
		controller = c;
		golem = m;
		lastUpdateTime = System.nanoTime();
	}

	protected boolean check()
	{
		float elapsedTime = (System.nanoTime() - lastUpdateTime)/(1000000.0f);

		if(elapsedTime >= 2000.0f)
		{
			lastUpdateTime = System.nanoTime();
			controller.setNearFlag(false);
			return true;
		}
		else return false;
	}
}