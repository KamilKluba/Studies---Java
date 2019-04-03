package javazaawansowana.lab1;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class designed to solve Knapsack Problem. It contains only one method (except
 * of constructor) that lets you choose one of three algorithms to solve
 * instance of Knapsack Problem you put into the class.
 * 
 * @author Kamil Kluba 226016
 * @see Knapsack
 * @see Item
 * @see GreedyAlgorithm
 * @see RandomAlgorithm
 * @see BruteForceAlgorithm
 * 
 *
 */
public class KnapsackProblem {
	private static Scanner scanner;
	/*
	 * List of items that may be packed to knapsack.
	 */
	private ArrayList<Item> listOfItems;
	/*
	 * Capacity of knapsack.
	 */
	private int capacity;
	private static ArrayList<String> listOfAlgorithms;
	private String result;

	/**
	 * Constructor contains two parameters that are necessary to solve an instance
	 * of Knapsack Problem.
	 * 
	 * @param capacity    Capacity of knapsack.
	 * @param listOfItems List of items that may be packed into knapsack.
	 */
	public KnapsackProblem(int capacity, ArrayList<Item> listOfItems) {
		this.capacity = capacity;
		this.listOfItems = listOfItems;
	}

	/**
	 * This method lets you choose algorithm you want to use for your instance of
	 * Knapsack Problem.
	 * 
	 * @param algorithm   Chooses an algorithm to use. There are three possible
	 *                    options of using it: 1 - chooses greedy algorithm 2 -
	 *                    chooses random algorithm any Integer else - chooses brute
	 *                    force algorithm.
	 * @param repetitions This parameter only matters when you use random algorithm.
	 *                    It describes number of repetitions that algorithm makes to
	 *                    find the best solutions. In two other cases this parameter
	 *                    does nothing.
	 */
	public void chooseAlgorithmToRun(String algorithm, int repetitions) {
		if (algorithm.equals("Algorytm zach�anny")) {
			GreedyAlgorithm ga = new GreedyAlgorithm(listOfItems, capacity);
			result = ga.description();
		} else if (algorithm.equals("Algorytm losowy")) {
			RandomAlgorithm ra = new RandomAlgorithm(listOfItems, capacity, repetitions);
			result = ra.description();
		} else {
			BruteForceAlgorithm bfa = new BruteForceAlgorithm(listOfItems, capacity);
			result = bfa.description();
		}
	}

	/**
	 * This method returns list of implemented algorithms that solves Knapsack problem
	 * @return list of possible algorithms
	 */
	public static ArrayList<String> getListOfAlgorithms() {
		listOfAlgorithms = new ArrayList<String>();
		listOfAlgorithms.add("Algorytm zach�anny");
		listOfAlgorithms.add("Algorytm losowy");
		listOfAlgorithms.add("Przegl�d zupe�ny");
		return listOfAlgorithms;
	}
	
	/**
	 * This method returns String that contains solution
	 * @return solution of the Knapsack problem 
	 */
	public String getResult() {
		return result;
	}

	public static void main(String args[]){

	}
	
}
