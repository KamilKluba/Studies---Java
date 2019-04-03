package javazaawansowana.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to solve Knapsack Problem with brute force algorithm. It
 * solves all possible solutions that is equal to 2 to power of size of list of
 * items. Works for small amount of items. For big amount of items it may last
 * an eternity to find the best solution.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Knapsack
 * @see Item
 * @see GreedyAlgorithm
 * @see RandomAlgorithm
 *
 */
public class BruteForceAlgorithm implements AlgorithmInterface {
	private int instanceNumber;
	private int capacity;
	private List<Item> listOfItems;
	private boolean binaryListOfItems[];
	private Knapsack knapsack;
	private Knapsack mostValuableKnapsack;

	/**
	 * Constructor that contains two parameters: list of item that may be put into a
	 * knapsack and capacity of it.
	 * 
	 * @param listOfItems List of items that may be put into a knapsack.
	 * @param capacity Capacity of a knapsack.
	 */
	public BruteForceAlgorithm(List<Item> listOfItems, int capacity) {
		super();
		this.listOfItems = listOfItems;
		this.capacity = capacity;

		mostValuableKnapsack = new Knapsack(capacity);
		instanceNumber = 0;
		binaryListOfItems = new boolean[listOfItems.size()];
		for (boolean b : binaryListOfItems)
			b = false;

		solve();
		description();
	}

	/**
	 * Method that returns description of an algorithm with solution of it.
	 * 
	 * @return Description of an algorithm with solution of it.
	 */
	public String description() {
		String desc = "Rozwi¹zywanie problemu algorytmem zach³annym \nIlosc miejsca: " + mostValuableKnapsack.getMaxCapacity()
				+ ", wykorzystane miejsce: " + mostValuableKnapsack.getCurrentCapacity() + ", wartosc: "
				+ mostValuableKnapsack.getCurrentValue() + "\nZapakowane przedmioty: ";
		for (int i = 0; i < mostValuableKnapsack.getListOfItems().size(); i++) {
			desc += "\n" + mostValuableKnapsack.getListOfItems().get(i).getName() + " "
					+ mostValuableKnapsack.getListOfItems().get(i).getWeight() + " " + mostValuableKnapsack.getListOfItems().get(i).getItemValue();
		}

		return desc;
	}

	/**
	 * Method that solves the problem.
	 */
	public void solve() {
		for (int i = 0; i < Math.pow(2, listOfItems.size()); i++) {
			knapsack = new Knapsack(capacity);

			decimalToBinary();

			for (int j = 0; j < listOfItems.size(); j++)
				if (binaryListOfItems[j]) {
					knapsack.getListOfItems().add(listOfItems.get(j));
					knapsack.setCurrentCapacity(knapsack.getCurrentCapacity() + listOfItems.get(j).getWeight());
					knapsack.setCurrentValue(knapsack.getCurrentValue() + listOfItems.get(j).getItemValue());
				}

			if (knapsack.getCurrentCapacity() < capacity
					&& knapsack.getCurrentValue() > mostValuableKnapsack.getCurrentValue())
				mostValuableKnapsack = knapsack;

			instanceNumber++;
		}
	}

	/**
	 * Method that converts decimal number to a binary. It is needed to create array
	 * of bits that tells which items should be put into a knapsack.
	 */
	private void decimalToBinary() {
		int buffer = instanceNumber;
		int position = binaryListOfItems.length;

		while (buffer > 0) {
			position--;

			if (buffer % 2 == 1)
				binaryListOfItems[position] = true;
			else
				binaryListOfItems[position] = false;

			buffer /= 2;
		}
	}
}
