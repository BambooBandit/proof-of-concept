package game.items;

import game.GameCharacter;
import game.World;

public class HealthPotion extends UsableItem{
    public HealthPotion(World world){
        super(world);
        this.name = "Health Potion";
    }

    @Override
    public void apply(GameCharacter character) {
        int health = character.getHealth() + 25;
        if(health > character.getMaxHealth());
        health = character.getMaxHealth();
        character.setHealth(health);
    }
}
