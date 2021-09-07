package lb.engines.main;

import lb.engines.commands.TestCommand;
import lb.engines.events.onPlayerJoin;
import lb.engines.events.onPlayerQuit;
import lb.engines.utils.functionsManager;
import lb.engines.utils.mysqlManager;
import lb.engines.utils.playerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class mainEngines extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static mainEngines instance;

    public static HashMap<UUID, playerManager> stats = new HashMap<>();

    public static mainEngines getPlugin() {
        return instance;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new onPlayerQuit(), this);
        console.sendMessage(functionsManager.formatRGB("&aLBEngines: Eventos carregados com sucesso."));
    }

    public void registerCommands() {
        TestCommand engines = new TestCommand(this, "lbengines");
        console.sendMessage(functionsManager.formatRGB("&aLBEngines: Comandos carregados com sucesso."));
    }

    @Override
    public void onEnable() {
        instance = this;
        mysqlManager.openConnection();
        registerEvents();
        registerCommands();
        console.sendMessage(functionsManager.formatRGB("&aLBEngines: Plugin habilitado com sucesso."));
    }

    @Override
    public void onDisable() {
        mysqlManager.closeConnection();
        console.sendMessage(functionsManager.formatRGB("&caLBEngines: Plugin desabilitado com sucesso."));
    }
}
