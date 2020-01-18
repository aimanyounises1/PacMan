package element;


//import com.google.gson.annotations.JsonAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class Fruits {

    private Point3D pos;
    private double value;
    private int type;

    public Fruits() {
        this.pos = null;
        this.value = 0;
        this.type = 0;
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
