package game;

public class EnemyGenerator {
    public static GameCharacter generateRandomEnemy(Player player){
        int random = Utils.randomInt(1, 100);

        if(random < 50)
            return generateGoblin(player);
        else if(random < 70)
            return generateWizard(player);
        else if(random < 90)
            return generateElementalEngima(player);
        else
            return generateDragon(player);
    }

    private static GameCharacter generateGoblin(Player player){
        GameCharacter character = new GameCharacter("Goblin", 20 * player.level, 20 * player.level, 0, 0);
        character.setStrength(5);
        character.addExperience(20);
        character.abilities.add(Element.NONE);
        character.setItemDropAmount(1);
        return character;
    }
    private static GameCharacter generateWizard(Player player){
        GameCharacter character = new GameCharacter("Wizard", 200 * player.level, 200 * player.level, 0, 0);
        character.setStrength(15);
        character.addExperience(30);
        character.addAbility(Element.FIRE);
        character.addAbility(Element.LIGHTNING);
        character.addAbility(Element.WATER);
        character.setItemDropAmount(2);
        return character;
    }
    private static GameCharacter generateElementalEngima(Player player){
        GameCharacter character = new GameCharacter("Elemental Enigma", 300 * player.level, 300 * player.level, 0, 0);
        character.setStrength(15);
        character.addExperience(30);
        int random = Utils.randomInt(0, 2);
        character.setItemDropAmount(2);
        switch(random){
            case 0:
                character.addAbility(Element.FIRE);
                character.addWeakness(Element.WATER);
                break;
            case 1:
                character.addAbility(Element.LIGHTNING);
                character.addWeakness(Element.FIRE);
                break;
            case 2:
                character.addAbility(Element.WATER);
                character.addWeakness(Element.LIGHTNING);
        }
        return character;
    }
    private static GameCharacter generateDragon(Player player){
        GameCharacter character = new GameCharacter("Dragon", 500 * player.level, 500 * player.level, 0, 0);
        character.setStrength(20);
        character.addExperience(50);
        character.addAbility(Element.FIRE);
        character.addWeakness(Element.WATER);
        character.setItemDropAmount(3);
        return character;
    }
}
