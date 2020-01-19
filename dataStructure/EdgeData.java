package dataStructure;

import java.io.Serializable;

public class EdgeData implements edge_data{

    private int Src;
    private int Dest;
    private int tag;
    private double weight;
    private String info;

   /**
    * @Default constructor
    */
    public EdgeData(){
        this.Src = 0;
        this.Dest = 0;
        this.tag = 0;
        this.weight = 0;
        this.info = null;
    }

   
   /**
    * @Copy Constructor
    * @param e
    */
    public EdgeData(EdgeData e){
        this.Src = e.Src;
        this.Dest = e.Dest;
        this.tag = e.tag;
        this.weight = e.weight;
        this.info = e.info;
    }
    /**
     * @Constructor
     * @param Src
     * @param Dest
     * @param Weight
     */
     public EdgeData(int Src, int Dest, double Weight){
         this.Src = Src;
         this.Dest = Dest;
         this.weight = Weight;
     }
   
    @Override
    public int getSrc() {
        return this.Src;
    }

    
    @Override
    public int getDest() {
        return this.Dest;
    }

    
    @Override
    public double getWeight() {
        return this.weight;
    }

    
    @Override
    public String getInfo() {
        return this.info;
    }

   
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

  
    @Override
    public int getTag() {
        return this.tag;
    }

   
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

   
    public String toString (){
        return "Src =" + this.getSrc() + ", " + "Dest = " + this.getDest() + ", " + "Weight =" + this.getWeight();
    }
}
