package element;

import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class RobotsAlgo {
    public List<Robots> robots ;
    private int amountRobots;
    private game_service numGame;
    /** 
     * @constructor
     * @converting the server robots to robots  
     */
    public RobotsAlgo(game_service numGame) {
        this.numGame = numGame;
        this.amountRobots = RobotSize();
        this.robots = new LinkedList<>();
        this.robots = list(this.numGame.getRobots());
    }
    /** 
     * converting the server robots to robots  
     * used to update the server robots
     * @param list
     * @return a list f robots 
     */
    public List<Robots> list(List<String> temp) {
        List<Robots> tempR = new LinkedList<>();
        for (String r : temp) {
            Robots ro = new Robots();
            ro = (Robots) ro.init(r);
            tempR.add(ro);
        }
        this.robots = tempR;
        this.amountRobots = this.robots.size();
        return this.robots;
    }
    /** 
     * @param size
     * @return a size of robots 
     */
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
    /** 
     @return list
     */
    public List<Robots> getrobots()
    {
    	return this.robots;
    }
}
