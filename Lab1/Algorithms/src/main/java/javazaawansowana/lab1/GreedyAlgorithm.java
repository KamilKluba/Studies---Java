package javazaawansowana.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to solve Knapsack Problem with greedy algorithm. This
 * algorithm calculates the value/weight ratio of each items and packs items
 * with the highest ratio to a backpack.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Knapsack
 * @see Item
 * @see RandomAlgorithm
 * @see BruteForceAlgorithm
 *
 */
public class GreedyAlgorithm implements AlgorithmInterface {
	/*
	 * list of all items that may be put into a knapsack.
	 */
	private List<Item> listOfItems;
	/*
	 * list of all items that may be put into a backpack sorted by value/weight ratio.
	 */
	private List<Item> listOfSortedItems;
	/*
	 * Knapsack that will contains items.
	 */
	Knapsack knapsack;

	/**
	 * Constructor that contains two parameters: list of items and capacity.
	 * @param listOfItems List of items that may be put into a knapsack.
	 * @param capacity Capacity of a knapsack.
	 */
	public GreedyAlgorithm(List<Item> listOfItems, int capacity) {
		super();
		listOfSortedItems = new ArrayList<Item>();
		
		this.knapsack = new Knapsack(capacity);

		this.listOfItems = new ArrayList<Item>();
		for (Item i : listOfItems)
			this.listOfItems.add(i);
		
		sort();
		solve();
		description();
	}

	/**
	 * Method that returns description of an algorithm with solution of it.
	 * @return Description of an algorithm with solution of it.
	 */
	public String description() {
		String desc = "Rozwi¹zywanie problemu algorytmem zach³annym \nIlosc miejsca: " + knapsack.getMaxCapacity()
				+ ", wykorzystane miejsce: " + knapsack.getCurrentCapacity() + ", wartosc: "
				+ knapsack.getCurrentValue() + "\nZapakowane przedmioty: ";
		for (int i = 0; i < knapsack.getListOfItems().size(); i++) {
			desc += "\n" + knapsack.getListOfItems().get(i).getName() + " "
					+ knapsack.getListOfItems().get(i).getWeight() + " " + knapsack.getListOfItems().get(i).getItemValue();
		}

		return desc;
	}

	/**
	 * Method that solves the problem.
	 */
	public void solve() {
		while (listOfSortedItems.size() > 0) {
			if (knapsack.getMaxCapacity() > knapsack.getCurrentCapacity() + listOfSortedItems.get(0).getWeight()) {
				knapsack.setCurrentCapacity(knapsack.getCurrentCapacity() + listOfSortedItems.get(0).getWeight());
				knapsack.setCurrentValue(knapsack.getCurrentValue() + listOfSortedItems.get(0).getItemValue());
				knapsack.getListOfItems().add(listOfSortedItems.get(0));
			}
			listOfSortedItems.remove(0);
		}
	}

	private void sort() {
		while (listOfItems.size() > 0) {
			int itemIndex = 0;
			double itemValueToWeight = 0;
			for (int i = 0; i < listOfItems.size(); i++) {
				if (itemValueToWeight < listOfItems.get(i).getItemValue() / listOfItems.get(i).getWeight()) {
					itemValueToWeight = listOfItems.get(i).getItemValue() / listOfItems.get(i).getWeight();
					itemIndex = i;
				}
			}
			listOfSortedItems.add(listOfItems.get(itemIndex));
			listOfItems.remove(itemIndex);
		}
	}
}
