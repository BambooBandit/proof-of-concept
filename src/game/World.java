package game;

import game.items.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class World {
    private WorldState worldState;
    private WorldSubState worldSubState;
    public Player player;

    public World(){
        System.out.println("Enter the name of your new characters name.");
        String playerName = Game.input.next();
        this.player = new Player(playerName);
        this.setUpWorld();
    }

    public World(Player player){
        this.player = player;
    }

    public void setUpWorld(){
        this.worldState = WorldState.AT_STAIRS;
        this.worldSubState = WorldSubState.NONE;

        System.out.println("You enter a dungeon and walk into an endlessly spiraling staircase." +
                " Due to the poop trail you have left in your wake, you cannot go back. ");

        stepGame();
    }

    /** Play out the players inputs and have the game react to it after the players turn is done. */
    private void stepGame(){
        printStatePrompt();
        boolean doneWithPlayerInput = getPlayerInput();
        if(!doneWithPlayerInput)
            stepGame();

        if(this.worldState == WorldState.ENEMY_ATTACK){
            player.applyStatusEffects();

            if(player.health <= 0){
                System.out.println("You have died.");
                return;
            }

            GameCharacter enemy = player.getEnemy();
            if(enemy.health <= 0){
                System.out.println("You have killed the " + enemy.name);
                player.addExperience(enemy.getExperience());
                System.out.println("The enemy dropped...");
                for(int i = 0; i < enemy.getItemDropAmount(); i ++)
                    ItemGenerator.generateRandomItem(this, this.player);
            }
            else {
                if(enemy.statusEffects.contains(StatusEffect.STUN)) {
                    System.out.println("Enemy could not attack due to being stunned!");
                    enemy.statusEffects.remove(StatusEffect.STUN); // Remove so the enemy is not stunned forever
                }
                else{
                    enemy.aiChoice();
                }
                enemy.applyStatusEffects();
                if(enemy.health <= 0){
                    System.out.println("You have killed the " + enemy.name);
                    player.addExperience(enemy.getExperience());
                    System.out.println("The enemy dropped...");
                    for(int i = 0; i < enemy.getItemDropAmount(); i ++)
                        ItemGenerator.generateRandomItem(this, this.player);
                }
            }
        }

        if(player.health <= 0){
            System.out.println("You have died.");
        }
        else
            stepGame();
    }

    /** Let the player know what their options are based on the state or sub-state of the game. */
    private void printStatePrompt(){
        switch(this.worldSubState){
            case AT_INVENTORY:
                this.player.printInventory();
                System.out.println(this.player.name);
                System.out.println("Level: " + this.player.getLevel());
                System.out.println("Health: " + this.player.getHealth() + " / " + this.player.getMaxHealth());
                System.out.println("Enter: 1 to use item. 2 to equip item. 3 to destroy item. 4 to leave inventory.");
                return;
            case USE_INVENTORY_ITEM:
                System.out.println("Enter: index of the item you want to use.");
                return;
            case EQUIP_INVENTORY_ITEM:
                System.out.println("Enter: index of the item you want to equip.");
                return;
            case DESTROY_INVENTORY_ITEM:
                System.out.println("Enter: index of the item you want to destroy.");
                return;
            case MAGIC_SELECTION:
                System.out.println("Enter: 1 for fire. 2 for lightning. 3 for water.");
                return;
            case SAVING:
                System.out.println("Enter: 1 to save the game. 2 to Quit the game.");
                return;
        }
        switch(this.worldState){
            case AT_STAIRS:
                System.out.println("Enter: 1 to go down the stairs. 2 to open inventory. 3 to save or quit.");
                break;
            case AT_CHEST:
                System.out.println("Enter: 1 to open the chest. 2 to ignore.");
                break;
            case ENEMY_ENCOUNTER:
                System.out.println("Enter: 1 to approach the enemy. 2 to run away.");
                break;
            case ENEMY_ATTACK:
                System.out.println("Enter: 1 to melee. 2 to use magic.");
                break;
        }
    }

    /** Return true if the players turn is down. False if it isn't (will prompt the player more). */
    private boolean getPlayerInput(){
        try {
            int playerInput = Integer.parseInt(Game.input.next());
            ArrayList<Item> inventory = player.getInventory();
            Item item;
            switch (this.worldSubState) {
                case AT_INVENTORY:
                    switch (playerInput) {
                        case 1:
                            worldSubState = WorldSubState.USE_INVENTORY_ITEM;
                            return false;
                        case 2:
                            worldSubState = WorldSubState.EQUIP_INVENTORY_ITEM;
                            return false;
                        case 3:
                            worldSubState = WorldSubState.DESTROY_INVENTORY_ITEM;
                            return false;
                        case 4:
                            worldSubState = WorldSubState.NONE;
                            return false;
                    }
                    break;
                case USE_INVENTORY_ITEM:
                    if (playerInput < 0 || playerInput > inventory.size() - 1) {
                        System.out.println("Index out of bounds. Try again.");
                        return false;
                    }
                    item = inventory.get(playerInput);
                    if (item.usable()) {
                        player.use((UsableItem) item);
                        inventory.remove(item);
                        return true;
                    } else {
                        System.out.println("That item is not usable. Try again.");
                        return false;
                    }
                case EQUIP_INVENTORY_ITEM:
                    if (playerInput < 0 || playerInput > inventory.size() - 1) {
                        System.out.println("Index out of bounds. Try again.");
                        return false;
                    }
                    item = inventory.get(playerInput);
                    if (item.equippable()) {
                        player.equip((EquippableItem) item);
                        return true;
                    } else {
                        System.out.println("That item is not equippable. Try again.");
                        return false;
                    }
                case DESTROY_INVENTORY_ITEM:
                    if (playerInput < 0 || playerInput > inventory.size() - 1) {
                        System.out.println("Index out of bounds. Try again.");
                        return false;
                    }
                    item = inventory.get(playerInput);
                    inventory.remove(item);
                    return true;
                case MAGIC_SELECTION:
                    switch (playerInput) {
                        case 1:
                            this.player.fire();
                            return true;
                        case 2:
                            this.player.lightning();
                            return true;
                        case 3:
                            this.player.water();
                            return true;
                    }
                case SAVING:
                    switch (playerInput) {
                        case 1:
                            save();
                            return false;
                        case 2:
                            quit();
                            return true;
                    }
            }
            switch (this.worldState) {
                case AT_STAIRS:
                    switch (playerInput) {
                        case 1:
                            goDownStairs();
                            return true;
                        case 2:
                            worldSubState = WorldSubState.AT_INVENTORY;
                            return false;
                        case 3:
                            save();
                            return false;
                    }
                    break;
                case AT_CHEST:
                    switch (playerInput) {
                        case 1:
                            openChest();
                            return true;
                        case 2:
                            worldSubState = WorldSubState.NONE;
                            return true;
                    }
                    break;
                case ENEMY_ENCOUNTER:
                    switch (playerInput) {
                        case 1:
                            worldState = WorldState.ENEMY_ATTACK;
                            return false;
                        case 2:
                            attemptRunaway();
                            return true;
                    }
                    break;
                case ENEMY_ATTACK:
                    switch (playerInput) {
                        case 1:
                            this.player.melee();
                            return true;
                        case 2:
                            worldSubState = WorldSubState.MAGIC_SELECTION;
                            return false;
                    }
                    break;
            }
            System.out.println("Input not recognized. Try again.");
            return false;
        }catch (NumberFormatException e){
            System.out.println("Input not recognized. Try again.");
            return false;
        }
    }

    /** When you go down stairs, there's chance to run into an enemy, a chest, or nothing.*/
    private void goDownStairs() {
        System.out.println("You walk down the stairs...");
        int random = Utils.randomInt(0, 2);

        switch(random){
            case 0:
                GameCharacter enemy = EnemyGenerator.generateRandomEnemy(this.player);
                enemy.setEnemy(this.player);
                this.player.setEnemy(enemy);
                System.out.println("You encountered a " + enemy +"!");
                worldState = WorldState.ENEMY_ENCOUNTER;
                break;
            case 1:
                System.out.println("You found a treasure chest...");
                worldState = WorldState.AT_CHEST;
                break;
        }
    }

    private void openChest() {
        System.out.println("You open the chest to find... ");
        ItemGenerator.generateRandomItem(this, this.player);
        ItemGenerator.generateRandomItem(this, this.player);
        ItemGenerator.generateRandomItem(this, this.player);
    }

    /** Half chance to run away. */
    private void attemptRunaway() {
        int random = Utils.randomInt(0, 1);

        if(random == 0) {
            System.out.println("You have failed to run away! You are forced to fight.");
            worldState = WorldState.ENEMY_ATTACK;
        }
        else{
            System.out.println("You have successfully run away.");
            worldState = WorldState.AT_STAIRS;
        }

    }

    private void save() {
        System.out.println("Enter a name you'd like to name the save file.");
        String name = Game.input.next();

        try {
            PrintWriter writer = new PrintWriter(name + ".txt", "UTF-8");
            writer.println(this.player.name);
            writer.println(this.player.getHealth());
            writer.println(this.player.getMaxHealth());
            writer.println(this.player.getMana());
            writer.println(this.player.getMaxMana());
            writer.println(this.player.getExperience());
            writer.println(this.player.getLevel());
            writer.println("-ITEMS-");
            ArrayList<Item> inventory = this.player.getInventory();
            for(int i = 0; i < inventory.size(); i ++){
                Item item = inventory.get(i);
                writer.println(item);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void quit(){
        System.exit(0);
    }
}
