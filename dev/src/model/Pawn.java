package model;

/**
 * Pawn's class
 * <p>
 * The class Pawn is used to create a movable object from the board (a player or a crate).
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-04
 * @see Type
 * @see Case
 */

public class Pawn {
	private Type type;
	private Case aCase;
	private Board board;

	public Pawn(Type type, Case aCase, Board board) {
		this.type = type;
		this.aCase = aCase;
		this.board = board;
	}

	public Type getType() {
		return type;
	}

	public Case getCase() {
		return aCase;
	}

	private void setaCase(Case aCase) {
		this.aCase = aCase;
	}

	public void move(Direction direction) {
		switch (direction) {
			case UP:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]].addPawn(this);
				setaCase(board.getLevel().getCaseArray()[aCase.getCoord()[0] - 1][aCase.getCoord()[1]]);
				break;
			case DOWN:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]].addPawn(this);
				setaCase(board.getLevel().getCaseArray()[aCase.getCoord()[0] + 1][aCase.getCoord()[1]]);
				break;
			case LEFT:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1].addPawn(this);
				setaCase(board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] - 1]);
				break;
			case RIGHT:
				aCase.removePawn();
				board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1].addPawn(this);
				setaCase(board.getLevel().getCaseArray()[aCase.getCoord()[0]][aCase.getCoord()[1] + 1]);
				break;
		}
	}
}
