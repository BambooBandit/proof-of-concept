package game.items;

import game.Player;
import game.Utils;
import game.World;

public class LeatherRobe extends Armor {

    public LeatherRobe(World world){
        super(world);
        this.name = "Leather Robe";

    }
    public LeatherRobe(World world, Player player){
        super(world);
        this.name = "Leather Robe";
        this.defense = (int) (player.getLevel() * Utils.randomDouble(2, 4));
        addRandomStats();
    }
}
