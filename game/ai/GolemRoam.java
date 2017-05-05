package game.ai;

import game.characters.Monster;

import graphicslib3D.Vector3D;
import graphicslib3D.Matrix3D;

import sage.ai.behaviortrees.BTAction;
import sage.ai.behaviortrees.BTStatus;

public class GolemRoam extends BTAction
{
	private Monster golem;

	public GolemRoam(Monster m)
	{
		golem = m;
	}

	@Override
	protected BTStatus update(float elapsedTime) {
		golem.setMoving(!(golem.isMoving()));
		
		if (golem.isMoving()) {
			//golem.getRoar().stop();
			golem.stopAnimation(); 
			golem.switchDirections();
		 	golem.startAnimation("Walk");
		 	//npc.getGrowl().play();
		}
		else 
		{
			//npc.getGrowl().stop();
			golem.stopAnimation();		 
			//npc.startAnimation("Roar");
			//npc.getRoar().play();
		}
		
		return BTStatus.BH_SUCCESS;
	}
}