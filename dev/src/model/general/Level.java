package model.general;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Level's class
 * <p>
 * The Level class represents a level of the game.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-07
 * @see Case
 */

public class Level {
	private final Case[][] caseArray;
	private final List<Case> targetArray;
	private final Score score;
	private final LocalDateTime[] dates;

	/**
	 * Level's constructor that initializes the caseArray, the score, the dates and the targetArray of the Level.
	 *
	 * @param caseArray 2D array of the level's grid
	 */
	Level(Case[][] caseArray) {
		this.caseArray = caseArray;

		score = new Score();
		dates = new LocalDateTime[2];

		targetArray = new ArrayList<>();

		for (Case[] line : caseArray)
			for (Case case1 : line)
				if (case1.getState() == State.TARGET)
					targetArray.add(case1);
	}

	/**
	 * Getter of the Level's 2D case grid.
	 *
	 * @return the Level's 2D case grid
	 */
	public Case[][] getCaseArray() {
		return caseArray;
	}

	/**
	 * Getter of the targets of the Level.
	 *
	 * @return a list of all the targets of the Level.
	 */
	public List<Case> getTargetArray() {
		return targetArray;
	}

	/**
	 * Getter of the Case owning the Player pawn.
	 *
	 * @return the Case owning the Player pawn.
	 */
	public Case getPlayerCase() {
		for (Case[] line : caseArray)
			for (Case case1 : line)
				if (case1.getPawn() != null && case1.getPawn().getType() == Type.PLAYER)
					return case1;

		return null;
	}

	/**
	 * Set the starting date of the Level (when the player begins the game).
	 */
	void setStartDate() {
		dates[0] = LocalDateTime.now();
	}

	/**
	 * Set the ending date of the Level (when the player complete the game).
	 */
	public void setEndDate() {
		dates[1] = LocalDateTime.now();
	}

	/**
	 * Getter of the dates of the Level.
	 *
	 * @return the dates of the Level
	 */
	public LocalDateTime[] getDates() {
		return dates;
	}

	/**
	 * Getter of the Level's score.
	 *
	 * @return the Level's score
	 */
	public Score getScore() {
		return score;
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();

		for (Case[] row : caseArray) {
			for (Case selectedCase : row)
				res.append(selectedCase);
			res.append("\n");
		}

		return res.toString();
	}
}
