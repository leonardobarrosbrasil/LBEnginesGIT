package lb.engines.utils;

import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.command.ConsoleCommandSender;

public class LBPlaceholder extends PlaceholderExpansion {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public LBPlaceholder(MainEngines plugin) {
    }

    @Override
    public String getAuthor() {
        return "Dimensions";
    }

    @Override
    public String getIdentifier() {
        return "LBEngines";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        switch (params) {
            case "level":
                return String.valueOf(MainEngines.getPlugin().getManager().getCached(player.getUniqueId()).getLevel());
            case "exp":
                return String.valueOf(MainEngines.getPlugin().getManager().getCached(player.getUniqueId()).getExp());
            default:
                return null; // Placeholder is unknown by the Expansion
        }
    }
}