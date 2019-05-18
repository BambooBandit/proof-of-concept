package game;

import game.items.Item;
import game.items.ItemGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    public static Scanner input;
    public static StatusEffectSorter statusEffectSorter;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        statusEffectSorter = new StatusEffectSorter();

        initialPrompt();
    }

    private static void initialPrompt(){
        System.out.println("Welcome to the Endless Dungeon!");
        System.out.println("Enter 1 to start a new game. Enter 2 to continue a saved game.");

        try {
            int playerInput = Integer.parseInt(input.next());
            if (playerInput == 1)
                startNewGame();
            else if (playerInput == 2)
                continueGame();
            else {
                throw new NumberFormatException();
            }
        }catch(NumberFormatException e){
            System.out.println("Wrong input. Try again.");
            initialPrompt();
        }
    }

    private static void startNewGame(){
        new World();
    }

    private static void continueGame(){
        System.out.println("Enter the path of your save file.");

        String fileName = input.next();

        Scanner fileInput = null;
        try {
            File file = new File(fileName);
            fileInput = new Scanner(file);
            while (fileInput.hasNextLine())
                System.out.println(fileInput.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Player player = new Player(fileInput.nextLine(), Integer.parseInt(fileInput.nextLine()), Integer.parseInt(fileInput.nextLine()), Integer.parseInt(fileInput.nextLine()), Integer.parseInt(fileInput.nextLine()), Integer.parseInt(fileInput.nextLine()), Integer.parseInt(fileInput.nextLine()));
        fileInput.nextLine();
        World world = new World(player);
        while(fileInput.hasNextLine()){
            String itemName = fileInput.nextLine();
            ItemGenerator.generateItem(world, player, itemName);
        }
        world.setUpWorld();
    }
}
