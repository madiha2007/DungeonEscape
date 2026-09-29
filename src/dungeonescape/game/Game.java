package dungeonescape.game;

import dungeonescape.model.Dungeon;
import dungeonescape.model.Player;

import java.util.Scanner;

/**
 * Owns the game loop: shows the menu and calls the right manager.
 */
public class Game {

    public void start() {
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

        Player player = new Player(name, dungeon.getStartRoom());
        MovementManager movement = new MovementManager(dungeon, player);
        InventoryManager inventory = new InventoryManager(player);

        System.out.println("\nWelcome, " + player.getName() + "!\n");
        movement.showCurrentRoom();
        movement.showConnections();

        boolean running = true;
        while (running) {
            System.out.println("\n====================================");
            System.out.println("          DUNGEON ESCAPE");
            System.out.println("====================================");
            System.out.println("\nCurrent Room: " + player.getCurrentRoom().getName());
            System.out.println();
            System.out.println("1. Move");
            System.out.println("2. Undo Last Move");
            System.out.println("3. View Current Room");
            System.out.println("4. View Movement History");
            System.out.println("5. View Inventory");
            System.out.println("6. Search Inventory");
            System.out.println("7. Use/Remove Item");
            System.out.println("8. Exit Game");
            System.out.print("\nEnter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    if (movement.handleMove(scanner)) {
                        inventory.offerRoomItem(player.getCurrentRoom(), scanner);
                    }
                    break;
                case "2":
                    System.out.println();
                    if (movement.handleUndo()) {
                        inventory.offerRoomItem(player.getCurrentRoom(), scanner);
                    }
                    break;
                case "3":
                    System.out.println();
                    movement.showCurrentRoom();
                    movement.showConnections();
                    inventory.offerRoomItem(player.getCurrentRoom(), scanner);
                    break;
                case "4":
                    movement.showHistory();
                    break;
                case "5":
                    inventory.showInventory();
                    break;
                case "6":
                    inventory.searchInventory(scanner);
                    break;
                case "7":
                    inventory.useOrRemoveItem(scanner);
                    break;
                case "8":
                    System.out.println("Thanks for playing, " + player.getName() + "!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 8.");
            }
        }
        scanner.close();
    }
}