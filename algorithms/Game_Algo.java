package algorithms;

import Server.game_service;
import dataStructure.*;
import element.Fruits;
import element.FruitsAlgo;
import element.Robots;
import element.RobotsAlgo;

import java.util.LinkedList;
import java.util.List;


public class Game_Algo {

    private DGraph graph;
    private FruitsAlgo fruits;
    private game_service server;
    public int RobotsSize;

/**
 * @Default constructor
 * @param game
 */
    public Game_Algo(game_service game) {
        this.server = game;
        String graph = this.server.getGraph();
        this.graph = new DGraph();
        this.graph.init(graph);
        this.fruits = new FruitsAlgo(this.server);
        RobotsAlgo robots = new RobotsAlgo(this.server);
        this.RobotsSize = robots.RobotSize();
    }
/**
 * 
 * @param fruit
 * @return the edge of the fruit
 */
    public edge_data getEdgeOfFruit(Fruits fruit) {
        for (node_data n : this.graph.getV()) {
            if (this.graph.getE(n.getKey()) != null) {
            	for (edge_data e : this.graph.getE(n.getKey())) {
                    node_data dest = this.graph.getNode(e.getDest());
                    node_data src = this.graph.getNode(e.getSrc());
                     double dis = src.getLocation().distance2D(dest.getLocation());
                	 double dis2 = src.getLocation().distance2D(fruit.getLocation());
                	 double dis3= fruit.getLocation().distance2D(dest.getLocation());
                    if (dis2 + dis3 <= dis + 0.0001) {
                        if (fruit.getType() == -1 && src.getKey() > dest.getKey()) {
                            return e;
                        } else if (fruit.getType() == 1 && src.getKey() < dest.getKey()) {
                            return e;
                        }
                    }
                }
            }
        }
        return null;
    }   
/**
 * 
 * @return list of fruits
 */
    public List<edge_data> getListOfEdgeF() {
        List<edge_data> edgeOfFruit = new LinkedList<>();
        System.out.println("this.server: " + this.server.toString());
        this.fruits = new FruitsAlgo(this.server);
        for (Fruits f : this.fruits.fruits) {
            edgeOfFruit.add(getEdgeOfFruit(f));
        }
        return edgeOfFruit;
    }
/**
 *@method to remove edge of fruit when the robot is on it
 */
    public void locationRobot() {
        List<edge_data> edgeOfFruit = getListOfEdgeF();
        for (int i = 0; i < this.RobotsSize; i++) {
            double min = Integer.MAX_VALUE;
            edge_data ans = new EdgeData();
            for (edge_data e : edgeOfFruit) {
                if (e.getWeight() < min) {
                    min = e.getWeight();
                    ans = e;
                }
            }
            this.server.addRobot(this.graph.getNode(ans.getSrc()).getKey());
            edgeOfFruit.remove(ans);
        }
    }
    /**
     * @Descrptions we used shortest path algorithm to track the fruits
     * by getting the edges of the fruits or in another words we found out between which vertexs 
     * we have a fruits, and we started the shortest path algorithm to the src of this fruit
     * then the destination of this fruits and thats it ;) 
     * @param 
     * @param graphGame
     */
    public void next(Robots r, DGraph graphGame) {
        Graph_Algo g = new Graph_Algo();
        g.init(graphGame);
        List<edge_data> edgeOfFruit = getListOfEdgeF();
        edge_data minDest = new EdgeData();
        double min = Integer.MAX_VALUE;
        for (edge_data e : edgeOfFruit) {
            double temp = g.shortestPathDist(r.getSrc(), e.getSrc());
            if (temp < min) {
                min = temp;
                minDest = e;
            }
        }
        List<node_data> Path = g.shortestPath(r.getSrc(), minDest.getSrc());
        Path.add(this.graph.getNode(minDest.getDest()));
        if (Path.size() > 1) {
            if (r.getLocation().equalsXY(this.graph.getNode(minDest.getSrc()).getLocation())){
               this.server.chooseNextEdge(r.getId(), minDest.getDest());
            }
           
           else this.server.chooseNextEdge(r.getId(), Path.get(1).getKey());
           this.server.move();
        }
    }
    
   
}
