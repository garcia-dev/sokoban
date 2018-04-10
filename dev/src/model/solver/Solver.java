package model.solver;

import controller.GameController;
import model.general.*;

import java.util.*;

/**
 * Solver's class
 * <p>
 * The Solver class represents the AI code. It is used to solve the board by itself.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 * @see Board
 * @see Node
 */
public class Solver {
	private final Board board;
	private Node[][] nodes;

	/**
	 * Solver's constructor initializing the Board.
	 *
	 * @param board the board to use for the Solver
	 */
	public Solver(Board board) {
		this.board = board;

		initNodes();
	}

	/**
	 * Method that creates a Node for each Case in the Board.
	 */
	private void initNodes() {
		nodes = new Node[board.getLevel().getCaseArray().length][];

		for (int row = 0; row < board.getLevel().getCaseArray().length; row++) {
			nodes[row] = new Node[board.getLevel().getCaseArray()[row].length];

			for (int indCase = 0; indCase < board.getLevel().getCaseArray()[row].length; indCase++)
				nodes[row][indCase] = new Node(board.getLevel().getCaseArray()[row][indCase]);
		}
	}

	/**
	 * Method that solves the board.
	 *
	 * @param gameController the gameController used in the game
	 * @param anyTime        tells if the "anyTime" mode is enabled or not
	 */
	public void solveBoard(GameController gameController, Boolean anyTime) {
		Node playerNode = findPlayerNode();

		assert playerNode != null;

		while (!(gameController.isFinished())) {
			// Selection of the nearest crate from the player node and the nearest target from that crate
			Node nearestCrate = findNearest(playerNode, Type.CRATE, null);
			Node nearestTargetFromCrate = findNearest(nearestCrate, null, State.TARGET);

			// Construction of the path from the selected crate and the selected target
			List<Node> path = searchPath(nearestCrate, nearestTargetFromCrate);

			// Construction of the list of moves following the path
			assert path != null;
			ArrayList<Direction> movesFromCrateToTarget = getMoves(path);

			// For each move
			Direction currentDirection = null;
			for (Direction direction : movesFromCrateToTarget) {
				// If the direction of the movement changes, replace the player to be able to push the crate to the good direction
				if (direction != currentDirection) {
					currentDirection = direction;

					Node playerMovementNode = null;

					switch (direction) {
						case UP:
							playerMovementNode = nodes[nearestCrate.getCase().getCoord()[0] + 1][nearestCrate.getCase().getCoord()[1]];
							break;
						case DOWN:
							playerMovementNode = nodes[nearestCrate.getCase().getCoord()[0] - 1][nearestCrate.getCase().getCoord()[1]];
							break;
						case LEFT:
							playerMovementNode = nodes[nearestCrate.getCase().getCoord()[0]][nearestCrate.getCase().getCoord()[1] + 1];
							break;
						case RIGHT:
							playerMovementNode = nodes[nearestCrate.getCase().getCoord()[0]][nearestCrate.getCase().getCoord()[1] - 1];
							break;
					}

					// Construction of the list of moves from the player node to the correct position to push the crate
					ArrayList<Direction> movesToPlayerMovementNode = getMoves(searchPath(playerNode, playerMovementNode));

					// If anyTime mode is enabled, moves the player case by case to be able to put the Thread to sleep, else moves it until the end
					if (!anyTime) {
						gameController.moveSequence(playerNode.getCase().getPawn(), movesToPlayerMovementNode);
					} else {
						for (Direction move : movesToPlayerMovementNode) {
							gameController.move(playerNode.getCase().getPawn(), move);
						}
					}

					// Getting the new coordinates of the player
					playerNode = findPlayerNode();
				}

				// Moves the player following the direction
				gameController.move(playerNode.getCase().getPawn(), currentDirection);
				playerNode = findPlayerNode();
				nearestCrate = findNearest(playerNode, Type.CRATE, null);
			}
		}
	}

	/**
	 * Loop through the 2D array of nodes to search the for node corresponding to the Player pawn.
	 *
	 * @return the node corresponding to the Player pawn
	 */
	private Node findPlayerNode() {
		for (Node[] row : nodes)
			for (Node node : row)
				if (node.getCase().getPawn() != null && node.getCase().getPawn().getType() == Type.PLAYER)
					return node;

		return null;
	}

	/**
	 * Method finding the nearest Node corresponding to the type or state entered as parameter from the starting Node
	 *
	 * @param startingNode the node from which we are searching
	 * @param type the type of the node we are searching for
	 * @param state the state of the node we are searching for
	 * @return the nearest Node corresponding to the type or state entered as parameter from the starting Node
	 */
	private Node findNearest(Node startingNode, Type type, State state) {
		ArrayList<Node> nodesArraylist = new ArrayList<>();

		for (Node[] row : nodes)
			for (Node node : row)
				if (state == null && type != null) {
					if (node.getCase().getPawn() != null && node.getCase().getPawn().getType() == type)
						if (type != Type.CRATE) {
							nodesArraylist.add(node);
						} else if (node.getCase().getState() != State.TARGET) {
							nodesArraylist.add(node);
						}
				} else if (state != null && type == null)
					if (node.getCase().getState() == state)
						nodesArraylist.add(node);

		Node selectedNode = null;
		for (Node node : nodesArraylist)
			if (selectedNode == null || selectedNode.calcDistanceFromNode(startingNode) > node.calcDistanceFromNode(startingNode))
				selectedNode = node;

		return selectedNode;
	}

	/**
	 * Calculates an ArrayList from the path list
	 *
	 * @param path the path to create a list of movements from
	 * @return a list of direction
	 */
	private ArrayList<Direction> getMoves(List<Node> path) {
		ArrayList<Direction> moves = new ArrayList<>();

		for (int ind = 1, pathSize = path.size(); ind < pathSize; ind++) {
			Case before = path.get(ind - 1).getCase();
			Case current = path.get(ind).getCase();

			if (before.getCoord()[0] > current.getCoord()[0])
				moves.add(Direction.UP);
			else if (before.getCoord()[0] < current.getCoord()[0])
				moves.add(Direction.DOWN);
			else if (before.getCoord()[1] < current.getCoord()[1])
				moves.add(Direction.RIGHT);
			else if (before.getCoord()[1] > current.getCoord()[1])
				moves.add(Direction.LEFT);
		}

		return moves;
	}

	/**
	 * Method searching the path from startNode to goalNode
	 *
	 * @param startNode the node we are going from
	 * @param goalNode the node we are going to
	 * @return the list of Node to go through
	 */
	private List<Node> searchPath(Node startNode, Node goalNode) {
		// Resetting the parent node of each node
		for (Node[] row : nodes)
			for (Node node : row) node.setParentNode(null);

		// The closedList is a list of Node checked
		ArrayList<Node> closedList = new ArrayList<>();

		// The openedList is a list of Node we can reach from the checked nodes to check them
		ArrayList<Node> openedList = new ArrayList<>(Collections.singletonList(startNode));

		// Map of the distance for each node to the startNode
		Map<Node, Integer> distanceFromNodeToStartNode = new HashMap<>();
		distanceFromNodeToStartNode.put(startNode, 0);

		// Map of the score of each node
		Map<Node, Integer> fScore = new HashMap<>();
		for (Node[] row : nodes)
			for (Node node : row)
				fScore.put(node, Integer.MAX_VALUE);

		fScore.put(startNode, startNode.calcDistanceFromNode(goalNode));

		Comparator<Node> comparator = (o1, o2) -> {
			if (fScore.get(o1) < fScore.get(o2))
				return -1;
			if (fScore.get(o2) < fScore.get(o1))
				return 1;
			return 0;
		};

		// While the openedList contains a Node
		while (!openedList.isEmpty()) {
			Node current = openedList.get(0);

			// If the current node is the goal node
			if (current == goalNode)
				return constructPath(startNode, goalNode);

			// We remove the checking node from the openedList and adding it to the closedList
			openedList.remove(0);
			closedList.add(current);

			// Setting the neighbors of the current node
			current.setNeighbors(nodes);

			// For each neighbor, if the neighbor node is not valid or is the goal node
			for (Node neighbor : current.getNeighbors()) {
				if (neighbor.getCase().getPawn() != null || (startNode.getCase().getPawn().getType() == Type.CRATE && neighbor.isCorner() && neighbor != goalNode) || closedList.contains(neighbor))
					continue;

				// Calculates the distance between the neighbor node and the start node
				int neighborDistanceFromStartNode = distanceFromNodeToStartNode.get(current) + current.calcDistanceFromNode(neighbor);
				if (!openedList.contains(neighbor))
					openedList.add(neighbor);
				else if (neighborDistanceFromStartNode >= distanceFromNodeToStartNode.get(neighbor))
					continue;

				// Setting the neighbor parent and adding the distance from the start node to the map
				neighbor.setParentNode(current);
				distanceFromNodeToStartNode.put(neighbor, neighborDistanceFromStartNode);

				// Calculating the neighbor score and adding it to the map
				int neighborScore = neighborDistanceFromStartNode + neighbor.calcDistanceFromNode(goalNode);
				fScore.put(neighbor, neighborScore);

				// Resortting the openedList
				openedList.sort(comparator);
			}
		}

		return null;
	}

	/**
	 * Construct the path from a node to another.
	 * <p>
	 *     What it does is rolling up the parent nodes and adding it to the path.
	 * </p>
	 *
	 * @param start starting node
	 * @param end ending node
	 * @return a list of Nodes as path
	 */
	private List<Node> constructPath(Node start, Node end) {
		LinkedList<Node> path = new LinkedList<>();

		Node node = end;
		path.addFirst(node);

		while (!path.contains(start)) {
			node = node.getParentNode();
			path.addFirst(node);
		}

		return path;
	}
}
