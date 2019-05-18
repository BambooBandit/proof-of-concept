package game.items;

import game.GameCharacter;
import game.World;

public abstract class UsableItem extends Item{
    public UsableItem(World world) {
        super(world);
    }

    @Override
    public boolean usable() {
        return true;
    }

    @Override
    public boolean equippable() {
        return false;
    }

    public abstract void apply(GameCharacter character);
}
