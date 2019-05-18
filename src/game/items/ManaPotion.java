package game.items;

import game.GameCharacter;
import game.World;

public class ManaPotion extends UsableItem{
    public ManaPotion(World world){
        super(world);
        this.name = "Mana Potion";
    }

    @Override
    public void apply(GameCharacter character) {
        int mana = character.getMana() + 25;
        if(mana > character.getMaxMana());
        mana = character.getMaxMana();
        character.setMana(mana);
    }
}
