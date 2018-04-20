package model.solver;

import model.general.Board;
import model.general.Case;
import model.general.State;

import java.util.ArrayList;

/**
 * Node's class
 * <p>
 *     A Node represents a node from the grid modeled by the AI. It is affiliated to the Case a the same coordinates.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 * @see Case
 * @see Board
 */
class Node {
	private final Case aCase;
	private final Board board;
	private final ArrayList<Node> neighbors;

	private Node parentNode;

	/**
	 * Node's constructor initializing the Case affiliated.
	 *
	 * @param aCase the Case affiliated to the node
	 */
	Node(Case aCase) {
		this.aCase = aCase;

		neighbors = new ArrayList<>();

		board = aCase.getBoard();
	}

	/**
	 * Method that calculate the distance from currentNode to the parameter Node.
	 *
	 * @param node the node to calculate the distance to
	 * @return the distance between the two nodes
	 */
	int calcDistanceFromNode(Node node) {
		int[] goalNodeCoord = node.getCase().getCoord();

		return Math.abs(goalNodeCoord[0] - aCase.getCoord()[0]) + Math.abs(goalNodeCoord[1] - aCase.getCoord()[1]);
	}

	/**
	 * Getter of the Case affiliated to the Node.
	 *
	 * @return the Case affiliated to the Node
	 */
	public Case getCase() {
		return aCase;
	}

	/**
	 * Getter of the ParentNode Node.
	 *
	 * @return the ParentNode Node
	 */
	Node getParentNode() {
		return parentNode;
	}

	/**
	 * Getter of the neighbors list.
	 *
	 * @return an ArrayList of the Node neighbors
	 */
	ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	/**
	 * Setter of the ParentNode Node.
	 *
	 * @param parentNode the ParentNode
	 */
	void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * Setter of the Node neighbors.
	 *
	 * @param nodes the 2D array of Nodes
	 */
	void setNeighbors(Node[][] nodes) {
		if (nodes != null) {
			if (aCase.getCoord()[0] - 1 >= 0 && nodes[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getCase().getState() != State.WALL)
				neighbors.add(nodes[aCase.getCoord()[0] - 1][aCase.getCoord()[1]]);

			if (aCase.getCoord()[0] + 1 < nodes.length && nodes[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getCase().getState() != State.WALL)
				neighbors.add(nodes[aCase.getCoord()[0] + 1][aCase.getCoord()[1]]);

			if (aCase.getCoord()[1] - 1 >= 0 && nodes[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getCase().getState() != State.WALL)
				neighbors.add(nodes[aCase.getCoord()[0]][aCase.getCoord()[1] - 1]);

			if (aCase.getCoord()[1] + 1 < nodes[aCase.getCoord()[0]].length && nodes[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getCase().getState() != State.WALL)
				neighbors.add(nodes[aCase.getCoord()[0]][aCase.getCoord()[1] + 1]);
		} else
			neighbors.clear();
	}

	/**
	 * Method checking if the node is in a corner.
	 *
	 * @return true if the node is in a corner, false if it isn't
	 */
	boolean isCorner() {
		Case[][] caseArray = board.getLevel().getCaseArray();

		if (caseArray[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getState() == State.WALL || caseArray[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getState() == State.WALL)
			return caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getState() == State.WALL || caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getState() == State.WALL;

		if (caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getState() == State.WALL || caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getState() == State.WALL)
			return caseArray[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getState() == State.WALL || caseArray[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getState() == State.WALL;

		return false;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		if (parentNode != null)
			return "Node{" + System.identityHashCode(this) + " ; Coordinates : " + aCase.getCoord()[0] + " - " +
					aCase.getCoord()[1] + " ; parentNode Coordinates : (" +
					parentNode.getCase().getCoord()[0] + ";" + parentNode.getCase().getCoord()[1] + ") }";
		else
			return "Node{" + System.identityHashCode(this) + " ; Coordinates : " + aCase.getCoord()[0] + " - " +
					aCase.getCoord()[1] + " }";
	}
}
