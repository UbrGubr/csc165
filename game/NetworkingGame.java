package game;

import game.network.GameClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.UUID;

import sage.networking.IGameConnection.ProtocolType;
import sage.event.EventManager;
import sage.event.IEventManager;
import sage.scene.*;

import graphicslib3D.Point3D;

public class NetworkingGame extends TheGame
{
	private String serverAddress;
	private int serverPort;
	private ProtocolType serverProtocol;
	private GameClient thisClient;
	private boolean connected;

	private Point3D playerPosition;

	public NetworkingGame(String serverAddr, int sPort)
	{
		super();
		this.serverAddress = serverAddr;
		this.serverPort = sPort;
		this.serverProtocol = ProtocolType.TCP;
	}

	protected void initGame()
	{

		try
		{
			thisClient = new GameClient(InetAddress.getByName(serverAddress), serverPort, serverProtocol, this);
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		if(thisClient != null)
		{
			thisClient.sendJoinMessage();
		}

		super.initGame();

		playerPosition = getPlayerPosition();
	}

	protected void update(float time)
	{
		if(thisClient != null)
		{
			thisClient.processPackets();
			if(playerPosition != getPlayerPosition())
			{
				thisClient.sendMoveMessage(getPlayerPosition());
				playerPosition = getPlayerPosition();
			}
		}

		super.update(time);
	}

	protected void shutdown()
	{
		super.shutdown();
		if(thisClient != null)
		{
			thisClient.sendByeMessage();
			try
			{
				thisClient.shutdown();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setConnected(boolean c)
	{
		connected = c;
	}

	public boolean isConnected()
	{
		return connected;
	}

	public Point3D getPlayerPosition()
	{
		return getPlayer().getLocation();
	}

	public void addGameWorldObject(SceneNode s)
	{
		super.addGameWorldObject(s);
	}

	public boolean removeGameWorldObject(SceneNode s)
	{
		return super.removeGameWorldObject(s);
	}
}