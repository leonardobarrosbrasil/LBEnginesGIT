package lb.engines.utils;

import org.bukkit.Location;

import java.util.UUID;

public class LBHomes {

    private UUID uuid;
    private String name;

    private Location loc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }
}
