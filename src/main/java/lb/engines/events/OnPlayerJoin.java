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
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendTitle("§a§lBEM VINDO", "Logue-se usando /login <senha>", 10, 10000000, 10);
        if (MainEngines.getPlugin().getManager().hasCache(player.getUniqueId())) return;
        Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
            if (!MainEngines.getPlugin().getMySQL().accountExist(player.getUniqueId()))
                MainEngines.getPlugin().getMySQL().createAccount(player.getUniqueId());
            MainEngines.getPlugin().getManager().addCache(player.getUniqueId(), MainEngines.getPlugin().getMySQL().getData(player.getUniqueId()));
            console.sendMessage("§aLBEngines: Dados de " + player.getName() + " carregados com sucesso.");
        });
    }
}
