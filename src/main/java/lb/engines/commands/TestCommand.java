package lb.engines.commands;

import lb.engines.main.MainEngines;
import lb.engines.utils.AccountManager;
import lb.engines.utils.FunctionsManager;
import lb.engines.utils.MysqlManager;
import lb.engines.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TestCommand implements CommandExecutor {

    public TestCommand(MainEngines test, String command) {
        Objects.requireNonNull(test.getCommand(command)).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (args.length == 0) {
            args0(sender, args);
            return true;
        }
        if (args.length == 1) {
            args1(sender, args);
            return true;
        }
        sender.sendMessage(FunctionsManager.formatRGB("&cArgumentos inválidos."));
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        PlayerManager ps = MysqlManager.getPlayerCached(player);
        player.sendMessage(FunctionsManager.formatRGB("&aSeu dinheiro" + ps.getMoney()));
        player.sendMessage(FunctionsManager.formatRGB("&cSuas mortes" + ps.getKills()));
        player.sendMessage(FunctionsManager.formatRGB("&2Suas mortes" + ps.getDeaths()));
    }

    private void args1(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(target.isOnline()) {
            PlayerManager ps = MysqlManager.getPlayerCached(target.getPlayer());
            player.sendMessage(FunctionsManager.formatRGB("&aSeu dinheiro" + ps.getMoney()));
            player.sendMessage(FunctionsManager.formatRGB("&cSuas mortes" + ps.getKills()));
            player.sendMessage(FunctionsManager.formatRGB("&2Suas mortes" + ps.getDeaths()));
            return;
        }
        player.sendMessage(FunctionsManager.formatRGB("&aSeu dinheiro" + AccountManager.getMoney(target)));
        player.sendMessage(FunctionsManager.formatRGB("&cSuas mortes" + AccountManager.getKills(target)));
        player.sendMessage(FunctionsManager.formatRGB("&2Suas mortes" + AccountManager.getDeaths(target)));
    }

}
