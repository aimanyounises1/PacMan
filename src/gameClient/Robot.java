package gameClient;

import utils.Point3D;


/*this class respresents a robot the data is of this robot is provided by json files.
the robot has an id
the robot has a src which is the node its currently standing on and a dst which is the node its going to
the robot has a location point
the robot has to collect a score from the fruits*/

public class Robot {
	int key;
	int src;
	int dst;
	double sum=0;
	Point3D location;
	
	
	
	public Robot()
	{
		
	}
	
	public Robot(int key,int src,int dst, double sum,Point3D p)
	{
		this.key=key;
		this.src=src;
		this.dst=dst;
		this.sum=sum;
		this.location=p;
	}
	public int getsrc()
	{
		return src;
	}
	public int getdest()
	{
		return dst;
		
	}
	public double getvalue()
	{
		return sum;
	}
	public Point3D getlocation()
	{
		return location;
		
	}
	public String toString() {
		
	
	return "Robot:{id:"+this.key+",value:"+this.sum+",src:"+this.src+",des:"+this.dst+",pos:"+this.location+"}";
	}
	

}
