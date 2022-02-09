package lb.engines.utils;

import lb.engines.main.MainEngines;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class LBPlaceholder extends PlaceholderExpansion {

    private final MainEngines plugin;

    public LBPlaceholder(MainEngines plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Dimensions";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "LBEngines";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        LBPlayer data = MainEngines.getPlugin().getManager().getCached(player.getUniqueId());
        switch (params) {
            case "wedding_tag":
                if (!data.getPartner().equals(player.getUniqueId())) return "[❤] ";
            case "level_tag":
                return "[Nvl " + MainEngines.getPlugin().getManager().getCached(player.getUniqueId()).getLevel() + "] ";
            case "level":
                return String.valueOf(MainEngines.getPlugin().getManager().getCached(player.getUniqueId()).getLevel());
            case "exp":
                return String.valueOf(MainEngines.getPlugin().getManager().getCached(player.getUniqueId()).getExp());
            case "exp_remaining":
                return String.valueOf(MainEngines.getPlugin().getFunctions().getExpRemaing(player.getUniqueId()));
            default:
                return null; // Placeholder is unknown by the Expansion
        }
    }
}