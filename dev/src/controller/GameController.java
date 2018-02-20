package controller;

/**
 * Gamecontroller's class
 * <p>
 * The class GameController receives the view's requests, checks if the request is possible, gives the order to the model to apply the request and updates the view.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-13
 * @see model.Board
 */

import model.*;

public class GameController {
	private Board board;

	public GameController(Board board) {
		this.board = board;
	}

	public boolean move(Pawn pawn, Direction direction) {
		switch (direction) {
			case UP: // if the direction is up
				Case up = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0] - 1][pawn.getCase().getCoord()[1]];

				if (up.getPawn() != null && up.getPawn().getType() == Type.CRATE) {
					if (this.move(up.getPawn(), Direction.UP)) {
						pawn.move(Direction.UP);
						return true;
					}
					return false;
				} else if (up.getState() != State.WALL) {
					pawn.move(Direction.UP);
					return true;
				}

				break;
			case RIGHT:
				Case right = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0]][pawn.getCase().getCoord()[1] + 1];

				if (right.getPawn() != null && right.getPawn().getType() == Type.CRATE) {
					if (this.move(right.getPawn(), Direction.RIGHT)) {
						pawn.move(Direction.RIGHT);
						return true;
					}
					return false;
				} else if (right.getState() != State.WALL) {
					pawn.move(Direction.RIGHT);
					return true;
				}

				break;
			case DOWN:
				Case down = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0] + 1][pawn.getCase().getCoord()[1]];

				if (down.getPawn() != null && down.getPawn().getType() == Type.CRATE) {
					if (this.move(down.getPawn(), Direction.DOWN)) {
						pawn.move(Direction.DOWN);
						return true;
					}
					return false;
				} else if (down.getState() != State.WALL) {
					pawn.move(Direction.DOWN);
					return true;
				}

				break;
			case LEFT:
				Case left = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0]][pawn.getCase().getCoord()[1] - 1];

				if (left.getPawn() != null && left.getPawn().getType() == Type.CRATE) {
					if (this.move(left.getPawn(), Direction.LEFT)) {
						pawn.move(Direction.LEFT);
						return true;
					}
					return false;
				} else if (left.getState() != State.WALL) {
					pawn.move(Direction.LEFT);
					return true;
				}

				break;
		}

		return false;
	}
}
