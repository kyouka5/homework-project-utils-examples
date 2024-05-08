package games.dummy;

import game.TwoPhaseMoveState;

/**
 * Represents the states of a dummy two-player game that was created purely for
 * demonstrational purposes. The game is played on a board with
 * {@value BOARD_SIZE} rows and columns, respectively. Each corner square of the
 * board contains a coin, all the other squares are empty. The players move in
 * alternating turns. On each turn, a coin must be moved to an 8-adjacent empty
 * square. The player who moves a coin to the square in the middle of the board
 * wins the game.
 */
public class DummyState implements TwoPhaseMoveState<Position> {

    /**
     * The size of the game board.
     */
    public static final int BOARD_SIZE = 5;

    private boolean[][] board;

    private Player player;

    /**
     * Creates a {@code DummyState} object representing the initial state of
     * the game.
     */
    public DummyState() {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
        board[0][0] = board[0][BOARD_SIZE - 1] = board[BOARD_SIZE - 1][0] = board[BOARD_SIZE - 1][BOARD_SIZE - 1] = true;
        player = Player.PLAYER_1;
    }

    /**
     * {@return the player who moves next}
     */
    public Player getNextPlayer() {
        return player;
    }

    /**
     * {@return whether the game is over}
     */
    @Override
    public boolean isGameOver() {
        return board[BOARD_SIZE / 2][BOARD_SIZE / 2];
    }

    /**
     * {@return the status of the game}
     */
    @Override
    public Status getStatus() {
        if (!isGameOver()) {
            return Status.IN_PROGRESS;
        };
        return player == Player.PLAYER_2 ? Status.PLAYER_1_WINS : Status.PLAYER_2_WINS;
    }

    /**
     * {@return whether is it possible to move a coin from the source
     * position specified}
     *
     * @param from the source position from which a move is to be made
     */
    @Override
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from) && hasEmptyNeighbor(from);
    }

    /**
     * {@return whether is it possible to move a coin from the source position
     * specified to the target position specified}
     *
     * @param from the source position
     * @param to the target position
     */
    @Override
    public boolean isLegalMove(Position from, Position to) {
        return isOnBoard(from)
                && !isEmpty(from)
                && isOnBoard(to)
                && isEmpty(to)
                && isKingMove(from, to);
    }

    /**
     * Moves a coin from the source position specified to the empty target
     * position specified.
     *
     * @param from the source position
     * @param to the target position
     */
    @Override
    public void makeMove(Position from, Position to) {
        board[from.row()][from.col()] = false;
        board[to.row()][to.col()] = true;
        player = player.opponent();
    }

    private boolean hasEmptyNeighbor(Position p) {
        for (int i = p.row() - 1; i <= p.row() + 1; i++) {
            for (int j = p.col() - 1; j <= p.col() + 1; j++) {
                if (i == p.row() && j == p.col()) {
                    continue;
                }
                if (isOnBoard(i, j) && !board[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOnBoard(Position p) {
        return isOnBoard(p.row(), p.col());
    }

    private boolean isOnBoard(int row, int col) {
        return 0 <= row && row < BOARD_SIZE && 0 <= col && col < BOARD_SIZE;
    }
    private boolean isEmpty(Position p) {
        return !board[p.row()][p.col()];
    }

    private boolean isKingMove(Position from, Position to) {
        var dx = Math.abs(from.row() - to.row());
        var dy = Math.abs(from.col() - to.col());
        return dx + dy == 1 || dx * dy == 1;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j] ? 'O' : '_').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var state = new DummyState();
        System.out.println(state.hasEmptyNeighbor(new Position(0, 0)));
    }
}
