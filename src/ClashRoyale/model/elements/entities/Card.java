package ClashRoyale.model.elements.entities;


import javafx.scene.image.Image;


public abstract class Card extends Entity {

    public Card(Type type, boolean isEnemy) {
        super(type, isEnemy);
    }

    public static Image loadCardImage(Entity.Type cardType) {
        switch (cardType) {
            case ARCHER -> {
                return new Image("/ClashRoyale/resources/troops/archers/archer.png");
            }
            case ARROWS -> {
                return new Image("/ClashRoyale/resources/arrows/arrows.png");
            }
            case BABY_DRAGON -> {
                return new Image("/ClashRoyale/resources/troops/baby-dragon/baby-dragon.png");
            }
            case BARBARIANS -> {
                return new Image("/ClashRoyale/resources/troops/barbarians/barbarian.png");
            }
            case CANNON -> {
                return new Image("/ClashRoyale/resources/buildings/canon-card.png");
            }
            case FIRE -> {
                return new Image("/ClashRoyale/resources/fireball/fireball-card.png");
            }
            case GIANT -> {
                return new Image("/ClashRoyale/resources/troops/giant/giant.png");
            }
            case INFERNO_TOWER -> {
                return new Image("/ClashRoyale/resources/buildings/inferno-tower-card.png");
            }
            case MINI_PEKKA -> {
                return new Image("/ClashRoyale/resources/troops/mini-pekka/mini-pekka.png");
            }
            case RAGE -> {
                return new Image("/ClashRoyale/resources/rage/rage.png");
            }
            case VALKYRIE -> {
                return new Image("/ClashRoyale/resources/troops/valkyrie/valkyrie.png");
            }
            case WIZARD -> {
                return new Image("/ClashRoyale/resources/troops/wizard/wizard.png");
            }
            default -> {
                return null;
            }
        }
    }
}
