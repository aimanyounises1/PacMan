package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Game_Algo;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import element.Fruits;
import element.FruitsAlgo;
import element.Robots;
import element.RobotsAlgo;

import org.json.JSONException;
import org.json.JSONObject;


import utils.Point3D;
import utils.StdDraw;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyGameGUI extends Thread {
    public DGraph Graph;
    Graph_Algo graph;
    public  FruitsAlgo fruits;
    public  game_service server;
    public  RobotsAlgo robots;
    public  Game_Algo game_algo;
    private boolean  menual, auto = false;
    private Robots r;
    int click =0;
    public KML_Logger kml;
    public int num;
    public MyGameGUI() {
        StdDraw.setCanvasSize(1024, 600);
       // StdDraw.enableDoubleBuffering();
        String[] chooseNumOfGame = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
        Object selectedNumOfGame = JOptionPane.showInputDialog(null, "Choose a num of game", "Message", JOptionPane.INFORMATION_MESSAGE, null, chooseNumOfGame, chooseNumOfGame[0]);
        String s = selectedNumOfGame.toString();
        this.num = Integer.parseInt((String) selectedNumOfGame);
        this.server = Game_Server.getServer(num);
        String[] chooseGame = {"Manual game", "Auto game"};
        Object selectedGame = JOptionPane.showInputDialog(null, "Choose a game mode", "Message", JOptionPane.INFORMATION_MESSAGE, null, chooseGame, chooseGame[0]);
        StdDraw.g = this;
      // StdDraw.g=this;
        if (selectedGame == "Manual game"){
            this.menual = true;
            String graph= this.server.getGraph();
            this.robots = new RobotsAlgo(this.server);
            this.fruits = new FruitsAlgo (this.server);
            this.Graph= new DGraph();
            this.Graph.init(graph);
            StdDraw.setCanvasSize(1024, 600);
            this.GUIgraph();
            this.game_algo = new Game_Algo(this.server);
            FruitsGui();
            RobotsGui();
            this.server.startGame();
           this.start();
            StdDraw.show();
        }
        if (selectedGame=="Auto game") {
            this.auto = true;
            String graph= this.server.getGraph();
            this.Graph= new DGraph();
            this.Graph.init(graph);
            StdDraw.setCanvasSize(1024, 600);
            this.GUIgraph();
            this.game_algo = new Game_Algo(this.server);
            FruitsGui();
            RobotsGui();
            StdDraw.show();
            this.server.startGame();
            kml = new KML_Logger();
			kml.init(server, s);
			kml.start();
            this.start();
        }
    }
    public void FruitsGui(){
        this.fruits = new FruitsAlgo(this.server);
        for(Fruits f : this.fruits.fruits){
        	if(f.getType()== 1) {
            StdDraw.picture(f.getLocation().x(),f.getLocation().y(),"data/banana.png",0.001,0.0008);
        }else {
            StdDraw.picture(f.getLocation().x(),f.getLocation().y(),"data/apple.png",0.001,0.0008);

        }
    }
    }

    public void RobotsGui(){
        this.robots = new RobotsAlgo(this.server);
        this.game_algo.locationRobot();
        this.robots = new RobotsAlgo(this.server);
        for(Robots c : this.robots.robots){
        	StdDraw.picture(c.getLocation().x(),c.getLocation().y(),"data/robot.png", 0.001,0.0007);
        }
    }

    public void AddRobot(int key){
        this.robots = new RobotsAlgo(this.server);
        this.server.addRobot(key);
        StdDraw.picture(this.Graph.getNode(key).getLocation().x(), this.Graph.getNode(key).getLocation().y(), "data/robot.png",0.001,0.0007);
        StdDraw.show();
    }

    private void moveRobots() {
		List<String> list = this.server.move();
		if (list != null) {
			this.robots.list(list);
			for (Robots r : this.robots.robots) {
				if (r.getDest() == -1) {
					this.game_algo.nextNode(r, this.Graph);
					this.server.move();
				}
			}
		}
		this.server.move();
	}
    private node_data getNextNode(double x, double y) {
		Point3D p = new Point3D(x,y,0);
		System.out.println("I am in");
		for (node_data n : this.Graph.getV()) {
			if(p.distance2D(n.getLocation()) < 0.001) {
				System.out.println(p.distance2D(n.getLocation())<0.001);
					return n;
		}else {
			System.out.println("Failed");
		}
		}
		return null;
	}

    public void moveRobotByClick() throws InterruptedException {
        double x;
        double y;
        if (this.click == 0) {
            x = StdDraw.mouseX();
            y = StdDraw.mouseY();
            System.out.println(x);
            System.out.println(y);
            System.out.println(this.click);
            node_data n = getTheRobot(x, y);
            System.out.println("robot node"+n.getKey());
            if (n == null) {
                JOptionPane.showMessageDialog(null,"Try again");
            } 
            //this.click++;
        }
            if(this.click == 1) {
            	x = StdDraw.mouseX();
                y = StdDraw.mouseY();
                System.out.println("in click 1"+x);
                System.out.println(y);
                node_data nextNode = getNextNode(x, y);
                System.out.println("next node"+nextNode.getKey());
                if (this.r != null) {
                    for (edge_data e : this.Graph.getE(this.r.getSrc())) {
                        if (nextNode.getKey() == e.getDest()) {
                            this.server.chooseNextEdge(this.r.getId(), nextNode.getKey());
                            //this.click = 0;
                        }
                    }
                }else {
                	JOptionPane.showMessageDialog(null, "No path Assoiacated");
                    //this.click = 0;
                }
            }
            if(this.click == 1) 
            	this.click = 0;
            else {
            	this.click++;
            }
            System.out.println(this.click);
        }
	private void moveRobotsToDest() {
		List<String> log = this.server.move();
		if (log != null) {
			this.robots.list(log);
			for (Robots r : this.robots.robots) {
				if (r.getDest() == -1) {
					this.server.move();
				}
			}
		}
		this.server.move();
	}
    private node_data getTheRobot(double x, double y) {
        this.robots.list(this.server.getRobots());
        Point3D p = new Point3D(x,y,0);
        for(Robots n : this.robots.robots) {
           if(p.distance2D(n.getLocation()) < 0.005) {
        	   System.out.println(p.distance2D(n.getLocation()) < 0.005);
        	   Point3D p2 = new Point3D(n.getLocation());
        	   System.out.println(n.getSrc());
        	   node_data data = new NodeData(p2);
        	   	this.r = n;
                    return data;
                }
        }
        return null;
    }

	public void run() {
		while (this.server.isRunning()) {
			FruitsGui();
			RobotsGui();
			if (this.auto == true)
				moveRobots();
			if (this.menual == true) 
				moveRobotsToDest();
	      // StdDraw.picture(0, 0, "data/A0.png");
				StdDraw.show();
				//StdDraw.picture(0, 0, "data/A0.png");
			//	StdDraw.pause(10);
				this.GUIgraph();
			try {
				Thread.sleep(5);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Your Score is : " + myGrade(this.server));
	}
	

    public double myGrade(game_service server){
        double myGrade =0 ;
        try {
            String json = server.toString();
            JSONObject gameJson = new JSONObject(json);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            myGrade = gameServer.getDouble("grade");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return myGrade;
    }
    public void Draw(graph d){
        StdDraw.clear();
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        Iterator<node_data> it = d.getV().iterator();
        while (it.hasNext()) {
            node_data temp = (node_data) it.next();
            Point3D p = temp.getLocation();
            minX = Math.min(minX, p.x());
            minY = Math.min(minY, p.y());
            maxX = Math.max(maxX, p.x());
            maxY = Math.max(maxY, p.y());
        }
        
        double ScaleX =(maxX-minX)*0.04;
    //    double ScaleY=(maxY-minY)*0.04;
        StdDraw.setXscale(minX - 0.002, maxX +0.002);
        StdDraw.setYscale(minY - 0.002, maxY+ 0.002);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.05);
        
        String k=this.server.toString();
        int i=k.indexOf("graph");
        String kk=k.substring(i+8,k.length()-3);
        System.out.println(kk);
        StdDraw.picture((minX+maxX)/2, (maxY+minY)/2, kk+".png");
       
        
       
        Iterator<node_data> it1 = d.getV().iterator();
        while (it1.hasNext()) {
            node_data temp = (node_data) it1.next();
            Point3D p1 = temp.getLocation();
            StdDraw.filledCircle(p1.x(), p1.y(),ScaleX*0.1);
            StdDraw.text(p1.x(), p1.y() + (((maxX-minX)*0.04)*0.2), "" + temp.getKey());
        }
        StdDraw.setPenRadius(0.004);
        Iterator<node_data> edge1 = d.getV().iterator();
        while (edge1.hasNext()) {
            node_data temp1 = (node_data) edge1.next();
            if (d.getE(temp1.getKey()) != null) {
                Iterator<edge_data> edge2 = d.getE(temp1.getKey()).iterator();
                while (edge2.hasNext()) {
                    StdDraw.setPenColor(Color.LIGHT_GRAY);
                    edge_data temp2 = (edge_data) edge2.next();
                    node_data n1 = d.getNode(temp2.getSrc());
                    node_data n2 = d.getNode(temp2.getDest());
                    Point3D p1 = n1.getLocation();
                    Point3D p2 = n2.getLocation();
                    StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                    double x = 0.2 * p1.x() + 0.8 * p2.x();
                    double y = 0.2 * p1.y() + 0.8 * p2.y();
                    StdDraw.setPenColor(Color.BLUE);
                    double weight = Math.round(temp2.getWeight()*100.0)/100.0;
                    StdDraw.text(x, y , "" + weight);
                    StdDraw.setPenColor(Color.YELLOW);
                    double x1 = 0.1 * p1.x() + 0.9 * p2.x();
                    double y1 = 0.1 * p1.y() + 0.9 * p2.y();
                    StdDraw.filledCircle(x1, y1, ScaleX*0.1);
                }
            }
        }
    }
    public void GUIgraph() {
    	StdDraw.enableDoubleBuffering();
        this.Draw(this.Graph);
        

    }
    

    public static void main(String[] args) {
      MyGameGUI m =new MyGameGUI();
          
    }
}
