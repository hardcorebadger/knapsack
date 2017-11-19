package com.jaketrefethen.knapsack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Knapsack {
	
	private HashMap<Item,Boolean> items;
	private Problem problem;
	
	public Knapsack(Problem p) {
		problem = p;
		generateRandomKnapsack(p.items);
	}

	public Knapsack(Problem p, HashMap<Item,Boolean> chromosome) {
		problem = p;
		items = chromosome;
	}
	
	private void generateRandomKnapsack(ArrayList<Item> itemList) {
		items = new HashMap<Item,Boolean>();
		Random r = new Random();
		for (Item i : itemList) {
			items.put(i, r.nextBoolean());
		}
	}
	
	public int fitness() {
		return fitnessConvergenceFixed();
	}
	
	public int fitnessFlatFail() {
		// this is the fitness function from the assignment
		int weight = 0;
		int value = 0;
		for (Item i : items.keySet()) {
			if (items.get(i)) {
				weight += i.weight;
				value += i.value;
			}
		}
		if (weight > problem.maxWeight)
			return 0;
		else
			return value;
	}
	
	public int fitnessOverweightPenalty() {
		// this penalizes sacks that are overweight
		// in proportion to how overweight the sack is
		// in the form of a negative value that grows with overweight-ness
		int weight = 0;
		int value = 0;
		for (Item i : items.keySet()) {
			if (items.get(i)) {
				weight += i.weight;
				value += i.value;
			}
		}
		if (weight > problem.maxWeight)
			return problem.maxWeight - weight;
		else
			return value;
	}
	
	public int fitnessConvergence() {
		// this one is interesting, it allows for over-filled sacks
		// the idea is that if you are over by just a bit, then
		// you are more fit than a sack that is barely filled at all
		// issue is it can spit out actually wrong answers
		// but it generates technically closer answers faster
		int weight = 0;
		int value = 0;
		for (Item i : items.keySet()) {
			if (items.get(i)) {
				weight += i.weight;
				value += i.value;
			}
		}
		if (problem.maxWeight == weight)
			return value;
		return value / Math.abs(problem.maxWeight - weight)+1;
	}
	
	public int fitnessConvergenceFixed() {
		// this is an attempt to keep the goods
		// of convergence and also fix the overweight
		// allowance
		int weight = 0;
		int value = 0;
		for (Item i : items.keySet()) {
			if (items.get(i)) {
				weight += i.weight;
				value += i.value;
			}
		}

		if (weight < problem.maxWeight)
			return value / Math.abs(problem.maxWeight - weight)+1;
		if (weight > problem.maxWeight)
			return value / Math.abs(problem.maxWeight - weight)+4;
		else
			return value;
	}
	
	public void print() {
		int weight = 0;
		int value = 0;
		System.out.println("====== SOLUTION ======");
		System.out.print("{");
		for (Item i : items.keySet()) {
			if (items.get(i)) {
				weight += i.weight;
				value += i.value;
				System.out.print("[w:" + i.weight + ", v:" + i.value + "] ");
			}
		}
		System.out.println("}");
		System.out.println("weight: " + weight + " / " + problem.maxWeight);
		System.out.println("value: " + value);
		System.out.println("fitness: " + fitness());
		System.out.println("======================");
	}
	
	public static Knapsack breed(Knapsack k1, Knapsack k2, int mutationPercent) {
		HashMap<Item,Boolean> chromosome = new HashMap<Item,Boolean>();
		Random r = new Random();
		int crossover = r.nextInt(k1.problem.items.size());
		int j = 0;
		
		for (Item i : k1.items.keySet()) {
			if (j < crossover)
				chromosome.put(i, k1.items.get(i));
			else
				chromosome.put(i, k2.items.get(i));
			j++;
		}
		
		Item mutationGene = k1.problem.items.get(r.nextInt(k1.problem.items.size()));
		if (r.nextInt(100) < mutationPercent)
			chromosome.put(mutationGene, !chromosome.get(mutationGene));
		
		return new Knapsack(k1.problem, chromosome);
	}

}
