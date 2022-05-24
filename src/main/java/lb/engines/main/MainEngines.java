package lb.engines.main;

import lb.engines.commands.CommandSave;
import lb.engines.events.OnPlayerJoin;
import lb.engines.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public final class MainEngines extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainEngines instance;

    public static MainEngines getPlugin() {
        return instance;
    }

    private static LBMySQL SQL;
    private static LBManager Manager;
    private static LBFunctions Functions;

    public void registerCommands() {
        CommandSave save = new CommandSave(this, "salvar");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        console.sendMessage("§aLBEngines: Eventos carregados com sucesso.");
    }

    public void registerAutoSave() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getManager().cacheSize() < 1)
                return;
            Iterator<LBPlayer> it = getManager().getCache().values().iterator();
            while (it.hasNext()) {
                LBPlayer data = it.next();
                getMySQL().saveData(data.getUUID());
                Player player = Bukkit.getPlayer(data.getUUID());
                if (player == null)
                    it.remove();
            }
            this.console.sendMessage("§aLBEngines: Dados salvos automaticamente.");
        }, 600 * 20L, 600 * 20L);
    }

    public void forceSave() {
        if (getManager().cacheSize() < 1)
            return;
        getManager().getCache().forEach((uuid, data) -> getMySQL().saveData(uuid));
        this.console.sendMessage("§aLBEngines: Dados salvos forçadamente.");
    }

    public void forceSaveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (getManager().cacheSize() < 1)
                return;
            Iterator<LBPlayer> it = getManager().getCache().values().iterator();
            while (it.hasNext()) {
                LBPlayer data = it.next();
                getMySQL().saveData(data.getUUID());
                Player player = Bukkit.getPlayer(data.getUUID());
                if (player == null)
                    it.remove();
            }
            this.console.sendMessage("§aLBEngines: Dados salvos forçadamente.");
        });
    }

    public void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new LBPlaceholder(this).register();
            console.sendMessage("§aLBEngines: PlaceholderAPI carregada com sucesso.");
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        SQL = new LBMySQL();
        Manager = new LBManager();
        Functions = new LBFunctions();
        SQL.createTables();
        registerCommands();
        registerAutoSave();
        registerEvents();
        registerPlaceholders();
        console.sendMessage("§aLBEngines: Plugin carregado com sucesso.");
    }

    @Override
    public void onDisable() {
        forceSave();
        SQL.closeConnection();
        console.sendMessage("§cLBEngines: Plugin descarregado com sucesso.");
    }

    public LBMySQL getMySQL() {
        return SQL;
    }

    public LBManager getManager() {
        return Manager;
    }

    public LBFunctions getFunctions() {
        return Functions;
    }
}
