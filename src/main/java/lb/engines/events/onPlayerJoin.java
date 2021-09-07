package lb.engines.events;

import lb.engines.main.mainEngines;
import lb.engines.utils.accountManager;
import lb.engines.utils.playerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onPlayerJoin implements Listener {

    public static ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!accountManager.accountExist(player)) accountManager.createAccount(player);
        playerManager stats = new playerManager();
        Bukkit.getScheduler().scheduleSyncDelayedTask(mainEngines.getPlugin(), new Runnable() {
            @Override
            public void run() {
                stats.setMoney(accountManager.getMoney(player));
                stats.setKills(accountManager.getKills(player));
                stats.setDeaths(accountManager.getDeaths(player));
            }
        });
        mainEngines.stats.put(player.getUniqueId(), stats);
        console.sendMessage("§aConfigurações de " + player.getName() + " carregadas com sucesso.");
    }
}
