package lb.engines.utils;

public class pluginManager {

    private static accountManager accountmanager;
    private static playerManager playermanager;
    private static mysqlManager mysqlmanager;
    private static functionsManager functionsmanager;

    public static accountManager getAccount() {
        return accountmanager;
    }

    public static playerManager getPlayer() {
        return playermanager;
    }

    public static mysqlManager getMySQL() {
        return mysqlmanager;
    }

    public static functionsManager getFunctions() {
        return functionsmanager;
    }
}
