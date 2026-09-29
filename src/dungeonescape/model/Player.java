package dungeonescape.model;

/**
 * The player. For now it only needs a name and the room it is standing in.
 */
public class Player {

    private String name;
    private Room currentRoom;   // the vertex the player is currently standing on

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
    }

    public String getName()          { return name; }
    public Room getCurrentRoom()     { return currentRoom; }
    public void setCurrentRoom(Room room) { this.currentRoom = room; }
}