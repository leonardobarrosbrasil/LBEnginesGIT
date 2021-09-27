package lb.engines.main;

import lb.engines.commands.CommandSave;
import lb.engines.events.OnPlayerJoin;
import lb.engines.events.OnPlayerQuit;
import lb.engines.utils.LBManager;
import lb.engines.utils.LBMySQL;
import lb.engines.utils.LBPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainEngines extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainEngines instance;

    public static MainEngines getPlugin() {
        return instance;
    }

    private LBMySQL SQL;
    private LBManager Manager;

    public void registerCommands() {
        CommandSave save = new CommandSave(this, "salvar");
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
        console.sendMessage("§aLBEngines: Eventos carregados com sucesso.");
    }

    public void registerAutoSave() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), () -> {
            if (getManager().cacheSize() < 1) return;
            getManager().getCache().forEach((uuid, data) -> {
                getMysql().saveData(uuid);
            });
            console.sendMessage("§aLBEngines: Salvando todos os dados cacheados na database.");
        }, 300 * 20L, 300 * 20L);
    }

    public void forceSave() {
        if (getManager().cacheSize() < 1) return;
        getManager().getCache().forEach((uuid, data) -> {
            getMysql().saveData(uuid);
        });
        console.sendMessage("§aLBEngines: Salvando todos os dados cacheados na database forcadamente.");
    }

    public void forceSaveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), () -> {
            if (getManager().cacheSize() < 1) return;
            getManager().getCache().forEach((uuid, data) -> {
                getMysql().saveData(uuid);
            });
            console.sendMessage("§aLBEngines: Salvando todos os dados cacheados na database forcadamente.");
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
        console.sendMessage("§cLBEngines: Plugin desabilitado com sucesso.");
    }

}
