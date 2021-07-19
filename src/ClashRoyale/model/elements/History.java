package ClashRoyale.model.elements;

import ClashRoyale.model.elements.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class History {
    private Player opponent;
    private String dateStr;
    private String winner;

    public History(Player opponent, String winner) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateStr = formatter.format(date);
        this.opponent = opponent;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "oponent: " + opponent + "|" +
                "date: " + dateStr + "|" +
                "winner" + winner;
    }


}
