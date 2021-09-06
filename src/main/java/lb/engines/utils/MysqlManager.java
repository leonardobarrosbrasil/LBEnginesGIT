package lb.engines.utils;

import com.zaxxer.hikari.HikariDataSource;
import lb.engines.main.MainEngines;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class MysqlManager {

    protected static Connection connection = null;

    public static ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static HikariDataSource hikariCP;

    private MysqlManager api;

    private static final String host = "localhost";
    private static final String port = "3306";
    private static final String user = "root";
    private static final String database = "phpmyadmin";
    private static final String password = "";

    public static void openConnection() {
        try {
            hikariCP = new HikariDataSource();
            hikariCP.setIdleTimeout(870000000);
            hikariCP.setMaxLifetime(870000000);
            hikariCP.setConnectionTimeout(870000000);
            hikariCP.setMinimumIdle(20);
            hikariCP.setRegisterMbeans(true);
            hikariCP.setMaximumPoolSize(10);
            hikariCP.setConnectionTestQuery("SELECT 1");
            hikariCP.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
            hikariCP.addDataSourceProperty("serverName", host);
            hikariCP.addDataSourceProperty("port", port);
            hikariCP.addDataSourceProperty("databaseName", database);
            hikariCP.addDataSourceProperty("user", user);
            hikariCP.addDataSourceProperty("password", password);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
            console.sendMessage(FunctionsManager.formatRGB("§aLBMechanism: Database conectada com sucesso."));
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                console.sendMessage(FunctionsManager.formatRGB("§cLBEngines: Database desconectada com sucesso."));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTables() {
            PreparedStatement stm = null;
            try {
                connection = hikariCP.getConnection();
                stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255),`money` DOUBLE, `kills` INTEGER, `deaths` INTEGER)");
                stm.executeUpdate();
                console.sendMessage(FunctionsManager.formatRGB("&aLBEngines: As tabelas carregadas com sucesso."));
            } catch (Exception e) {
                e.printStackTrace();
                console.sendMessage(FunctionsManager.formatRGB("&aLBEngines: As tabelas não foram carregadas."));
            }
    }

    public static void addPlayerToCache(Player player, PlayerManager ps){
        MainEngines.stats.put(player, ps);
    }

    public static PlayerManager getPlayerCached(Player player){
        return MainEngines.stats.get(player);
    }

}
