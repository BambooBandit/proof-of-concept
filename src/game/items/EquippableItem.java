package game.items;

import game.World;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class EquippableItem extends Item {
    public Set<ItemStat> stats;

    public EquippableItem(World world){
        super(world);
        this.stats = new HashSet<>();
    }

    @Override
    public boolean usable() {
        return false;
    }

    @Override
    public boolean equippable() {
        return true;
    }

    public abstract void addRandomStats();

    @Override
    public String toString(){
        String toString = this.name;

        if(this == world.player.armor || this == world.player.weapon) {
            toString += " -(Equipped)";

            if(this == world.player.armor)
                toString += " -" + world.player.armor.defense + " defense";
            else if(this == world.player.weapon)
                toString += " -" + world.player.weapon.damage + " damage";
        }

        Iterator<ItemStat> statsIterator = stats.iterator();
        while(statsIterator.hasNext()) {
            ItemStat itemStat = statsIterator.next();
            toString += " -" + itemStat.name;
        }
        return toString;
    }
}
