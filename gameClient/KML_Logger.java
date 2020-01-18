package gameClient;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.node_data;
import element.Fruits;
import element.FruitsAlgo;
import element.Robots;
import element.RobotsAlgo;
import utils.Point3D;
public class KML_Logger extends Thread {
	//MyGameGUI g = new MyGameGUI();
	List<Robots> ro;
	List<Fruits> f;
	ArrayList<Point3D> points;
	DGraph graph;
	game_service game;
	FruitsAlgo fruits;
	private String num;
	FileWriter fileWriter;
	BufferedWriter bufferedWriter;
	
	public KML_Logger() {
		ro = new ArrayList<Robots>();
		f = new ArrayList<Fruits>();
		points = new ArrayList<>();
		graph = new DGraph();
	}
	public void init (game_service game,String s) {
		this.game = Game_Server.getServer(Integer.valueOf(s));
		this.game = game;
		f =  new FruitsAlgo(this.game).fruits;
		ro =  new RobotsAlgo(this.game).robots;
		graph.init(game.getGraph());
		this.num = s;
		num = num+".kml";
		try {
			this.fileWriter = new FileWriter(this.num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bufferedWriter = new BufferedWriter(this.fileWriter);
		this.game.startGame();
	}
	public void StartKml() {
		try {
		this.fileWriter = new FileWriter(this.num);
		this.bufferedWriter = new BufferedWriter(this.fileWriter);
		this.bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" + 
				"  <Document>\n" + 
				"    <name>Points with TimeStamps</name>\n" + 
				"    <Style id=\"paddle-a\">\n" + 
				"      <IconStyle>\n" + 
				"        <Icon>\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/paddle/A.png</href>\n" + 
				"        </Icon>\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <Style id=\"paddle-b\">\n" + 
				"      <IconStyle>\n" + 
				"        <Icon>\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/paddle/B.png</href>\n" + 
				"        </Icon>\n" + 
				"        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <Style id=\"hiker-icon\">\n" + 
				"      <IconStyle>\n" + 
				"        <Icon>\n" + 
				"          <href>http://maps.google.com/mapfiles/ms/icons/hiker.png</href>\n" + 
				"        </Icon>\n" + 
				"        <hotSpot x=\"0\" y=\".5\" xunits=\"fraction\" yunits=\"fraction\"/>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <Style id=\"check-hide-children\">\n" + 
				"      <ListStyle>\n" + 
				"        <listItemType>checkHideChildren</listItemType>\n" + 
				"      </ListStyle>\n" + 
				"    </Style>\n" + 
				"    <styleUrl>#check-hide-children</styleUrl>\n<Folder>\n");
		this.bufferedWriter.write(this.PlaceTheGraph());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public String PlaceTheGraph() {
		String s= "";
		for (node_data data :this.graph.getV() ) {
		 s+=	"<Placemark>\n"+
	    "<description>"+"Node num"+data.getKey()+"</description>\n"+
	    "<Point>\n"+
	      "<coordinates>"+data.getLocation().x() +","+data.getLocation().y()+","+data.getLocation().z()+"</coordinates>\n"+
	    "</Point>\n"+
	  "</Placemark>\n";
	}
		return s;
	}
    public String PlaceMarkFruit() {
    	String s="";
		String t="";
		f =  new FruitsAlgo(this.game).fruits;
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String hour  = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String FullDate = date+"T"+hour+"Z";
		for (Fruits fruits : f) {
				s+=      "      <Placemark>\n" + 
						"        <TimeStamp>\n" ;
				t=t.replaceAll("T", " ");
				s+="          <when>"+FullDate+"</when>\n";
				s+="        </TimeStamp>\n" + 
						"        <styleUrl>";
				String s2 ="";
				if(fruits.getType() ==-1) {
					s2 = "#paddle-a";
				}else{
					s2 = "#paddle-b";
				}
				s+= s2 + "</styleUrl>\n";
				s+=	"        <Point>\n" + 
						"          <coordinates>"+  fruits.getLocation().y() +","+fruits.getLocation().x()+"</coordinates>\n" + 
						"        </Point>\n" + 
						"      </Placemark>\n";
			}
			return s;
		}
    	
    public String PLaceMarkRobots() {
    	String s="";
		ro =  new RobotsAlgo(this.game).robots;
    	String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String hour  = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String FullDate = date+"T"+hour+"Z";
    		for (Robots robot : ro) {
    	
				s+=      "      <Placemark>\n" + 
						"        <TimeStamp>\n" ;
				s+="          <when>"+FullDate+"</when>\n";
				s+="        </TimeStamp>\n" + 
						"        <styleUrl>";
				s+="#hiker-icon</styleUrl>\n";
				s+=	"        <Point>\n" + 
						"          <coordinates>"+robot.getLocation().y() +"," +robot.getLocation().x() + "</coordinates>\n" + 
						"        </Point>\n" + 
						"      </Placemark>\n";
			}
    		return s;	
    }
    	public void run() {
    		while(this.game.isRunning()) {
    		try {
       		this.bufferedWriter.write(this.PlaceMarkFruit());
    		this.bufferedWriter.write(this.PLaceMarkRobots());
    		
    	System.out.println("Saved"); 
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    		}
   }
    	public void Save() {
    		try {
    		this.bufferedWriter.write("   </Folder>\n");
    		this.bufferedWriter.write("\n" + 
				"  </Document>\n" + 
				"</kml>");
    	this.bufferedWriter.close();
    		}catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
			}
    	}
    public static void main(String[] args) {
    	 MyGameGUI m =new MyGameGUI();
            
      } 
}