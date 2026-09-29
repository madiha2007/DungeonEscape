package dungeonescape.game;

import dungeonescape.model.Item;
import dungeonescape.model.ItemFactory;
import dungeonescape.model.Player;
import dungeonescape.model.Room;
import dungeonescape.structures.InventoryLinkedList;

import java.util.Scanner;

/**
 * Handles everything the player does with items:
 * picking up, viewing, searching, using and dropping.
 * The actual storage is done by the player's InventoryLinkedList.
 */
public class InventoryManager {

    private Player player;

    public InventoryManager(Player player) {
        this.player = player;
    }

    /** If the room still holds an item, show it and ask whether to pick it up. */
    public void offerRoomItem(Room room, Scanner scanner) {
        if (!room.hasItem()) {
            return;
        }
        Item item = ItemFactory.createItem(room.getItemName());
        if (item == null) {
            return;
        }

        System.out.println("\nYou discovered:\n");
        System.out.println(item.getName());
        System.out.println("Type: " + item.getType());
        System.out.println(item.getDescription());
        System.out.println("Value: " + item.getValue());

        // Already collected (e.g. the player owns an item with this name)
        if (player.getInventory().searchItem(item.getName()) != null) {
            System.out.println("\nYou already have this item.");
            room.markItemTaken();
            return;
        }

        while (true) {
            System.out.println("\nDo you want to pick it up?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.print("Enter choice: ");
            String input = scanner.nextLine().trim();

            if (input.equals("1")) {
                if (player.getInventory().addItem(item)) {
                    room.markItemTaken();
                    System.out.println(item.getName() + " added to inventory.");
                } else {
                    System.out.println("Could not add " + item.getName() + ".");
                }
                return;
            } else if (input.equals("2")) {
                System.out.println("You left the " + item.getName() + " here.");
                return;
            } else {
                System.out.println("Invalid choice. Enter 1 or 2.");
            }
        }
    }

    public void showInventory() {
        System.out.println();
        player.getInventory().displayInventory();
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth()
                + "   Attack: " + player.getAttack());
    }

    public void searchInventory(Scanner scanner) {
        System.out.println();
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.print("Search item: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Invalid input. Please type an item name.");
            return;
        }

        Item found = player.getInventory().searchItem(name);
        if (found != null) {
            System.out.println("\nResult:");
            System.out.println(found.getName() + " is present in your inventory.");
            System.out.println(found);
        } else {
            System.out.println("\nResult:");
            System.out.println(name + " is not present in your inventory.");
        }
    }

    /** Lets the player pick an item (by number or name), then use or drop it. */
    public void useOrRemoveItem(Scanner scanner) {
        InventoryLinkedList inventory = player.getInventory();
        System.out.println();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }

        inventory.displayInventory();
        System.out.print("Enter item number or name (0 to cancel): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Invalid input.");
            return;
        }
        if (input.equals("0")) {
            System.out.println("Cancelled.");
            return;
        }

        Item item;
        try {
            int number = Integer.parseInt(input);
            item = inventory.getItemAt(number - 1);
            if (item == null) {
                System.out.println("Invalid choice. Pick a number from 1 to " + inventory.size() + ".");
                return;
            }
        } catch (NumberFormatException e) {
            // Not a number, so treat the text as an item name
            item = inventory.searchItem(input);
            if (item == null) {
                System.out.println(input + " is not in your inventory.");
                return;
            }
        }

        System.out.println("\nSelected: " + item.getName());
        System.out.println("1. Use");
        System.out.println("2. Drop (remove)");
        System.out.println("0. Cancel");
        System.out.print("Enter choice: ");
        String action = scanner.nextLine().trim();

        switch (action) {
            case "1":
                useItem(item);
                break;
            case "2":
                dropItem(item);
                break;
            case "0":
                System.out.println("Cancelled.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** Applies an item's effect. Potions are removed from the list after use. */
    public void useItem(Item item) {
        switch (item.getType()) {
            case POTION:
                if (player.getHealth() >= player.getMaxHealth()) {
                    System.out.println("Your HP is already full. " + item.getName() + " was not used.");
                    return;
                }
                int before = player.getHealth();
                player.heal(item.getEffectValue());
                System.out.println("Player HP: " + before);
                System.out.println("Use " + item.getName());
                System.out.println("Player HP: " + player.getHealth());
                player.getInventory().removeItem(item.getName());   // deletion from the list
                System.out.println(item.getName() + " removed from inventory.");
                break;

            case WEAPON:
                if (player.getEquippedWeapon() == item) {
                    System.out.println(item.getName() + " is already equipped.");
                    return;
                }
                int oldAttack = player.getAttack();
                player.equipWeapon(item);
                System.out.println("Current Attack: " + oldAttack);
                System.out.println("Equip " + item.getName());
                System.out.println("Attack: " + player.getAttack());
                break;

            default:
                System.out.println(item.getName() + " cannot be used here.");
        }
    }

    private void dropItem(Item item) {
        if (player.getEquippedWeapon() == item) {
            player.unequipWeapon();   // a dropped weapon can't stay equipped
        }
        player.getInventory().removeItem(item.getName());
        System.out.println(item.getName() + " removed from inventory.");
    }
}