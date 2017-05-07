/*
 * Node.java
 * Create a tour and evolve a solution
 */

package tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TSP_GA {
	private static ArrayList<Node> destinationCities = new ArrayList<Node>();
	static ArrayList<String> checked = new ArrayList<String>();
	public static int goalX = 14;
	public static int goalY = 14;
	private static 	int [][] map = new int[][]{
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1},
		{1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
	};
	// Constructs a randomly placed city
	public static class Node{
		public int x, y;
		Node(int x, int y){
			this.x = x;
			this.y = y;
		}
		public String toString(){
			return "(" + x + ", " + y + ")";
		}
		public Double distanceTo(Node city) {
			return new Double(Math.sqrt((Math.abs(x - city.x) + Math.abs(y - city.y))));
		}
	}
	public static class Tour{
		public Node point;
		public Double f;
		public Double g;
		static ArrayList<Node> tour = new ArrayList<Node>();
		private static double fitness = 0;
		private static int distance = 0;
		public Tour parent;
		/*public Tour(){
			parent = null;
			point = null;
			g = 0.0;
			f = 0.0;
		}*/
		public Tour(Tour p){
			parent = p;
			g = p.g;
			f = p.f;
		}
	    public Tour(){
	        for (int i = 0; i < numberOfCities(); i++) {
	            tour.add(null);
	        }
	    }
	    
	    @SuppressWarnings("static-access")
		public Tour(ArrayList<Node> tour){
	        this.tour = tour;
	    }
		public Node getPoint(){
			return point;
		}	
		public void setPoint(Node point){
			this.point = point;
		}
		public int compareTo(Object o){
			Tour p = (Tour)o;
			if(f - p.f < 0)
				return -1;
			else if (f - p.f == 0)
				return 0;
			else if (f - p.f > 0)
				return 1;
			return 0;
		}
		public int getDistance(){
			if (distance == 0) {
				int tourDistance = 0;
				// Loop through our tour's cities
				for (int cityIndex=0; cityIndex < tour.size(); cityIndex++) {
					// Get city we're travelling from
					Node fromCity = getCity(cityIndex);
					// Node we're travelling to
					Node destinationCity;
					// Check we're not on our tour's last city, if we are set our 
					// tour's final destination city to our starting city
					if(cityIndex+1 < tour.size()){
						destinationCity = getCity(cityIndex+1);
					}
					else{
						destinationCity = getCity(0);
					}
					// Get the distance between the two cities
					//tourDistance += (fromCity).distanceTo(destinationCity);
					tourDistance += heuristic(fromCity, destinationCity);
				}
				distance = tourDistance;
			}
			return distance;
		}
	    public void generateIndividual() {
	        // Loop through all our destination cities and add them to our tour
	        for (int cityIndex = 0; cityIndex < numberOfCities(); cityIndex++) {
	          setCity(cityIndex, getCity(cityIndex));
	        }
	        // Randomly reorder the tour
	        //Collections.shuffle(tour);
	    }
	    public Node getCity(int tourPosition) {
	        return (Node)tour.get(tourPosition);
	    }
	    public static void setCity(int tourPosition, Node node) {
	        tour.set(tourPosition, node);
	        // If the tours been altered we need to reset the fitness and distance
	        fitness = 0;
	        distance = 0;
	    }
	    public boolean containsCity(Node city){
	        return tour.contains(city);
	    }
	    public static double getFitness(List<Node> finalTour) {
	        fitness = finalTour.size();
	        return fitness;
	    }
	    @Override
	    public String toString() {
	        String geneString = "|";
	        for (int i = 0; i < tour.size(); i++) {
	            geneString += getCity(i)+"|";
	        }
	        return geneString;
	    }
	    protected /*List<Node>*/ void grabNodes(Node node){
			//List<Node> destinationCities = new LinkedList<Node>();
			int x = node.x;
			int y = node.y;	
			for(String s: checked){
				if(s.equals("(" + x + ", " + y + ") ")){
					//return destinationCities;
				}
			}
			if(y > 0)
				if(map[y-1][x] == 0) //N
					tour.add(new Node(x, y-1));
			if(y < map.length - 1)
				if(map[y+1][x] == 0) //S
					tour.add(new Node(x, y+1));
			if(x < map.length - 1)
				if(map[y][x+1] == 0) //E
					tour.add(new Node(x+1, y));

			if(x > 0){
				if(map[y][x-1] == 0) //W
					tour.add(new Node(x-1, y));
				if(y < map.length - 1 && map[y+1][x-1] == 0) //SW
					tour.add(new Node(x-1, y+1));
			}
			if(y > 0){
				if(x < map.length - 1 && map[y-1][x+1] == 0) //NE
					tour.add(new Node(x+1, y-1));
				if(x > 0 && map[y-1][x-1] == 0) //NW
					tour.add(new Node(x-1, y-1));
			}
			if(y < map.length - 1)
				if(x < map.length - 1)
					if(map[y+1][x+1] == 0) //SE
						tour.add(new Node(x+1, y+1));
			checked.add("(" + x + ", " + y + ") ");
			//return destinationCities;
		}
	}
	public int x;
	public int y;
	public TSP_GA(int[][] map){
		TSP_GA.map = map;
	}
	protected static Double heuristic(Node from, Node to){
		return new Double((Math.abs(from.x - to.x) + Math.abs(from.y - to.y)) + 
				(1.5 - 2 * 1) * Math.min(Math.abs(from.x - to.x), Math.abs(from.y - to.y)));
	}
	// Get the number of destination cities
	public static int numberOfCities(){
		return destinationCities.size();
	}


	public static void main(String[] args) {
		/*for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map.length; x++)
				if(map[y][x] == 0)
					destinationCities.add(new Node(x, y));
		}
		for(Node n : destinationCities){
			System.out.println(n);
		}*/
		long begin = System.currentTimeMillis();	
		// Initialise population
		Population pop = new Population(1, true);
		System.out.println("Initial distance: " + pop.getFittest());

		pop = GA.evolvePopulation(pop);
		for (int i = 0; i < 1000; i++) {
			pop = GA.evolvePopulation(pop);
		}

		// Print final results
		/*System.out.println("Finished");
		long end = System.currentTimeMillis();	
		System.out.println("Time = " + (end - begin) + "ms" );
		System.out.println("Final distance: " + pop.getFittest().getDistance());
		System.out.println("Solution:");
		System.out.println(pop.getFittest());*/
	}
}