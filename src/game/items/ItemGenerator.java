package game.items;

import game.Player;
import game.Utils;
import game.World;

public class ItemGenerator {

    public static Item generateRandomItem(World world, Player player){
        int random = Utils.randomInt(0, 10);
        Item item;
        switch (random){
            case 0:
                item = new HealthPotion(world);
                break;
            case 1:
                item = new ManaPotion(world);
                break;
            case 2:
                item = new LongSword(world, player);
                break;
            case 3:
                item = new BroadAxe(world, player);
                break;
            case 4:
                item = new LeatherRobe(world, player);
                break;
            case 5:
                item = new IronChestplate(world, player);
                break;
            default:
                item = null;
                break;
        }
        System.out.print("--- ");
        if(item != null) {
            System.out.println(item);
            player.getInventory().add(item);
        }
        return item;
    }

    public static void generateItem(World world, Player player, String name){
        String[] tokens = name.split(" -");

        String itemName = tokens[0];

        Item item = null;

        // Rebuild the correct item object based on the name
        switch (itemName){
            case "Health Potion":
                item = new HealthPotion(world);
                break;
            case "Mana Potion":
                item = new ManaPotion(world);
                break;
            case "Long Sword":
                item = new LongSword(world);
                break;
            case "Broad Axe":
                item = new BroadAxe(world);
                break;
            case "Leather Robe":
                item = new LeatherRobe(world);
                break;
            case "Iron Chestplate":
                item = new IronChestplate(world);
                break;
        }

        // Restore random stats of the item restored from save.
        for(int i = 1; i < tokens.length; i ++){
            EquippableItem equippableItem = (EquippableItem) item;
            String statName = tokens[i];
            ItemStat[] itemStatPossibilities = ItemStat.values();
            for(int k = 0; k < itemStatPossibilities.length; k ++){
                if(statName.equals(itemStatPossibilities[k].name)){
                    equippableItem.stats.add(itemStatPossibilities[k]);
                }
            }
        }
        int mainStatTokenIndex = 1;

        // Equip the item restored from save.
        if(tokens.length > 1 && tokens[1].equals("(Equipped)")){
            mainStatTokenIndex = 2;
            if(item instanceof Weapon) {
                player.weapon = (Weapon) item;
            }
            else {
                player.armor = (Armor) item;
            }
        }

        // Restore main stat of item restored from save.
        if(item instanceof Weapon){
            String[] mainStatTokens = tokens[mainStatTokenIndex].split(" ");
            ((Weapon) item).damage = Integer.parseInt(mainStatTokens[0]);
        }
        else if(item instanceof Armor){
            String[] mainStatTokens = tokens[mainStatTokenIndex].split(" ");
            ((Armor) item).defense = Integer.parseInt(mainStatTokens[0]);
        }

        player.getInventory().add(item);
    }
}
