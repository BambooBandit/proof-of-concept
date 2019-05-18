package game;

import game.items.Armor;
import game.items.EquippableItem;
import game.items.Item;
import game.items.Weapon;

import java.util.ArrayList;

public class Player extends GameCharacter {
    private int experience;
    public int level;
    public ArrayList<Item> inventory;
    public Weapon weapon;
    public Armor armor;

    public Player(String name){
        super(name);
        this.experience = 0;
        this.level = 1;
        this.strength = getStrengthFromLevel();
        this.inventory = new ArrayList<>();
    }

    public Player(String name, int health, int maxHealth, int mana, int maxMana, int experience, int level){
        super(name, health, maxHealth, mana, maxMana);
        this.experience = experience;
        this.level = level;
        this.strength = getStrengthFromLevel();
        this.inventory = new ArrayList<>();
    }

    @Override
    public void addExperience(int experience){
        super.addExperience(experience);
        if(this.experience >= getExperienceNeededToLevelUp()){
            this.level ++;
            System.out.println("You leveled up to " + this.level + "!");
            this.strength = getStrengthFromLevel();
        }
    }

    public void printInventory(){
        for(int i = 0; i < 100; i ++){
            System.out.println("inventory");
        }
    }

    public int getStrengthFromLevel(){
        return 10 + (level * 5);
    }

    public int getExperienceNeededToLevelUp(){
        return this.level * 200;
    }

    public int getLevel() {
        return this.level;
    }

    public void equip(EquippableItem item) {
        if(item instanceof Armor) {
            this.armor = (Armor) item;
        }
        else{
            this.weapon = (Weapon) item;
        }
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }
}
