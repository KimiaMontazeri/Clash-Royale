package ClashRoyale.model.elements.entities;

import javafx.geometry.Point2D;

public class Tower extends Entity {

    private int hp, damage;
    private final int hitSpeed;
    private final int range;
    private boolean isDestroyed; // used for when we want to render the image of a destroyed tower

    public Tower(String name, boolean isEnemy, Point2D loc, int hp, int damage, int hitSpeed, int range) {
        super(name, isEnemy, loc);
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
        isDestroyed = false;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getHitSpeed() {
        return hitSpeed;
    }

    public int getRange() {
        return range;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void attack() {

    }

    public void getAttacked(int damage) {
        hp -= damage;
        if (hp == 0)
            isDestroyed = true;
    }
}
