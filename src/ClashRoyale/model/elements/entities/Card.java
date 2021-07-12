package ClashRoyale.model.elements.entities;


public abstract class Card extends Entity {

    private final int cost;

    public Card(Type type, boolean isEnemy, int cost) {
        super(type, isEnemy);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }
}
