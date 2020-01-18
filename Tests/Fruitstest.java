package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.json.JSONException;
import org.junit.Test;

import Server.Game_Server;
import Server.game_service;
import element.Fruits;
import element.FruitsAlgo;
import utils.Point3D;

public class Fruitstest {
	
	@Test
	public void inittest()
	{
		game_service server=Game_Server.getServer(0);
		Fruits f= new Fruits();
		f=f.init(server.getFruits().get(0));
        assertEquals(f.getValue(), 5.0,0.00001);
        if(f.getType()!=-1)
        	fail("error: should be equals");
        if(f.getLocation().x()!=35.197656770719604&&f.getLocation().y()!=32.10191878639921)
        	fail("error: should be equals");


	}
	
	@Test
	public void settest()
	{
		Fruits f= new Fruits();
		double d=5.0;
		int t=-1;
		Point3D p= new Point3D(12,12,0);
		f.setLocation(p);
		f.setType(t);
		f.setValue(d);
        assertEquals(f.getValue(), d,0.00001);
        assertEquals(f.getType(), t,0.00001);
        if(f.getLocation().x()!=12&&f.getLocation().y()!=12)
        	fail("error: should be equals");

        	


	}
	
	

}
