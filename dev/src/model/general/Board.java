package model.general;

import utility.AbstractModel;

/**
 * Board's class
 * <p>
 * The Board class loads a level instance.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-27
 * @see Level
 */
public class Board extends AbstractModel {
	private Level level;

	/**
	 * Getter of the "level" Level.
	 *
	 * @return the "level" Level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Setter of the "level" Level.
	 *
	 * @param level the level value to apply to "level"
	 */
	public void setLevel(Level level) {
		this.level = level;
		level.setStartDate();
	}
}
