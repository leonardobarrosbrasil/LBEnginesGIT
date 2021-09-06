package lb.engines.events;

import lb.engines.main.MainEngines;
import lb.engines.utils.AccountManager;
import lb.engines.utils.MysqlManager;
import lb.engines.utils.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //if (!AccountManager.accountExist(player)) AccountManager.createAccount(player);
        PlayerManager stats = new PlayerManager();
        stats.setMoney(AccountManager.getMoney(player));
        stats.setKills(AccountManager.getKills(player.getPlayer()));
        stats.setDeaths(AccountManager.getDeaths(player.getPlayer()));
        MainEngines.stats.put(player.getUniqueId(), stats);
    }
}
