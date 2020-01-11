package gameClient;

import java.awt.List;
import java.util.ArrayList;

import Server.game_service;
import dataStructure.graph;
import utils.Point3D;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;


public class MyGameGui {
	graph g;
	ArrayList<Fruit>Fruits=new ArrayList<Fruit>();
	ArrayList<Robot>Robots=new ArrayList<Robot>();
	
	public MyGameGui() {
		this.g=null;
	}
	
	public MyGameGui(graph g)
	{
	this.g=g;
	}
	
	public void initRobots(game_service s)
	{
		ArrayList<String> l=(ArrayList)s.getRobots();
		if(l!=null)
		{
			String rolist=l.toString();
			try
			{
				JSONObject obj=new JSONObject(rolist);
				JSONArray arr=obj.getJSONArray("Robot");
				int i=0;
				while(i<arr.length())
				{
					JSONObject robot=arr.getJSONObject(i);
					String location=robot.getString("pos");
					String[] points=location.split(",");
					double x=Double.parseDouble(points[0]);
					double y=Double.parseDouble(points[1]);
					double z=Double.parseDouble(points[2]);
					int key=robot.getInt("id");
					int src=robot.getInt("src");
					int dst=robot.getInt("dst");
					double sum=robot.getDouble("value");
					Robot ro= new Robot(key,src,dst,sum,new Point3D(x,y,z));
					Robots.add(ro);
					i++;
							
					
					
					
				}
			}
			catch(JSONException ex)
			{
				ex.printStackTrace();
			}
			
		}
			
	}
	
	public void initFruits(game_service s)
	{
		
		ArrayList <String>arr=(ArrayList)s.getFruits();
		if(arr!=null)
		{
			
			String f=arr.toString();
			
			try
			{
				JSONObject fruit=new JSONObject(f);
				JSONArray fruits=fruit.getJSONArray("Fruit");
				
				int i=0;
				while (i<fruits.length())
				{
					JSONObject perot=fruits.getJSONObject(i);
					String location=perot.getString("pos");
					String []points=location.split(",");
					double x=Double.parseDouble(points[0]);
					double y=Double.parseDouble(points[1]);

					double z=Double.parseDouble(points[2]);
					int type=perot.getInt("type");
					double value=perot.getDouble("value");
					Fruit fr=new Fruit(value,type,new Point3D(x,y,z));
					i++;

					
					
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
			
		}
		
		
	}


	
	

}
