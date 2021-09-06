package lb.engines.utils;

import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerManager {

    private UUID uuid;

    private double money = 0.0;
    private int kills = 0;
    private int deaths = 0;

    public void setMoney(double money) {
        this.money = money;
    }

    public void setKills(int k){
        this.kills = k;
    }

    public void setDeaths(int d){
        this.deaths = d;
    }

    public int getKills(){
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public double getMoney() {
        return this.money;
    }

    public UUID getUUID(){
        return this.uuid;
    }

    public void setUUID(UUID uuid){
        this.uuid = uuid;
    }
}
