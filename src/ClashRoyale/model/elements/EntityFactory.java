package ClashRoyale.model.elements;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.entities.*;
import ClashRoyale.model.elements.entities.Troop.Speed;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates entities with the given descriptions in the game's document and adds them on the map
 * GameManager will activate these newly made entities
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class EntityFactory {
    /**
     * Creates an entity with the given type
     * @param type type
     * @param x x coordinate
     * @param y y coordinate
     * @param gameData game data
     * @param isEnemy is enemy or not
     * @param level level of the entity
     * @return list of newly made entities
     * @throws IllegalArgumentException if the given location is not valid (is in the territory of a tower)
     */
    public static ArrayList<Entity> createEntity(Entity.Type type, int x, int y, GameData gameData, boolean isEnemy, int level)
            throws IllegalArgumentException {
        // handling bad x,y coordinates
        if (x <= 0) x = 1;
        else if (x >= gameData.rowCount - 1)
            x = 16;
        if (y <= 0) y = 1;
        else if (y >= gameData.colCount - 1)
            y = 30;

        // handling the towers' territories (but not for spell cards)
        if ((type != Entity.Type.RAGE && type != Entity.Type.FIRE && type != Entity.Type.ARROWS) && gameData.isInTerritory(x, y, isEnemy))
            throw new IllegalArgumentException("cannot place the card here");

        Point2D loc = new Point2D(x,y);
        ArrayList<Entity> entities = new ArrayList<>();
        switch (type) {
            case KING_TOWER ->    entities.add(createKingTower(loc, isEnemy, level, gameData));
            case QUEEN_TOWER ->   entities.add(createQueenTower(loc, isEnemy, level, gameData));
            case BARBARIANS ->    entities.addAll(Arrays.asList(createBarbarians(loc, isEnemy, level, gameData)));
            case ARCHER ->        entities.addAll(Arrays.asList(createArchers(loc, isEnemy, level, gameData)));
            case BABY_DRAGON ->   entities.add(createBabyDragon(loc, isEnemy, level, gameData));
            case WIZARD ->        entities.add(createWizard(loc, isEnemy, level, gameData));
            case MINI_PEKKA ->    entities.add(createMiniPekka(loc, isEnemy, level, gameData));
            case GIANT ->         entities.add(createGiant(loc, isEnemy, level, gameData));
            case VALKYRIE ->      entities.add(createValkyrie(loc, isEnemy, level, gameData));
            case RAGE ->          entities.add(createRage(loc, isEnemy, level, gameData));
            case FIRE ->          entities.add(createFireball(loc, isEnemy, level, gameData));
            case ARROWS ->        entities.add(createArrows(loc, isEnemy, level, gameData));
            case CANNON ->        entities.add(createCannon(loc, isEnemy, level, gameData));
            case INFERNO_TOWER -> entities.add(createInfernoTower(loc, isEnemy, level, gameData));
        }
        return entities;
    }

    /**
     * Create a king tower
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createKingTower(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int range = 7, hitSpeed = 1, hp = 0, damage = 0;
        switch (level) {
            case 1 -> {
                hp = 2400;
                damage = 50;
            }
            case 2 -> {
                hp = 2568;
                damage = 53;
            }
            case 3 -> {
                hp = 2736;
                damage = 57;
            }
            case 4 -> {
                hp = 2904;
                damage = 60;
            }
            case 5 -> {
                hp = 3096;
                damage = 64;
            }
        }
        Tower kingTower = new Tower(Entity.Type.KING_TOWER, isEnemy, loc, hp, damage, hitSpeed, range);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = kingTower;
        return kingTower;
    }

    /**
     * Create a queen tower
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createQueenTower(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int range = 8, hp = 0, damage = 0;
        double hitSpeed = 0.8;
        switch (level) {
            case 1 -> {
                hp = 1400;
                damage = 50;
            }
            case 2 -> {
                hp = 1512;
                damage = 54;
            }
            case 3 -> {
                hp = 1624;
                damage = 58;
            }
            case 4 -> {
                hp = 1750;
                damage = 62;
            }
            case 5 -> {
                hp = 1890;
                damage = 69;
            }
        }
        Tower queenTower = new Tower(Entity.Type.QUEEN_TOWER, isEnemy, loc, hp, damage, hitSpeed, range);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = queenTower;
        return queenTower;
    }

    /**
     * Creates 4  4 barbarians
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entities
     */
    public static Entity[] createBarbarians(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 0.8;
        Speed speed = Speed.MEDIUM;
        TargetType targetType = TargetType.GROUND;
        int range = 1, count = 4;
        switch (level) {
            case 1 -> {
                hp = 300;
                damage = 75;
            }
            case 2 -> {
                hp = 330;
                damage = 82;
            }
            case 3 -> {
                hp = 363;
                damage = 90;
            }
            case 4 -> {
                hp = 438;
                damage = 99;
            }
            case 5 -> {
                hp = 480;
                damage = 109;
            }
        }

        Troop[] troops = new Troop[4];
        int x = (int) loc.getX(), y = (int) loc.getY();
        Troop barbarian = new Troop(Entity.Type.BARBARIANS, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
        barbarian.setLocation(loc);
        gameData.map[x][y] = barbarian;
        troops[0] = barbarian;

        for (int i = 1; i < count; i++) {
            barbarian = new Troop(Entity.Type.BARBARIANS, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
            if (gameData.map[x][y + 1] != null) {
                y++;
            } else if (gameData.map[x + 1][y + 1] != null) {
                y++; x++;
            } else if (gameData.map[x][y - 1] != null) {
                y--;
            } else if (gameData.map[x - 1][y - 1] != null) {
                y--; x--;
            } else if (gameData.map[x + 1][y] != null) {
                x++;
            } else if (gameData.map[x - 1][y] != null) {
                x--;
            }

            gameData.map[x][y] = barbarian;
            barbarian.setLocation(new Point2D(x,y));
            x = (int) loc.getX(); y = (int) loc.getY();
            troops[i] = barbarian;
        }
        return troops;
    }

    /**
     * Creates 2 archers
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entities
     */
    public static Entity[] createArchers(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.2;
        Speed speed = Speed.MEDIUM;
        TargetType targetType = TargetType.AIR_GROUND;
        int range = 5;
        switch (level) {
            case 1 -> {
                hp = 125;
                damage = 33;
            }
            case 2 -> {
                hp = 127;
                damage = 44;
            }
            case 3 -> {
                hp = 151;
                damage = 48;
            }
            case 4 -> {
                hp = 166;
                damage = 53;
            }
            case 5 -> {
                hp = 182;
                damage = 58;
            }
        }

        Troop[] troops = new Troop[2];
        int x = (int) loc.getX(), y = (int) loc.getY();
        Troop archer = new Troop(Entity.Type.ARCHER, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
        archer.setLocation(loc);
        gameData.map[x][y] = archer;
        troops[0] = archer;

        if (gameData.map[x][y + 1] != null)
            y++;
        else if (gameData.map[x][y - 1] != null)
            y--;
        else if (gameData.map[x + 1][y] != null)
            x++;
        else if (gameData.map[x - 1][y] != null)
            x--;

        archer = new Troop(Entity.Type.ARCHER, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
        archer.setLocation(new Point2D(x,y));
        gameData.map[x][y] = archer;
        troops[1] = archer;
        return troops;
    }

    /**
     * Creates a baby dragon
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createBabyDragon(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.8;
        Speed speed = Speed.FAST;
        TargetType targetType = TargetType.AIR_GROUND;
        int range = 3;
        switch (level) {
            case 1 -> {
                hp = 800;
                damage = 100;
            }
            case 2 -> {
                hp = 880;
                damage = 110;
            }
            case 3 -> {
                hp = 968;
                damage = 121;
            }
            case 4 -> {
                hp = 1064;
                damage = 133;
            }
            case 5 -> {
                hp = 1168;
                damage = 146;
            }
        }

        Troop dragon = new Troop(Entity.Type.BABY_DRAGON, isEnemy, hp, damage, targetType, speed, hitSpeed, range, true);
        dragon.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = dragon;
        return dragon;
    }

    /**
     * Create a wizard
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createWizard(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.7;
        Speed speed = Speed.MEDIUM;
        TargetType targetType = TargetType.AIR_GROUND;
        int range = 5;
        switch (level) {
            case 1 -> {
                hp = 340;
                damage = 130;
            }
            case 2 -> {
                hp = 374;
                damage = 143;
            }
            case 3 -> {
                hp = 411;
                damage = 157;
            }
            case 4 -> {
                hp = 452;
                damage = 172;
            }
            case 5 -> {
                hp = 496;
                damage = 189;
            }
        }

        Troop wizard = new Troop(Entity.Type.WIZARD, isEnemy, hp, damage, targetType, speed, hitSpeed, range, true);
        wizard.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = wizard;
        return wizard;
    }

    /**
     * Creates a mini pekka
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createMiniPekka(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.8;
        Speed speed = Speed.FAST;
        TargetType targetType = TargetType.GROUND;
        int range = 1;
        switch (level) {
            case 1 -> {
                hp = 600;
                damage = 325;
            }
            case 2 -> {
                hp = 660;
                damage = 357;
            }
            case 3 -> {
                hp = 726;
                damage = 393;
            }
            case 4 -> {
                hp = 798;
                damage = 432;
            }
            case 5 -> {
                hp = 876;
                damage = 474;
            }
        }

        Troop miniPekka = new Troop(Entity.Type.MINI_PEKKA, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
        miniPekka.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = miniPekka;
        return miniPekka;
    }

    /**
     * Creates a giant
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createGiant(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.5;
        Speed speed = Speed.SLOW;
        TargetType targetType = TargetType.BUILDINGS;
        int range = 1;
        switch (level) {
            case 1 -> {
                hp = 2000;
                damage = 126;
            }
            case 2 -> {
                hp = 2200;
                damage = 138;
            }
            case 3 -> {
                hp = 2420;
                damage = 152;
            }
            case 4 -> {
                hp = 2660;
                damage = 167;
            }
            case 5 -> {
                hp = 2920;
                damage = 183;
            }
        }

        Troop giant = new Troop(Entity.Type.GIANT, isEnemy, hp, damage, targetType, speed, hitSpeed, range, false);
        giant.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = giant;
        return giant;
    }

    /**
     * Creates a valkyrie
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createValkyrie(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 1.5;
        Speed speed = Speed.MEDIUM;
        TargetType targetType = TargetType.GROUND;
        int range = 1;
        switch (level) {
            case 1 -> {
                hp = 880;
                damage = 120;
            }
            case 2 -> {
                hp = 968;
                damage = 132;
            }
            case 3 -> {
                hp = 1064;
                damage = 145;
            }
            case 4 -> {
                hp = 1170;
                damage = 159;
            }
            case 5 -> {
                hp = 1284;
                damage = 175;
            }
        }

        Troop valkyrie = new Troop(Entity.Type.VALKYRIE, isEnemy, hp, damage, targetType, speed, hitSpeed, range, true);
        valkyrie.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = valkyrie;
        return valkyrie;
    }

    /**
     * Create rage spell
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createRage(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        double duration = 1;
        int radius = 5;
        switch (level) {
            case 1 -> duration = 6;
            case 2 -> duration = 6.5;
            case 3 -> duration = 7;
            case 4 -> duration = 7.5;
            case 5 -> duration = 8;
        }

        Spell rage = new Spell(Entity.Type.RAGE, isEnemy, radius, duration);
        rage.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = rage;
        return rage;
    }

    /**
     * Creates fireball spell
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createFireball(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        double areaDamage = 1;
        int radius = 2;
        switch (level) {
            case 1 -> areaDamage = 325;
            case 2 -> areaDamage = 357;
            case 3 -> areaDamage = 393;
            case 4 -> areaDamage = 432;
            case 5 -> areaDamage = 474;
        }

        Spell fireball = new Spell(Entity.Type.FIRE, isEnemy, radius, areaDamage);
        fireball.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = fireball;
        return fireball;
    }

    /**
     * Creates arrows spell
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createArrows(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        double areaDamage = 1;
        int radius = 4;
        switch (level) {
            case 1 -> areaDamage = 144;
            case 2 -> areaDamage = 156;
            case 3 -> areaDamage = 174;
            case 4 -> areaDamage = 189;
            case 5 -> areaDamage = 210;
        }

        Spell arrows = new Spell(Entity.Type.ARROWS, isEnemy, radius, areaDamage);
        arrows.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = arrows;
        return arrows;
    }

    /**
     * Creates cannon
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly made entity
     */
    public static Entity createCannon(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0;
        double hitSpeed = 0.8;
        TargetType targetType = TargetType.GROUND;
        int range = 5, lifetime = 30;
        switch (level) {
            case 1 -> {
                hp = 380;
                damage = 60;
            }
            case 2 -> {
                hp = 418;
                damage = 66;
            }
            case 3 -> {
                hp = 459;
                damage = 72;
            }
            case 4 -> {
                hp = 505;
                damage = 79;
            }
            case 5 -> {
                hp = 554;
                damage = 87;
            }
        }

        Building cannon = new Building(Entity.Type.CANNON, isEnemy, hp, damage, hitSpeed, targetType, range, lifetime);
        cannon.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = cannon;
        return cannon;
    }

    /**
     * Creates inferno tower
     * @param loc location
     * @param isEnemy is enemy
     * @param level level
     * @param gameData game data
     * @return newly mad entity
     */
    public static Entity createInfernoTower(Point2D loc, boolean isEnemy, int level, GameData gameData) {
        int hp = 0, damage = 0, maxDamage = 0;
        double hitSpeed = 0.4;
        TargetType targetType = TargetType.AIR_GROUND;
        int range = 6, lifetime = 40;
        switch (level) {
            case 1 -> {
                hp = 800;
                damage = 20;
                maxDamage = 400;
            }
            case 2 -> {
                hp = 880;
                damage = 22;
                maxDamage = 440;
            }
            case 3 -> {
                hp = 968;
                damage = 24;
                maxDamage = 484;
            }
            case 4 -> {
                hp = 1064;
                damage = 26;
                maxDamage = 532;
            }
            case 5 -> {
                hp = 1168;
                damage = 29;
                maxDamage = 584;
            }
        }

        Building infernoTower = new Building(Entity.Type.INFERNO_TOWER, isEnemy, hp, damage, hitSpeed, targetType, range, lifetime);
        infernoTower.setMaxDamage(maxDamage);
        infernoTower.setLocation(loc);
        gameData.map[(int) loc.getX()][(int) loc.getY()] = infernoTower;
        return infernoTower;
    }
}
