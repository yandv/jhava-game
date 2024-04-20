package br.ufrrj.game.game.world.location;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location implements Cloneable {

    private double x, y;

    /**
     * Vector sum of two locations
     * 
     * @param x
     * @param y
     */

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Vector sum of two locations
     * 
     * @param location
     */

    public void add(Location location) {
        this.x += location.getX();
        this.y += location.getY();
    }

    /**
     * Vector subtraction of two locations
     * 
     * @param location
     */

    public void subtract(Location location) {
        this.x -= location.getX();
        this.y -= location.getY();
    }

    /**
     * Vector subtraction of two locations
     * 
     * @param x
     * @param y
     */

    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    @Override
    protected Object clone() {
        return new Location(x, y);
    }

}
