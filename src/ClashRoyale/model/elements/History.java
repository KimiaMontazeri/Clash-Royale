package ClashRoyale.model.elements;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class History implements Serializable {
    private final String opponentName;
    private final String dateStr;
    private final String winner;

    public History(String opponentName, String winner) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateStr = formatter.format(date);
        this.opponentName = opponentName;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "opponent: " + opponentName + "|" +
                "date: " + dateStr + "|" +
                "winner" + winner;
    }


}
