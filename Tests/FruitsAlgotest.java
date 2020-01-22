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
import utils.Point3D;

public class FruitsAlgotest {

	@Test
	public void listtest()
	{
		List<Fruits> tempp = new ArrayList<Fruits>();
		game_service game = Game_Server.getServer(0);
		FruitsAlgo ff=new FruitsAlgo(game);
		for(String json:game.getFruits() )
        try {
        	Fruits temp=new Fruits();
            JSONObject fruit = new JSONObject(json);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            temp.setType( fruitt.getInt("type"));
            temp.setValue(fruitt.getDouble("value"));
           temp.setLocation(new Point3D(fruitt.getString("pos")));
           tempp.add(temp);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
		if(ff.fruits.size()!=tempp.size())
			fail("should be the same size");
		
		for(int i=0;i<tempp.size();i++)
		{
			if(!tempp.get(i).equals(ff.fruits.get(i)))
				fail("fruits should be equal");
				
			
		}
		
	}

}
