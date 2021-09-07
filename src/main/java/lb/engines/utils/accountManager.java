package lb.engines.utils;

import lb.engines.main.mainEngines;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class accountManager extends mysqlManager {


    public void saveData(playerManager player) {
        Bukkit.getScheduler().runTaskAsynchronously(mainEngines.getPlugin(), () -> {
            setMoney(player.getUUID(), player.getMoney());
            setKills(player.getUUID(), player.getKills());
            setDeaths(player.getUUID(), player.getDeaths());
        });
    }

    public void loadData(Player player){
        playerManager stats = new playerManager();
        stats.setMoney(pluginManager.getAccount().getMoney(player.getUniqueId()));
        stats.setKills(pluginManager.getAccount().getKills(player.getUniqueId()));
        stats.setDeaths(pluginManager.getAccount().getDeaths(player.getUniqueId()));
        pluginManager.getMySQL().addData(player.getUniqueId(), stats);
    }

    public boolean accountExist(UUID uuid) {
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, uuid.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public void createAccount(UUID uuid) {
        try {
            PreparedStatement stm = connection.prepareStatement("INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`) VALUES (?,?,?,?)");
            stm.setString(1, uuid.toString()); // uuid
            stm.setDouble(2, 0.0D); // money
            stm.setInt(3, 0); // kills
            stm.setInt(4, 0); // deaths
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getMoney(UUID uuid) {
        double i = 0.0;
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, uuid.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void setMoney(UUID uuid, Double value) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE `players` SET `money` = ? WHERE `uuid` = ?");
            stm.setDouble(1, value);
            stm.setString(2, uuid.toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getDeaths(UUID uuid) {
        int i = 0;
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, uuid.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("deaths");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void setDeaths(UUID uuid, int value) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE `players` SET `deaths` = ? WHERE `uuid` = ?");
            stm.setInt(1, value);
            stm.setString(2, uuid.toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getKills(UUID uuid) {
        int i = 0;
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM `players` WHERE `uuid` = ?");
            stm.setString(1, uuid.toString());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                i = rs.getInt("kills");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    public void setKills(UUID uuid, int value) {
        try {
            PreparedStatement stm = connection.prepareStatement("UPDATE `players` SET `kills` = ? WHERE `uuid` = ?");
            stm.setInt(1, value);
            stm.setString(2, uuid.toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
