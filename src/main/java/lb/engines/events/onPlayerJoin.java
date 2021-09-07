package lb.engines.events;

import lb.engines.main.mainEngines;
import lb.engines.utils.accountManager;
import lb.engines.utils.playerManager;
import lb.engines.utils.pluginManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class onPlayerJoin implements Listener {

    public static ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(mainEngines.getPlugin(), () -> {
            if (pluginManager.getAccount().accountExist(player.getUniqueId()))
                pluginManager.getAccount().createAccount(player.getUniqueId());
          pluginManager.getAccount().loadData(player);
        });
    }
}
