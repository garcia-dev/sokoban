package model.solver;

import model.general.Board;
import model.general.Case;
import model.general.State;

import java.util.ArrayList;

/**
 * Node's class
 * <p>
 *     A Node represents a node from the grid modelized by the AI. It is affiliated to the Case a the same coordinates.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 * @see Case
 * @see Board
 */
public class Node {
	private final Case aCase;
	private final Board board;
	private final ArrayList<Node> neighbors;

	private Node parentNode;

	private int distanceFromStartNode = 0;

	Node(Case aCase) {
		this.aCase = aCase;

		neighbors = new ArrayList<>();

		board = aCase.getBoard();
	}

	public int calcDistanceFromNode(Node node) {
		int[] goalNodeCoord = node.getCase().getCoord();

		return Math.abs(goalNodeCoord[0] - aCase.getCoord()[0]) + Math.abs(goalNodeCoord[1] - aCase.getCoord()[1]);
	}

	public int getDistanceFromStartNode() {
		return distanceFromStartNode;
	}

	public Case getCase() {
		return aCase;
	}

	Node getParentNode() {
		return parentNode;
	}

	ArrayList<Node> getNeighbors() {
		return neighbors;
	}

	public void setDistanceFromStartNode() {
		this.distanceFromStartNode += parentNode.getDistanceFromStartNode() + this.calcDistanceFromNode(parentNode);
	}

	void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

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

	boolean isCorner() {
		Case[][] caseArray = board.getLevel().getCaseArray();

		if (caseArray[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getState() == State.WALL || caseArray[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getState() == State.WALL)
			return caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getState() == State.WALL || caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getState() == State.WALL;

		if (caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].getState() == State.WALL || caseArray[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].getState() == State.WALL)
			return caseArray[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].getState() == State.WALL || caseArray[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].getState() == State.WALL;

		return false;
	}

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
