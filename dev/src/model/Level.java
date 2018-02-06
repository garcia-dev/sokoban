package model;

/**
 * Level's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-04
 * @see Case
 */

public class Level {
	private boolean isLocked;
	private Case[][] caseArray;

	public Level(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public Level() {
		this.isLocked = true;
	}

	public Case[][] getCaseArray() {
		return caseArray;
	}

	public void loadLevel(String filePath, Board board) {
		caseArray = LevelLoader.loadFile(filePath, board);
	}

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
