package br.ufrrj.game.game.world.entity;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

import br.ufrrj.game.game.world.location.Location;

public interface Entity {

    /**
     * Get the unique id of the entity
     * 
     * @return the unique id of the entity
     */

    UUID getUniqueId();

    /**
     * Get the type of the entity
     * 
     * @return the type of the entity
     */

    EntityType getEntityType();

    /**
     * Get the location of the entity
     * 
     * @return the location of the entity
     */

    Location getLocation();

    /**
     * Teleport the entity to a location
     * 
     * @param location
     */

    void teleport(Location location);

    /**
     * Teleport the entity to a location
     * 
     * @param x
     * @param y
     */

    void teleport(double x, double y);

    /**
     * Get the bounding box of the entity (used for collision detection)
     * 
     * @return the bounding box of the entity
     */

    Rectangle2D.Double getBoundingBox();

    void render(Graphics graphics);

}
