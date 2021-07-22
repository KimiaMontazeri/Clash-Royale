package ClashRoyale.model.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayersArchieve implements Serializable {   ////singleton

    private static PlayersArchieve instance = null;
    private Player currentPlayer=null;
    private ArrayList<Player> playersArchieve =new ArrayList<>();

    public static PlayersArchieve getInstance()
    {
        if (instance == null) {
            instance = new PlayersArchieve();
            return instance;
        }
        return instance;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Player> getPlayersArchieve() {
        return playersArchieve;
    }

    public void setPlayersArchieve(ArrayList<Player> playersArchieve) {
        this.playersArchieve = playersArchieve;
    }

    public static void setInstance(PlayersArchieve instance) {
        PlayersArchieve.instance = instance;
    }

    public boolean isAvailable(String username){
        for (Player player : playersArchieve) {
            if (player.getUsername().equals (username)) {
                return true;
            }
        }
        return false;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getPlayerByUserName(String username){
        for (Player player : playersArchieve) {
            if (player.getUsername().equals (username)) {
                return player;
            }
        }
        return null;
    }

}
