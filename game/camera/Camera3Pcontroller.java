package game.camera;

import net.java.games.input.Event;
import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;
import sage.camera.ICamera;
import sage.input.IInputManager;
import sage.input.action.AbstractInputAction;
import sage.input.action.IAction;
import sage.scene.SceneNode;
import sage.util.MathUtils;

public class Camera3Pcontroller
{
	private ICamera cam; //the camera being controlled
	private SceneNode target; //the target the camera looks at
	private float cameraAzimuth; //rotation of camera around target Y axis
	private float cameraElevation; //elevation of camera above target
	private float cameraDistanceFromTarget;
	private Point3D targetPos; // avatar’s position in the world
	private Vector3D worldUpVec;
	private boolean cameraAttached = false;
	
	public Camera3Pcontroller(ICamera cam, SceneNode target, IInputManager inputMgr, String controllerName)
	{ 
		this.cam = cam;
		this.target = target;
		worldUpVec = new Vector3D(0,1,0);
		cameraDistanceFromTarget = 30.0f;
		cameraAzimuth = 180; // start from BEHIND and ABOVE the target
		cameraElevation = 10.0f; // elevation is in degrees
		update(0.0f); // initialize camera state
		if(inputMgr == null)
		{
			System.out.println("inputMgr is null");
		}
		setupInput(inputMgr, controllerName);
	}
 
	public void update(float time)
	{
		updateTarget();
		updateCameraPosition();
		cam.lookAt(targetPos, worldUpVec); // SAGE built-in function
	}
	
	private void updateTarget()
	{ 
		targetPos = new Point3D(target.getWorldTranslation().getCol(3)); 
	}
 
	private void updateCameraPosition()
	{
		double theta = cameraAzimuth;
		double phi = cameraElevation ;
		double r = cameraDistanceFromTarget;
		
		// calculate new camera position in Cartesian coords
		Point3D relativePosition = MathUtils.sphericalToCartesian(theta, phi, r);
		Point3D desiredCameraLoc = relativePosition.add(targetPos);
		cam.setLocation(desiredCameraLoc);
	}
 
	private void setupInput(IInputManager im, String cn)
	{ 
		//IAction orbitAction = new OrbitAroundAction();
		//im.associateAction(cn, Axis.RX, orbitAction, REPEAT_WHILE_DOWN);
		//IAction orbitLeft = new OrbitActionLeft();
		//IAction orbitRight = new OrbitActionRight();
		IAction zoomIn = new ZoomIn();
		IAction zoomOut = new ZoomOut();
		//IAction zoomController = new ZoomActionController();
		//IAction orbitController = new OrbitActionController();
		IAction attachCamera = new SetCameraAttached();

		// Keyboard maps
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Key.Z, orbitLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Key.X, orbitRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(cn, net.java.games.input.Component.Identifier.Key.R, zoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(cn, net.java.games.input.Component.Identifier.Key.F, zoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		im.associateAction(cn, net.java.games.input.Component.Identifier.Key.V, attachCamera, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);

		// Controller maps
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Button._2, orbitLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Button._1, orbitRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Button._3, zoomIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Button._0, zoomOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		//im.associateAction(cn, net.java.games.input.Component.Identifier.Button._5, attachCamera, IInputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
	}

	private boolean isAttached()
	{
		return cameraAttached;
	}
	
	private class OrbitActionLeft extends AbstractInputAction
	{

		public void performAction(float time, Event evt)
		{
			float rotAmount = 0.1f;
			
			cameraAzimuth += rotAmount;
			cameraAzimuth = cameraAzimuth % 360;
			
			if(isAttached())
			{
				target.rotate(rotAmount, worldUpVec);
			}
		}
	}
	
	private class OrbitActionRight extends AbstractInputAction
	{	

		public void performAction(float time, Event evt)
		{
			float rotAmount = 0.1f;
			
			cameraAzimuth -= rotAmount;
			cameraAzimuth = cameraAzimuth % 360;
			
			if(isAttached())
			{
				target.rotate(rotAmount, worldUpVec);
			}
		}
	}

	private class OrbitActionController extends AbstractInputAction
	{

		public void performAction(float time, Event evt)
		{
			float rotAmount = 0.1f*time;
			
			if (evt.getValue() < -0.2) {
				cameraAzimuth += rotAmount;
				cameraAzimuth = cameraAzimuth % 360; 
				target.rotate(rotAmount, worldUpVec);
			}
			else if (evt.getValue() > 0.2) {
				rotAmount = rotAmount*(-1);
				cameraAzimuth += rotAmount;
				cameraAzimuth = cameraAzimuth % 360; 
				target.rotate(rotAmount, worldUpVec);
			}
		}
	}

	private class ZoomActionController extends AbstractInputAction
	{

		public void performAction(float time, Event evt) {
			float zoomAmount = 0.1f; 
						
			if (evt.getValue() < -0.2) {
				if (cameraDistanceFromTarget > 3)
					cameraDistanceFromTarget -= zoomAmount;
			}	
			else if (evt.getValue() > 0.2) {
				if (cameraDistanceFromTarget < 40)
					cameraDistanceFromTarget += zoomAmount;
			}	
		}
	}
	
	private class ZoomIn extends AbstractInputAction 
	{
		
		public void performAction(float time, Event evt) {
			float zoomAmount = 0.1f; 
			
			if (cameraDistanceFromTarget > 3)
				cameraDistanceFromTarget -= zoomAmount;	
		}		
	}

	private class ZoomOut extends AbstractInputAction 
	{
	
		public void performAction(float time, Event evt) {
			float zoomAmount=0.1f; 
			
			if (cameraDistanceFromTarget < 100)
				cameraDistanceFromTarget += zoomAmount;	
		}		
	}

	private class SetCameraAttached extends AbstractInputAction
	{

		public void performAction(float time, Event evt) {
			cameraAttached = !cameraAttached;
			System.out.println("Camera attached:" + cameraAttached);
		}
	}
}