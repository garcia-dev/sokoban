package model;

import java.util.ArrayList;
import java.util.List;

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
	private List<Case> targetArray;

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
		targetArray = new ArrayList<>();

		for (Case[] line : caseArray)
			for(Case case1 : line)
				if (case1.getState() == State.TARGET)
					targetArray.add(case1);
	}

	public boolean isFinished(){
		int crateOnTarget = 0;

		for (Case case1 : targetArray)
			if (case1.getPawn() != null && case1.getPawn().getType() == Type.CRATE)
				crateOnTarget++;

		return crateOnTarget == targetArray.size();
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
