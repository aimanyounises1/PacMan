package gameClient;

import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Range;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
import utils.StdDraw;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyGameGui implements ActionListener, MouseListener  {
	// Range for x and y scale
   private Range rangex, rangey;
   game_service ser;
   public DGraph g;
	public ArrayList<Fruit>Fruits=new ArrayList<Fruit>();
	public ArrayList<Robot>Robots=new ArrayList<Robot>();
	
	public MyGameGui() {
		this.g=null;
		this.ser=null;
	}
	
	public void MyGameGuiInit(game_service s)
	{
		this.ser=s;
		
		this.g=new DGraph();
	
	   g.init(s.getGraph());
	   initRobots(s);
	   initFruits(s);
	   
	   
	}
	
	public void initRobots(game_service s)
	{
		ArrayList<String> l=(ArrayList<String>)s.getRobots();
		if(l!=null)
		{
			try
			{
				String t=s.getRobots().toString();
				t=t.substring(1,t.length()-1);
				String[] arr=t.split(", ");
				int i=0;
				while(i<arr.length)
				{
					JSONObject robots=new JSONObject(arr[0]);

					JSONObject robot=robots.getJSONObject("Robot");
					String location=robot.getString("pos");
					String[] points=location.split(",");
					double x=Double.parseDouble(points[0]);
					double y=Double.parseDouble(points[1]);
					double z=Double.parseDouble(points[2]);
					int key=robot.getInt("id");
					int src=robot.getInt("src");
					int dst=robot.getInt("dest");
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
		
		ArrayList <String>arr=(ArrayList<String>)s.getFruits();
		if(arr!=null)
		{
			
			try
			{
				String ss=s.getFruits().toString();
				ss=ss.substring(1,ss.length()-1);
				String []sss=ss.split(", ");
				
				
				int i=0;
				while (i<sss.length)
				{
					
					
					
					JSONObject fff=new JSONObject(sss[i]);
					JSONObject perot=fff.getJSONObject("Fruit");
					 
					
					String location=perot.getString("pos");
					String []points=location.split(",");
					double x=Double.parseDouble(points[0]);
					double y=Double.parseDouble(points[1]);

					double z=Double.parseDouble(points[2]);
					int type=perot.getInt("type");
					double value=perot.getDouble("value");
					Fruit fr=new Fruit(value,type,new Point3D(x,y,z));
					Fruits.add(fr);
					i++;

					
					
				}
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public void drawgame() {

       //set the x and y scale according the max and min points
		findmaxminx();

		findmaxminy();

		double lx = rangex.get_length()/15;

		double ly = rangey.get_length()/10;
		double circle = Math.abs(rangex.get_length()/200);


		StdDraw.setXscale(rangex.get_min()-lx, rangex.get_max()+lx);

		StdDraw.setYscale(rangey.get_min()-ly, rangey.get_max()+ly);


		//clear the  screen

		StdDraw.clear(Color.white);

		

		// draw the nodes

		for(node_data n : g.getV()) {

			StdDraw.setPenColor(Color.blue);

			Point3D src = n.getLocation();

			StdDraw.filledCircle(src.x(), src.y(), circle);

			StdDraw.text(src.x(), src.y()+circle*3, Integer.toString(n.getKey()));



			//draw the graphs edges

			for(edge_data e : g.getE(n.getKey())) {

				StdDraw.setPenColor(Color.RED);

				Point3D dest = g.getNode(e.getDest()).getLocation();

				StdDraw.line(src.x(), src.y(), dest.x(), dest.y());
				double ww=e.getWeight();
				double value=Double.parseDouble(new DecimalFormat(".##").format(ww));

				StdDraw.text((src.x()+dest.x())/2, (src.y()+dest.y())/2,Double.toString(value));

				

				// draw the yellow circle for the direction(arrow)						

				double f = dest.distance2D(src);
				double o = (circle*5)/f;				
				Point3D start;
				Point3D finish;
				double k;
				if(src.x() < dest.x()) {
					start = src;
					finish = dest;

					k = -(dest.x()-src.x())*o;
				}
				else {
					start = dest;
					finish = src;
					 k = (src.x()-dest.x())*o;
				}	
				double m = (finish.y()-start.y()) / (finish.x()-start.x());
				double nn = (m * (-start.x())) + start.y();
				double y = (m * (dest.x()+k)) + nn;
				double x = dest.x() + k;
				StdDraw.setPenColor(Color.YELLOW);	
				StdDraw.filledCircle(x, y, circle/2);
			}
		}
		
		//draw the fruits
		for(Fruit fru:Fruits)
		{
			double x=fru.getlocation().x();
			double y=fru.getlocation().y();
		    int type=fru.gettype();
		    if(type==-1)
		    {
		    	StdDraw.picture(x, y, "data/banana.png",0.0006,0.0006);
		    }
		    else
		    	StdDraw.picture(x, y, "data/apple.png",0.0006,0.0006);

		    	
		}

		//draw the robots 
		for(Robot ro:Robots)
		{StdDraw.setPenColor(Color.cyan);
			double xx=ro.location.x();
			double yy=ro.getlocation().y();
		    
			StdDraw.filledCircle(xx, yy, circle*1.5);
		}
	}
		//this finds the max and min point location for x this helps us set the scale properly
		private void findmaxminx() {
			

			double max = Double.MIN_VALUE;

			double min = Double.MAX_VALUE;

			for(node_data n : g.getV()) {

				if(max < n.getLocation().x())

					max = n.getLocation().x();

				if(min > n.getLocation().x())

					min = n.getLocation().x();

			}
			rangex=new Range(max,min);
			


		}
		
		//this finds the max and min point location for y this helps us set the scale properly
		private void findmaxminy() {

			double max = Double.MIN_VALUE;

			double min = Double.MAX_VALUE;

			for(node_data n : g.getV()) {

				if(max < n.getLocation().y())

					max = n.getLocation().y();

				if(min > n.getLocation().y())

					min = n.getLocation().y();

			}
			rangey=new Range(max,min);
			


		}
		
		public void initGUI() {
			
			// Preparation of the frame
			StdDraw.setCanvasSize(1200,600);
			StdDraw.addMouseListener(this);
			// Draw the graph
			this.drawgame();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	
	
	
	

	
	

}
