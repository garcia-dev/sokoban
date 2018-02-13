package model;

/**
 * Case's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-04
 * @see State
 * @see Pawn
 */

public class Case {
	private State state;
	private Pawn pawn;
	private Board board;
	private int[] coord;

	Case(State state, int[] coord, Board board) {
		this.state = state;
		this.board = board;
		this.coord = coord;

		this.pawn = null;
	}

	public void addPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public void removePawn() {
		this.pawn = null;
	}

	public State getState() {
		return state;
	}

	public int[] getCoord() {
		return coord;
	}

	public Pawn getPawn() {
		return pawn;
	}

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
