package lb.engines.events;

import lb.engines.main.mainEngines;
import lb.engines.utils.accountManager;
import lb.engines.utils.playerManager;
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
        playerManager stats = mainEngines.stats.get(player.getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(mainEngines.getPlugin(), new Runnable() {
            @Override
            public void run() {
                accountManager.setMoney(player, stats.getMoney());
                accountManager.setKills(player, stats.getKills());
                accountManager.setDeaths(player, stats.getDeaths());
                mainEngines.stats.remove(player.getUniqueId());
            }
        });
        console.sendMessage("§aConfigurações de " + player.getName() + " salvas com sucesso.");
    }
}
