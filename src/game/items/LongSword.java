package game.items;

import game.Player;
import game.Utils;
import game.World;

public class LongSword extends Weapon {

    public LongSword(World world){
        super(world);
        this.name = "Long Sword";
    }

    public LongSword(World world, Player player){
        super(world);
        this.name = "Long Sword";
        this.damage = (int) (player.getLevel() * Utils.randomDouble(.25, 5));
        addRandomStats();
    }
}
