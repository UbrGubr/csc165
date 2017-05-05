package game.ai;

import game.TheGame;
import game.characters.Monster;

import sage.ai.behaviortrees.BTSequence;

public class GolemController extends NPCController
{
	private TheGame game;
	private Monster golem;

	public GolemController(TheGame g, Monster m)
	{
		game = g;
		golem = m;
		startTime = System.nanoTime();
		lastUpdateTime = startTime;
		setupBehaviorTree();
	}

	private void setupBehaviorTree()
	{
		bt.insertAtRoot(new BTSequence(10)); 
		bt.insertAtRoot(new BTSequence(20)); 
		bt.insert(10, new TwoSecPassed(this, golem, false)); 
		bt.insert(10, new GolemRoam(golem)); 
		bt.insert(20, new PlayerNear(game, this, golem, false)); 
		bt.insert(20, new AttackPlayer(golem)); 
	}

	public void update(float time)
	{
		long frameStartTime = System.nanoTime();
		float elapsedTime = (frameStartTime-lastUpdateTime)/(1000000.0f);
		if(elapsedTime >= 50.0f)
		{
			lastUpdateTime = frameStartTime;
			golem.updateLocation();
			bt.update(elapsedTime);
		}
		Thread.yield();
	}
}