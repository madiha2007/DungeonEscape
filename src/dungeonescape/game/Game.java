package dungeonescape.game;

import dungeonescape.model.Dungeon;
import dungeonescape.model.Player;

import java.util.Scanner;

/**
 * Owns the game loop: shows the menu and calls the right manager.
 */
public class Game {

    public void start() {
        // Same banner as Step 1
        System.out.println("==========================");
        System.out.println("    DUNGEON ESCAPE");
        System.out.println(" DSA-Based Adventure Game");
        System.out.println("==========================");

        Scanner scanner = new Scanner(System.in);
        Dungeon dungeon = new Dungeon();

        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Adventurer";
        }

        // The player starts at the dungeon's entrance room
        Player player = new Player(name, dungeon.getStartRoom());
        MovementManager movement = new MovementManager(dungeon, player);

        System.out.println("\nWelcome, " + player.getName() + "!\n");
        movement.showCurrentRoom();
        movement.showConnections();

        boolean running = true;
        while (running) {
            System.out.println("\n================================");
            System.out.println("DUNGEON ESCAPE");
            System.out.println("Current Room: " + player.getCurrentRoom().getName());
            System.out.println();
            System.out.println("1. Move");
            System.out.println("2. View Current Room");
            System.out.println("3. Exit Game");
            System.out.print("\nEnter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    movement.handleMove(scanner);
                    break;
                case "2":
                    System.out.println();
                    movement.showCurrentRoom();
                    movement.showConnections();
                    break;
                case "3":
                    System.out.println("Thanks for playing, " + player.getName() + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1, 2 or 3.");
            }
        }
        scanner.close();
    }
}