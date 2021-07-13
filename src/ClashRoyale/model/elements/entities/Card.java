package ClashRoyale.model.elements.entities;


import javafx.scene.image.Image;

public abstract class Card extends Entity {
    private Image cardImage;

    public Card(Type type, boolean isEnemy) {
        super(type, isEnemy);
    }

    public Image getCardImage() {
        return cardImage;
    }

    public void setCardImage(Image cardImage) {
        this.cardImage = cardImage;
    }
}
