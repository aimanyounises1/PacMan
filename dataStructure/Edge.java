package dataStructure;

import java.io.Serializable;

public class Edge implements edge_data,Serializable{

    private int Src;
    private int Dest;
    private int Tag;
    private double Weight;
    private String Info;

    /**
     * a default constructor
     */
    public Edge(){
        this.Src = 0;
        this.Dest = 0;
        this.Tag = 0;
        this.Weight = 0;
        this.Info = null;
    }

    /**
     * * constructor of new edge.
     * @param Src- the key of the src node
     * @param Dest - the key of the dest node
     * @param Weight - the weight of the edge
     */
    public Edge(int Src, int Dest, double Weight){
        if(Weight<0) throw new RuntimeException("The weight must be positive");
        this.Src = Src;
        this.Dest = Dest;
        this.Weight = Weight;
    }

    /**
     * Copy constructor
     * @param e - the edge for copy
     */
    public Edge(Edge e){
        this.Src = e.Src;
        this.Dest = e.Dest;
        this.Tag = e.Tag;
        this.Weight = e.Weight;
        this.Info = e.Info;
    }

    /**
     * @return the key of the node src
     */
    @Override
    public int getSrc() {
        return this.Src;
    }

    /**
     * @return the key of the node dest
     */
    @Override
    public int getDest() {
        return this.Dest;
    }

    /**
     * @return the weight of the edge
     */
    @Override
    public double getWeight() {
        return this.Weight;
    }

    /**
     * @return the info of the edge
     */
    @Override
    public String getInfo() {
        return this.Info;
    }

    /**
     * the method change the info of the edge
     * @param s - new string for the info
     */
    @Override
    public void setInfo(String s) {
        this.Info = s;
    }

    /**
     * @return the tag of the edge
     */
    @Override
    public int getTag() {
        return this.Tag;
    }

    /**
     * the method change the tag of the edge
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.Tag = t;
    }

    /**
     * @return String that represent the edge.
     */
    public String toString (){
        return "Src =" + this.getSrc() + ", " + "Dest = " + this.getDest() + ", " + "Weight =" + this.getWeight();
    }
}
