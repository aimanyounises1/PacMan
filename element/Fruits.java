package element;

import Server.Game_Server;
import Server.game_service;
//import com.google.gson.annotations.JsonAdapter;
import dataStructure.EdgeData;
import dataStructure.edge_data;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class Fruits {

    private Point3D pos;
    private double value;
    private int type;
    private String pic;

    public Fruits() {
        this.pos = null;
        this.value = 0;
        this.type = 0;
        this.pic = null;
    }

    public String toString(){
        return "pos:"+ pos.toString()+ "\n" + "value:"+ this.value+ "\n" + "type:" + this.type;
    }

    public Fruits init(String json) {
        Fruits temp = new Fruits();
        try {
            JSONObject fruit = new JSONObject(json);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            temp.type = fruitt.getInt("type");
            temp.value = fruitt.getDouble("value");
            if(temp.type==1) temp.pic="apple.png";
            else if(temp.type==-1) temp.pic= "banana.png";
            else temp.pic = "";
            String pos = fruitt.getString("pos");
            temp.pos = new Point3D(pos);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public Point3D getLocation() {
        return this.pos;
    }

    public void setLocation(Point3D p) {
        this.pos = p;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double v) {
        this.value = v;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int t) {
        this.type = t;
    }
}