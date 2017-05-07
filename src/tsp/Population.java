/*
* Population.java
* Manages a population of candidate tours
*/

package tsp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tsp.TSP_GA.*;
import tsp.TSP_GA.Tour;

public class Population {
	int startX = 1;
	int startY = 1;
	int goalX = 1;
	int goalY = 9;
	//int c = 1;
    // Holds population of tours
    Tour[] tours;
    // Construct a population
	public Population(int populationSize, boolean initialise) {
        tours = new Tour[populationSize];
        
        // If we need to initialise a population of tours do so
        if (initialise) {
        	if (initialise){
        		for(int i = 0; i < populationSize(); i++){
        			Tour newRoute = new Tour();
        			newRoute.grabNodes(new Node(1, 1));
        			saveTour(i, newRoute);
        			System.out.println(newRoute);
        		}
        	}
        }
    }
    
	protected boolean goal(Node node){
		return (node.x == goalX) && (node.y == goalY);
	}
	
    // Saves a tour
    public void saveTour(int index, Tour tour) {
    	System.out.println(tour);
        tours[index] = tour;
    }
    
    // Gets a tour from population
    public Tour getTour(int index) {
        return tours[index];
    }

    // Gets the best tour in the population
    public Tour getFittest() {
        Tour fittest = tours[0];
        
        // Loop through individuals to find fittest
        for (int i = 1; i < populationSize(); i++) {
        	//System.out.println("Fittest: " + fittest);
            /*if (fittest.getFitness() <= getTour(i).getFitness()) {
                fittest = getTour(i);
            }*/
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return tours.length;
    }
}