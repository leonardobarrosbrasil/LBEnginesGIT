package lb.engines.events;

import lb.engines.main.MainEngines;
import lb.engines.utils.LBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    public ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
            if (!MainEngines.getPlugin().getMysql().accountExist(player.getUniqueId()))
                MainEngines.getPlugin().getMysql().createAccount(player.getUniqueId());
            LBPlayer playerc = MainEngines.getPlugin().getMysql().getData(player.getUniqueId());
            MainEngines.getPlugin().getManager().addCache(playerc.getUUID(), playerc);
            console.sendMessage("§aDados de " + player.getName() + " adicionados ao servidor.");
        });
    }
}
