package lb.engines.utils;

import java.util.UUID;

public class LBHomes {

    private UUID uuid;
    private String name;

    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setYaw(double yaw) {
        this.yaw = yaw;
    }

    public double getYaw() {
        return yaw;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch;
    }

    public double getPitch() {
        return pitch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
