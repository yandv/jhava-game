package br.ufrrj.game.game.world.entity;

public interface LivingEntity extends Entity {

    void setHealth(double health);

    double getHealth();

    double getMaxHealth();

    /**
     * Set the max health of the entity
     * 
     * @param maxHealth
     */

    void setMaxHealth(double maxHealth);

    /**
     * Hit the entity with a damage
     * 
     * @param damage
     */

    void damage(double damage);

}
