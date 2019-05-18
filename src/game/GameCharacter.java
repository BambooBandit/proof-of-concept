package game;

import game.items.Item;
import game.items.ItemStat;
import game.items.UsableItem;

import java.util.ArrayList;
import java.util.Iterator;

public class GameCharacter {
    protected String name;
    protected int health, maxHealth;
    protected int mana, maxMana;
    protected GameCharacter enemy;
    protected ArrayList<StatusEffect> statusEffects;
    public int strength;
    public int experience;
    public int itemDropAmount;
    public ArrayList<Element> weaknesses;
    protected ArrayList<Element> abilities;

    public GameCharacter(String name, int health, int maxHealth, int mana, int maxMana){
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = health;
        this.maxMana = maxMana;
        this.mana = mana;

        this.statusEffects = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public GameCharacter(String name){
        this.name = name;
        this.maxHealth = 100;
        this.health = this.maxHealth;
        this.maxMana = 100;
        this.mana = this.maxMana;

        this.statusEffects = new ArrayList<>();
        this.weaknesses = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public void addStatusEffect(StatusEffect statusEffect) {
        statusEffects.add(statusEffect);
        statusEffects.sort(Game.statusEffectSorter);
    }

    public void addWeakness(Element weakness){
        this.weaknesses.add(weakness);
    }

    public void addAbility(Element fire) {
        abilities.add(fire);
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setItemDropAmount(int itemDropAmount){
        this.itemDropAmount = itemDropAmount;
    }

    public int getItemDropAmount() {
        return this.itemDropAmount;
    }

    public void addExperience(int experience){
        this.experience += experience;
    }

    public int getExperience() {
        return this.experience;
    }

    public void applyStatusEffects() {
        int totalPoisonDamage = 0;
        int totalRegenHeal = 0;
        for(int i = 0; i < statusEffects.size(); i ++){
            StatusEffect statusEffect = statusEffects.get(i);
            switch(statusEffect){
                case POISON:
                    int damage = Utils.percentOfInt(Utils.randomInt(5, 20), maxHealth);
                    totalPoisonDamage += damage;
                    break;
                case REGEN:
                    int heal = Utils.percentOfInt(Utils.randomInt(5, 20), maxHealth);
                    totalRegenHeal += heal;
                    break;
                case STUN:
                    return;
            }
        }

        String name;
        if(this instanceof Player)
            name = "You";
        else
            name = "The " + this.name;
        if(totalPoisonDamage > 0)
            System.out.println(name + " took " + totalPoisonDamage + " poison damage.");
        if(totalRegenHeal > 0)
            System.out.println(name + " regenerated " + totalRegenHeal + " health.");
    }

    public void aiChoice(){
        int randomChoice = Utils.randomInt(0, abilities.size());
        Element randomAbility = abilities.get(randomChoice);

        switch(randomAbility){
            case NONE:
                melee();
                break;
            case FIRE:
                fire();
                break;
            case LIGHTNING:
                lightning();
                break;
            case WATER:
                water();
                break;
        }
    }

    public void melee(){
        int damage = (int) (this.strength * Utils.randomDouble(1, 1.5));
        GameCharacter enemy = this.enemy;
        if(this.enemy instanceof Player) {

            // Make player armor subtract damage
            Player player = (Player) this.enemy;
            if(player.armor != null)
                damage -= player.armor.defense;

            System.out.println(this.name + " strikes you for " + damage + " damage.");
        }
        else {
            Player player = (Player) this;
            // Check for weapon stats to apply them
            if(player.weapon != null) {
                damage += player.weapon.damage;

                Iterator<ItemStat> statsIterator = player.weapon.stats.iterator();
                while(statsIterator.hasNext()){
                    ItemStat itemStat = statsIterator.next();
                    if(itemStat == ItemStat.POISON && Utils.randomInt(0, 1) == 1) { // half chance to poison enemy
                        System.out.println("You have poisoned the enemy.");
                        enemy.addStatusEffect(StatusEffect.POISON);
                    }
                    else if(itemStat == ItemStat.STUN && Utils.randomInt(0, 1) == 1) { // half chance to stun enemy
                        System.out.println("You have stunned the enemy.");
                        enemy.addStatusEffect(StatusEffect.STUN);
                    }
                }
            }

            System.out.println("You strike " + this.name + " for " + damage + " damage");
        }
        enemy.health -= damage;

    }

    public void fire(){
        int damage = (int) (this.strength * Utils.randomDouble(.75, 1.25));
        GameCharacter enemy = this.enemy;
        if(enemy.weaknesses.contains(Element.FIRE))
            damage *= 1.75;

        if(this.enemy instanceof Player) {
            // Make player armor absorb damage if it has the right stat
            Player player = (Player) this.enemy;
            if(player.armor != null && player.armor.stats.contains(ItemStat.FIREABSORB)) {
                damage *= -.5;
                System.out.println(this.name + " uses Fire magic against you but you absorbed it and gained " + damage + " health.");
            }
            else
                System.out.println(this.name + " uses Fire magic against you for " + damage + " damage.");
        }
        else
            System.out.println("You use Fire magic against " + this.name + " for " + damage + " damage");

        enemy.health -= damage;

        // Make sure absorb doesn't overfill health
        if(enemy.health > enemy.maxHealth)
            enemy.health = enemy.maxHealth;
    }

    public void lightning(){
        int damage = (int) (this.strength * Utils.randomDouble(.75, 1.25));
        GameCharacter enemy = this.enemy;
        if(enemy.weaknesses.contains(Element.LIGHTNING))
            damage *= 1.75;

        if(this.enemy instanceof Player) {
            // Make player armor absorb damage if it has the right stat
            Player player = (Player) this.enemy;
            if(player.armor != null && player.armor.stats.contains(ItemStat.LIGHTNINGABSORB)) {
                damage *= -.5;
                System.out.println(this.name + " uses Lightning magic against you but you absorbed it and gained " + damage + " health.");
            }
            else
                System.out.println(this.name + " uses Lightning magic against you for " + damage + " damage.");
        }
        else
            System.out.println("You use Lightning magic against " + this.name + " for " + damage + " damage");

        enemy.health -= damage;

        // Make sure absorb doesn't overfill health
        if(enemy.health > enemy.maxHealth)
            enemy.health = enemy.maxHealth;
    }

    public void water(){
        int damage = (int) (this.strength * Utils.randomDouble(.75, 1.25));
        GameCharacter enemy = this.enemy;
        if(enemy.weaknesses.contains(Element.WATER))
            damage *= 1.75;

        if(this.enemy instanceof Player) {
            // Make player armor absorb damage if it has the right stat
            Player player = (Player) this.enemy;
            if(player.armor != null && player.armor.stats.contains(ItemStat.WATERABSORB)) {
                damage *= -.5;
                System.out.println(this.name + " uses Water magic against you but you absorbed it and gained " + damage + " health.");
            }
            else
                System.out.println(this.name + " uses Water magic against you for " + damage + " damage.");
        }
        else
            System.out.println("You use Water magic against " + this.name + " for " + damage + " damage");

        enemy.health -= damage;

        // Make sure absorb doesn't overfill health
        if(enemy.health > enemy.maxHealth)
            enemy.health = enemy.maxHealth;
    }

    public GameCharacter getEnemy() {
        return enemy;
    }

    public void setEnemy(GameCharacter enemy) {
        this.enemy = enemy;
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return this.mana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void use(UsableItem item) {
        System.out.println("You drank the " + item + ".");
        item.apply(this);
        System.out.println("Health: " + this.health + " / " + this.maxHealth + ". Mana: " + this.mana + " / " + this.maxMana);
    }
}
