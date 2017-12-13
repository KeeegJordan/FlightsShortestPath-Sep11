

import java.util.*;

/**
 * <b>Graph</b> represents a system of nodes and edges.
 * Each edge contains a label and all nodes contain data.
 * Each edge has a direction and can have cycles and edges
 * to themselves.
 * <p>
 *
 * Example Graph: 4 nodes, A, B, C, D. Edges include (A to B, 1),
 * (B to D, 2), (A to D, 3), and (D to A, 4). C is unreachable 
 * because no nodes have edges to it. There is a cycle in the 
 * path A to D to A 
 */
public class Graph<N, E extends Comparable<E>> {

	/** Holds all the Nodes in this List */
	private Map<N, Node<N, E>> nodes;
	
	/**
     * @effects Constructs a new Graph with no nodes.
     */
	public Graph() {
		this.nodes = new HashMap<N, Node<N, E>>();
		checkRep();
	}
	
	/**
     * Checks that the representation invariant holds (if any).
     **/
	private void checkRep() {
		assert (nodes != null);
	    
	    for (N node : nodes.keySet()) {
	    		Node<N, E> currentNode = nodes.get(node);
			assert (currentNode != null) : "graph cannot contain null nodes";
	        assert (currentNode.label() != null) : "graph cannot contain null labeled nodes";
	    }
	}
	
	/**
	 * Add node element to graph
	 * 
	 * @param label The label of the Node to be added to the graph
	 * @modifies this.nodes
	 * @effects adds n to nodes
	 */
	public void addNode(N label) {
		if (!containsNode(label))
			this.nodes.put(label, new Node<N, E>(label));
		checkRep();
	}
	
	/**
	 * Remove node element from graph
	 * 
	 * @param n The label of the Node to be removed from the graph
	 * @modifies this.nodes
	 * @effects removes n from nodes
	 */
	public void removeNode(N label) {
		if (containsNode(label))
			this.nodes.remove(label);
	}
	
	/**
	 * Creates an edge between two nodes
	 * 
	 * @param from The label of the node from where the edge starts
	 * @param to The label of the node where the edge ends
	 * @param label The label of the edge
	 * @modifies nodes.get(from).neighbors
	 * @effects adds a child to the node specified in the
	 * 			from parameter with the label, label
	 */
	public void connectNodes(N from, N to, E label) {
		if (!nodes.containsKey(from) || !nodes.containsKey(to)) return;
		Node<N, E> toNode = nodes.get(to);
		nodes.get(from).connect(label, toNode);
	}
	
	/**
	 * Removes an edge between two nodes
	 * 
	 * @param from The node whose edge to remove
	 * @param to The node to which the edge leads
	 * @modifies nodes.get(from).neighbors
	 * @effects removes edge going from from to
	 *          to
	 */
	public void disconnectNodes(N from, N to) {
		if (!nodes.containsKey(from) || !nodes.containsKey(to)) return;
		nodes.get(from).disconnect(to);
	}

	/**
	 * Returns true if this graph contains a node with
	 * the specified label
	 * 
	 * @param label The label whose node presence is to be tested
	 * @return true if this graph contains node with label
	 */
	public boolean containsNode(N label) {
		return nodes.containsKey(label);
	}

	/**
	 * Returns true if the edge exists from the from to the
	 * to node
	 * 
	 * @param from The node where the edge begins
	 * @param to The node where the edge ends
	 * @return true if the edge exists in the graph
	 */
	public boolean areNeighbors(N from, N to) {
		if (!nodes.containsKey(from) || !nodes.containsKey(to)) 
			return false;
		Node<N, E> fromNode = nodes.get(from);
		return fromNode.neighbors().contains(to.toString());
	}
	
	/**
	 * Returns a list of neighbors of the from node
	 * in lexicographical order based on label
	 * 
	 * @param from The node whose neighbors to get
	 * @return List of the labels of nodes that from
	 * has an edge to
	 */
	public List<String> getNeighbors(N from) {
		Node<N, E> fromNode = nodes.get(from);
		return fromNode.getNeighbors();
	}
	
	/**
	 * Returns the edge label if the edge exists, null otherwise
	 * 
	 * @param from The node where the edge begins
	 * @param to The node where the edge ends
	 * @return the label of the edge if exists in graph,
	 * 		   null otherwise
	 */
	public E getEdgeLabel(N from, N to) {
		if (!nodes.containsKey(from) || !nodes.containsKey(to)) 
			return null;
		Node<N, E> fromNode = nodes.get(from);
		return fromNode.edgeLabel(to);
	}
	
	/**
	 * Returns the amount of nodes in the graph
	 * 
	 * @return the number of nodes in the graph
	 */
	public int size() {
		return nodes.size();
	}
	
	/**
	 * Returns the amount of edges in the graph
	 * 
	 * @return the number of edges in the graph
	 */
	public int edgeCount() {
		int sum = 0;
		for (N node : nodes.keySet()) {
			sum += nodes.get(node).edgeCount();
		}
		return sum;
	}

	/**
	 * Returns the nodes in the graph represented
	 * as a String
	 * 
	 * @return the nodes in the graph represented
	 * as a String
	 */
	public String nodes() {
		String nodeString = "";
		boolean first = true;
		for (N node : nodes.keySet()) {
			if (!first) nodeString += " ";
			else first = false;
			nodeString += node.toString();
		}
		return nodeString;
	}

	/**
	 * Returns the neighbors of the given node 
	 * in the graph represented as a String
	 * 
	 * @param parentName The label of the node
	 * whose neighbors are to be returned as
	 * a String
	 * @return the neighbors of the given node 
	 * in the graph represented s a String
	 */
	public String neighbors(N label) {
		Node<N, E> node = nodes.get(label);
		return node.neighbors();
	}

	public Node<N, E> getNode(N from) {
		if (!nodes.containsKey(from)) return null;
		return nodes.get(from);
	}
	
	
	
}