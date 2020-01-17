package algorithms;

import dataStructure.*;
import utils.Point3D;
import java.io.*;
import java.util.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable {

	public graph algo;

	public Graph_Algo(){
		this.algo = new DGraph();
	}

	public Graph_Algo(graph d){
		init(d);
	}

	/**
	 * This method get a graph and make him to the algo graph.
	 * @param g -the algo graph
	 */
	@Override
	public void init(graph g) {
		this.algo=g;
	}


	/**
	 * This method get a file name of graph and make him to the algo graph.
	 * @param file_name -the name of the file
	 */
	@Override
	public void init(String file_name) {
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);
			this.algo = (graph) in.readObject();
			in.close();
			file.close();
		}
		catch(IOException ex) {
			System.out.println("IOException is caught");
		}
		catch(ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}
	}

	/**
	 * this methods save the graph as a file
	 * @param file_name - to save withe this name on the computer
	 */
	@Override
	public void save(String file_name) {
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(this.algo);
			out.close();
			file.close();
		}
		catch (IOException ex) {
			System.out.println("IOException is caught");
		}
	}

	/**
	 * @return true if exist a valid path from EVREY node to each else false.
	 */
	@Override
	public boolean isConnected() {
		if(this.algo.nodeSize()==1 || this.algo.nodeSize()==0) return true;
		graph ans = this.copy();
		changeTagNode(ans);
		Iterator it = ans.getV().iterator();
		dfs(ans,(node_data) it.next());
		it = ans.getV().iterator();
		while (it.hasNext()) {
			node_data temp = (node_data) it.next();
			if (temp.getTag() == 0) return false;
		}
		changeTagEdge(ans);
		changeTagNode(ans);
		oppositeDest(ans);
		it = ans.getV().iterator();
		dfs(ans,(node_data) it.next());
		it = ans.getV().iterator();
		while (it.hasNext()) {
			node_data temp = (node_data) it.next();
			if (temp.getTag() == 0) return false;
		}
		return true;
	}

	public void dfs(graph copied,node_data n) {
		if (copied.getE(n.getKey()) != null) {
			Iterator it = copied.getE(n.getKey()).iterator();
			while (it.hasNext()) {
				edge_data e = (edge_data) it.next();
				node_data dest = copied.getNode(e.getDest());
				if (dest.getTag() == 0) {
					dest.setTag(1);
					dfs(copied,dest);
				}
			}
		}
	}


	public void oppositeDest(graph d) {
		Iterator it = d.getV().iterator();
		while (it.hasNext()) {
			node_data n = (node_data) it.next();
			if (d.getE(n.getKey()) != null) {
				Iterator it1 = d.getE(n.getKey()).iterator();
				while (it1.hasNext()) {
					edge_data e = (edge_data) it1.next();
					if (e.getTag() == 0) {
						if (d.getEdge(e.getDest(), e.getSrc()) != null) {
							Edge temps = new Edge((Edge) d.getEdge(e.getSrc(), e.getDest()));
							double tempWeight1 = d.getEdge(e.getSrc(), e.getDest()).getWeight();
							double tempWeight2 = d.getEdge(e.getDest(), e.getSrc()).getWeight();
							d.connect(e.getSrc(), e.getDest(), tempWeight2);
							d.connect(temps.getDest(), temps.getSrc(), tempWeight1);
							d.getEdge(temps.getDest(), temps.getSrc()).setTag(1);
							d.getEdge(temps.getSrc(), temps.getDest()).setTag(1);
							it1 = d.getE(n.getKey()).iterator();
						}
						else {
							d.connect(e.getDest(), e.getSrc(), e.getWeight());
							d.removeEdge(e.getSrc(), e.getDest());
							d.getEdge(e.getDest(), e.getSrc()).setTag(1);
							it1 = d.getE(n.getKey()).iterator();
						}
					}
				}
			}
		}
	}



	public void changeTagNode(graph g) {
		Iterator it = g.getV().iterator();
		while (it.hasNext()) {
			node_data n = (node_data) it.next();
			n.setTag(0);
		}
	}


	public void changeTagEdge(graph g) {
		Iterator it = g.getV().iterator();
		while (it.hasNext()) {
			node_data n = (node_data) it.next();
			if (g.getE(n.getKey()) != null) {
				Iterator itEdge = g.getE(n.getKey()).iterator();
				while (itEdge.hasNext()) {
					edge_data e = (edge_data) itEdge.next();
					e.setTag(0);
				}
			}
		}
	}

	/**
	 * This method get Src and Dest that represented by the key of the desired nodes.
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return the length of the shortest path between src to dest
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(src==dest) return 0;
		if(this.algo.getNode(src)==null || this.algo.getNode(dest)==null){
			throw new RuntimeException("This nodes are not exist");
		}
		changeTagNode(this.algo);
		Iterator it = this.algo.getV().iterator();
		while (it.hasNext()) {
			node_data n = (node_data) it.next();
			n.setWeight(Integer.MAX_VALUE);
		}
		node_data temp = this.algo.getNode(src);
		temp.setWeight(0);
		shortestPathDistRec(temp, this.algo.getNode(dest));
		return this.algo.getNode(dest).getWeight();
	}

	// help methods for the shortestPathDist method.
	public void shortestPathDistRec(node_data n, node_data dest) {
		if (n.getTag() == 1 && n.getKey() == dest.getKey()) {
			return;
		}
		if (this.algo.getE(n.getKey()) != null) {
			Iterator it = this.algo.getE(n.getKey()).iterator();
			while (it.hasNext()) {
				edge_data e = (edge_data) it.next();
				node_data d = this.algo.getNode(e.getDest());
				if (this.algo.getNode(e.getSrc()).getWeight() + e.getWeight() < d.getWeight()) {
					d.setWeight(this.algo.getNode(e.getSrc()).getWeight() + e.getWeight());
					n.setTag(1);
					shortestPathDistRec(this.algo.getNode(e.getDest()), dest);
				}
			}
		}
	}

	/**
	 *This method get Src and Dest that represented by the key of the desired node
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return the vertexes that should pass in the shortest path.
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> ans = new LinkedList<>();
		this.shortestPathDist(src,dest);
		if(this.algo.getNode(src).getWeight()==Integer.MAX_VALUE  || this.algo.getNode(dest).getWeight()==Integer.MAX_VALUE){
			System.out.print("There is no a path between this nodes. ");
			return null;
		}
		graph tempGraph = this.copy();
		node_data min = tempGraph.getNode(dest);
		oppositeDest(tempGraph);
		ans.add(min);
		while (min.getKey()!=src){
			Collection<edge_data> coll =tempGraph.getE(min.getKey());
			if(coll!=null) {
				for (edge_data e : coll) {
					node_data temp = tempGraph.getNode(e.getDest());
					if (temp.getWeight() + e.getWeight() == min.getWeight()) {
						min = temp;
					}
				}
			}
			ans.add(min);
		}
		List<node_data> temp = new LinkedList<>();
		for (int i = ans.size()-1; i >=0 ; i--) {
			temp.add(ans.get(i));
		}
		ans=temp;
		return ans;
	}

	/**
	 * @param targets - group of Vertexe
	 * @return the most optional Path to visit each node in the targets List
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		List<Integer> targets2 = new LinkedList<>();
		for (int i = 0; i <targets.size() ; i++) {
			if(!targets2.contains(targets.get(i))) targets2.add(targets.get(i));
		}
		targets=targets2;
		List<node_data> ans = new LinkedList<>();
		if(targets.size()==1){
			ans.add(this.algo.getNode(targets.get(0)));
			return ans;
		}
		double tempshortestPath = 0;
		int tempk1 = 0;
		int tempk2 = 0;
		int k1 = targets.get(0);
		while (targets.size()!=0) {
			double minshortestPath = Integer.MAX_VALUE;
			for (int j = 0; j < targets.size(); j++) {
				int k2 = targets.get(j);
				if (k1 != k2) {
					tempshortestPath = minshortestPath;
					if (this.shortestPathDist(k1, k2) == Integer.MAX_VALUE) return null;
					minshortestPath = Math.min(minshortestPath, this.shortestPathDist(k1, k2));
					if (tempshortestPath != minshortestPath) {
						tempk1 = k1;
						tempk2 = k2;
					}
				}
			}
			List<node_data> add = this.shortestPath(tempk1, tempk2);
			for (int j = 0; j < add.size(); j++) {
				node_data n = add.get(j);
				if (ans.size() == 0 || ans.get(ans.size() - 1).getKey()!=n.getKey()) {
					ans.add(n);
				}
				for (int k = 0; k < targets.size(); k++) {
					int k4 = targets.get(k);
					if (n.getKey() == k4) {
						targets.remove(k);
					}
				}
			}
			k1 = tempk2;
		}
		return ans;
	}

	/**
	 * This method compute a deep copy of this graph.
	 * @return the copy graph
	 */
	@Override
	public graph copy() {
		graph ans = new DGraph();
		for (node_data n : this.algo.getV()) {
			node_data temp = new Node((Node) n);
			ans.addNode(temp);
		}
		for (node_data n : ans.getV()) {
			Collection<edge_data> coll = this.algo.getE(n.getKey());
			if(coll!=null) {
				for (edge_data e : this.algo.getE(n.getKey())) {
					edge_data temp = new Edge((Edge) e);
					ans.connect(temp.getSrc(), temp.getDest(), temp.getWeight());
				}
			}
		}
		return ans;
	}
}