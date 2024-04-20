package br.ufrrj.game.game.world.entity.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.UUID;

import br.ufrrj.game.game.world.entity.Entity;
import br.ufrrj.game.game.world.entity.EntityType;
import br.ufrrj.game.game.world.location.Location;
import lombok.Getter;

@Getter
public abstract class EntityImpl implements Entity {

    private UUID uniqueId;
    private Location location;

    private EntityType entityType;

    private Rectangle2D.Double boundingBox;

    public EntityImpl(UUID uniqueId, EntityType entityType) {
        this.uniqueId = uniqueId;
        this.location = new Location(50, 50);
        this.entityType = entityType;
        this.boundingBox = new Rectangle2D.Double(50, 50, entityType.getSprite().getAtlas().getWidth(),
                entityType.getSprite().getAtlas().getHeight());
    }

    public void renderHitboxes(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.drawRect((int) boundingBox.x, (int) boundingBox.y, (int) boundingBox.width, (int) boundingBox.height);
    }

    @Override
    public void teleport(Location location) {
        throw new UnsupportedOperationException("Unimplemented method 'teleport'");
    }

    @Override
    public void teleport(double x, double y) {
        teleport(new Location(x, y));
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(entityType.getSprite().getImage(), (int) (location.getX() - getBoundingBox().x),
                (int) (location.getY() - getBoundingBox().y), entityType.getSprite().getWidth(),
                entityType.getSprite().getHeight(), null);
    }

}
