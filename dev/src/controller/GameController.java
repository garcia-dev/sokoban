package controller;

import model.general.*;

import java.util.ArrayList;
import java.util.List;

/**
 * GameController's class
 * <p>
 * The class GameController takes care of all the verifications for the movements and to check if the game is finished.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 * @see Board
 */
public class GameController {
	private final Board board;

	/**
	 * GameController's constructor which initializes the board.
	 *
	 * @param board the board to use
	 */
	public GameController(Board board) {
		this.board = board;
	}

	/**
	 * Method that checks if the Pawn can move in the asked Direction, if it can tells to move it.
	 *
	 * @param pawn      pawn to move
	 * @param direction direction to move the pawn
	 * @return true if the move is done, false if it isn't
	 */
	public boolean move(Pawn pawn, Direction direction) {
		/*  For each direction, we check if the case from that direction isn't a wall, if it isn't, we check if it is
			a CRATE, if it is we check if there is no obstacle behind it, else we move the pawns.*/
		switch (direction) {
			case UP:
				Case up = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0] - 1][pawn.getCase().getCoord()[1]];

				if (up.getPawn() != null && up.getPawn().getType() == Type.CRATE) {
					Case upCrate = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0] - 2][pawn.getCase().getCoord()[1]];

					if (upCrate.getPawn() != null && upCrate.getPawn().getType() == Type.CRATE) {
						return false;
					} else if (this.move(up.getPawn(), Direction.UP)) {
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
					Case rightCrate = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0]][pawn.getCase().getCoord()[1] + 2];
					if (rightCrate.getPawn() != null && rightCrate.getPawn().getType() == Type.CRATE) {
						return false;
					} else if (this.move(right.getPawn(), Direction.RIGHT)) {
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
					Case downCrate = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0] + 2][pawn.getCase().getCoord()[1]];
					if (downCrate.getPawn() != null && downCrate.getPawn().getType() == Type.CRATE) {
						return false;
					} else if (this.move(down.getPawn(), Direction.DOWN)) {
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
					Case leftCrate = board.getLevel().getCaseArray()[pawn.getCase().getCoord()[0]][pawn.getCase().getCoord()[1] - 2];
					if (leftCrate.getPawn() != null && leftCrate.getPawn().getType() == Type.CRATE) {
						return false;
					} else if (this.move(left.getPawn(), Direction.LEFT)) {
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

	/**
	 * Method that checks movements of a Pawn within a Direction ArrayList using the "move" method for each Direction.
	 *
	 * @param pawn  the pawn to move
	 * @param moves the list of direction
	 * @see GameController#move(Pawn, Direction)
	 */
	public void moveSequence(Pawn pawn, ArrayList<Direction> moves) {
		for (Direction direction : moves)
			move(pawn, direction);
	}

	/**
	 * Method that checks if the game is done or not
	 *
	 * @return true if the game is done, false if it isn't
	 */
	public boolean isFinished() {
		List<Case> targetArray = board.getLevel().getTargetArray();
		int crateOnTarget = 0;

		for (Case case1 : targetArray)
			if (case1.getPawn() != null && case1.getPawn().getType() == Type.CRATE)
				crateOnTarget++;

		if (crateOnTarget == targetArray.size()) {
			board.getLevel().setEndDate();
			board.getLevel().getScore().setDuration(board.getLevel().getDates()[0], board.getLevel().getDates()[1]);

			return true;
		}
		return false;
	}
}
