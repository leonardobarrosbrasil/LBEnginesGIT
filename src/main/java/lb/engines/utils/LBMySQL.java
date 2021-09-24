package lb.engines.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LBMySQL {
    private final String CREATE_TABLES = "CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255),`money` DOUBLE, `kills` INTEGER, `deaths` INTEGER)";
    private final String SELECT_PLAYER = "SELECT uuid,money,kills,deaths FROM players WHERE uuid=?";
    private final String CREATE_PLAYER = "INSERT INTO `players` (`uuid`, `money`, `kills`, `deaths`) VALUES (?,?,?,?)";
    private final String CHECK_PLAYER = "SELECT * FROM `players` WHERE `uuid` = ?";
    public ConsoleCommandSender console = Bukkit.getConsoleSender();

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
        console.sendMessage(LBFunctions.formatRGB("§aLBEngines: Database conectada com sucesso."));
    }

    public HikariDataSource getHikariCP() {
        return hikariCP;
    }

    public void createTables() {
        try (Connection conn = hikariCP.getConnection()) {
            PreparedStatement stm = conn.prepareStatement(CREATE_TABLES);
            stm.executeUpdate();
            stm.close();
            console.sendMessage(LBFunctions.formatRGB("&aLBEngines: As tabelas carregadas com sucesso."));
        } catch (Exception e) {
            e.printStackTrace();
            console.sendMessage(LBFunctions.formatRGB("&aLBEngines: As tabelas não foram carregadas."));
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
            pstmt.setString(1, uuid.toString());
            pstmt.setDouble(2, 0.0D);
            pstmt.setInt(3, 0);
            pstmt.setInt(4, 0);
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
}
