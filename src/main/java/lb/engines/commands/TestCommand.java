package lb.engines.commands;

import lb.engines.main.MainEngines;
import lb.engines.utils.LBFunctions;
import lb.engines.utils.LBPlayer;
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
        if (args.length == 3) {
            args3(sender, args);
            return true;
        }
        sender.sendMessage(LBFunctions.formatRGB("§cArgumentos inválidos."));
        return true;
    }

    private void args0(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        LBPlayer stats = MainEngines.getPlugin().getManager().getCached(player.getUniqueId());
        player.sendMessage("§6Seu dinheiro" + stats.getMoney());
        player.sendMessage("§aSuas mortes" + stats.getKills());
        player.sendMessage("§cSuas mortes" + stats.getDeaths());
    }

    private void args1(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!MainEngines.getPlugin().getMysql().accountExist(target.getUniqueId())) {
            player.sendMessage("§cJogador não encontrado.");
            return;
        }
        if (target.isOnline()) {
            LBPlayer stats = MainEngines.getPlugin().getManager().getCached(target.getUniqueId());
            player.sendMessage("§6Seu dinheiro" + stats.getMoney());
            player.sendMessage("§aSuas mortes" + stats.getKills());
            player.sendMessage("§cSuas mortes" + stats.getDeaths());
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
            LBPlayer stats2 = MainEngines.getPlugin().getMysql().getData(player.getUniqueId());
            player.sendMessage("§6Dinheiro de " + target.getPlayer() + stats2.getMoney());
            player.sendMessage("§aMortes de " + target.getPlayer() + stats2.getKills());
            player.sendMessage("§cMortes de " + target.getPlayer() + stats2.getDeaths());
        });
    }

    private void args3(CommandSender sender, String[] args) {
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!MainEngines.getPlugin().getMysql().accountExist(target.getUniqueId())) {
            sender.sendMessage("§cJogador não encontrado.");
            return;
        }
        switch (args[0]) {
            case "dinheiro":
                if (target.isOnline()) {
                    LBPlayer stats = MainEngines.getPlugin().getManager().getCache(target.getUniqueId());
                    stats.setMoney(Double.parseDouble(args[2]));
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
                    MainEngines.getPlugin().getManager().setMoney(target.getUniqueId(), Double.parseDouble(args[2]));
                });
                break;
            case "matou":
                if (target.isOnline()) {
                    LBPlayer stats = MainEngines.getPlugin().getManager().getCache(target.getUniqueId());
                    stats.setKills(Integer.parseInt(args[2]));
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
                    MainEngines.getPlugin().getManager().setKills(target.getUniqueId(), Integer.parseInt(args[2]));
                });
                break;
            case "morreu":
                if (target.isOnline()) {
                    LBPlayer stats = MainEngines.getPlugin().getManager().getCache(target.getUniqueId());
                    stats.setDeaths(Integer.parseInt(args[2]));
                    return;
                }
                Bukkit.getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), () -> {
                    MainEngines.getPlugin().getManager().setDeaths(target.getUniqueId(), Integer.parseInt(args[2]));
                });
                break;
            default:
                sender.sendMessage("§cArgumentos inválidos.");
                break;
        }
    }
}
