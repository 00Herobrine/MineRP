package x00Hero.MineRP.Items.Generic;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import x00Hero.MineRP.Events.Constructors.JobItemInteractEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;

public class Baton {
    private static final ItemBuilder arrestBaton = new ItemBuilder(Material.BLAZE_ROD, "Arrest Baton", "Arrests people");
    private static final ItemBuilder unarrestBaton = new ItemBuilder(Material.BLAZE_ROD, "unarrest Baton", "Frees inmates");

    @EventHandler
    public void CustomItemInteract(JobItemInteractEvent e) {

    }


}
