package lb.engines.events;

import lb.engines.main.MainEngines;
import lb.engines.utils.LBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerQuit implements Listener {

    public ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onLogin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!MainEngines.getPlugin().getManager().hasCache(player.getUniqueId())) return;
        Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
            MainEngines.getPlugin().getMySQL().saveData(player.getUniqueId());
            MainEngines.getPlugin().getManager().removeCache(player.getUniqueId());
            console.sendMessage("§aLBEngines: Dados de " + player.getName() + " descarregados com sucesso.");
        });
    }
}
