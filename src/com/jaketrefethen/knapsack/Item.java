package com.jaketrefethen.knapsack;

import java.util.ArrayList;
import java.util.Random;

public class Item {
	
	public int weight;
	public int value;
	
	public Item(int w, int v) {
		weight = w;
		value = v;
	}
	
	public static ArrayList<Item> generateRandomItemList(int size, int maxWeight, int maxValue) {
		Random r = new Random();
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < size; i++) {
			list.add(new Item(r.nextInt(maxWeight)+1,r.nextInt(maxValue)+1));
		}
		return list;
	}

}
