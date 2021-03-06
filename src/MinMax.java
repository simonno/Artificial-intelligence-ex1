import static java.lang.Double.max;
import static java.lang.Double.min;

/**
 * The type Min max.
 *
 * @param <E> the type parameter
 */
public class MinMax<E> implements Searcher<E> {
    private boolean isMaximizing;
    private int maxDepth;

    /**
     * Instantiates a new Min max.
     *
     * @param isMaximizing the is maximizing
     * @param maxDepth     the max depth
     */
    public MinMax(boolean isMaximizing, int maxDepth) {
        this.isMaximizing = isMaximizing;
        this.maxDepth = maxDepth;
    }

    /**
     * Instantiates a new Min max.
     *
     * @param isMaximizing the is maximizing
     */
    public MinMax(boolean isMaximizing) {
        this.isMaximizing = isMaximizing;
        this.maxDepth = 3;
    }

    /**
     * Search state.
     *
     * @param searchable the searchable
     * @return the goal state - the next best state.
     */
    @Override
    public State<E> search(Searchable<E> searchable) {

        State<E> initialState = searchable.getInitialState();
        State<E> theBestNextState = null;
        if (this.isMaximizing) {
            double bestValue = -Double.MAX_VALUE;
            for (State<E> successor : searchable.getSuccessors(initialState)) {
                double v = minimax(successor, this.maxDepth - 1, false, searchable);
                if (bestValue <= v) {
                    theBestNextState = successor;
                    theBestNextState.setCost(v);
                    bestValue = v;
                }
            }
            return theBestNextState;

        } else {   // minimizing player
            double bestValue = Double.MAX_VALUE;
            for (State<E> successor : searchable.getSuccessors(initialState)) {
                double v = minimax(successor, this.maxDepth - 1, true, searchable);
                if (bestValue >= v) {
                    theBestNextState = successor;
                    theBestNextState.setCost(v);
                    bestValue = v;
                }
            }
            return theBestNextState;
        }
    }

    /**
     * Implement of minmax algorithm
     *
     * @param state            is the state for start the search.
     * @param depth            max depth for searching.
     * @param maximizingPlayer is the player is a maximizing player.
     * @param searchable       is searchable object to search.
     * @return the beat value.
     */
    private double minimax(State<E> state, int depth, boolean maximizingPlayer, Searchable<E> searchable) {
        if (depth == 0 || searchable.isGoal(state)) {
            return searchable.getHeuristics(state);
        }

        if (maximizingPlayer) {
            double bestValue = -Double.MAX_VALUE;
            for (State<E> successor : searchable.getSuccessors(state)) {
                double v = minimax(successor, depth - 1, false, searchable);
                bestValue = max(bestValue, v);
            }
            return bestValue;

        } else {   // minimizing player
            double bestValue = Double.MAX_VALUE;
            for (State<E> successor : searchable.getSuccessors(state)) {
                double v = minimax(successor, depth - 1, true, searchable);
                bestValue = min(bestValue, v);
            }
            return bestValue;
        }
    }

    /**
     * Gets max depth.
     *
     * @return the max depth
     */
    public int getMaxDepth() {
        return maxDepth;
    }

    /**
     * Sets max depth.
     *
     * @param maxDepth the max depth
     */
    public void setMaxDepth(int maxDepth) {
        if (maxDepth <= 0) {
            this.maxDepth = 3;
        }
        this.maxDepth = maxDepth;
    }

    /**
     * Is maximizing boolean.
     *
     * @return the boolean
     */
    public boolean isMaximizing() {
        return isMaximizing;
    }

    /**
     * Sets maximizing.
     *
     * @param maximizing the maximizing
     */
    public void setMaximizing(boolean maximizing) {
        isMaximizing = maximizing;
    }

}
