package dungeonescape.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one room (one node/vertex) in the dungeon graph.
 * The list of connected room IDs is this room's adjacency list.
 */
public class Room {

    private int id;
    private String name;
    private RoomType type;
    private String description;
    private List<Integer> connections;
    private String enemyName;              // null if there is no enemy
    private String itemName;               // null if there is no item
    private boolean itemTaken;             // NEW: true once the item was picked up

    public Room(int id, String name, RoomType type, String description,
                String enemyName, String itemName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.enemyName = enemyName;
        this.itemName = itemName;
        this.itemTaken = false;
        this.connections = new ArrayList<>();
    }

    public void addConnection(int roomId) {
        if (!connections.contains(roomId)) {
            connections.add(roomId);
        }
    }

    public boolean hasEnemy() { return enemyName != null; }

    // CHANGED: a room only "has an item" while the item is still lying there
    public boolean hasItem()  { return itemName != null && !itemTaken; }

    // NEW: called after the player picks the item up
    public void markItemTaken() { itemTaken = true; }
    public boolean isItemTaken() { return itemTaken; }

    public int getId()                     { return id; }
    public String getName()                { return name; }
    public RoomType getType()              { return type; }
    public String getDescription()         { return description; }
    public List<Integer> getConnections()  { return connections; }
    public String getEnemyName()           { return enemyName; }
    public String getItemName()            { return itemName; }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (" + type + ")";
    }
}