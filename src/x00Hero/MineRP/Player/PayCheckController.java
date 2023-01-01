package x00Hero.MineRP.Player;

import org.bukkit.entity.Player;
import x00Hero.MineRP.Events.Constructors.PayCheckEvent;

public class PayCheckController {

    public void onPayCheck(PayCheckEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        if(!rPlayer.isInJail()) {
            rPlayer.addCash(e.getAmount());
        } else {
            // in jail
        }
    }
}
