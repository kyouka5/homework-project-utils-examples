package puzzles.jugs;

import puzzle.TwoPhaseMoveState;
import puzzle.solver.BreadthFirstSearch;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the states of the three jugs problem also known as a water pouring
 * puzzle. Consider three jugs, whose capacities are 3, 5, and 8 liters,
 * respectively. Initially, the two smaller jugs are full of water, and the
 * largest jugs is empty. The goal of the puzzle is to have 4-4 liters of water
 * in the larger two jugs. In a move, water can be poured from a non-empty jug
 * into another one that is not fully loaded.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Water_pouring_puzzle">Water pouring puzzle</a>
 */
public class ThreeJugsState implements TwoPhaseMoveState<Integer> {

    // The set of all possible moves
    private static final Set<TwoPhaseMove<Integer>> MOVES = new HashSet<>();

    static {
        // For performance reasons, all possible moves are generated in advance
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                MOVES.add(new TwoPhaseMove<>(i, j));
            }
        }
    }

    /**
     * The volumes of the jugs.
     */
    public static final int[] VOLUMES = {3, 5, 8};

    private int[] contents;

    /**
     * Creates a {@code ThreeJugsState} instance representing the initial state
     * of the puzzle.
     */
    public ThreeJugsState() {
        contents = new int[] {3, 5, 0};
    }

    /**
     * {@return whether the puzzle is solved}
     */
    @Override
    public boolean isSolved() {
        return contents[0] == 0 && contents[1] == 4 && contents[2] == 4;
    }

    /**
     * {@return whether is it possible to pour a source jug into a target jug}
     * The jugs are identified with the integers 0, 1, and 2, respectively.
     *
     * @param move wraps the numbers of the source and the target jugs
     */
    @Override
    public boolean isLegalMove(TwoPhaseMove<Integer> move) {
        return contents[move.from()] > 0
                && contents[move.to()] < VOLUMES[move.to()];
    }

    /**
     * Pours the source jug specified into the target jug specified. The jugs
     * are identified with the integers 0, 1, and 2, respectively.
     *
     * @param move wraps the numbers of the source and the target jugs
     */
    @Override
    public void makeMove(TwoPhaseMove<Integer> move) {
        var change = Math.min(contents[move.from()], VOLUMES[move.to()] - contents[move.to()]);
        contents[move.from()] -= change;
        contents[move.to()] += change;
    }

    /**
     * {@return the set of all moves that can be applied to the state}
     */
    @Override
    public Set<TwoPhaseMove<Integer>> getLegalMoves() {
        var moves = new HashSet<TwoPhaseMove<Integer>>();
        for (var move : MOVES) {
            if (isLegalMove(move)) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public ThreeJugsState clone() {
        ThreeJugsState copy;
        try {
            copy = (ThreeJugsState) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        this.contents = contents.clone();
        return copy;
    }

    /**
     * {@return whether is it possible to pour the source jug specified into any
     * of the other jugs, i.e., whether the source jug is non-empty} The jugs
     * are identified with the integers 0, 1, and 2, respectively.
     *
     * @param from the number of the source jug
     */
    @Override
    public boolean isLegalToMoveFrom(Integer from) {
        return contents[from] > 0;
    }

    @Override
    public String toString() {
        return String.format("[%d/%d] [%d/%d] [%d/%d]",
                contents[0], VOLUMES[0],
                contents[1], VOLUMES[1],
                contents[2], VOLUMES[2]);
    }

    public static void main(String[] args) {
        new BreadthFirstSearch<TwoPhaseMove<Integer>>()
                .solveAndPrintSolution(new ThreeJugsState());

    }

}
