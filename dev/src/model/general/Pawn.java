package model.general;

/**
 * Pawn's class
 * <p>
 * The class Pawn is used to create a movable object from the board (a player or a crate).
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-28
 * @see Type
 * @see Board
 * @see Case
 */

public class Pawn {
	private final Type type;
	private final Board board;
	private Case aCase;

	/**
	 * Pawn's constructor initializes a Pawn with its type, affiliated Case and Board.
	 *
	 * @param type  the type of the Pawn
	 * @param aCase the Case on which the Pawn is
	 * @param board the Board owning the Case
	 */
	Pawn(Type type, Case aCase, Board board) {
		this.type = type;
		this.aCase = aCase;
		this.board = board;
	}

	/**
	 * Getter of the Pawn's type.
	 *
	 * @return the Pawn's type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Getter of the Pawn's affiliated Case.
	 *
	 * @return the Pawn's affiliated Case
	 */
	public Case getCase() {
		return aCase;
	}

	/**
	 * Method that moves the Pawn following the given direction.
	 *
	 * @param direction the direction to move the Pawn to
	 */
	public void move(Direction direction) {
		// For each direction, we remove the Pawn from the actual Case, adds it to the Case in the direction and exchange the cases
		switch (direction) {
			case UP:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].addPawn(this);
				aCase = board.getLevel().getCaseArray()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]];
				break;
			case DOWN:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].addPawn(this);
				aCase = board.getLevel().getCaseArray()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]];
				break;
			case LEFT:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].addPawn(this);
				aCase = board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1];
				break;
			case RIGHT:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].addPawn(this);
				aCase = board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1];
				break;
		}

		// If the pawn is Player type, we notify the board's observers
		if (getType() == Type.PLAYER) {
			board.getLevel().getScore().addStepCount();
			board.notifyObservers();
		}
	}
}
