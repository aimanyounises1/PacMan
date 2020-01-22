package Tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import Server.Game_Server;
import Server.game_service;
import element.Fruits;
import element.FruitsAlgo;
import element.Robots;
import element.RobotsAlgo;
import utils.Point3D;

public class RobotsAlgotest {
	
	@Test
	public void listtest()
	{
		List<Robots> tempp = new ArrayList<Robots>();
		game_service game = Game_Server.getServer(0);
		RobotsAlgo rr=new RobotsAlgo(game);
		
		for(String json:game.getRobots() )
        try {
            Robots temp = new Robots();

        	 JSONObject robot = new JSONObject(json);
             JSONObject robott = robot.getJSONObject("Robot");
             temp.setSrc(robott.getInt("src"));
             temp.setDest( robott.getInt("dest"));
             temp.setValue( robott.getDouble("value"));
             temp.setid( robott.getInt("id"));
             String pos = robott.getString("pos");
             Point3D poss = new Point3D(pos);
             temp.setLocation(poss);
             temp.setSpeed( robott.getInt("speed"));
             tempp.add(temp);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
		if(rr.robots.size()!=tempp.size())
			fail("should be the same size");
		
		for(int i=0;i<tempp.size();i++)
		{
			if(!tempp.get(i).equals(rr.robots.get(i)))
				fail("robots should be equal");
				
			
		}
		
	}


}
