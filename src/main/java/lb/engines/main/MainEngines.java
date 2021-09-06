package lb.engines.main;

import lb.engines.commands.TestCommand;
import lb.engines.events.OnPlayerJoin;
import lb.engines.utils.FunctionsManager;
import lb.engines.utils.MysqlManager;
import lb.engines.utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class MainEngines extends JavaPlugin {

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private static MainEngines instance;

    public static HashMap<UUID, PlayerManager> stats = new HashMap<>();

    public static MainEngines getPlugin() {
        return instance;
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        console.sendMessage(FunctionsManager.formatRGB("&aLBEngines: Eventos carregados com sucesso."));
    }

    public void registerCommands() {
        TestCommand engines = new TestCommand(this, "lbengines");
        console.sendMessage(FunctionsManager.formatRGB("&aLBEngines: Comandos carregados com sucesso."));
    }

    @Override
    public void onEnable() {
        instance = this;
        MysqlManager.openConnection();
        registerEvents();
        registerCommands();
        console.sendMessage(FunctionsManager.formatRGB("&aLBEngines: Plugin habilitado com sucesso."));
    }

    @Override
    public void onDisable() {
        MysqlManager.closeConnection();
        console.sendMessage(FunctionsManager.formatRGB("&caLBEngines: Plugin desabilitado com sucesso."));
    }
}
