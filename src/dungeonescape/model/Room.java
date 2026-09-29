package dungeonescape.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one room (one node/vertex) in the dungeon graph.
 * The list of connected room IDs is this room's adjacency list.
 */
public class Room {

    private int id;                        // Unique number, used to find the room
    private String name;                   // Short name shown to the player
    private RoomType type;                 // What kind of room this is
    private String description;            // Text describing the room
    private List<Integer> connections;     // IDs of rooms directly connected to this one
    private String enemyName;              // null if there is no enemy
    private String itemName;               // null if there is no item

    public Room(int id, String name, RoomType type, String description,
                String enemyName, String itemName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.enemyName = enemyName;
        this.itemName = itemName;
        this.connections = new ArrayList<>();
    }

    // Adds a connection to another room (ignores duplicates)
    public void addConnection(int roomId) {
        if (!connections.contains(roomId)) {
            connections.add(roomId);
        }
    }

    public boolean hasEnemy() { return enemyName != null; }
    public boolean hasItem()  { return itemName != null; }

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