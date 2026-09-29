package dungeonescape.structures;

import dungeonescape.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * The dungeon as an undirected graph, stored as an adjacency list.
 * Vertex = Room, Edge = connection between two rooms.
 * The adjacency list is an array of hand-made linked lists.
 */
public class Graph {

    // One node of a linked list = one neighbour of a room
    private static class EdgeNode {
        int roomId;        // the neighbour's ID
        EdgeNode next;     // next neighbour in the list (or null)

        EdgeNode(int roomId) {
            this.roomId = roomId;
        }
    }

    private Room[] rooms;               // rooms[i] is the Room with id i
    private EdgeNode[] adjacencyList;   // adjacencyList[i] = head of room i's neighbour list
    private int vertexCount;            // how many rooms have been added

    public Graph() {
        rooms = new Room[10];
        adjacencyList = new EdgeNode[10];
        vertexCount = 0;
    }

    /** Adds a vertex. Room IDs must be added in order: 0, 1, 2, ... */
    public void addRoom(Room room) {
        if (room.getId() != vertexCount) {
            throw new IllegalArgumentException(
                    "Rooms must be added in order. Expected id " + vertexCount
                            + " but got " + room.getId());
        }
        if (vertexCount == rooms.length) {
            growArrays();
        }
        rooms[vertexCount] = room;
        adjacencyList[vertexCount] = null;   // starts with no neighbours
        vertexCount++;
    }

    /** Adds an undirected edge: puts each room in the other's list. */
    public void addConnection(int roomId1, int roomId2) {
        checkValid(roomId1);
        checkValid(roomId2);
        if (roomId1 == roomId2 || areConnected(roomId1, roomId2)) {
            return;   // ignore self-loops and duplicates
        }
        addOneWay(roomId1, roomId2);
        addOneWay(roomId2, roomId1);
    }

    // Adds 'to' at the END of 'from's linked list (keeps insertion order)
    private void addOneWay(int from, int to) {
        EdgeNode newNode = new EdgeNode(to);
        if (adjacencyList[from] == null) {
            adjacencyList[from] = newNode;
            return;
        }
        EdgeNode current = adjacencyList[from];
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    /** True if there is a direct edge between the two rooms. */
    public boolean areConnected(int roomId1, int roomId2) {
        checkValid(roomId1);
        checkValid(roomId2);
        EdgeNode current = adjacencyList[roomId1];
        while (current != null) {
            if (current.roomId == roomId2) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** IDs of all neighbours of a room. */
    public List<Integer> getNeighborIds(int roomId) {
        checkValid(roomId);
        List<Integer> result = new ArrayList<>();
        EdgeNode current = adjacencyList[roomId];
        while (current != null) {
            result.add(current.roomId);
            current = current.next;
        }
        return result;
    }

    /** Room objects of all neighbours of a room. */
    public List<Room> getNeighbors(int roomId) {
        List<Room> result = new ArrayList<>();
        for (int id : getNeighborIds(roomId)) {
            result.add(rooms[id]);
        }
        return result;
    }

    public Room getRoom(int roomId) {
        checkValid(roomId);
        return rooms[roomId];
    }

    public int getVertexCount() {
        return vertexCount;
    }

    /** Number of undirected edges (each edge is stored twice, so divide by 2). */
    public int getEdgeCount() {
        int total = 0;
        for (int i = 0; i < vertexCount; i++) {
            total += getNeighborIds(i).size();
        }
        return total / 2;
    }

    /** Prints the adjacency list in a readable format. */
    public void displayAdjacencyList() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print("Room " + i + " (" + rooms[i].getName() + ") -> ");
            EdgeNode current = adjacencyList[i];
            while (current != null) {
                System.out.print("Room " + current.roomId);
                if (current.next != null) {
                    System.out.print(", ");
                }
                current = current.next;
            }
            System.out.println();
        }
    }

    // Doubles the arrays when they are full
    private void growArrays() {
        Room[] newRooms = new Room[rooms.length * 2];
        EdgeNode[] newLists = new EdgeNode[adjacencyList.length * 2];
        for (int i = 0; i < vertexCount; i++) {
            newRooms[i] = rooms[i];
            newLists[i] = adjacencyList[i];
        }
        rooms = newRooms;
        adjacencyList = newLists;
    }

    private void checkValid(int roomId) {
        if (roomId < 0 || roomId >= vertexCount) {
            throw new IllegalArgumentException("No room with id " + roomId);
        }
    }
}