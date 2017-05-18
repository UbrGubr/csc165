package game.ai;

import game.characters.Monster;

import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTStatus;

public class AttackPlayer extends BTAction
{
	private Monster golem;

	public AttackPlayer(Monster m)
	{
		golem = m;
	}

	@Override
	protected BTStatus update(float elapsedTime) {
		// TODO Auto-generated method stub
		//System.out.println("Attacking!");
		return BTStatus.BH_SUCCESS;
	}
}