package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import Server.Game_Server;
import Server.game_service;
import element.Fruits;
import element.Robots;
import utils.Point3D;

public class Robotstest {

 @Test
 public void inittest()
	{
		game_service server=Game_Server.getServer(0);
		server.addRobot(1);
		Robots f= new Robots();
		f=f.init(server.getRobots().get(0));
     assertEquals(f.getId(), 0,0.00001);
     assertEquals(f.getValue(), 0,0.00001);
     assertEquals(f.getDest(),-1,0.00001);
     assertEquals(f.getSrc(),1,0.00001);
     assertEquals(f.getSpeed(),1.0,0.00001);



     if(f.getLocation().x()!=35.18958953510896&&f.getLocation().y()!=32.10785303529412)
     	fail("error: should be equals");


	}
 
 
 @Test
	public void settest()
	{
		Robots f= new Robots();
		int d=1;
		int s=1;
		int t=-1;
		double v=5.0;
		int speed=1;
		Point3D p= new Point3D(12,12,0);
		f.setLocation(p);
		f.setDest(d);
		f.setSrc(s);
		f.setSpeed(speed); f.setValue(v);
     assertEquals(f.getValue(), v,0.00001);
     assertEquals(f.getDest(), d,0.00001);
     assertEquals(f.getSrc(), s,0.00001);
     assertEquals(f.getSpeed(), speed,0.00001);
     if(f.getLocation().x()!=12&&f.getLocation().y()!=12)
      	fail("error: should be equals");



     if(f.getLocation().x()!=12&&f.getLocation().y()!=12)
     	fail("error: should be equals");

     	


	}
}