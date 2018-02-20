package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Level's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-20
 * @see Case
 */

public class Level {
	private Case[][] caseArray;
	private List<Case> targetArray;
	private Score score;
	private LocalDateTime[] dates;

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

	public Case[][] getCaseArray() {
		return caseArray;
	}

	public Case getPlayerCase(){
		for (Case[] line : caseArray)
			for (Case case1 : line)
				if (case1.getPawn() != null && case1.getPawn().getType() == Type.PLAYER)
					return case1;

		return null;
	}

	public void setStartDate(){
		dates[0] = LocalDateTime.now();
	}

	private void setEndDate(){
		dates[1] = LocalDateTime.now();
	}

	public boolean isFinished(){
		int crateOnTarget = 0;

		for (Case case1 : targetArray)
			if (case1.getPawn() != null && case1.getPawn().getType() == Type.CRATE)
				crateOnTarget++;

		if (crateOnTarget == targetArray.size()) {
			setEndDate();
			score.setDuration(dates[0], dates[1]);

			return true;
		}
		return false;
	}

	public Score getScore() {
		return score;
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
