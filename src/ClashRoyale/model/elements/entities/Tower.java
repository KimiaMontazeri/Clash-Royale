package ClashRoyale.model.elements.entities;

public class Tower {

    private final String name;
    private int hp, damage;
    private final int hitSpeed;
    private final int range;

    public Tower(String name, int hp, int damage, int hitSpeed, int range) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
    }

    public String getName() {
        return name;
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
}
