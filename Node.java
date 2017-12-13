

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<N, E extends Comparable<E>> {

	private N label;
	
	/** 
	 * This list contains all edges that this 
	 * node is the beginning of 
	 */
	private List<Edge<N, E>> neighbors;
	
	/**
	 * Constructs a new node
	 * 
	 * @param label The label of the node to be constructed
	 * @effects Constructs a Node with label label
	 */
	public Node(N label) {
		this.label = label;
		this.neighbors = new ArrayList<Edge<N, E>>();
		checkRep();
	}
	
	/**
     * Checks that the representation invariant holds (if any).
     **/
	private void checkRep() {
		assert (neighbors != null) : "neighbors cannot be null";
		
		assert (label != null) : "Nodes must have a label";
		
		for (Edge<N, E> edge : neighbors) {
			assert (edge != null) : "cannot contain null edges";
		}
	}
	
	/**
	 * Returns the label of this node
	 * 
	 * @return the label of this node
	 */
	public N label() {
		return label;
	}
	
	/**
	 * Returns the label of this node
	 * 
	 * @return the label of this node
	 */
	public N arrive() {
		return label;
	}
	
	/**
	 * Connects given edge to this node
	 * 
	 * @param edge The edge to be added to the
	 * 		  list of edges
	 * @modifies this.neighbors
	 * @effects adds edge to the list of edges
	 */
	public void connect(E label, Node<N, E> node) {
		neighbors.add(new Edge<>(label, this, node));
		checkRep();
	}
	
	/**
	 * Removes the edge to the given node if
	 * edge exists.
	 * 
	 * @param node The node to be disconnected
	 * @modifies this.neighbors
	 * @effects removes edge to node from the list
	 * 		    of edges
	 */
	public void disconnect(N to) {
		Edge<N, E> remove = findEdge(to);
		if (remove != null) neighbors.remove(remove);
	}
	
	/**
	 * Returns a list of labels of nodes that this 
	 * node is connected to in lexicographical order
	 * 
	 * @return a list of labels that this node is
	 * connected to through its edges
	 */
	public List<String> getNeighbors() {
		List<String> result = new ArrayList<String>();
		for (Edge<N, E> edge : neighbors) {
			result.add(edge.toLabel() + "(" + edge.label().toString() + ")");
		}
		Collections.sort(result);
		return result;
	}
	
	/**
	 * Returns the edge label where to is the 
	 * label of the node at which the edge ends,
	 * null if it does not exist
	 * 
	 * @param to The label of the node at which the 
	 * 		  edge ends
	 * @return the label of the edge, null if it
	 * 		   does not exist
	 */
	public E edgeLabel(N to) {
		Edge<N, E> edge = findEdge(to);
		if (edge != null) return edge.label();
		return null;
	}
	
	/**
	 * finds and returns the edge that ends at the 
	 * node labeled to
	 * 
	 * @param to The label of the node at which the 
	 * 		  edge ends
	 * @return the Edge, null if it
	 * 		   does not exist
	 */
	private Edge<N, E> findEdge(N to) {
		Edge<N, E> found = null;
		for (Edge<N, E> edge : neighbors) {
			if (edge.toLabel().equals(to)) {
				found = edge;
				break;
			}
		}
		return found;
	}

	/**
	 * Returns the amount of edges this node is 
	 * connected to
	 * 
	 * @return size of the list of edges
	 */
	public int edgeCount() {
		return neighbors.size();
	}

	/**
	 * Returns a string of labels of nodes that this 
	 * node is connected to in lexicographical order
	 * 
	 * @return a String of labels that this node is
	 * connected to through its edges
	 */
	public String neighbors() {
		List<String> sortedNeighbors = new ArrayList<String>();
		for (Edge<N, E> edge : neighbors) {
			sortedNeighbors.add(edge.toLabel().toString() + "(" + edge.label().toString() + ")");
		}
		Collections.sort(sortedNeighbors);
		String neighborNodes = "";
		boolean first = true;
		for (String label : sortedNeighbors) {
			if (!first) neighborNodes += " ";
			else first = false;
			neighborNodes += label;
		}
		return neighborNodes;
	}
	
	public List<Edge<N, E>> edges() {
		return new ArrayList<Edge<N, E>>(neighbors);
	}
}
