package lb.engines.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LBMySQL {
    private final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255),`money` DOUBLE, `kills` INTEGER, `deaths` INTEGER, `level` INTEGER, `exp` INTEGER, `eventWins` INTEGER, `eventParticipations` INTEGER)";
    private final String SELECT_PLAYER = "SELECT uuid,money,kills,deaths,level,exp,eventWins,eventParticipations FROM players WHERE uuid = ?";
    private final String CREATE_PLAYER = "INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`,`level`,`exp`,`eventWins`,`eventParticipations`) VALUES (?,?,?,?,?,?,?,?)";
    private final String CHECK_PLAYER = "SELECT * FROM `players` WHERE `uuid` = ?";

    public final String SET_ALL = "UPDATE `players` SET `money` = ?, `kills` = ?, `deaths` = ?, `level` = ?, `exp` = ?, `eventWins` = ?, `eventParticipations` = ? WHERE `uuid` = ?";
    public final String SET_MONEY = "UPDATE `players` SET `money` = ? WHERE `uuid` = ?";
    public final String SET_KILLS = "UPDATE `players` SET `kills` = ? WHERE `uuid` = ?";
    public final String SET_DEATHS = "UPDATE `players` SET `deaths` = ? WHERE `uuid` = ?";
    public final String SET_LEVEL = "UPDATE `players` SET `level` = ? WHERE `uuid` = ?";
    public final String SET_EXP = "UPDATE `players` SET `exp` = ? WHERE `uuid` = ?";
    public final String SET_EVENTWINS = "UPDATE `players` SET `eventWins` = ? WHERE `uuid` = ?";
    public final String SET_EVENTPARTICIPATIONS = "UPDATE `players` SET `eventParticipations` = ? WHERE `uuid` = ?";
    public final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private HikariDataSource hikariCP;

    public LBMySQL() {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("");
        hikariCP = new HikariDataSource();
        hikariCP.setIdleTimeout(870000000);
        hikariCP.setMaxLifetime(870000000);
        hikariCP.setConnectionTimeout(870000000);
        //hikariCP.setRegisterMbeans(true);
        hikariCP.setMaximumPoolSize(10);
        String host = "localhost";
        config.setJdbcUrl("jdbc:mysql://" +
                "127.0.0.1:3306/" + "phpmyadmin");
        hikariCP = new HikariDataSource(config);
        console.sendMessage("§aLBEngines: Database conectada com sucesso.");
    }

    public HikariDataSource getHikariCP() {
        return hikariCP;
    }

    public void closeConnection() {
        try (Connection conn = hikariCP.getConnection()) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(CREATE_TABLES);
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§aLBEngines: As tabelas carregadas com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
            console.sendMessage("§aLBEngines: As tabelas não foram carregadas.");
        }
    }

    public LBPlayer getData(UUID uuid) {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(SELECT_PLAYER);
            pstmt.setString(1, uuid.toString());
            ResultSet result = pstmt.executeQuery();
            if (!result.next()) return null;
            LBPlayer player = new LBPlayer();
            player.setUUID(result.getString(1));
            player.setMoney(result.getDouble(2));
            player.setKills(result.getInt(3));
            player.setDeaths(result.getInt(4));
            player.setLevel(result.getInt(5));
            player.setExp(result.getInt(6));
            player.setEventWins(result.getInt(7));
            player.setEventParticipations(result.getInt(8));
            result.close();
            pstmt.close();
            return player;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void createAccount(UUID uuid) {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(CREATE_PLAYER);
            pstmt.setString(1, uuid.toString()); // UUID
            pstmt.setDouble(2, 0.0D); // money
            pstmt.setInt(3, 0); // kills
            pstmt.setInt(4, 0); // deaths
            pstmt.setInt(5, 1); // level
            pstmt.setInt(6, 0); // exp
            pstmt.setInt(7, 0); // eventWins
            pstmt.setInt(8, 0); // eventParticipations
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Boolean accountExist(UUID uuid) {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(CHECK_PLAYER);
            pstmt.setString(1, uuid.toString());
            ResultSet result = pstmt.executeQuery();
            if (result.next())
                return true;
            result.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void saveData(UUID uuid) {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_ALL);
            LBPlayer data = MainEngines.getPlugin().getManager().getCached(uuid);
            stm.setDouble(1, data.getMoney());
            stm.setInt(2, data.getKills());
            stm.setInt(3, data.getDeaths());
            stm.setInt(4, data.getLevel());
            stm.setInt(5, data.getExp());
            stm.setInt(6, data.getEventWins());
            stm.setInt(7, data.getEventParticipations());
            stm.setString(8, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Object setMoney(UUID uuid, double value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_MONEY);
            stm.setDouble(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§aLBEngines: O valor `money` de " + uuid + " foi definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setKills(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            MainEngines.getPlugin().getManager().getCached(uuid).setKills(value);
        } else {
            try (Connection conn = hikariCP.getConnection()) {
                PreparedStatement stm = conn.prepareStatement(SET_KILLS);
                stm.setInt(1, value);
                stm.setString(2, String.valueOf(uuid));
                stm.executeUpdate();
                stm.close();
                console.sendMessage("§a§aLBEngines: O valor `kills` de " + uuid + " foi definido para" + value + ".");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Object setDeaths(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
            MainEngines.getPlugin().getManager().getCached(uuid).setDeaths(value);
        } else {
            try (Connection conn = hikariCP.getConnection()) {
                PreparedStatement stm = conn.prepareStatement(SET_DEATHS);
                stm.setInt(1, value);
                stm.setString(2, String.valueOf(uuid));
                stm.executeUpdate();
                stm.close();
                console.sendMessage("§a§aLBEngines: O valor `deaths` de " + uuid + " foi definido para" + value + ".");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Object setLevel(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_LEVEL);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: O valor `level` de " + uuid + " foi definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setExp(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_EXP);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: O valor `exp` de " + uuid + " foi definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setEventWins(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_EVENTWINS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: O valor `eventWins` de " + uuid + " foi definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setEventParticipations(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_EVENTPARTICIPATIONS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: O valor `eventWins` de " + uuid + " foi definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
