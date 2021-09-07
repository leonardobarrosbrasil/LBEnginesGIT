package lb.engines.commands;

import lb.engines.main.mainEngines;
import lb.engines.utils.accountManager;
import lb.engines.utils.functionsManager;
import lb.engines.utils.mysqlManager;
import lb.engines.utils.playerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TestCommand implements CommandExecutor {

    public TestCommand(mainEngines test, String command) {
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
        if (args.length == 5) {
            args5(sender, args);
            return true;
        }
        sender.sendMessage(functionsManager.formatRGB("&cArgumentos inválidos."));
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        playerManager stats = mainEngines.stats.get(player.getUniqueId());
        player.sendMessage(functionsManager.formatRGB("&aSeu dinheiro" + stats.getMoney()));
        player.sendMessage(functionsManager.formatRGB("&cSuas mortes" + stats.getKills()));
        player.sendMessage(functionsManager.formatRGB("&2Suas mortes" + stats.getDeaths()));
    }

    private void args1(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if(target.isOnline()) {
            playerManager stats = mainEngines.stats.get(player.getUniqueId());
            player.sendMessage(functionsManager.formatRGB("&aSeu dinheiro" + stats.getMoney()));
            player.sendMessage(functionsManager.formatRGB("&cSuas mortes" + stats.getKills()));
            player.sendMessage(functionsManager.formatRGB("&2Suas mortes" + stats.getDeaths()));
            return;
        }
        player.sendMessage(functionsManager.formatRGB("&aSeu dinheiro" + accountManager.getMoney(target)));
        player.sendMessage(functionsManager.formatRGB("&cSuas mortes" + accountManager.getKills(target)));
        player.sendMessage(functionsManager.formatRGB("&2Suas mortes" + accountManager.getDeaths(target)));
    }

    private void args5(CommandSender sender, String[] args) {
        if(args[0].equalsIgnoreCase("setar")){
            Player player = (Player) sender;
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
            if(target.isOnline()) {
                playerManager stats = mainEngines.stats.get(target.getUniqueId());
                stats.setMoney(Double.parseDouble(args[2]));
                stats.setKills(Integer.parseInt(args[3]));
                stats.setDeaths(Integer.parseInt(args[4]));
                player.sendMessage("&aConfiguraoces definidas para um jogador online.");
                return;
            }
            accountManager.setMoney(target, Double.parseDouble(args[2]));
            accountManager.setKills(target, Integer.parseInt(args[4]));
            accountManager.setDeaths(target, Integer.parseInt(args[4]));
            player.sendMessage("&aConfiguraoces definidas para um jogador offline.");
        }

    }
}
