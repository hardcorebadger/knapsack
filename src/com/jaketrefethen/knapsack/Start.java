package com.jaketrefethen.knapsack;

public class Start {
	
	public static void main(String[] args) {
		Problem myProb = new Problem(20,200,100,50);
		myProb.print();
		Population myPop = new Population(myProb, 20, 60);
		myPop.getBest().print();
		myPop.iterate(100000);
		myPop.getBest().print();
	}

}
