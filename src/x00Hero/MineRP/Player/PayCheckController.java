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
            rPlayer.sendMessage("Added $" + amount + " to your bank.", Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            rPlayer.addCash(amount);
            rPlayer.savePlayerFile();
        } else {
            // in jail
            rPlayer.sendMessage("Crime doesn't pay.", Sound.BLOCK_ANVIL_FALL, 0.6f, 0.7f);
        }
    }
}
