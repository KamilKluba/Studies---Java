package javazaawansowana.lab1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class designed to simulate knapsack for Knapsack Problem. It contains
 * maximum possible capacity, capacity that is used by items that are already in
 * backpack, value of these items and list of these items.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Item
 * @see GreedyAlgorithm
 * @see RandomAlgorithm
 * @see BruteForceAlgorithm
 */
public class Knapsack {
	/*
	 * Maximum possible capacity.
	 */
	private int maxCapacity;
	/*
	 * Capacity that is used by items in knapsack.
	 */
	private int currentCapacity;
	/*
	 * Value of items in backpack.
	 */
	private double currentValue;
	/*
	 * List of items in knapsack.
	 */
	private List<Item> listOfItems;

	/**
	 * Constructor contains only one parameter and it is maximum capacity of a
	 * knapsack. It also creates list of items that will be packed into it in the
	 * future and sets current capacity and current value to 0.
	 * 
	 * @param maxCapacity Maximum possible capacity.
	 */
	public Knapsack(int maxCapacity) {
		super();

		this.maxCapacity = maxCapacity;
		currentCapacity = 0;
		currentValue = 0;
		listOfItems = new ArrayList<Item>();
	}

	/**
	 * Returns maximum possible capacity.
	 * @return Maximum possible capacity.
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * Sets maximum possible capacity.
	 * @param maxCapacity Maximum possible capacity.
	 */
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	/**
	 * Returns capacity used by the time of using this method.
	 * @return Capacity used by the time of using this method.
	 */
	public int getCurrentCapacity() {
		return currentCapacity;
	}

	/**
	 * Sets capacity that is used by items in a knapsack to value in the parameter
	 * @param currentCapacity Capacity that is used by items in a knapsack.
	 */
	public void setCurrentCapacity(int currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	/**
	 * Returns value of items in a knapsack.
	 * @return Value of items in a knapsack.
	 */
	public double getCurrentValue() {
		return currentValue;
	}

	/**
	 * Sets value of items in a knapsack to value in the parameter
	 * @param currentValue Value of items in a knapsack.
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * Returns list of items in knapsack.
	 * @return List of items in knapsack.
	 */
	public List<Item> getListOfItems() {
		return listOfItems;
	}

	/**
	 * Sets list of items in a knapsack
	 * @param listOfItems List of items in a knapsack.
	 */
	public void setListOfItems(List<Item> listOfItems) {
		this.listOfItems = listOfItems;
	}
}
