package lb.engines.utils;

import org.bukkit.entity.Player;

import java.util.UUID;

public class LBPlayer {

    private UUID uuid;

    private double money = 0.0;
    private int kills = 0;
    private int deaths = 0;

    //private int level = 0;
    //private int exp = 0;

    //private int eventWins = 0;
    //private int eventParticipations = 0;

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

    public void setUUID(String uuid){
        this.uuid = UUID.fromString(uuid);
    }
}
