package model.general;

/**
 * Case's class
 * <p>
 * The Case class represents a case from the Level.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-28
 * @see State
 * @see Board
 * @see Pawn
 */
public class Case {
	private final State state;
	private final Board board;
	private final int[] coord;
	private Pawn pawn;

	/**
	 * Case's constructor that initializes the state and the coordinates into the affiliated board.
	 *
	 * @param state the state of the Case
	 * @param coord the coordinates of the Case
	 * @param board the Board which is affiliated the Case
	 */
	Case(State state, int[] coord, Board board) {
		this.state = state;
		this.board = board;
		this.coord = coord;

		this.pawn = null;
	}

	/**
	 * Method that adds a Pawn to the Case.
	 *
	 * @param pawn the Pawn to add to the Case
	 */
	void addPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	/**
	 * Method that removes the Pawn from the Case.
	 */
	void removePawn() {
		this.pawn = null;
	}

	/**
	 * Getter of the "board" Board.
	 *
	 * @return the "board" Board.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Getter of the Case's state.
	 *
	 * @return the Case's state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Getter of the Case's coordinates.
	 *
	 * @return the Case's coordinates
	 */
	public int[] getCoord() {
		return coord;
	}

	/**
	 * Getter of the Case's pawn.
	 *
	 * @return the Case's pawn
	 */
	public Pawn getPawn() {
		return pawn;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		if (pawn != null) {
			if (pawn.getType() == Type.PLAYER)
				return "@";
			else
				return "$";
		}
		switch (getState()) {
			case WALL:
				return "#";
			case EMPTY:
				return " ";
			case TARGET:
				return ".";
		}
		return null;
	}
}
