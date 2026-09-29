package dungeonescape.model;

/**
 * Rooms only store an item NAME (Step 2). This class turns that name
 * into a real Item object when the player finds it.
 */
public class ItemFactory {

    /** Returns a new Item for the given name, or null if the name is unknown. */
    public static Item createItem(String name) {
        if (name == null) {
            return null;
        }
        switch (name) {
            case "Iron Sword":
                return new Item(101, "Iron Sword", ItemType.WEAPON, "Attack bonus +15", 80, 15);
            case "Healing Potion":
                return new Item(102, "Healing Potion", ItemType.POTION, "Restores 30 HP", 50, 30);
            case "Golden Key":
                return new Item(103, "Golden Key", ItemType.KEY, "Opens a locked door (used in a later step)", 200, 0);
            case "Blessed Water":
                return new Item(104, "Blessed Water", ItemType.POTION, "Restores 50 HP", 120, 50);
            default:
                return null;
        }
    }
}