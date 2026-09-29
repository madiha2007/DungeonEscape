package dungeonescape.game;

import dungeonescape.model.Dungeon;
import dungeonescape.model.Player;
import dungeonescape.model.Room;
import dungeonescape.structures.Stack;

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
    private Stack<Room> history;    // NEW: movement history (the undo stack)

    public MovementManager(Dungeon dungeon, Player player) {
        this.dungeon = dungeon;
        this.player = player;
        this.history = new Stack<>();
        history.push(player.getCurrentRoom());   // NEW: the entrance is the first entry
    }

    /** Neighbours of the current room, read from our Graph's adjacency list. */
    public List<Room> getAvailableMoves() {
        return dungeon.getGraph().getNeighbors(player.getCurrentRoom().getId());
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
        history.push(to);                       // NEW: remember the new room

        System.out.println("\nYou moved from:");
        System.out.println(from.getName());
        System.out.println("To:");
        System.out.println(to.getName());

        System.out.println();
        showCurrentRoom();
        showConnections();
    }
    /**
     * NEW: Undo the last move.
     * pop()  removes the current room from the top of the stack.
     * peek() then reads the previous room, which is now the top.
     */
    public void handleUndo() {
        // Only the entrance is left, so there is nowhere to go back to.
        if (history.size() <= 1) {
            System.out.println("There is no previous room to return to.");
            return;
        }

        history.pop();                          // remove the current room
        Room previous = history.peek();         // the room we came from
        player.setCurrentRoom(previous);

        System.out.println("You moved back to " + previous.getName() + ".");
        System.out.println();
        showCurrentRoom();
        showConnections();

        System.out.println("\nStack:");
        history.display();
    }

    /** NEW: Shows the history in two ways so the difference is clear. */
    public void showHistory() {
        System.out.println("\n========== MOVEMENT HISTORY ==========");
        System.out.println("(Chronological order: oldest room first)\n");

        List<Room> path = history.toListBottomToTop();
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i).getName());
            if (i < path.size() - 1) {
                System.out.println("↓");
            }
        }

        System.out.println("\n---------- THE STACK ITSELF ----------");
        System.out.println("(Top = newest room. This is what Undo pops.)");
        history.display();
        System.out.println("Stack size: " + history.size());
        System.out.println("=======================================");
    }
}
