package game.network;

import game.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

import sage.networking.client.GameConnectionClient;

import graphicslib3D.Point3D;
import graphicslib3D.Vector3D;

public class GameClient extends GameConnectionClient
{
	private NetworkingGame game;
	private UUID id;
	private GhostAvatar ghost;

	public GameClient(InetAddress remAddr, int remPort, ProtocolType pType, NetworkingGame game) throws IOException
	{
		super(remAddr, remPort, pType);
		this.game = game;
		this.id = UUID.randomUUID();
		System.out.println(id.toString());
	}

	protected void processPacket(Object msg)
	{
		String message = (String) msg; 
		String[] msgTokens = message.split(",");
		System.out.println("Processing client packets");

		if(msgTokens[0].compareTo("join")==0)
		{
			// format join,success or join,failure
			System.out.println("Join message received");
			if(msgTokens[1].compareTo("success")==0)
			{
				game.setConnected(true);
				sendCreateMessage(game.getPlayerPosition());
			}
			else if(msgTokens[1].compareTo("failure")==0)
			{
				game.setConnected(false);
			}

		}
		else if(msgTokens[0].compareTo("bye")==0)
		{
			// format bye,remoteID
			UUID ghostID = UUID.fromString(msgTokens[1]);
			removeGhostAvatar(ghostID);
		}
		else if(msgTokens[0].compareTo("create")==0)
		{
			// format create,remoteID,x,y,z or dsfr,remoteID,x,y,z
			UUID ghostID = UUID.fromString(msgTokens[1]);

			// extract ghost x,y,z position from message
			Point3D ghostPosition = new Point3D(Double.parseDouble(msgTokens[2]), Double.parseDouble(msgTokens[3]), Double.parseDouble(msgTokens[4]));
			if(ghost == null)
			{
				createGhostAvatar(ghostID, ghostPosition);
			}
		}
		else if(msgTokens[0].compareTo("dsfr")==0)
		{
			// format dsfr,remoteID,x,y,z
			UUID ghostID = UUID.fromString(msgTokens[1]);
			Point3D ghostPosition = new Point3D(Double.parseDouble(msgTokens[2]), Double.parseDouble(msgTokens[3]), Double.parseDouble(msgTokens[4]));

			if(ghost == null)
			{
				createGhostAvatar(ghostID, ghostPosition);
				sendReadyMessage();
			}
		}
		else if(msgTokens[0].compareTo("wsds")==0)
		{
			Point3D pos = game.getPlayerPosition();
			UUID remID = UUID.fromString(msgTokens[1]);
			sendDetailsForMessage(remID, pos);
		}
		else if(msgTokens[0].compareTo("move")==0)
		{
			UUID ghostID = UUID.fromString(msgTokens[1]);
			Point3D ghostPosition = new Point3D(Double.parseDouble(msgTokens[2]), Double.parseDouble(msgTokens[3]), Double.parseDouble(msgTokens[4]));
			moveGhostAvatar(ghostID, ghostPosition);
		}
	}

	public void sendCreateMessage(Point3D position)
	{
		// format create,localid,x,y,z
		try
		{
			System.out.println("create message sent to " + id.toString());
			String message = new String("create," + id.toString());
			message += "," + position.getX() + "," + position.getY() + "," + position.getZ();
			sendPacket(message);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void sendDetailsForMessage(UUID remId, Point3D position) 
	{
		try 
		{ 
			String message = new String("dsfr," + id.toString() + "," + remId.toString());
			message += "," + position.getX(); 
			message += "," + position.getY(); 
			message += "," + position.getZ(); 
			sendPacket(message); 
		}
		catch (IOException e) 
		{ 
			e.printStackTrace();
		}
	}

	public void sendJoinMessage() 
	{
		// format: join, localId 
		try 
		{ 
			sendPacket(new String("join," + id.toString()));
		}
		catch (IOException e) 
		{ 
			e.printStackTrace();
		}
	}

	public void sendByeMessage() 
	{  
		try 
		{ 
			String message = new String("bye," + id.toString()); 
			sendPacket(message); 
		} 
		catch (IOException e) 
		{ 
			e.printStackTrace();
		}
	} 

	public void sendReadyMessage()
	{
		try
		{
			String message = new String("ready," + id.toString());
			sendPacket(message);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void createGhostAvatar(UUID ghostID, Point3D ghostPosition)
	{
		ghost = new GhostAvatar(ghostID, ghostPosition);
		game.addGameWorldObject(ghost);
	}

	private void removeGhostAvatar(UUID ghostID)
	{
		game.removeGameWorldObject(ghost);
		ghost = null;
	}

	private void moveGhostAvatar(UUID ghostID, Point3D ghostPosition)
	{
		if(ghost != null)
		{
			ghost.move(ghostPosition);
		}
	}
}