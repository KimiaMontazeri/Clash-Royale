package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.Target;

public class Building extends Card {

    private int hp;
    private int damage;
    private final int hitSpeed;
    private final Target target;
    private final double range;
    private int lifetime;

    public Building(String name, boolean isEnemy, int cost, int hp, int damage,
                    int hitSpeed, Target target, double range, int lifetime) {
        super(name, isEnemy, cost);
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.target = target;
        this.range = range;
        this.lifetime = lifetime;
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

    public Target getTarget() {
        return target;
    }

    public double getRange() {
        return range;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void decrementLifetime() {
        lifetime--;
        if (lifetime <= 5 && lifetime > 0) {
            super.setDying(true);
        } else if (lifetime == 0) {
            // remove it from the map (map[x][y] = null)
        }
    }

    public void attack() {

    }

    public void getAttacked(int damage) {
        this.hp -= damage;
    }
}
