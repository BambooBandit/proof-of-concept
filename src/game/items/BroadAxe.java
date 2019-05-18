package game.items;

import game.Player;
import game.Utils;
import game.World;

public class BroadAxe extends Weapon {

    public BroadAxe(World world){
        super(world);
        this.name = "Broad Axe";
    }

    public BroadAxe(World world, Player player){
        super(world);
        this.name = "Broad Axe";
        this.damage = (int) (player.getLevel() * Utils.randomDouble(.1, 7));
        addRandomStats();
    }
}
