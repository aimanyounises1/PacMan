package element;

import Server.Game_Server;
import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

public class Fruits {

    public List<Fruit> fruit ;
    private int amountFruits;
    private game_service numGame;

    public Fruits() {
        this.fruit = new LinkedList<>();
        this.amountFruits = fruit.size();
        this.numGame = null;
    }

    public Fruits(game_service numGame) {
        this.numGame = numGame;
        this.amountFruits = getAmountFruits();
        this.fruit = new LinkedList<>();
        this.fruit = listF(this.numGame.getFruits());
        //System.out.println("this.numGame.getFruits:" + this.numGame.getFruits()); /////////////////
    }

    public List<Fruit> listF(List<String> temp) {
        for (String f : temp) {
            Fruit fr = new Fruit();
            fr = (Fruit) fr.init(f);
            this.fruit.add(fr);
        }
        this.amountFruits = this.fruit.size();
        return this.fruit;
    }

    public int getAmountFruits() {
        int amount = 0;
        try {
            String gameString = this.numGame.toString();
            JSONObject gameJson = new JSONObject(gameString);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            amount = gameServer.getInt("fruits");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return amount;
    }
}
