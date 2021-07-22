package ClashRoyale.utils;

import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;

import java.io.*;
import java.util.ArrayList;

/**
 * This class helps us read and write from the database file to access the players data
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class FileUtil {

    private final File file;

    /**
     * Constructs a file util object
     * If the database file is not created in the specified path, it will create a new one
     */
    public FileUtil() {
        this.file = new File("src/ClashRoyale/resources/database");

        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("created new file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the players archive to database
     * @param archive players archive
     */
    public void writeToFile(PlayersArchieve archive) {
        ArrayList<Player> players = archive.getPlayersArchieve();

        try (FileOutputStream out = new FileOutputStream(file)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(players);
            outputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads from the database to load the players archive
     * @return the players archive
     */
    public PlayersArchieve readFromFile() {
        PlayersArchieve archive = null;
        ArrayList<Player> players;

        try (FileInputStream in = new FileInputStream(file)) {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            players = (ArrayList<Player>) inputStream.readObject();
            inputStream.close();
            archive = new PlayersArchieve();
            archive.setPlayersArchieve(players);
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return archive;
    }
}
