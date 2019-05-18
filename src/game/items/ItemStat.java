package game.items;

public enum ItemStat {
    // weapon stats
    POISON("Poison", true), STUN("Stun", true),

    // armor stats
    REGEN("Regen", false), FIREABSORB("Fire Absorb", false), LIGHTNINGABSORB("Lightning Absorb", false), WATERABSORB("Fire Absorb", false);

    public String name;
    public boolean isOnWeapon;
    ItemStat(String name, boolean isOnWeapon) {
        this.name = name;
        this.isOnWeapon = isOnWeapon;
    }
}
