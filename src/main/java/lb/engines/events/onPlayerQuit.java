package lb.engines.events;

import lb.engines.main.mainEngines;
import lb.engines.utils.playerManager;
import lb.engines.utils.pluginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onPlayerQuit implements Listener {

    public static ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onLogin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(!pluginManager.getMySQL().hasData(player.getUniqueId())) return;
        Bukkit.getScheduler().runTaskAsynchronously(mainEngines.getPlugin(), () -> {
            playerManager data = pluginManager.getMySQL().getData(player.getUniqueId());
            pluginManager.getAccount().saveData(data);
            pluginManager.getAccount().removeData(player.getUniqueId());
        });
    }
}
