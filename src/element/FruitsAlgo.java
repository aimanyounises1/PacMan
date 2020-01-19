package element;

import Server.Game_Server;
import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.tools.xjc.model.Constructor;

import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

public class FruitsAlgo {

    public List<Fruits> fruits ;
    private int size;
    private game_service num;
    
    /** 
     @Default contructor
     */
     public FruitsAlgo() {
        this.fruits = new LinkedList<>();
        this.size = fruits.size();
        this.num = null;
    }
     /** 
      * converting the server fruits to fruits  
      * @param list
      * @return a list f fruits 
      */

     public List<Fruits> FruitList(List<String> temp) {
         for (String f : temp) {
             Fruits fr = new Fruits();
             fr = (Fruits) fr.init(f);
             this.fruits.add(fr);
         }
         this.size = this.fruits.size();
         return this.fruits;
     }
     /** 
      * converting the server fruits to fruits  
      * @param count of size list
      * @return list size
      */
    public int getSizeFruits() {
        int CountFruits = 0;
        try {
            String string = this.num.toString();
            JSONObject gameJson = new JSONObject(string);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            CountFruits = gameServer.getInt("fruits");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return CountFruits;
    }
    /**  
     * @return a list f fruits 
     */
    public List<Fruits> getfruits()
    {
    	return this.fruits;
    }
    /** 
     * @converting the server fruits to fruits  by this {@link Constructor}
     * @param list
     * @return a list f fruits 
     */
    public FruitsAlgo(game_service numGame) {
        this.num = numGame;
        this.size = this.getSizeFruits();
        this.fruits = new LinkedList<>();
        this.fruits = FruitList(this.num.getFruits());
    }

   
   
}
