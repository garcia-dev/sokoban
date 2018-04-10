package model.solver;

import controller.GameController;
import model.general.*;

import java.util.*;

/**
 * Solver's class
 * <p>
 *     The Solver class represents the AI code. It is used to solve the board by itself.
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

	public Solver(Board board) {
		this.board = board;

		initNodes();
	}

	private void initNodes() {
		nodes = new Node[board.getLevel().getCaseArray().length][];

		for (int row = 0; row < board.getLevel().getCaseArray().length; row++) {
			nodes[row] = new Node[board.getLevel().getCaseArray()[row].length];

			for (int indCase = 0; indCase < board.getLevel().getCaseArray()[row].length; indCase++)
				nodes[row][indCase] = new Node(board.getLevel().getCaseArray()[row][indCase]);
		}
	}

	public void solveBoard(GameController gameController) {
		Node playerNode = findPlayerNode();

		while (!(gameController.isFinished())) {
			Node nearestCrate = findNearest(playerNode, Type.CRATE, null);
			Node nearestTargetFromCrate = findNearest(nearestCrate, null, State.TARGET);

			List<Node> path = searchPath(nearestCrate, nearestTargetFromCrate);

			ArrayList<Direction> movesFromCrateToTarget = getMoves(path);

			Direction currentDirection = null;
			for (Direction direction : movesFromCrateToTarget) {
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

					ArrayList<Direction> movesToPlayerMovementNode = getMoves(searchPath(playerNode, playerMovementNode));
					gameController.moveSequence(playerNode.getCase().getPawn(), movesToPlayerMovementNode);
					playerNode = findPlayerNode();
				}

				gameController.move(playerNode.getCase().getPawn(), currentDirection);
				playerNode = findPlayerNode();
				nearestCrate = findNearest(playerNode, Type.CRATE, null);
			}
		}
	}

	private Node findPlayerNode() {
		for (Node[] row : nodes)
			for (Node node : row)
				if (node.getCase().getPawn() != null && node.getCase().getPawn().getType() == Type.PLAYER)
					return node;

		return null;
	}

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

	private List<Node> searchPath(Node startNode, Node goalNode) {
		for (Node[] row : nodes)
			for (Node node : row) node.setParentNode(null);

//		System.out.println("\n\nstartNode=" + startNode + " - type=" + startNode.getCase().getPawn().getType());
//		System.out.println("goalNode=" + goalNode);

		ArrayList<Node> closedList = new ArrayList<>();
		ArrayList<Node> openedList = new ArrayList<>(Collections.singletonList(startNode));

		Map<Node, Integer> distanceFromNodeToStartNode = new HashMap<>();
		distanceFromNodeToStartNode.put(startNode, 0);

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

		while (!openedList.isEmpty()){
//			System.out.println("openedList=");
//			openedList.forEach(node -> System.out.println("\t" + node));

//			System.out.println("closedList=");
//			closedList.forEach(node -> System.out.println("\t" + node));

			Node current = openedList.get(0);

			if (current == goalNode)
				return constructPath(startNode, goalNode);

			openedList.remove(0);
			closedList.add(current);

//			System.out.println("currentNode=" + current);

			current.setNeighbors(nodes);
			for (Node neighbor : current.getNeighbors()){
//				System.out.println("\t" + neighbor + " - isCorner=" + neighbor.isCorner());
				if (neighbor.getCase().getPawn() != null || (startNode.getCase().getPawn().getType() == Type.CRATE && neighbor.isCorner() && neighbor != goalNode) || closedList.contains(neighbor))
					continue;

				int neighborDistanceFromStartNode = distanceFromNodeToStartNode.get(current) + current.calcDistanceFromNode(neighbor);
				if (!openedList.contains(neighbor))
					openedList.add(neighbor);
				else if (neighborDistanceFromStartNode >= distanceFromNodeToStartNode.get(neighbor))
					continue;

				neighbor.setParentNode(current);
				distanceFromNodeToStartNode.put(neighbor, neighborDistanceFromStartNode);

				int neighborScore = neighborDistanceFromStartNode + neighbor.calcDistanceFromNode(goalNode);
				fScore.put(neighbor, neighborScore);

				openedList.sort(comparator);
			}
		}

		return null;
	}

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
