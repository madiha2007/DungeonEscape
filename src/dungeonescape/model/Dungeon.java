package dungeonescape.model;

import dungeonescape.structures.Graph;      // CHANGE 1: new import

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages all rooms in the dungeon.
 * The room's ID equals its index in the list, so lookup is fast.
 * Later this can be upgraded into a graph with an adjacency list.
 */
public class Dungeon {

    private List<Room> rooms;
    private Graph graph;
    private int startRoomId;
    private int exitRoomId;

    public Dungeon() {
        rooms = new ArrayList<>();
        graph = new Graph();                // CHANGE 3: create it BEFORE buildDungeon()
        buildDungeon();
    }

    // Creates all rooms and connects them
    private void buildDungeon() {
        // Room IDs must be added in order 0, 1, 2, ...
        addRoom(new Room(0, "Entrance Hall", RoomType.ENTRANCE,
                "A wide stone hall lit by flickering torches. The heavy door slams shut behind you.",
                null, null));
        addRoom(new Room(1, "Dusty Corridor", RoomType.NORMAL,
                "A long corridor covered in cobwebs and dust.",
                null, null));
        addRoom(new Room(2, "Guard Room", RoomType.MONSTER,
                "Broken weapons litter the floor. Someone is still on guard.",
                "Goblin Guard", null));
        addRoom(new Room(3, "Old Armory", RoomType.TREASURE,
                "Rusty racks line the walls, but one sword still gleams.",
                null, "Iron Sword"));
        addRoom(new Room(4, "Spider Den", RoomType.MONSTER,
                "Thick webs cover every wall and something moves above you.",
                "Giant Spider", null));
        addRoom(new Room(5, "Flooded Passage", RoomType.TRAP,
                "Ankle-deep water hides a pressure plate under the floor.",
                null, null));
        addRoom(new Room(6, "Fountain Chamber", RoomType.HEALING,
                "A calm fountain glows with a soft blue light.",
                null, "Healing Potion"));
        addRoom(new Room(7, "Crossroads", RoomType.NORMAL,
                "Several tunnels meet here. The air is cold and still.",
                null, null));
        addRoom(new Room(8, "Riddle Hall", RoomType.PUZZLE,
                "A stone statue blocks the way and asks you a riddle.",
                null, null));
        addRoom(new Room(9, "Bone Crypt", RoomType.MONSTER,
                "Coffins line the walls. One of them is open.",
                "Skeleton Warrior", null));
        addRoom(new Room(10, "Spike Corridor", RoomType.TRAP,
                "The walls are full of small holes. Something is very wrong here.",
                null, null));
        addRoom(new Room(11, "Torch Hall", RoomType.NORMAL,
                "A quiet hall with torches burning along the walls.",
                null, null));
        addRoom(new Room(12, "Treasure Vault", RoomType.TREASURE,
                "A small vault with a golden key resting on a pedestal.",
                null, "Golden Key"));
        addRoom(new Room(13, "Shrine of Light", RoomType.HEALING,
                "A peaceful shrine. Your wounds begin to feel lighter.",
                null, "Blessed Water"));
        addRoom(new Room(14, "Ogre Lair", RoomType.MONSTER,
                "The stench is awful. A huge shape sleeps in the corner.",
                "Ogre", null));
        addRoom(new Room(15, "Cursed Library", RoomType.PUZZLE,
                "Books float in the air. One of them holds the answer.",
                null, null));
        addRoom(new Room(16, "Dark Antechamber", RoomType.NORMAL,
                "A dark room before a massive door. You hear breathing beyond it.",
                null, null));
        addRoom(new Room(17, "Dragon's Chamber", RoomType.BOSS,
                "A giant cavern filled with gold and a very large, very angry dragon.",
                "Dragon Lord", null));
        addRoom(new Room(18, "Exit Gate", RoomType.EXIT,
                "Sunlight! The gate to freedom stands open.",
                null, null));

        startRoomId = 0;
        exitRoomId = 18;

        // Connections (each call connects both ways)
        connectRooms(0, 1);
        connectRooms(0, 2);
        connectRooms(1, 3);
        connectRooms(1, 4);
        connectRooms(2, 5);
        connectRooms(2, 6);
        connectRooms(3, 7);
        connectRooms(4, 7);
        connectRooms(4, 8);
        connectRooms(5, 8);
        connectRooms(6, 9);
        connectRooms(7, 10);
        connectRooms(8, 10);
        connectRooms(8, 11);
        connectRooms(9, 11);
        connectRooms(10, 12);
        connectRooms(11, 13);
        connectRooms(12, 14);
        connectRooms(13, 15);
        connectRooms(14, 15);
        connectRooms(14, 16);
        connectRooms(15, 16);
        connectRooms(16, 17);
        connectRooms(17, 18);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        graph.addRoom(room);
    }

    // Two-way connection (an undirected edge in graph terms)
    public void connectRooms(int roomId1, int roomId2) {
        getRoom(roomId1).addConnection(roomId2);
        getRoom(roomId2).addConnection(roomId1);
        graph.addConnection(roomId1, roomId2);   // CHANGE 4b: register the edge
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public Room getStartRoom() { return getRoom(startRoomId); }
    public Room getExitRoom()  { return getRoom(exitRoomId); }
    public int getTotalRooms() { return rooms.size(); }
    public List<Room> getRooms() { return rooms; }

    public Graph getGraph() { return graph; }    // new getter

    // ... getRoom, getStartRoom, getExitRoom, getTotalRooms, getRooms unchanged ...
}