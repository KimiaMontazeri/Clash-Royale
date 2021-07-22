package ClashRoyale.model.elements;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An archive that holds all the game's players' accounts
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class PlayersArchieve implements Serializable {   ////singleton

    private static PlayersArchieve instance = null;
    private Player currentPlayer=null;
    private ArrayList<Player> playersArchieve =new ArrayList<>();

    /**
     * @return the only instance of this class
     */
    public static PlayersArchieve getInstance()
    {
        if (instance == null) {
            instance = new PlayersArchieve();
            return instance;
        }
        return instance;
    }

    /**
     * @return current player that has signed in
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @return an ArrayList of all the players
     */
    public ArrayList<Player> getPlayersArchieve() {
        return playersArchieve;
    }

    /**
     * Sets the list of players
     * @param playersArchieve list of players
     */
    public void setPlayersArchieve(ArrayList<Player> playersArchieve) {
        this.playersArchieve = playersArchieve;
    }

    /**
     * Sets the instance of this class (used for reading from database)
     * @param instance instance
     */
    public static void setInstance(PlayersArchieve instance) {
        PlayersArchieve.instance = instance;
    }

    /**
     * Checks if this username exists in the archive or not
     * @param username username to check
     * @return true if the username exists
     */
    public boolean isAvailable(String username){
        for (Player player : playersArchieve) {
            if (player.getUsername().equals (username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the current player
     * @param currentPlayer current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Finds a player in the archive using its username
     * @param username username
     * @return player that has been found
     */
    public Player getPlayerByUserName(String username){
        for (Player player : playersArchieve) {
            if (player.getUsername().equals (username)) {
                return player;
            }
        }
        return null;
    }

}
