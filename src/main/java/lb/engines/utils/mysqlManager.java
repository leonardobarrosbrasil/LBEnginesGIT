package lb.engines.utils;

import com.zaxxer.hikari.HikariDataSource;
import lb.engines.main.mainEngines;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class mysqlManager {

    protected Connection connection = null;

    public ConsoleCommandSender console = Bukkit.getConsoleSender();

    private HikariDataSource hikariCP;

    public HashMap<UUID, playerManager> stats = new HashMap<>();

    public void openConnection() {
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
            String host = "localhost";
            hikariCP.addDataSourceProperty("serverName", host);
            String port = "3306";
            hikariCP.addDataSourceProperty("port", port);
            String database = "phpmyadmin";
            hikariCP.addDataSourceProperty("databaseName", database);
            String user = "root";
            hikariCP.addDataSourceProperty("user", user);
            String password = "";
            hikariCP.addDataSourceProperty("password", password);
            createTables();
        } catch (Exception e) {
            e.printStackTrace();
            console.sendMessage(functionsManager.formatRGB("§aLBMechanism: Database conectada com sucesso."));
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                console.sendMessage(functionsManager.formatRGB("§cLBEngines: Database desconectada com sucesso."));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTables() {
            try {
                connection = hikariCP.getConnection();
                PreparedStatement stm = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `players` (`uuid` varchar(255),`money` DOUBLE, `kills` INTEGER, `deaths` INTEGER)");
                stm.executeUpdate();
                console.sendMessage(functionsManager.formatRGB("&aLBEngines: As tabelas carregadas com sucesso."));
            } catch (Exception e) {
                e.printStackTrace();
                console.sendMessage(functionsManager.formatRGB("&aLBEngines: As tabelas não foram carregadas."));
            }
    }

    public void autoSave() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(mainEngines.getPlugin(), () -> {
            for (playerManager allData : pluginManager.getMySQL().getAllDatas()) {
                pluginManager.getAccount().saveData(allData);
            }
        },600 * 20L);
    }

    public void forceSave() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(mainEngines.getPlugin(), () -> {
            for (playerManager allData : pluginManager.getMySQL().getAllDatas()) {
                pluginManager.getAccount().saveData(allData);
            }
        });
    }

    public void addData(UUID uuid, playerManager ps){
       stats.put(uuid, ps);
    }

    public Boolean removeData(UUID uuid){
        if(!stats.containsKey(uuid)){
            return false;
        }
        stats.remove(uuid);
        return true;
    }

    public Boolean hasData(UUID uuid){
        return stats.containsKey(uuid);
    }

    public playerManager getData(UUID uuid){
        return stats.get(uuid);
    }

    public Collection<playerManager> getAllDatas(){
        return stats.values();
    }

    public void clearCache() {
        stats.clear();
    }

    public Map<UUID, playerManager> getDatas() {
        return stats;
    }

}
