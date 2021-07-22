package ClashRoyale.model.elements;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class shows a game history of the signed-in player that contains the opponent's name, date and winner
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class History implements Serializable {
    private final String opponentName;
    private final String dateStr;
    private final String winner;

    /**
     * Constructs a history
     * @param opponentName name of the opponent
     * @param winner name of the winner
     */
    public History(String opponentName, String winner) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateStr = formatter.format(date);
        this.opponentName = opponentName;
        this.winner = winner;
    }

    /**
     * @return string format of a game history
     */
    @Override
    public String toString() {
        return "opponent: " + opponentName + " | " +
                "date: " + dateStr + " | " +
                "winner: " + winner;
    }


}
