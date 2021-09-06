package lb.engines.utils;

import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class AccountManager extends MysqlManager {

    public static boolean accountExist(OfflinePlayer player) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void createAccount(OfflinePlayer player) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(MainEngines.getPlugin(), new Runnable() {
            @Override
            public void run() {
                PreparedStatement stm = null;
                try {
                    stm = connection.prepareStatement("INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`) VALUES (?,?,?,?)");
                    stm.setString(1, player.getUniqueId().toString()); // uuid
                    stm.setDouble(2, 0.0D); // money
                    stm.setInt(3, 0); // kills
                    stm.setInt(4, 0); // deaths
                    stm.executeUpdate();
                } catch (SQLException e) {
                    console.sendMessage(FunctionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao criar o jogador " + player.getName() + "."));
                    e.printStackTrace();
                }
                Object oi = null;
                return oi;
            }
        });
    }

    public static CompletableFuture<Double> getMoney(OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            double i = 0.0;
            PreparedStatement stm = null;
            try {
                stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
                stm.setString(1, player.getUniqueId().toString());
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    i = rs.getDouble("money");
                }
            } catch (SQLException e) {
                console.sendMessage("§cLBEngines: Ocorreu um erro ao ver o dinheiro de " + player.getName() + ".");
            }
            return i;
        });
    }

    public static Integer getDeaths(OfflinePlayer player) {
        int i = 0;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("deaths");
            }
        } catch (SQLException e) {
            console.sendMessage(FunctionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao ver as mortes de " + player.getName() + "."));
        }
        return i;
    }

    public static Integer getKills(OfflinePlayer player) {
        int i = 0;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("kills");
            }
        } catch (SQLException e) {
            console.sendMessage(FunctionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao ver as mortes de " + player.getName() + "."));
        }
        return i;
    }
}
