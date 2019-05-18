package game.items;

import game.Player;
import game.Utils;
import game.World;

public class IronChestplate extends Armor {

    public IronChestplate(World world){
        super(world);
        this.name = "Iron Chestplate";
    }

    public IronChestplate(World world, Player player){
        super(world);
        this.name = "Iron Chestplate";
        this.defense = (int) (player.getLevel() * Utils.randomDouble(1, 5));
        addRandomStats();
    }
}
