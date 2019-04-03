package javazaawansowana.lab1;

import java.util.List;

/**
 * Interface designed to be implements in each algorithm that solves Knapsack Problem.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Knapsack
 * @see Item
 * @see GreedyAlgorithm
 * @see RandomAlgorithm
 * @see BruteForceAlgorithm
 *
 */
public interface AlgorithmInterface {
	/**
	 * Returns description of the algorithm and solution.
	 * @return Description of the algorithm and solution.
	 */
	abstract String description();
	/**
	 * Method that solves problem.
	 */
	abstract void solve();
}
