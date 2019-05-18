package game.items;

import game.Utils;
import game.World;

public abstract class Armor extends EquippableItem {
    public int defense;

    public Armor(World world) {
        super(world);
    }

    @Override
    public void addRandomStats(){
        ItemStat[] statPossibilities = ItemStat.values();
        for(int i = 0; i < statPossibilities.length; i ++){
            if(!statPossibilities[i].isOnWeapon && Utils.randomInt(0, 1) == 1)
                this.stats.add(statPossibilities[i]);
        }
    }
}
