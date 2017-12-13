

import java.util.ArrayList;
import java.util.List;

/**
 * The path object keeps track of a path from 
 * start to destination. It holds a list of edges
 * to get from the start to the destination and
 * has a total length of the path. 
 * @author keegan jordan
 *
 */
public class Path<T> implements Comparable<Path<T>>{
	List<Edge<T, Integer>> path;
	T start;
	T destination;
	Integer length = 0;
	Integer time;

	// AF(this) = A collection of edges from 'start' to 'destination
	//			  with start = destination if edge is empty
	//			  start(*edge label*) -> ... -> destination(*edge label*) otherwise
	// Representation Invariant = path, start, destination != null, length > 0
	// 		for each edge in path, edge != null and edge.length >= 0.0
	//		start equals the beginning label of the first edge in path
	//		destination equals the ending label of the last edge in path
	//		start = destination if no edges in path and length = 0
	
	/**
	 * Constructs a new Path
	 * 
	 * @param start The label of the starting node
	 * @param destination The label of the starting node
	 * @param edges The list of edges that make up this path
	 * @effects constructs a new Path
	 */
	public Path(T start, T destination, List<Edge<T, Integer>> edges) {
		this.start = start;
		this.destination = destination;
		this.path = new ArrayList<>();
		if (edges != null) {
			for (Edge<T, Integer> edge : edges) {
				addEdge(edge);
			}
		}
		checkRep();
	}
	
	/**
	 * Constructs a new Path
	 * 
	 * @param start The label of the starting node
	 * @param destination The label of the starting node
	 * @effects constructs a new Path
	 */
	public Path(T start, T destination) {
		this(start, destination, null);
	}
	
	/**
	 * Adds an edge to the path
	 * 
	 * @param edge The edge to be added to the path
	 * @modifies path, length
	 * @effects Increments length by length of new edge
	 * 			and adds edge to path list
	 */
	private void addEdge(Edge<T, Integer> edge) {
		path.add(edge);
		length += edge.label();
		time = edge.label();
	}

	/**
	 * Returns the result of comparing two paths
	 * based only on total length. 0 if the lenghts
	 * are the same, 1 if this is greater than the 
	 * other, and -1 if this is less than the other
	 * 
	 * @param other The path to compare to
	 */
	@Override
	public int compareTo(Path<T> other) {
		Integer compare = this.length - other.length;
		if (compare > 0) return 1;
		if (compare == 0) return 0;
		return -1;
	}

	/**
	 * Returns the label of the destination node 
	 * 
	 * @return destination
	 */
	public T destinationLabel() {
		return destination;
	}
	
	/**
	 * Returns the length of the path 
	 * 
	 * @return length
	 */
	public Integer length() {
		return length;
	}

	/**
	 * Returns the length of the path 
	 * 
	 * @return length
	 */
	public Integer time() {
		return time;
	}
	
	/**
	 * Returns a copy of the edge that 
	 * make up this path
	 * 
	 * @return path The list of edges
	 */
	public List<Edge<T, Integer>> edges() {
		return new ArrayList<>(path);
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
	 */
	private void checkRep() {
		assert (path != null);
		assert (start != null && destination != null && length >= 0.0);
		
		for (Edge<T, Integer> edge : path) {
			assert (edge != null) : "null edge";
			assert (!(edge.label() < 0.0)) : "negative edge weight";
		}
		if (path.size() > 0) {
			assert(start.equals(path.get(0).fromLabel())) : "path does not begin at start";
			assert(destination.equals(path.get(path.size()-1).toLabel())) : "path does not end at destination";
		} else {
			assert(start.equals(destination)) : "zero path must be self referencing";
			assert(length == 0.0) : "self referencing path must have zero length";
		}
		
	}
	
	
}