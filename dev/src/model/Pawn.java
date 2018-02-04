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
	private Case coord;

	Pawn(Type type, Case coord) {
		this.type = type;
		this.coord = coord;
	}

	public Type getType() {
		return type;
	}

	public Case getCoord() {
		return coord;
	}
}
