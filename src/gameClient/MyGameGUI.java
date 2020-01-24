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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
/**
 *@this is our class of gui using stdraw every menu bar every action performed will be performed in stdraw
 *when you click best score button wait just 2 seconds enter the level then it will show your score
 *you have to wait a little bit because every time you search in the mysql
 *also you have my max level that i reached and the number of games that i played too
 *Hope you enjoy it a lot 
 *every code is explained well in using javadoc 
 */

public class MyGameGUI extends Thread {
	public DGraph Graph;
	Graph_Algo graph;
	private FruitsAlgo fruits;
	public game_service game;
	private RobotsAlgo robots;
	private Game_Algo game_algo;
	private Robots robo;
	private int click = 0;
	private KML_Logger kml;
	private int num = 0;
	private boolean menualGaming, autoGaming = false;
	int dt = 110;
	List<edge_data> arr;
	private String everything;

	public MyGameGUI() {
		StdDraw.setCanvasSize(1024, 600);
		String[] Num = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23" };
		Object NumGame = JOptionPane.showInputDialog(null, "Choose a num of game", "Message",
				JOptionPane.INFORMATION_MESSAGE, null, Num, Num[0]);
		String s = NumGame.toString();
		this.num = Integer.parseInt((String) NumGame);

		this.game = Game_Server.getServer(num);
		String[] Game = { "Manual game", "Auto game" };
		Object selectedGame = JOptionPane.showInputDialog(null, "Choose a game mode", "Message",
				JOptionPane.INFORMATION_MESSAGE, null, Game, Game[0]);
		/**
		 * this is so improtant because we have another mygamegui in stdraw to in order
		 * to use the mousepressed and action performed
		 * 
		 * @param stdraw.g = this
		 */
		StdDraw.g = this;
		/**
		 * @in this auto game we did a login to the server so if you want to play a level
		 * that you did't reached it you can't sorry about that ;) 
		 */
		if (selectedGame == "Auto game") {
			Object idd = JOptionPane.showInputDialog(null, "enter id");
			String iddd = idd.toString();

			int id = Integer.parseInt(iddd);
			while (id < 100000000 || id > 999999999) {
				JOptionPane.showMessageDialog(null, "invalid id try again");
				idd = JOptionPane.showInputDialog(null, "enter id");
				iddd = idd.toString();

				id = Integer.parseInt(iddd);
			}
			Game_Server.login(id);
			String ss = this.game.toString();
			int jj = ss.indexOf("max_user");
			ss = ss.substring(jj + 16);
			int kk = ss.indexOf(",");
			ss = ss.substring(0, kk);

			int scenario_num = Integer.parseInt(ss);
			if (num > Integer.parseInt(ss)) {
				JOptionPane.showMessageDialog(null, "not at this level yet");
				return;
			}

			game_service game = Game_Server.getServer(scenario_num);
			this.autoGaming = true;
			String graph = this.game.getGraph();
			this.Graph = new DGraph();
			this.Graph.init(graph);
			StdDraw.setCanvasSize(1024, 600);
			this.Refresh();
			this.game_algo = new Game_Algo(this.game);
			FruitsDraw();
			RobotsDraw();
			StdDraw.show();
			this.game.startGame();
			kml = new KML_Logger();
			kml.init(this.game, s);
			kml.start();
			this.start();
			if (!this.game.isRunning()) {
				game.sendKML("Not used");
			}
		}
		/**
		 * {@code we use this to start the manual game}
		 * 
		 */
		if (selectedGame == "Manual game") {
			this.menualGaming = true;
			String graph = this.game.getGraph();
			this.robots = new RobotsAlgo(this.game);
			this.fruits = new FruitsAlgo(this.game);
			this.Graph = new DGraph();
			this.Graph.init(graph);
			StdDraw.setCanvasSize(1024, 600);
			this.Refresh();
			this.game_algo = new Game_Algo(this.game);
			FruitsDraw();
			RobotsDraw();
			this.game.startGame();
			this.start();
			StdDraw.show();
		}
	}

	/**
	 * {@code use to draw the the fruits after are updated}
	 * 
	 * @param list
	 */
	public void FruitsDraw() {
		this.fruits = new FruitsAlgo(this.game);
		for (Fruits f : this.fruits.fruits) {
			if (f.getType() == 1) {
				StdDraw.picture(f.getLocation().x(), f.getLocation().y(), "data/banana.png", 0.001, 0.0008);
			} else {
				StdDraw.picture(f.getLocation().x(), f.getLocation().y(), "data/apple.png", 0.001, 0.0008);

			}
		}
	}

	/**
	 * {@code use to draw the the Robots after are updated}
	 * 
	 * @param list
	 */
	public void RobotsDraw() {
		this.robots = new RobotsAlgo(this.game);
		this.game_algo.locationRobot();
		this.robots = new RobotsAlgo(this.game);
		for (Robots c : this.robots.robots) {
			StdDraw.picture(c.getLocation().x(), c.getLocation().y(), "data/robot.png", 0.001, 0.0007);
		}
	}

	/**
	 * {@code use to add robot to the game and draw it}
	 * 
	 * @param list
	 */
	public void AddRobot(int key) {
		this.robots = new RobotsAlgo(this.game);
		this.game.addRobot(key);
		StdDraw.picture(this.Graph.getNode(key).getLocation().x(), this.Graph.getNode(key).getLocation().y(),
				"data/robot.png", 0.001, 0.0007);
		StdDraw.show();
	}

	/**
	 * {@code used to get the next vertex in manual game}
	 * 
	 * @param point
	 */
	private node_data getNext(double x, double y) {
		Point3D p = new Point3D(x, y, 0);
		System.out.println("I am in");
		for (node_data n : this.Graph.getV()) {
			if (p.distance2D(n.getLocation()) < 0.001) {
				System.out.println(p.distance2D(n.getLocation()) < 0.001);
				return n;
			} else {
				System.out.println("Failed");
			}
		}
		return null;
	}

	/**
	 * {@code this function called in stdraw class by mousepressed to choose the robot and the destination}
	 * {@code in manual play}
	 * 
	 * @param list
	 */
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
			System.out.println("robot node" + n.getKey());
			if (n == null)
				JOptionPane.showMessageDialog(null, "Try again");
		}
		if (this.click == 1) {
			x = StdDraw.mouseX();
			y = StdDraw.mouseY();
			System.out.println("in click 1" + x);
			System.out.println(y);
			node_data nextNode = getNext(x, y);
			System.out.println("next node" + nextNode.getKey());
			if (this.robo != null) {
				for (edge_data e : this.Graph.getE(this.robo.getSrc())) {
					if (nextNode.getKey() == e.getDest()) {
						this.game.chooseNextEdge(this.robo.getId(), nextNode.getKey());
						// this.click = 0;
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "No path Assoiacated");
				// this.click = 0;
			}
		}
		if (this.click == 1)
			this.click = 0;
		else {
			this.click++;
		}
		System.out.println(this.click);
	}

	/**
	 * {@code called after mouse pressed and move by click to move in run function when the game starts}
	 * 
	 * @param list
	 */
	private void moveRobotsToDest() {
		List<String> move = this.game.move();
		if (move != null) {
			this.robots.list(move);
			for (Robots r : this.robots.robots) {
				if (r.getDest() == -1) {
					this.game.move();
				}
			}
		}
		this.game.move();
	}

	/**
	 * {@code used to get the robots by destinations of click we got from the user}
	 * 
	 * @param list,point
	 */
	private node_data getTheRobot(double x, double y) {
		this.robots.list(this.game.getRobots());
		Point3D p = new Point3D(x, y, 0);
		for (Robots n : this.robots.robots) {
			if (p.distance2D(n.getLocation()) < 0.005) {
				System.out.println(p.distance2D(n.getLocation()) < 0.005);
				Point3D p2 = new Point3D(n.getLocation());
				System.out.println(n.getSrc());
				node_data data = new NodeData(p2);
				this.robo = n;
				return data;
			}
		}
		return null;
	}

	/**
	 * {@code this function is used to get the final score after the game is over}
	 * 
	 * @param Integer
	 * @return score
	 */
	public double Score(game_service server) {
		double Score = 0;
		try {
			String json = server.toString();
			JSONObject gameJson = new JSONObject(json);
			JSONObject game = gameJson.getJSONObject("GameServer");
			Score = game.getDouble("grade");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Score;
	}

	/**
	 * {@code this function is used to draw the game again with help of Double buffering that is useful 
	 * for animations like this game}
	 * 
	 * @param miny
	 *            minx maxX maxY to draw the game and setting the pictures form
	 *            A0-A10
	 */
	public void Draw(graph d) {
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
		double ScaleX = (maxX - minX) * 0.04;
		StdDraw.setXscale(minX - 0.002, maxX + 0.002);
		StdDraw.setYscale(minY - 0.002, maxY + 0.002);
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.setPenRadius(0.05);
		String k = this.game.toString();
		StdDraw.picture((minX + maxX) / 2, (maxY + minY) / 2, "data/pacman.png", 0.0289, 0.02);
		StdDraw.setPenColor(Color.RED);
		StdDraw.text((minX + maxX) / 2, (maxY + minY) / 2 + 0.006, "Time to End:" + this.game.timeToEnd() / 1000);
		StdDraw.text((minX + maxX) / 2, (maxY + minY) / 2 + 0.0055, "Your Score is:" + this.Score(this.game));
		Iterator<node_data> it1 = d.getV().iterator();
		StdDraw.setPenColor(Color.BLUE);
		while (it1.hasNext()) {
			node_data data = (node_data) it1.next();
			Point3D p1 = data.getLocation();
			StdDraw.filledCircle(p1.x(), p1.y(), ScaleX * 0.1);
			StdDraw.text(p1.x(), p1.y() + (((maxX - minX) * 0.04) * 0.2), "" + data.getKey());
		}
		StdDraw.setPenRadius(0.005);
		Iterator<node_data> edges = d.getV().iterator();
		while (edges.hasNext()) {
			node_data temp1 = (node_data) edges.next();
			if (d.getE(temp1.getKey()) != null) {
				Iterator<edge_data> ed = d.getE(temp1.getKey()).iterator();
				while (ed.hasNext()) {
					StdDraw.setPenColor(Color.LIGHT_GRAY);
					edge_data edge = (edge_data) ed.next();
					node_data n1 = d.getNode(edge.getSrc());
					node_data n2 = d.getNode(edge.getDest());
					Point3D p1 = n1.getLocation();
					Point3D p2 = n2.getLocation();
					StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
					double x = 0.2 * p1.x() + 0.8 * p2.x();
					double y = 0.2 * p1.y() + 0.8 * p2.y();
					StdDraw.setPenColor(Color.BLUE);
					// to enable just two digits
					double weight = Math.round(edge.getWeight() * 100.0) / 100.0;
					StdDraw.text(x, y, "" + weight);
					StdDraw.setPenColor(Color.YELLOW);
					double x1 = 0.1 * p1.x() + 0.9 * p2.x();
					double y1 = 0.1 * p1.y() + 0.9 * p2.y();
					StdDraw.filledCircle(x1, y1, ScaleX * 0.1);
				}
			}
		}
	}

	/**
	 * {@code we use this function to refresh the graph while the game is running}
	 * 
	 * @functions used is Draw the DGraph
	 */
	public void Refresh() {
		StdDraw.enableDoubleBuffering();
		this.Draw(this.Graph);
	}

	public String readfiletostring() {
		String remark = this.num + ".kml";
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(remark));
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
			br.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * {@code used to move the robots by function move of the server}
	 * 
	 * @param list
	 */
	private void moveRobots() {
		List<String> Robots = this.game.move();
		if (Robots != null) {
			this.robots.list(Robots);
			for (Robots r : this.robots.robots) {
				if (r.getDest() == -1)
					this.game_algo.next(r, this.Graph);
				for (edge_data e : this.game_algo.getListOfEdgeF()) {
					if (r.getSrc() == e.getSrc() && r.getDest() == e.getDest()) {
						node_data ds = this.Graph.getNode(e.getDest());
						node_data sr = this.Graph.getNode(e.getSrc());
						Point3D Psr = sr.getLocation();
						Point3D Pds = ds.getLocation();
						Point3D Pro = r.getLocation();
						double n = Pro.distance2D(Pds) / Psr.distance2D(Pds);
						double nn = (n * e.getWeight()) / r.getSpeed();
						dt = 130;

					}
				}
			}
		}
	}

	/**
	 * {@code this function starts the game}
	 * 
	 */

	public void run() {
		while (this.game.isRunning()) {
			FruitsDraw();
			RobotsDraw();
			if (this.autoGaming == true)
				moveRobots();
			if (this.menualGaming == true)
				moveRobotsToDest();
			StdDraw.show();
			this.Refresh();
			try {
				Thread.sleep(dt);
				dt = 60;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Your Score is : " + Score(this.game));
	}

	public static void main(String[] args) {
		MyGameGUI g = new MyGameGUI();
	}

}