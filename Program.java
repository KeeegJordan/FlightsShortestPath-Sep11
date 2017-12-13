import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Program {
	/**
	 * A class that allows clients to interact with loading data
	 * into a graph and finding paths in between nodes.
	 * 
	 * @param args unused
	 */
    public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
	
		Set<String> cities = new HashSet<String>();
		Map<String, Map<String, Integer>> time = new HashMap<String, Map<String, Integer>>();
		Graph<String, Integer> graph = new Graph<>();
		
		parseData("src/results.csv", cities, time);
		
		buildGraph(graph, cities, time);
		
		System.out.println("Enter Airport FAA code: '<from> <to>'");
		System.out.println("for shortest path");
		String twoCharacters = s.nextLine();
		
		while (!twoCharacters.equals("exit")) {
			
			String from = twoCharacters.split(" ")[0];
			String to = twoCharacters.split(" ")[1];
			
			if (!graph.containsNode(from)) {
				twoCharacters = s.nextLine();
				continue;
			}
			
			if (!graph.containsNode(to)) {
				twoCharacters = s.nextLine();
				continue;
			}
			
			Path<String> path = findPath(graph, from, to);
			if (path == null) {
				System.out.println("no path found");
			} else {
				for (Edge<String, Integer> edge : path.edges()) {
					System.out.println(edge.fromLabel() + " to " + 
						edge.toLabel() + " time: " + edge.label());
				}
			}
			twoCharacters = s.nextLine();
		}
		
		s.close();
	} 	
			

	private static void parseData(String filename, Set<String> cities, Map<String, Map<String, Integer>> time) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));
			br.readLine();

            String inputLine;
            while ((inputLine = br.readLine()) != null) {
            		String[] tokens = inputLine.split(",");
            		if (!tokens[4].equals("\"\"")) {
	            		int timestamp = Integer.parseInt(tokens[0]) * 10000 + Integer.parseInt(tokens[4]);
	            		String orig = tokens[1];
	            		String dest = tokens[2];
	            		cities.add(orig);
	            		cities.add(dest);
	            		
	            		if (!time.containsKey(orig))
	            			time.put(orig, new HashMap<String, Integer>());
            			if (!time.get(orig).containsKey(dest))
            				time.get(orig).put(dest, timestamp);
	            		
            		}
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	/**
     * Creates nodes and edges from characters and books in the given graph
     * 
     * @requires marvelGraph & characters & books != null 
     * @param graph Graph to add nodes and edges to
     * @param cities list in which all character names are stored;
     *          These become the nodes of the graph
     * @param time map from titles of comic books to characters that
     *          appear in them; These become the edges of the graph
     * @modifies marvelGraph
     * @effects fills graph with nodes from characters
     * @effects fills graph with edges from books
     */
	public static void buildGraph(Graph<String, Integer> graph, Set<String> cities, Map<String, Map<String, Integer>> time) {
		for (String city : cities) {
			graph.addNode(city);
		}
		
		for (String city : time.keySet()) {
			Map<String, Integer> characterMap = time.get(city);
			
			for (String neighbor : characterMap.keySet()) {
				int timestamp = characterMap.get(neighbor);
				graph.connectNodes(city, neighbor, timestamp);
			}
		}
	}
	
	/**
	 * Finds the shortest path between two nodes starting with from
	 * and ending with to. Returns a list of neighbors and their edges
	 * in the format: neighbor_label(edge_label). Returns null if no
	 * path exists
	 * 
	 * @param graph the graph to search through
	 * @param from the label of the starting node
	 * @param to the label of the destination node
	 * @return a list of neighbors and edges that lead connect node, from
	 * to node, to. null if no path exists.
	 */
	public static Path<String> findPath(Graph<String, Integer> graph, String from, String to) {		
		Queue<Path<String>> active = new PriorityQueue<>();
		Set<String> finished = new HashSet<String>();
		
		
		active.add(new Path<>(from, from, null));
		
		while (!active.isEmpty()) {
			Path<String> minPath = active.remove();
			String dest = minPath.destinationLabel();
			
			if (dest.equals(to))
				return minPath;
			
			if (finished.contains(dest))
				continue;
			
			finished.add(dest);
			
			List<Edge<String, Integer>> neighbors = graph.getNode(dest).edges();
			
			for (Edge<String, Integer> neighbor : neighbors) {
				
				if (!finished.contains(neighbor.toLabel())) {
					List<Edge<String, Integer>> newPathList = minPath.edges();
					if (newPathList.isEmpty() || 
							newPathList.get(newPathList.size() - 1).label() < neighbor.label()) {
						newPathList.add(neighbor);
						Path<String> newPath = new Path<>(from, neighbor.toLabel(), newPathList);
						active.add(newPath);
					}
				}
				
			}
			
		}
		
		return null;
	}
	
}
