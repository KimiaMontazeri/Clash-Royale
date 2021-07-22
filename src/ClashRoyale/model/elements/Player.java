package ClashRoyale.model.elements;

import ClashRoyale.model.elements.entities.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This class refers to a player that has an account in the game
 * Each player has a username, password, level, xp, a collection of 8 cards and some game history
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class Player implements Serializable {
    private ArrayList<Entity.Type> cards = new ArrayList<>();
    private ArrayList<History> histories = new ArrayList<>();
    private int level;
    private int xp;
    private String username;
    private String password;

    /**
     * Creates a player with the given parameters
     * @param username username
     * @param password password
     * @param level current level
     * @param xp xp
     */
    public Player(String username, String password, int level, int xp) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.xp = xp;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the list of cards
     * @param cards a list of cards
     */
    public void setCards(ArrayList<Entity.Type> cards) {
        this.cards = cards;
    }

    /**
     * Sets the list of histories
     * @param histories a list of histories
     */
    public void setHistories(ArrayList<History> histories) {
        this.histories = histories;
    }

    /**
     * Sets the level
     * @param level level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets the xp
     * @param xp xp
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Adds xp
     * @param xpToAdd xo to add
     */
    public void addXp(int xpToAdd) {
        this.xp += xpToAdd;
    }

    /**
     * @return xp
     */
    public int getXp() {
        return xp;
    }

    /**
     * @return player's cards
     */
    public ArrayList<Entity.Type> getCards() {
        return cards;
    }

    /**
     * Adds cards to the player's list of cards
     * @param type card type
     */
    public void addCard(Entity.Type type){
        cards.add(type);
    }

    /**
     * Removes the given card from the list
     * @param type card type
     */
    public void removeCard(Entity.Type type){
        cards.remove(type);
    }

    /**
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return level in string
     */
    public String  getLevelstr() {
        Integer lev = level;
        return lev.toString();
    }

    /**
     * @return the list of game histories
     */
    public ArrayList<History> getHistories() {
        return histories;
    }

    /**
     * Adds a history to player's histories
     * @param history history to add
     */
    public void addHistory(History history){
        histories.add(history);
    }

    /**
     * Compares this player to the given object
     * @param o object
     * @return true if they are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player user = (Player) o;
        boolean res1 = Objects.equals(getPassword(), user.getPassword());
        boolean res2 = Objects.equals(getUsername(), user.getUsername());
        return (res1) && (res2);
    }

    /**
     * @return player's info in a string form
     */
    @Override
    public String toString() {
        return "username: " + username + "|" + "password: " + password;
    }
}

