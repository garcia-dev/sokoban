package model;

public class Board extends AbstractModel {
	private Level level;

	public void setLevel(Level level) {
		this.level = level;
		level.setStartDate();
	}

	public Level getLevel() {
		return level;
	}
}
