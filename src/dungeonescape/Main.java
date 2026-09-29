package dungeonescape;

import dungeonescape.game.Game;
import dungeonescape.model.Dungeon;
import dungeonescape.model.Room;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();

        // ---- Step 2 test: build the dungeon and print everything ----
        Dungeon dungeon = new Dungeon();

        System.out.println("\nTotal rooms: " + dungeon.getTotalRooms());
        System.out.println("Start room : " + dungeon.getStartRoom());
        System.out.println("Exit room  : " + dungeon.getExitRoom());
        System.out.println("\n===== ALL ROOMS =====");

        for (Room room : dungeon.getRooms()) {
            System.out.println("\n" + room);
            System.out.println("  Description: " + room.getDescription());
            if (room.hasEnemy()) {
                System.out.println("  Enemy      : " + room.getEnemyName());
            }
            if (room.hasItem()) {
                System.out.println("  Item       : " + room.getItemName());
            }
            System.out.print("  Connected to: ");
            for (int id : room.getConnections()) {
                System.out.print(dungeon.getRoom(id).getName() + " (" + id + ")  ");
            }
            System.out.println();
        }
    }
}