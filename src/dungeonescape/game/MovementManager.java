package dungeonescape.game;

import dungeonescape.model.Dungeon;
import dungeonescape.model.Player;
import dungeonescape.model.Room;
import dungeonescape.structures.Stack;

import java.util.List;
import java.util.Scanner;

/**
 * Handles moving the player and undoing moves.
 * RULE: the room on TOP of the history stack is always the player's current room.
 */
public class MovementManager {

    private Dungeon dungeon;
    private Player player;
    private Stack<Room> history;

    public MovementManager(Dungeon dungeon, Player player) {
        this.dungeon = dungeon;
        this.player = player;
        this.history = new Stack<>();
        history.push(player.getCurrentRoom());
    }

    public List<Room> getAvailableMoves() {
        return dungeon.getGraph().getNeighbors(player.getCurrentRoom().getId());
    }

    public void showCurrentRoom() {
        Room room = player.getCurrentRoom();
        System.out.println("Current Room: " + room.getName());
        System.out.println(room.getDescription());
    }

    public void showConnections() {
        List<Room> moves = getAvailableMoves();
        System.out.println("Connected Rooms:");
        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i).getName());
        }
    }

    /** Returns true if the player moved to a new room. */
    public boolean handleMove(Scanner scanner) {
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
            return false;
        }

        if (choice == 0) {
            System.out.println("Move cancelled.");
            return false;
        }
        if (choice < 1 || choice > moves.size()) {
            System.out.println("Invalid choice. Pick a number from 1 to " + moves.size() + ".");
            return false;
        }

        Room from = player.getCurrentRoom();
        Room to = moves.get(choice - 1);
        player.setCurrentRoom(to);
        history.push(to);

        System.out.println("\nYou moved from:");
        System.out.println(from.getName());
        System.out.println("To:");
        System.out.println(to.getName());

        System.out.println();
        showCurrentRoom();
        showConnections();
        return true;
    }

    /** Returns true if the player moved back to the previous room. */
    public boolean handleUndo() {
        if (history.size() <= 1) {
            System.out.println("There is no previous room to return to.");
            return false;
        }

        history.pop();
        Room previous = history.peek();
        player.setCurrentRoom(previous);

        System.out.println("You moved back to " + previous.getName() + ".");
        System.out.println();
        showCurrentRoom();
        showConnections();

        System.out.println("\nStack:");
        history.display();
        return true;
    }

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