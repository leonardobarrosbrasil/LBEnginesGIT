package lb.engines.utils;

import java.util.UUID;

public class LBPlayer {

    private UUID uuid;

    private double money = 0.0;
    private int kills = 0;
    private int deaths = 0;

    private int level = 0;
    private int exp = 0;

    private int eventWins = 0;
    private int eventParticipations = 0;

    private int fightWins = 0;
    private int fightDefeats = 0;

    private UUID partner;

    public void setMoney(double money) {
        this.money = money;
    }

    public void setKills(int k) {
        this.kills = k;
    }

    public void setDeaths(int d) {
        this.deaths = d;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public double getMoney() {
        return this.money;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = UUID.fromString(uuid);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getExp() {
        return this.exp;
    }

    public void setEventWins(int eventWins) {
        this.eventWins = eventWins;
    }

    public int getEventWins() {
        return this.eventWins;
    }

    public void setEventParticipations(int eventParticipations) {
        this.eventParticipations = eventParticipations;
    }

    public int getEventParticipations() {
        return this.eventParticipations;
    }

    public int getFightWins() {
        return this.fightWins;
    }

    public void setFightWins(int fightWins) {
        this.fightWins = fightWins;
    }

    public int getFightDefeats() {
        return this.fightDefeats;
    }

    public void setFightDefeats(int fightDefeats) {
        this.fightDefeats = fightDefeats;
    }

    public UUID getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = UUID.fromString(partner);
    }
}
