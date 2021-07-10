package ClashRoyale.model.elements.entities;


public abstract class Card extends Entity {

    private final int cost;

    public Card(String name, boolean isEnemy, int cost) {
        super(name, isEnemy);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
