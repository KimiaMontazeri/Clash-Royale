package ClashRoyale.model.elements.entities;


import ClashRoyale.utils.AudioPlayer;
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

    public static void playSound(Entity.Type cardType) {
        String path = null;
        AudioPlayer audioPlayer;

        if (cardType == Type.ARCHER) {
            path = "src/ClashRoyale/resources/sound-effects/archer_deploy.wav";
        } else if (cardType == Type.ARROWS) {
            path = "src/ClashRoyale/resources/sound-effects/arrow_hit.wav";
        } else if (cardType == Type.BABY_DRAGON) {
            path = "src/ClashRoyale/resources/sound-effects/babydragon_fire.wav";
        } else if (cardType == Type.BARBARIANS) {
            path = "src/ClashRoyale/resources/sound-effects/barbarians_deploy.wav";
        } else if (cardType == Type.CANNON) {
            path = "src/ClashRoyale/resources/sound-effects/cannon_deploy.wav";
        } else if (cardType == Type.FIRE) {
            path = "src/ClashRoyale/resources/sound-effects/fire_ball_explo.wav";
        } else if (cardType == Type.GIANT) {
            path = "src/ClashRoyale/resources/sound-effects/giant_deploy.wav";
        } else if (cardType == Type.INFERNO_TOWER) {
            path = "src/ClashRoyale/resources/sound-effects/inferno_deploy.wav";
        } else if (cardType == Type.MINI_PEKKA) {
            path = "src/ClashRoyale/resources/sound-effects/mini_pekka_deploy.wav";
        } else if (cardType == Type.RAGE) {
            path = "src/ClashRoyale/resources/sound-effects/rage_spell.wav";
        } else if (cardType == Type.VALKYRIE) {
            path = "src/ClashRoyale/resources/sound-effects/valkyrie_deploy.wav";
        } else if (cardType ==Type.WIZARD) {
            path = "src/ClashRoyale/resources/sound-effects/wiz_deploy_voice.wav";
        }

        if (path != null) {
            audioPlayer = new AudioPlayer(path);
            audioPlayer.play();
        }
    }
}
