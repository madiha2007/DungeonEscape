package dungeonescape.game;

import dungeonescape.model.Dungeon;
import dungeonescape.model.Player;
import dungeonescape.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles everything about moving the player between rooms.
 * Uses the dungeon graph: rooms are vertices, connections are edges.
 */
public class MovementManager {

    private Dungeon dungeon;
    private Player player;

    public MovementManager(Dungeon dungeon, Player player) {
        this.dungeon = dungeon;
        this.player = player;
    }

    /**
     * Returns the neighbours of the player's current room.
     * This is the ONLY place that reads the adjacency list.
     * (If your Step 3 Graph has its own neighbour method, change it here.)
     */
    public List<Room> getAvailableMoves() {
        List<Room> moves = new ArrayList<>();
        for (int neighbourId : player.getCurrentRoom().getConnections()) {
            moves.add(dungeon.getRoom(neighbourId));
        }
        return moves;
    }

    // Prints the current room and its details
    public void showCurrentRoom() {
        Room room = player.getCurrentRoom();
        System.out.println("Current Room: " + room.getName());
        System.out.println(room.getDescription());
    }

    // Prints the numbered list of connected rooms
    public void showConnections() {
        List<Room> moves = getAvailableMoves();
        System.out.println("Connected Rooms:");
        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i).getName());
        }
    }

    /**
     * Asks the player which room to go to and moves them there.
     * Handles non-numeric input and out-of-range numbers without crashing.
     */
    public void handleMove(Scanner scanner) {
        List<Room> moves = getAvailableMoves();

        System.out.println();
        showConnections();
        System.out.println("0. Cancel");
        System.out.print("Enter room number: ");

        String input = scanner.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (choice == 0) {
            System.out.println("Move cancelled.");
            return;
        }
        if (choice < 1 || choice > moves.size()) {
            System.out.println("Invalid choice. Pick a number from 1 to " + moves.size() + ".");
            return;
        }

        // Valid choice: the list only contains connected rooms,
        // so the player can never move to an unconnected room.
        Room from = player.getCurrentRoom();
        Room to = moves.get(choice - 1);
        player.setCurrentRoom(to);

        System.out.println("\nYou moved from:");
        System.out.println(from.getName());
        System.out.println("To:");
        System.out.println(to.getName());

        System.out.println();
        showCurrentRoom();
        showConnections();
    }
}