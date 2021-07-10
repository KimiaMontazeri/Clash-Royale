package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.Direction;
import ClashRoyale.model.elements.Target;
import javafx.geometry.Point2D;

enum Speed {
    SLOW, MEDIUM, FAST
}

public class Troop extends Card {

    private int hp;
    private int damage;
    private final Target target;
    private final Speed speed;
    private final int hitSpeed;
    private final double range;
    private final boolean isAreaSplash;
    private final int count;
    private final int lifetime;
    private boolean isAttacking, isWalking, isWaiting;

    public Troop(String name, boolean isEnemy, int cost, int hp, int damage, Target target, Speed speed, int hitSpeed,
                 double range, boolean isAreaSplash, int count, int lifetime) {
        super(name, isEnemy, cost);
        this.hp = hp;
        this.damage = damage;
        this.target = target;
        this.speed = speed;
        this.hitSpeed = hitSpeed;
        this.range = range; // 0 -> melee, other -> x number of tiles will be checked for attacking
        this.isAreaSplash = isAreaSplash;
        this.count = count;
        this.lifetime = lifetime;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public Target getTarget() {
        return target;
    }

    public Speed getSpeed() {
        return speed;
    }

    public int getHitSpeed() {
        return hitSpeed;
    }

    public double getRange() {
        return range;
    }

    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    public int getCount() {
        return count;
    }

    public int getLifetime() {
        return lifetime;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public void attack() {

    }

    public void getAttacked(int damage) {
        this.hp -= damage;
    }

    public void move(Direction direction) {
        Point2D possibleVelocity = directionToPoint2D(direction);
        Point2D possibleLocation = super.getLocation().add(possibleVelocity);
    }

    public void move() {
        Point2D possibleDirection, possibleLoc;
        if (super.isEnemy())
            possibleDirection = directionToPoint2D(Direction.DOWN);
        else
            possibleDirection = directionToPoint2D(Direction.UP);

        possibleLoc = super.getLocation().add(possibleDirection);
        // check if there's an enemy in the possible location or ...

    }

    private Point2D directionToPoint2D(Direction direction) {
        switch (direction) {
            case UP -> {
                if (speed == Speed.SLOW)
                    return new Point2D(0,1);
                else if (speed == Speed.MEDIUM)
                    return new Point2D(0,2);
                else if (speed == Speed.FAST)
                    return new Point2D(0,3);
            }
            case DOWN -> {
                if (speed == Speed.SLOW)
                    return new Point2D(0,-1);
                else if (speed == Speed.MEDIUM)
                    return new Point2D(0,-2);
                else if (speed == Speed.FAST)
                    return new Point2D(0,-3);
            }
            case LEFT -> {
                if (speed == Speed.SLOW)
                    return new Point2D(-1,0);
                else if (speed == Speed.MEDIUM)
                    return new Point2D(-2,0);
                else if (speed == Speed.FAST)
                    return new Point2D(-3,0);
            }
            case RIGHT -> {
                if (speed == Speed.SLOW)
                    return new Point2D(1,0);
                else if (speed == Speed.MEDIUM)
                    return new Point2D(2,0);
                else if (speed == Speed.FAST)
                    return new Point2D(3,0);
            }
        }
        return new Point2D(0,0);
    }

    private Point2D directionToPointOld(Direction direction) {
        return switch (direction) {
            case UP -> new Point2D(0, 1);
            case DOWN -> new Point2D(0, -1);
            case LEFT -> new Point2D(-1, 0);
            case RIGHT -> new Point2D(1, 0);
        };
    }

    private boolean canMove() {
        // add some code
        return false;
    }

}
