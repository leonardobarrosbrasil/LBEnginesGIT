package lb.engines.utils;

import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LBFunctions {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public int getExpRemaing(UUID uuid) {
        LBPlayer playerData = MainEngines.getPlugin().getManager().getCached(uuid);
        return playerData.getLevel() * 525;
    }

    public void isElegible(UUID uuid) {
        if (MainEngines.getPlugin().getManager().hasCache(uuid)) {
            LBPlayer playerData = MainEngines.getPlugin().getManager().getCache(uuid);
            if (playerData.getExp() >= playerData.getLevel() * 525) {
                Player player = Bukkit.getPlayer(uuid);
                playerData.setLevel(playerData.getLevel() + 1);
                playerData.setExp(0);
                if (player != null) {
                    player.sendMessage("§aParabéns, você alcançou o nível " + playerData.getLevel());
                    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);
                }
            }
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
                LBPlayer playerData = MainEngines.getPlugin().getMySQL().getData(uuid);
                if (playerData.getExp() >= playerData.getLevel() * 525) {
                    try {
                        MainEngines.getPlugin().getMySQL().setLevel(uuid, playerData.getLevel() + 1);
                        MainEngines.getPlugin().getMySQL().setExp(uuid, 0);
                    } catch (NullPointerException ex) {
                        console.sendMessage("§cLBEngines: Ocorreu um erro ao tentar upar o nível do jogador. O jogador não foi encontrado na base de dados.");
                    }
                }
            });
        }
    }
}
