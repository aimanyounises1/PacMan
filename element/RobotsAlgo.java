package element;

import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class RobotsAlgo {
    public List<Robot> robot ;
    private int amountRobots;
    private game_service numGame;
    public RobotsAlgo(game_service numGame) {
        this.numGame = numGame;
        this.amountRobots = RobotSize();
        this.robot = new LinkedList<>();
        this.robot = list(this.numGame.getRobots());
    }

    public List<Robot> list(List<String> temp) {
        List<Robot> tempR = new LinkedList<>();
        for (String r : temp) {
            Robot ro = new Robot();
            ro = (Robot) ro.init(r);
            tempR.add(ro);
        }
        this.robot = tempR;
        this.amountRobots = this.robot.size();
        return this.robot;
    }
    public int RobotSize() {
        int size = 0;
        try {
            String gameString = this.numGame.toString();
            JSONObject gameJson = new JSONObject(gameString);
            JSONObject gameServer = gameJson.getJSONObject("GameServer");
            size = gameServer.getInt("robots");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return size;
    }
}
