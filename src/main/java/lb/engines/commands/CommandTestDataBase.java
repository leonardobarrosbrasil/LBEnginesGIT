
package lb.engines.commands;

import lb.engines.main.MainEngines;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandTestDataBase implements CommandExecutor {

    public CommandTestDataBase(MainEngines main, String command) {
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command arg1, @NotNull String arg2, String[] args) {
        if (args.length == 0) {
            args0(sender, args);
            return true;
        }
        sender.sendMessage("§cArgumentos inválidos.");
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (MainEngines.getPlugin().getManager().hasCache(player.getUniqueId())) {
            sender.sendMessage("§aVocê está cacheado.");
        } else {
            sender.sendMessage("§cVocê não está cacheado.");
        }
    }
}