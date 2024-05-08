package games.nim;

import game.console.BasicGame;

/**
 * Conducts a nim game on the console.
 */
public class ConsoleGame {

    public static void main(String[] args) {
        var state = new NimState(10, 3);
        var game = new BasicGame<>(state, Integer::parseInt);
        game.start();
    }

}
