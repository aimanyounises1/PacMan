package gameClient;

import utils.Point3D;
/*this class represnts a fruit either a bana or an apple banana or apple
the data is provided by json files for this class
the fruit has a value and a location point*/
public class Fruit {
	
	double value;
	Point3D p;
	enum e{apple,banana};
	e name;
	
	public Fruit()
	{
		
	}
	
	public Fruit(double vlue, int x,Point3D p)
	{
		this.value=vlue;
		this.p=p;
		if(x==-1)
			this.name=e.banana;
		if(x==1)
			this.name=e.apple;
		else
			name=null;
	}
	
	

}
