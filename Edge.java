

public class Edge<N, E extends Comparable<E>> {

	private E label;
	private Node<N, E> from;
	private Node<N, E> to;
	
	/**
	 * Constructs a new edge
	 * 
	 * @param label The label of the edge
	 * @param from The node where this edge begins
	 * @param to The node where this edge ends
	 * @effects Constructs a new edge from Node from to Node to
	 */
	public Edge(E label, Node<N, E> from, Node<N, E> to) {
		this.label = label;
		this.from = from;
		this.to = to;
		checkRep();
	}
	
	/**
     * Checks that the representation invariant holds (if any).
     **/
	private void checkRep() {
		assert (label != null) : "Nodes must have a label";
		
		assert (from != null && to != null) : "Cannot connect to null Nodes";
	}
	
	/**
	 * Returns the label of this edge
	 * 
	 * @return the label of this edge
	 */
	public E label() {
		return this.label;
	}
	
	/**
	 * Returns the label of the node where
	 * this edge begins 
	 * 
	 * @return the label of the node where
	 * this edge begins
	 */
	public N fromLabel() {
		return from.label();
	}
	
	/**
	 * Returns the label of the node where
	 * this edge ends 
	 * 
	 * @return the label of the node where
	 * this edge ends
	 */
	public N toLabel() {
		return to.label();
	}
}
