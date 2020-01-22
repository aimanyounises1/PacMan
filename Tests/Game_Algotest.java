package Tests;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import Server.Game_Server;
import Server.game_service;
import algorithms.Game_Algo;
import dataStructure.EdgeData;
import dataStructure.edge_data;
import element.FruitsAlgo;
import element.RobotsAlgo;

public class Game_Algotest {

	@Test 
	public void getedgetest()
	{
		game_service game =Game_Server.getServer(0);
		Game_Algo g= new Game_Algo(game);
		
		FruitsAlgo f= new FruitsAlgo(game);
		edge_data e = g.getEdgeOfFruit(f.getfruits().get(0));
		if(e.getSrc()!=9&&e.getDest()!=8)
			fail("should be equals");
	
		
	}
	
	@Test
	public void getListOfEdgeFtest()
	{

		game_service game =Game_Server.getServer(1);
		Game_Algo g= new Game_Algo(game);
		int c=0;
		
		List<edge_data>e= g.getListOfEdgeF();
		EdgeData ed = new EdgeData(9,8,1.46);
		EdgeData ed1 = new EdgeData(4,3,1.49);
		for(edge_data ee:e)
		{
			if((ee.getDest()==8&&ee.getSrc()==9)||(ee.getDest()==3&&ee.getSrc()==4))
				c++;
			
		}

if(c!=2)
fail("should be equals");
	
		
	}
	
	@Test
	public void locationrobottest()
	{
		game_service game =Game_Server.getServer(0);
		Game_Algo g= new Game_Algo(game);
		
		g.locationRobot();
		RobotsAlgo ro=new RobotsAlgo(game);
		if(ro.getrobots().get(0).getSrc()!=9)
			fail("source node should be 9");

	}
}
