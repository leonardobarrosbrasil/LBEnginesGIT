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

    public final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255),`money` DOUBLE, `kills` INTEGER, `deaths` INTEGER, `level` INTEGER, `exp` INTEGER, `eventWins` INTEGER, `eventParticipations` INTEGER, `fightWins` INTEGER, `fightDefeats` INTEGER, `partner` varchar(255))";
    public final String SELECT_PLAYER = "SELECT uuid,money,kills,deaths,level,exp,eventWins,eventParticipations,fightWins,fightDefeats,partner FROM players WHERE uuid = ?";
    public final String CREATE_PLAYER = "INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`,`level`,`exp`,`eventWins`,`eventParticipations`,`fightWins`,`fightDefeats`,`partner`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    public final String CHECK_PLAYER = "SELECT * FROM `players` WHERE `uuid` = ?";
    public final String SET_ALL = "UPDATE `players` SET `money` = ?, `kills` = ?, `deaths` = ?, `level` = ?, `exp` = ?, `eventWins` = ?, `eventParticipations` = ?, `fightWins` = ?, `fightDefeats` = ?, `partner` = ? WHERE `uuid` = ?";
    public final String SET_MONEY = "UPDATE `players` SET `money` = ? WHERE `uuid` = ?";
    public final String SET_KILLS = "UPDATE `players` SET `kills` = ? WHERE `uuid` = ?";
    public final String SET_DEATHS = "UPDATE `players` SET `deaths` = ? WHERE `uuid` = ?";
    public final String SET_LEVEL = "UPDATE `players` SET `level` = ? WHERE `uuid` = ?";
    public final String SET_EXP = "UPDATE `players` SET `exp` = ? WHERE `uuid` = ?";
    public final String SET_EVENTWINS = "UPDATE `players` SET `eventWins` = ? WHERE `uuid` = ?";
    public final String SET_EVENTPARTICIPATIONS = "UPDATE `players` SET `eventParticipations` = ? WHERE `uuid` = ?";
    public final String SET_FIGHTWINS = "UPDATE `players` SET `fightWins` = ? WHERE `uuid` = ?";
    public final String SET_FIGHTDEFEATS = "UPDATE `players` SET `fightDefeats` = ? WHERE `uuid` = ?";
    public final String SET_PARTNER = "UPDATE `players` SET `partner` = ? WHERE `uuid` = ?";

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
            console.sendMessage("§aLBEngines: Tabelas carregadas com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
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
            player.setFightWins(result.getInt(9));
            player.setFightDefeats(result.getInt(10));
            player.setPartner(result.getString(11));
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
            pstmt.setInt(9, 0); // fightWins
            pstmt.setInt(10, 0); // fightDefeats
            pstmt.setString(11, null); // fightDefeats
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
            stm.setInt(8, data.getFightWins());
            stm.setInt(9, data.getFightDefeats());
            stm.setString(10, String.valueOf(data.getPartner()));
            stm.setString(11, String.valueOf(uuid));
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
            console.sendMessage("§aLBEngines: Valor `money` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setKills(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_KILLS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: Valor `kills` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setDeaths(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_DEATHS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: Valor `deaths` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            console.sendMessage("§a§aLBEngines: Valor `level` de " + uuid + " definido para" + value + ".");
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
            console.sendMessage("§a§aLBEngines: Valor `exp` de " + uuid + " definido para" + value + ".");
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
            console.sendMessage("§a§aLBEngines: Valor `eventWins` de " + uuid + " definido para" + value + ".");
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
            console.sendMessage("§a§aLBEngines: Valor `eventWins` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setFightWins(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_FIGHTWINS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: Valor `fightWins` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setFightDefeats(UUID uuid, int value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_FIGHTDEFEATS);
            stm.setInt(1, value);
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: Valor `fightDefeats` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object setPartner(UUID uuid, UUID value) {
        if (!accountExist(uuid)) return null;
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(SET_PARTNER);
            stm.setString(1, String.valueOf(value));
            stm.setString(2, String.valueOf(uuid));
            stm.executeUpdate();
            stm.close();
            console.sendMessage("§a§aLBEngines: Valor `partner` de " + uuid + " definido para" + value + ".");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
