package dataStructure;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DGraph implements graph, Serializable {

	private HashMap<Integer, node_data> nodes = new HashMap<>();                   //integer for key, node for value
	private HashMap<Integer, HashMap<Integer,edge_data>> edges = new HashMap<>();  //integer for src, hashmap for dest
	private int nodesCount = 0;
	private int edgesCount = 0;
	private int modeCount = 0;

	private void init() {
		this.nodes = new HashMap();
		this.edges = new HashMap();
	}

	/**
	 * @param key - the node_id
	 * @return a specific node identify by the key from the graph
	 */
	@Override
	public node_data getNode(int key) {
		try {
			return this.nodes.get(key);
		}
		catch (NullPointerException e){
			return null;
		}
	}

	/**
	 * @param src of the edge
	 * @param dest of the edge
	 * @return This method get Src and Dest that represented by the key of the desired nodes and bring the edge
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		try{
			return this.edges.get(src).get(dest);
		}
		catch (NullPointerException e){
			return null;
		}
	}

	/**
	 * This method get node from the user and add it to the grph.
	 * @param n - the new node
	 */
	@Override
	public void addNode(node_data n) {
		this.nodes.put(n.getKey(),n);
		nodesCount++;
		modeCount++;
	}

	/**
	 * This method get Src and Dest that represented by the key of the
	 * desired nodes and Weight of the edge and make a new edge in the graph.
	 * @param src - the source of the edge.
	 * @param dest - the destination of the edge.
	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
	 */
	@Override
	public void connect(int src, int dest, double w) {
		edge_data temp = new Edge(src, dest, w);
		if (this.getNode(src) != null && this.getNode(dest) != null) {
			if (this.getEdge(src,dest)==null) {
				if (this.edges.get(src)== null) {
					HashMap<Integer,edge_data> add = new HashMap<>();
					this.edges.put(src,add);
					this.edges.get(src).put(dest,temp);
				}
				else{
					this.edges.get(src).put(dest,temp);
				}
			}
			else{
				this.removeEdge(src,dest);
				this.connect(src, dest, w);
			}
			edgesCount++;
			modeCount++;
		}
		else{
			System.err.println("ERR: The src and dest must be exist");
		}
	}

	/**
	 * @return the nodes collection of the graph
	 */
	@Override
	public Collection<node_data> getV() {
		return this.nodes.values();
	}

	/**
	 *
	 * @param node_id - the key of node
	 * @return edges collection tha connect to this node
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		try {
			return this.edges.get(node_id).values();
		}
		catch (NullPointerException e){
			return null;
		}
	}

	/**
	 *
	 * @param key - of node for delete
	 * @return the deleted node
	 */
	@Override
	public node_data removeNode(int key) {
		node_data nd = this.nodes.get(key);
		if(nd!=null) {
			this.nodes.remove(key);
			if (this.edges.get(key) != null) {
				this.edges.remove(key);
			}
			Iterator it = this.edges.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry mp = (Map.Entry) it.next();           //give the key
				int temp = ((int) mp.getKey());                 //save the key
				if (this.edges.get(temp).get(key) != null) {
					removeEdge(temp, key);
				}
			}
			this.nodesCount--;
			this.modeCount++;
		}
		return nd;
	}

	/**
	 *
	 * @param src of the edge
	 * @param dest of the edge
	 * @return the deleted edge
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		edge_data ed = this.getEdge(src,dest);
		if(ed!=null) {
			this.edges.get(src).remove(dest);
			this.edgesCount--;
			this.modeCount++;
		}
		return ed;
	}

	/**
	 * @return the nodesCount.
	 */
	@Override
	public int nodeSize() {
		return this.nodesCount;
	}

	/**
	 * @return the edgesCount.
	 */
	@Override
	public int edgeSize() {
		return this.edgesCount;
	}

	/**
	 * @return the modeCount
	 */
	@Override
	public int getMC() {
		return this.modeCount;
	}

	public void init(String jsonSTR) {
		try {

			this.nodesCount = 0;
			this.edgesCount = 0;
			JSONObject graph = new JSONObject(jsonSTR);
			JSONArray Jnodes = graph.getJSONArray("Nodes");
			JSONArray Jedges = graph.getJSONArray("Edges");
			int i;
			int s;
			for(i = 0; i < Jnodes.length(); ++i) {
				s = Jnodes.getJSONObject(i).getInt("id");
				String pos = Jnodes.getJSONObject(i).getString("pos");
				Point3D p = new Point3D(pos);
				this.addNode(new Node(s, p));
			}
			for(i = 0; i < Jedges.length(); ++i) {
				s = Jedges.getJSONObject(i).getInt("src");
				int d = Jedges.getJSONObject(i).getInt("dest");
				double w = Jedges.getJSONObject(i).getDouble("w");
				this.connect(s, d, w);
			}
		} catch (Exception var10) {
			var10.printStackTrace();
		}
	}
}

