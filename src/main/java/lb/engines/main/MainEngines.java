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

    private LBMySQL SQL;
    private LBManager Manager;
    private LBFunctions Functions;

    public void registerCommands() {
        CommandSave save = new CommandSave(this, "salvar");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        console.sendMessage("§aLBEngines: Eventos carregados com sucesso.");
    }

    public void registerAutoSave() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            if (getManager().cacheSize() <= 0)
                return;
            Iterator<LBPlayer> it = getManager().getCache().values().iterator();
            while (it.hasNext()) {
                LBPlayer data = it.next();
                getMySQL().saveData(data.getUUID());
                Player player = Bukkit.getPlayer(data.getUUID());
                if (player == null)
                    it.remove();
            }
            this.console.sendMessage("\u00A7aLBEngines: Salvando todos os dados cacheados na database.");
        }, 600 * 20L, 600 * 20L);
    }

    public void forceSave() {
        if (getManager().cacheSize() < 1)
            return;
        getManager().getCache().forEach((uuid, data) -> getMySQL().saveData(uuid));
        this.console.sendMessage("§aLBEngines: Salvando todos os dados cacheados na database forcadamente.");
    }

    public void forceSaveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (getManager().cacheSize() <= 0)
                return;
            Iterator<LBPlayer> it = getManager().getCache().values().iterator();
            while (it.hasNext()) {
                LBPlayer data = it.next();
                getMySQL().saveData(data.getUUID());
                Player player = Bukkit.getPlayer(data.getUUID());
                if (player == null)
                    it.remove();
            }
            this.console.sendMessage("§aLBEngines: Salvando todos os dados cacheados na database.");
        });
    }

    public void registerPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new LBPlaceholder(this).register();
            console.sendMessage("§aLBEngines: PlaceholderAPI registrado com sucesso.");
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        this.SQL = new LBMySQL();
        this.Manager = new LBManager();
        this.Functions = new LBFunctions();
        SQL.createTables();
        registerCommands();
        registerAutoSave();
        registerEvents();
        registerPlaceholders();
        console.sendMessage("§aLBEngines: Plugin habilitado com sucesso.");
    }

    @Override
    public void onDisable() {
        forceSave();
        SQL.closeConnection();
        console.sendMessage("§cLBEngines: Plugin desabilitado com sucesso.");
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
