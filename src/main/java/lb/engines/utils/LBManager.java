package lb.engines.utils;

import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class LBManager {

    private static final HashMap<UUID, LBPlayer> cache = new HashMap<>();

    private static final Map<UUID, Map<String, LBHomes>> homesCache = new HashMap<>();

    public ConsoleCommandSender console = Bukkit.getConsoleSender();

    public LBPlayer getCached(UUID uuid) {
        if (!cache.containsKey(uuid)) {
            Player target = Bukkit.getServer().getPlayer(uuid);
            if (target == null) return null;
            console.sendMessage("§cLBEngines: Os dados de " + target.getName() + " nao foram carregados corretamente.");
            Bukkit.getScheduler().runTask(MainEngines.getPlugin(), () -> Objects.requireNonNull(target.getPlayer()).kickPlayer("§cSeus dados não foram carregados corretamente. Reentre no servidor."));
            return null;
        } else {
            return cache.get(uuid);
        }
    }

    public LBPlayer getCache(UUID uuid) {
        return cache.get(uuid);
    }

    public void addCache(UUID uuid, LBPlayer player) {
        cache.put(uuid, player);
    }

    public void removeCache(UUID uuid) {
        cache.remove(uuid);
    }

    public boolean hasCache(UUID uuid) {
        return cache.containsKey(uuid);
    }

    public int cacheSize() {
        return cache.size();
    }

    public HashMap<UUID, LBPlayer> getCache() {
        return cache;
    }
}






