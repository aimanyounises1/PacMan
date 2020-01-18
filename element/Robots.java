package element;

import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Robots {

    public List<Robot> robot ;
    private int amountRobots;
    private game_service numGame;

    public Robots(game_service numGame) {
        this.numGame = numGame;
        this.amountRobots = getAmountRobots();
        this.robot = new LinkedList<>();
        this.robot = listR(this.numGame.getRobots());
        //System.out.println("this.numGame.getRobots" + this.numGame.getRobots()); /////////////////
    }

    public List<Robot> listR(List<String> temp) {
        List<Robot> tempR = new LinkedList<>();
        for (String r : temp) {
            //System.out.println("the line of the robot" + r); ////////////////
            Robot ro = new Robot();
            ro = (Robot) ro.init(r);
            tempR.add(ro);
        }
        this.robot = tempR;
        this.amountRobots = this.robot.size();
        return this.robot;
    }

    public int getAmountRobots() {
        int amount = 0;
        try {
            String gameString = this.numGame.toString();
            JSONObject gameJson = new JSONObject(gameString);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            amount = gameServer.getInt("robots");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return amount;
    }
}
