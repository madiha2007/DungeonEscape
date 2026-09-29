package dungeonescape.model;

import dungeonescape.structures.InventoryLinkedList;

/**
 * The player: name, position, stats and inventory.
 */
public class Player {

    private String name;
    private Room currentRoom;
    private int health;
    private int maxHealth;
    private int baseAttack;
    private Item equippedWeapon;              // null if no weapon equipped
    private InventoryLinkedList inventory;    // our own linked list

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.baseAttack = 20;
        this.equippedWeapon = null;
        this.inventory = new InventoryLinkedList();
    }

    public String getName()                  { return name; }
    public Room getCurrentRoom()             { return currentRoom; }
    public void setCurrentRoom(Room room)    { this.currentRoom = room; }

    public int getHealth()                   { return health; }
    public int getMaxHealth()                { return maxHealth; }
    public InventoryLinkedList getInventory() { return inventory; }
    public Item getEquippedWeapon()          { return equippedWeapon; }

    /** Total attack = base attack + equipped weapon's bonus. */
    public int getAttack() {
        int bonus = (equippedWeapon != null) ? equippedWeapon.getEffectValue() : 0;
        return baseAttack + bonus;
    }

    /** Sets HP directly, kept between 0 and maxHealth (used by tests). */
    public void setHealth(int hp) {
        this.health = Math.max(0, Math.min(hp, maxHealth));
    }

    /** Adds HP but never above maxHealth. */
    public void heal(int amount) {
        setHealth(health + amount);
    }

    public void equipWeapon(Item weapon)  { this.equippedWeapon = weapon; }
    public void unequipWeapon()           { this.equippedWeapon = null; }
}