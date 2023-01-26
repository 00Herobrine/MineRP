package x00Hero.MineRP.Player;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import x00Hero.MineRP.Events.Constructors.Player.PayCheckEvent;

public class PayCheckController implements Listener {

    @EventHandler
    public void onPayCheck(PayCheckEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        if(!rPlayer.isInJail()) {
            long amount = e.getAmount();
            rPlayer.sendMessage("Paycheck of $" + amount + " added to your balance.", Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7f, 1f);
            rPlayer.addCash(amount);
        } else {
            // in jail
            rPlayer.sendMessage("Crime doesn't pay.", Sound.BLOCK_ANVIL_FALL, 0.6f, 0.7f);
        }
    }
}
