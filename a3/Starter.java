package a3;

import game.*;
import game.network.*;

import java.io.IOException;
import java.util.Scanner;

public class Starter
{
	private static final int localPort = 1550;
	private static int numPlayers;

	public static void main(String[] args) throws IOException
	{
		boolean valid = false;
		Scanner s = new Scanner(System.in);

		while(!valid)
		{
			System.out.println("Number of players (1 or 2):");
			numPlayers = s.nextInt();
			if(numPlayers == 1 || numPlayers == 2)
			{
				valid = true;
			}
			else
			{
				System.out.println("Invalid entry, please try again");
			}
		}

		if(numPlayers == 1)
		{
			new TheGame().start();
		}
		else if(numPlayers == 2)
		{
			Scanner r = new Scanner(System.in);
			System.out.println("Will you be the host (y/n)?");
			String response = r.nextLine();
			if(response.charAt(0) == 'y')
			{
				System.out.println("You are about to host a new game");
				GameServer server = new GameServer(localPort);
				server.getLocalInetAddress();
				System.out.println("The server connection info is " + server.getLocalInetAddress() + ":" + localPort);
				System.out.println("waiting for client connection...");

				String[] msgTokens = server.getLocalInetAddress().toString().split("/");
				NetworkingGame serverClient = new NetworkingGame(msgTokens[1], localPort);
				serverClient.start();
			}
			else
			{
				System.out.println("Enter host server's IP address:");
				String serverIP = r.nextLine();
				System.out.println("Joining server " + serverIP + ":" + localPort);
				NetworkingGame client = new NetworkingGame(serverIP, localPort);
				client.start();
			}
		}
		else
		{
			System.out.println("Something went wrong, exiting...");
		}
	}
}