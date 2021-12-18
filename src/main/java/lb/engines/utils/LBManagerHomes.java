package lb.engines.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LBManagerHomes {

    private static final Map<UUID, Map<String, LBHomes>> homesCache = new HashMap<>();

    public LBHomes getCacheHome(UUID uuid) {
        return (LBHomes) homesCache.get(uuid);
    }

    public boolean hasHomes(UUID uuid) {
        Map<String, LBHomes> map = homesCache.get(uuid);
        return map != null;
    }

    public LBHomes getHome(UUID uuid, String name) {
        if(!hasHome(uuid, name)) return null;
        return homesCache.get(uuid).get(name);
    }

    public void addHome(UUID uuid, String name) {
        homesCache.put(uuid).put(name);
    }
}
