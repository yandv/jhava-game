package br.ufrrj.game.game.world.entity.impl;

import java.util.UUID;

import br.ufrrj.game.CommonConst;
import br.ufrrj.game.game.world.entity.EntityType;
import br.ufrrj.game.game.world.entity.LivingEntity;
import lombok.Getter;

@Getter
public abstract class LivingEntityImpl extends EntityImpl implements LivingEntity {

    private double health;
    private double maxHealth;

    public LivingEntityImpl(UUID uniqueId, EntityType entityType, double health, double maxHealth) {
        super(uniqueId, entityType);
    }

    public LivingEntityImpl(UUID uniqueId, EntityType entityType, double health) {
        this(uniqueId, entityType, health, health);
    }

    public LivingEntityImpl(UUID uniqueId, EntityType entityType) {
        this(uniqueId, entityType, CommonConst.DEFAULT_ENTITY_HEALTH);
    }

    @Override
    public void setHealth(double health) {
        throw new UnsupportedOperationException("Unimplemented method 'setHealth'");
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        throw new UnsupportedOperationException("Unimplemented method 'setMaxHealth'");
    }

    @Override
    public void damage(double damage) {
        throw new UnsupportedOperationException("Unimplemented method 'damage'");
    }

}
