package gameClient;
import java.io.BufferedWriter;
import java.io.FileWriter;
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
public class KML_Logger extends Thread {
	private List<Robots> ro;
	private List<Fruits> f;
	//ArrayList<Point3D> points;
	private DGraph graph;
	 private game_service game;
	private FruitsAlgo fruits;
	private String num;
	/** 
     * @Default Constructor
     */
	public KML_Logger() {
		ro = new ArrayList<Robots>();
		f = new ArrayList<Fruits>();
		graph = new DGraph();
	}
	/** 
     @Init the kml logger with our game service
     */
	public void init (game_service game,String s) {
		this.game = Game_Server.getServer(Integer.valueOf(s));
		this.game = game;
		f =  new FruitsAlgo(this.game).fruits;
		ro =  new RobotsAlgo(this.game).robots;
		graph.init(game.getGraph());
		this.num = s;
		this.game.startGame();
		//System.out.println("Iam in");
	}
	/** 
     @PlaceMark out graph to show the nodes
     */
	public String PlaceTheGraph() {
		String s= "";
		for (node_data data :this.graph.getV() ) {
		 s+=	"<Placemark>"+
	    "<description>"+"</description>"+
	    "<Point>"+
	      "<coordinates>"+data.getLocation().x() +","+data.getLocation().y()+","+data.getLocation().z()+"</coordinates>"+
	    "</Point>"+
	  "</Placemark>";
	}
		return s;
	}
	/** 
    @PlaceMark our fruits to show the fruits of the game
    @TimaStamp ued to get the date of this robots while it moving
     */
    public String PlaceMarkFruit() {
    	String s="";
		String t="";
		f =  new FruitsAlgo(this.game).fruits;
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String hour  = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String FullDate = date+"T"+hour+"Z";
		for (Fruits fruit : f) {
				s+=      "      <Placemark>\n" + 
						"        <TimeStamp>\n" ;
				t=t.replaceAll("T", " ");
				s+="          <when>"+FullDate+"</when>\n";
				s+="        </TimeStamp>\n" + 
						"        <styleUrl>";
				String s2 ="";
				if(fruit.getType() ==-1) {
					s2 = "#paddle-a";
				}else{
					s2 = "#paddle-b";
				}
				s+= s2 + "</styleUrl>\n";
				s+=	"        <Point>\n" + 
						"          <coordinates>"+  fruit.getLocation().x() +","+fruit.getLocation().y()+"</coordinates>\n" + 
						"        </Point>\n" + 
						"      </Placemark>\n";
			}
			return s;
		}
    /** 
     @PlaceMark our robots to show the game
     @TimeStamp of the robots too
     */
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
						"          <coordinates>"+robot.getLocation().x() +"," +robot.getLocation().y() + "</coordinates>\n" + 
						"        </Point>\n" + 
						"      </Placemark>\n";
    			
    			
			}
    return s;	
    }
    /** 
     @this method starts the Thread of our kml logger
     */
    public void run() {
    	String FileName = num+".kml";
    	try {
    		// Assume default encoding.
    	FileWriter fileWriter =new FileWriter(FileName);//write the file 
    	BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
    	bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
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
    		bufferedWriter.write(this.PlaceTheGraph());
    		long t=System.currentTimeMillis();
    		while(this.game.isRunning()) {
    			/**
    			 * @IMPORTANT: we enable buffer writter every half second to avoid a big kml
    			 * file size
    		     */
    			if(System.currentTimeMillis()-t>=500)
    			{
    		bufferedWriter.write(this.PlaceMarkFruit());
    		bufferedWriter.write(this.PLaceMarkRobots());
    		t=System.currentTimeMillis();
    			}
    		}
    		bufferedWriter.write("   </Folder>\n");
    		bufferedWriter.write("\n" + 
				"  </Document>\n" + 
				"</kml>");
    	bufferedWriter.close();
    	System.out.println("Saved"); 
    	}catch (Exception e) {
	// TODO: handle exception
    		System.out.println(e);
    	}
   }
}