
package lb.engines.commands;

import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandSave implements CommandExecutor {

    public CommandSave(MainEngines main, String command) {
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 0) {
            if (!sender.hasPermission("lb.admin.save")) {
                sender.sendMessage("§cVocê não tem permissão para fazer isto.");
                return false;
            }
            args0(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        sender.sendMessage("§aSalvando todos os dados cacheados na database.");
        MainEngines.getPlugin().forceSaveAsync();
    }
}