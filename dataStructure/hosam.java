package dataStructure;

import Server.Game_Server;
import Server.game_service;
import element.Fruits;
import element.Robots;

public class hosam {

	public static void main(String[] args) {
		for(int i=0 ;i<24;i++)
		{
			game_service server=Game_Server.getServer(i);

			System.out.println(server.toString());

			
		}
		


	}

}
