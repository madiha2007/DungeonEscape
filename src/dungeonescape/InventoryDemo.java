package dungeonescape;

import dungeonescape.game.InventoryManager;
import dungeonescape.model.Dungeon;
import dungeonescape.model.Item;
import dungeonescape.model.ItemFactory;
import dungeonescape.model.ItemType;
import dungeonescape.model.Player;
import dungeonescape.structures.InventoryLinkedList;

/** Runs the Step 6 linked-list tests without needing keyboard input. */
public class InventoryDemo {
    public static void main(String[] args) {
        Dungeon dungeon = new Dungeon();
        Player player = new Player("Tester", dungeon.getStartRoom());
        InventoryLinkedList inv = player.getInventory();
        InventoryManager manager = new InventoryManager(player);

        Item sword  = ItemFactory.createItem("Iron Sword");
        Item potion = ItemFactory.createItem("Healing Potion");
        Item shield = new Item(105, "Wooden Shield", ItemType.ARMOR, "Defense +5", 30, 5);

        System.out.println("--- Test 1: empty inventory ---");
        inv.displayInventory();

        System.out.println("\n--- Test 2: add one item ---");
        System.out.println(inv.addItem(potion) ? "Healing Potion added." : "Failed");
        System.out.println(inv.toPathString());
        inv.removeItem("Healing Potion");   // reset for the next test

        System.out.println("\n--- Test 3: add multiple items ---");
        inv.addItem(sword);
        inv.addItem(potion);
        inv.addItem(shield);
        System.out.println(inv.toPathString());
        System.out.println("Duplicate add allowed? " + inv.addItem(sword));

        System.out.println("\n--- Test 4: search existing ---");
        System.out.println(inv.searchItem("Iron Sword") != null ? "Iron Sword found." : "Not found.");

        System.out.println("\n--- Test 5: search missing ---");
        System.out.println(inv.searchItem("Magic Key") != null ? "Magic Key found." : "Magic Key not found.");

        System.out.println("\n--- Test 6: remove middle item ---");
        inv.removeItem("Healing Potion");
        System.out.println(inv.toPathString());

        System.out.println("\n--- Test 7: remove missing item ---");
        System.out.println(inv.removeItem("Healing Potion") == null
                ? "Healing Potion is not in your inventory." : "Removed?!");

        System.out.println("\n--- Test 8: use potion ---");
        inv.addItem(potion);
        player.setHealth(60);
        manager.useItem(potion);
        System.out.println(inv.toPathString());

        System.out.println("\n--- Test 8b: equip weapon ---");
        manager.useItem(sword);

        System.out.println("\n--- Test 8c: key cannot be used ---");
        Item key = ItemFactory.createItem("Golden Key");
        inv.addItem(key);
        manager.useItem(key);

        System.out.println("\n--- Update (traversal + modify a node) ---");
        Item p2 = new Item(102, "Healing Potion", ItemType.POTION, "Restores 20 HP", 50, 20);
        inv.addItem(p2);
        System.out.println("Before: " + inv.searchItem("Healing Potion"));
        inv.updateItem("Healing Potion", "Restores 30 HP", 30);
        System.out.println("After : " + inv.searchItem("Healing Potion"));
        System.out.println("Update missing item: " + inv.updateItem("Magic Key", "x", 1));

        System.out.println("\n--- Test 9: invalid input (null / blank) ---");
        System.out.println("add null   -> " + inv.addItem(null));
        System.out.println("search null-> " + inv.searchItem(null));
        System.out.println("remove ''  -> " + inv.removeItem(""));

        System.out.println("\n--- Final traversal ---");
        inv.displayInventory();
    }
}