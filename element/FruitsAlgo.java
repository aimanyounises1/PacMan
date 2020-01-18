package element;

import Server.Game_Server;
import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

public class FruitsAlgo {

    public List<Fruits> fruits ;
    private int size;
    private game_service num;

     public FruitsAlgo() {
        this.fruits = new LinkedList<>();
        this.size = fruits.size();
        this.num = null;
    }
     public List<Fruits> FruitList(List<String> temp) {
         for (String f : temp) {
             Fruits fr = new Fruits();
             fr = (Fruits) fr.init(f);
             this.fruits.add(fr);
         }
         this.size = this.fruits.size();
         return this.fruits;
     }
    public int getSizeFruits() {
        int CountFruits = 0;
        try {
            String gameString = this.num.toString();
            JSONObject gameJson = new JSONObject(gameString);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            CountFruits = gameServer.getInt("fruits");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return CountFruits;
    }

    public FruitsAlgo(game_service numGame) {
        this.num = numGame;
        this.size = this.getSizeFruits();
        this.fruits = new LinkedList<>();
        this.fruits = FruitList(this.num.getFruits());
    }

   
   
}
