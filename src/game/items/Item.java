package game.items;

import game.Player;
import game.World;

public abstract class Item {
    public String name;
    public World world;

    public Item(World world){
        this.world = world;
    }

    public abstract boolean usable();

    public abstract boolean equippable();

    @Override
    public String toString(){
        return name;
    }
}
