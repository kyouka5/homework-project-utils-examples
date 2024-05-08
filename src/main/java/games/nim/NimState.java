package games.nim;

import game.BasicState;

/**
 * Represents the states of the nim variant called the subtraction game. In this
 * game, two players are removing objects from a pile of objects in alternating
 * turns. On each turn, at least one object must be removed, however, no more
 * than {@code k} objects can be removed ({@code k} is a positive integer). The
 * winner of the game is the player who takes the last object.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Nim#The_subtraction_game">The
 * subtraction game</a>
 */
public class NimState implements BasicState<Integer> {

    private int numberOfObjects;
    private final int limit;
    private Player player;

    /**
     * Creates a {@code NimState} instance with the specified number of objects.
     *
     * @param numberOfObjects the initial number of objects, must be positive
     * @param limit upper limit for the number of objects to be removed, must be
     *              positive
     */
    public NimState(int numberOfObjects, int limit) {
        if (numberOfObjects < 1 || limit < 1) {
            throw new IllegalArgumentException();
        }
        this.numberOfObjects = numberOfObjects;
        this.limit = limit;
        player = Player.PLAYER_1;
    }

    /**
     * {@return whether it is allowed to remove the specified number of objects}
     *
     * @param n the number of objects to be removed
     */
    @Override
    public boolean isLegalMove(Integer n) {
        return 0 < n && n <= limit && n <= numberOfObjects;
    }

    /**
     * Removes the specified number of objects.
     *
     * @param n the number of objects to be removed
     */
    @Override
    public void makeMove(Integer n) {
        numberOfObjects -= n;
        player = player.opponent();
    }

    /**
     * {@return the player who moves next}
     */
    @Override
    public Player getNextPlayer() {
        return player;
    }

    /**
     * {@return whether the game is over}
     */
    @Override
    public boolean isGameOver() {
        return numberOfObjects == 0;
    }

    /**
     * {@return the status of the game}
     */
    @Override
    public Status getStatus() {
        if (!isGameOver()) {
            return Status.IN_PROGRESS;
        }
        return player == Player.PLAYER_2 ? Status.PLAYER_1_WINS : Status.PLAYER_2_WINS;
    }

    @Override
    public String toString() {
        return String.format("%d: [ %s]", numberOfObjects, "O ".repeat(numberOfObjects));
    }

}
