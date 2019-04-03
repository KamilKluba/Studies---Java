package javazaawansowana.lab1;

/**
 * Class designed to simulate items that can be put into knapsack in Knapsack Problem.
 * 
 * @author Kamil Kluba 226016
 * @see KnapsackProblem
 * @see Knapsack
 * @see GreedyAlgorithm
 * @see RandomAlgorithm
 * @see BruteForceAlgorithm
 */
public class Item {
	/*
	 * Name of the item.
	 */
	private String name;
	/*
	 * Weight of the item.
	 */
	private int weight;
	/*
	 * Value of the item.
	 */
	private double value;
	
	/**
	 * Constructor contains three parameters of item: name, weight and value.
	 * 
	 * @param name Name of the item.
	 * @param weight Weight of the item.
	 * @param value Value of the item.
	 */
	public Item(String name, int weight, double value) {
		super();
		this.name = name;
		this.weight = weight;
		this.value = value;
	}

	/**
	 * Returns name of the item.
	 * @return Name of the item.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets name of an item.
	 * @param name Name of the item.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns weight of the item.
	 * @return Weight of the item.
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Sets weight of the item.
	 * @param weight Weight of the item.
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**
	 * Returns value of the item.
	 * @return Value of the item.
	 */
	public double getItemValue() {
		return value;
	}
	
	/**
	 * Sets value of the item.
	 * @param value Value of the item.
	 */
	public void setItemValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Nazwa przedmiotu: " + name + ", waga: " + weight + ",warto��: " + value;
	}
	
}
