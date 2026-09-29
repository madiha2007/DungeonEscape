package dungeonescape.game;

import dungeonescape.ui.ConsoleUI;

public class Game {

    private ConsoleUI ui;

    public Game() {
        ui = new ConsoleUI();
    }

    public void start() {
        ui.showWelcome();
        // Game loop will be added in a later step
    }
}