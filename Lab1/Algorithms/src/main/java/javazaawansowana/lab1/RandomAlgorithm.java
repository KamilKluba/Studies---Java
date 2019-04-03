package javazaawansowana.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class designed to solve Knapsack Problem with random algorithm. This
 * algorithm creates random list of items that will be put into knapsack and
 * then calculates if the weight of these items is less than maximum capacity od
 * knapsack. The process repeats certaing number of times and then the best
 * solution that meets the conditions is chosen.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Knapsack
 * @see Item
 * @see GreedyAlgorithm
 * @see BruteForceAlgorithm
 *
 */
public class RandomAlgorithm implements AlgorithmInterface {
	private int solutionsAmount;
	private int capacity;
	private List<Item> listOfItems;
	private List<Knapsack> listOfKnapsacks;
	private Knapsack mostValuableKapsack;

	/**
	 * Constructor that contains three parameters: list of items that ma be put into a backpack, capacity of it, and number of repetitions of the algorithm.
	 * @param listOfItems List of items that may be put into a backpack.
	 * @param capacity Capacity of a backpack.
	 * @param repetitionsNumber Number of repetitions of the algorithm.
	 */
	public RandomAlgorithm(List<Item> listOfItems, int capacity, int repetitionsNumber) {
		super();
		this.capacity = capacity;
		this.solutionsAmount = repetitionsNumber;
		this.listOfItems = listOfItems;

		listOfKnapsacks = new ArrayList<Knapsack>();
		mostValuableKapsack = new Knapsack(capacity);

		solve();
		description();
	}
	
	/**
	 * Method that returns description of an algorithm with solution of it.
	 * @return Description of an algorithm with solution of it.
	 */
	public String description() {
		String desc = "Rozwi¹zywanie problemu algorytmem zach³annym \nLiczba powtorzen: " + solutionsAmount
				+ " Ilosc miejsca: " + mostValuableKapsack.getMaxCapacity() + ", wykorzystane miejsce: "
				+ mostValuableKapsack.getCurrentCapacity() + ", wartosc: " + mostValuableKapsack.getCurrentValue()
				+ "\nZapakowane przedmioty: ";
		for (int i = 0; i < mostValuableKapsack.getListOfItems().size(); i++) {
			desc += "\n" + mostValuableKapsack.getListOfItems().get(i).getName() + " "
					+ mostValuableKapsack.getListOfItems().get(i).getWeight() + " "
					+ mostValuableKapsack.getListOfItems().get(i).getItemValue();
		}

		return desc;
	}

	/**
	 * Method that solves the problem.
	 */
	public void solve() {
		for (int a = 0; a < solutionsAmount; a++) {
			Knapsack knapsack = new Knapsack(capacity);
			Random random = new Random();

			for (int i = 0; i < listOfItems.size(); i++)
				if (random.nextBoolean()) {
					knapsack.getListOfItems().add(listOfItems.get(i));
					if(knapsack.getCurrentCapacity() + listOfItems.get(i).getWeight() > knapsack.getMaxCapacity())
						break;
					knapsack.setCurrentCapacity(knapsack.getCurrentCapacity() + listOfItems.get(i).getWeight());
					knapsack.setCurrentValue(knapsack.getCurrentValue() + listOfItems.get(i).getItemValue());
				}

			listOfKnapsacks.add(knapsack);
		}

		for (int i = 0; i < listOfKnapsacks.size(); i++)
			if (listOfKnapsacks.get(i).getCurrentCapacity() < capacity
					&& listOfKnapsacks.get(i).getCurrentValue() > mostValuableKapsack.getCurrentValue())
				mostValuableKapsack = listOfKnapsacks.get(i);

	}
}