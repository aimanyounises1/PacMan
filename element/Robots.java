package element;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class Robots  {
	 private int src;
	    private int dest;
	    private int id;
	    private Point3D pos;
	    private int speed;
	    private double value;
	    int destnode;
	    /** 
	      *@Default Constructor
	      */
	    public Robots(){
	        this.src = 0;
	        this.dest = 0;
	        this.id = 0;
	        this.pos = null;
	        this.speed = 0;
	        this.value = 0;
	        destnode=Integer.MIN_VALUE;
	       
    }
	    /** 
	      * @param Robot
	      * @return a new robots from json string
	      */

    public Robots init(String json) {
        Robots temp = new Robots();
        try {
            JSONObject robot = new JSONObject(json);
            JSONObject robott = robot.getJSONObject("Robot");
            temp.src = robott.getInt("src");
            temp.dest = robott.getInt("dest");
            temp.value = robott.getDouble("value");
            temp.id = robott.getInt("id");
            String pos = robott.getString("pos");
            temp.pos = new Point3D(pos);
            temp.speed = robott.getInt("speed");
            destnode=Integer.MIN_VALUE;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }
    

    /** 
      * @param void
      * @return final destination of the robot to get the fruit
      */
    public int getdestnode()
    {
    	return this.destnode;
    }

    /** 
     *  
     * @return id of robot
     */
    public int getId() {
        return this.id;    }

    public Point3D getLocation() {
        return this.pos;
    }

    public void setLocation(Point3D p) {
        this.pos = p;

    }

    public int getSrc(){
        return this.src;
    }

    public void setSrc(int s){
        this.src = s;
    }

    public int getDest(){
        return this.dest;
    }

    public void setDest(int d){
        this.dest = d;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int sp){
        this.speed=sp;
    }

    public double getValue(){
        return this.value;
    }

    public void setValue(double v){
        this.value = v;
    }
    public void setdestnode(int d)
    {
    	this.destnode=d;
    }
    public void setid(int id)
    {
    	this.id=id;
    }
    
    public boolean equals(Robots ro)
    {
    	if(this.src!=ro.src||this.dest!=ro.dest||this.id!=ro.id||this.speed!=ro.speed||!this.pos.equals(ro.pos))
    		return false;
    	return true;
    }
    

}
