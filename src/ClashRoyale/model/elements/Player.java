package ClashRoyale.model.elements;

import ClashRoyale.model.elements.entities.Card;
import ClashRoyale.model.elements.entities.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements Serializable {
    private ArrayList<Entity.Type> cards = new ArrayList<>();
    private ArrayList<History> histories = new ArrayList<>();
    private int level = 1;
    private String username = "";
    private String password = "";
    private int cups = 0;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Entity.Type> getCards() {
        return cards;
    }
    public void addCard(Entity.Type type){
        cards.add(type);
    }
    public void removeCard(Entity.Type type){
        cards.remove(type);
    }

   /* public int getCups() {
        return cups;
    }*/

    public int getLevel() {
        return level;
    }
    public String  getLevelstr() {
        Integer lev = level;
        return lev.toString();
    }


    public ArrayList<History> getHistories() {
        return histories;
    }
    public void addHistory(History history){
        histories.add(history);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player user = (Player) o;
        boolean res1 = Objects.equals(getPassword(), user.getPassword());
        boolean res2 = Objects.equals(getUsername(), user.getUsername());
        return (res1) && (res2);
    }

    @Override
    public String toString() {
        return "username: " + username + "|" + "password: " + password;
    }
}

