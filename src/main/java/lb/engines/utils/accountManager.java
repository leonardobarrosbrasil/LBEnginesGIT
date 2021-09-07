package lb.engines.utils;

import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class accountManager extends mysqlManager {

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
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`) VALUES (?,?,?,?)");
            stm.setString(1, player.getUniqueId().toString()); // uuid
            stm.setDouble(2, 0.0D); // money
            stm.setInt(3, 0); // kills
            stm.setInt(4, 0); // deaths
            stm.executeUpdate();
        } catch (SQLException e) {
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao criar o jogador " + player.getName() + "."));
            e.printStackTrace();
        }
    }

    public static double getMoney(OfflinePlayer player) {
        double i = 0.0;
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("money");
            }
        } catch (SQLException e) {
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao ver o dinheiro de " + player.getName() + "."));
        }
        return i;
    }

    public static void setMoney(OfflinePlayer player, Double value) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("UPDATE `players` SET `money` = ? WHERE `uuid` = ?");
            stm.setDouble(1, value);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao definir o dinheiro do jogador " + player.getName() + "."));
        }
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
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao ver as mortes de " + player.getName() + "."));
        }
        return i;
    }

    public static void setDeaths(OfflinePlayer player, int value) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("UPDATE `players` SET `deaths` = ? WHERE `uuid` = ?");
            stm.setInt(1, value);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao definir as mortes de " + player.getName() + "."));
        }
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
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao ver as mortes de " + player.getName() + "."));
        }
        return i;
    }


    public static void setKills(OfflinePlayer player, int value) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("UPDATE `players` SET `kills` = ? WHERE `uuid` = ?");
            stm.setInt(1, value);
            stm.setString(2, player.getUniqueId().toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            console.sendMessage(functionsManager.formatRGB("§cLBEngines: Ocorreu um erro ao definir as mortes de " + player.getName() + "."));
        }
    }
}
