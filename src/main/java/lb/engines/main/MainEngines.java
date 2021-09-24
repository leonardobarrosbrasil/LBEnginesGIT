package lb.engines.main;

import lb.engines.commands.TestCommand;
import lb.engines.events.OnPlayerJoin;
import lb.engines.events.OnPlayerQuit;
import lb.engines.utils.LBFunctions;
import lb.engines.utils.LBManager;
import lb.engines.utils.LBMySQL;
import lb.engines.utils.LBPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public final class MainEngines extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainEngines instance;

    public static MainEngines getPlugin() {
        return instance;
    }

    private LBMySQL SQL;
    private LBManager Manager;

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
        console.sendMessage(LBFunctions.formatRGB("§aLBEngines: Eventos carregados com sucesso."));
    }

    public void registerCommands() {
        TestCommand engines = new TestCommand(this, "lbengines");
        console.sendMessage(LBFunctions.formatRGB("§aLBEngines: Comandos carregados com sucesso."));
    }

    public void registerAutoSave() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), () -> {
            if (getManager().cacheSize() < 1) return;
            getManager().getCache().forEach((uuid, data) -> {
                getManager().saveData(uuid);
                console.sendMessage("§aDados de " + uuid + " salvos no salvamento automatico.");
            });
            console.sendMessage("§aSalvamento automatico iniciado.");
        }, 0L, 20 * 20L);
    }

    public void forceSave() {
        if (getManager().cacheSize() < 1) return;
        getManager().getCache().forEach((uuid, data) -> {
            getManager().saveData(uuid);
            console.sendMessage("§aDados de " + uuid + " salvos no salvamento forcado.");
        });
        console.sendMessage("§aSalvamento forcado iniciado.");
    }

    @Override
    public void onEnable() {
        instance = this;
        this.SQL = new LBMySQL();
        this.Manager = new LBManager();
        SQL.createTables();
        registerAutoSave();
        registerEvents();
        registerCommands();
        console.sendMessage(LBFunctions.formatRGB("§aLBEngines: Plugin habilitado com sucesso."));
    }

    @Override
    public void onDisable() {
        forceSave();
        console.sendMessage(LBFunctions.formatRGB("§cLBEngines: Plugin desabilitado com sucesso."));
    }

}
