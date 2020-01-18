package element;

import Server.Game_Server;
import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

public class FruitsAlgo {

    public List<Fruit> fruit ;
    private int size;
    private game_service num;

     public FruitsAlgo() {
        this.fruit = new LinkedList<>();
        this.size = fruit.size();
        this.num = null;
    }
     public List<Fruit> FruitList(List<String> temp) {
         for (String f : temp) {
             Fruit fr = new Fruit();
             fr = (Fruit) fr.init(f);
             this.fruit.add(fr);
         }
         this.size = this.fruit.size();
         return this.fruit;
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
        this.fruit = new LinkedList<>();
        this.fruit = FruitList(this.num.getFruits());
    }

   
   
}
