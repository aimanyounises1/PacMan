package dataStructure;

import utils.Point3D;

import java.io.Serializable;

public class Node implements node_data, Serializable {

    private int Key;
    private int Tag;
    private double Weight;
    private String Info;
    private Point3D Location;
    public static int KeyCount=1;

    /**
     * a default constructor
     */
    public Node() {
        this.Key = KeyCount++;
        this.Tag = 0;
        this.Weight = 0;
        this.Info = null;
        this.Location = null;
    }

    /**
     * constructor of new node.
     * @param Location -get the location of the node on x/y/z axis
     */
    public Node(Point3D Location) {
        this.Location = new Point3D(Location);
        this.Key=KeyCount++;
        this.Tag = 0;
        this.Weight = 0;
        this.Info = null;
    }

    public Node(int key, Point3D Location) {
        this.Location = new Point3D(Location);
        this.Key=key;
        this.Tag = 0;
        this.Weight = 0;
        this.Info = null;
    }

    /**
     * Copy constructor
     * @param n - the node for copy
     */
    public Node(Node n) {
        this.Key = n.Key;
        this.Tag = n.Tag;
        this.Weight = n.Weight;
        this.Info = n.Info;
        this.Location = new Point3D(n.Location);
    }

    /**
     * @return the key of the node
     */
    @Override
    public int getKey() {
        return this.Key;
    }

    /**
     * @return the location of the node
     */
    @Override
    public Point3D getLocation() {
        return this.Location;
    }

    /**
     * the method change the kocation of the node
     * @param p - new location  (position) of this node.
     */
    @Override
    public void setLocation(Point3D p) {
        this.Location = new Point3D(p);
    }

    /**
     * @return the weight of the node
     */
    @Override
    public double getWeight() {
        return this.Weight;
    }

    /**
     * this method change the weight of the node
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.Weight = w;
    }

    /**
     * @return the info of the node
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * this method change the info of the node
     * @param s - new info
     */
    @Override
    public void setInfo(String s) {
        this.Info = s;
    }

    /**
     * @return the tag of the node
     */
    @Override
    public int getTag() {
        return this.Tag;
    }

    /**
     * this method change the tag of the node
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;
    }

    /**
     * @return String that represent the node.
     */
    public String toString() {
        return ""+this.getKey();
    }
}
