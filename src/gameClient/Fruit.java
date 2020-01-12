package gameClient;

import utils.Point3D;
/*this class represnts a fruit either a bana or an apple banana or apple
the data is provided by json files for this class
the fruit has a value and a location point*/
public class Fruit {
	
	double value;
	Point3D p;
	int type;
	
	public Fruit()
	{
		
	}
	
	public Fruit(double vlue, int x,Point3D p)
	{
		this.value=vlue;
		this.p=p;
		this.type=x;
	}
	public int gettype()
	{
		return this.type;
	}
	
	public double getvalue() {
		return this.value;
	}
	public Point3D getlocation()
	{
		return this.p;
	}
	
	public String toString()
	{
		return "Fruit:{"+"value:"+this.value+",type:"+this.type+",pos:"+this.p+"}";
	}
	
	

}
