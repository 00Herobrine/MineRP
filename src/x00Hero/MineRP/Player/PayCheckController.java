package x00Hero.MineRP.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import x00Hero.MineRP.Events.Constructors.PayCheckEvent;

public class PayCheckController implements Listener {

    @EventHandler
    public void onPayCheck(PayCheckEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        if(!rPlayer.isInJail()) {
            rPlayer.addCash(e.getAmount());
        } else {
            // in jail
        }
    }
}
