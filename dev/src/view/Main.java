package view;

/**
 * Main's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */

import controller.GameController;
import model.*;

import java.util.Scanner;

public class Main {
	private static Case selectPlayerCase(Case[][] cases) {
		for (int row = 0; row < cases.length - 1; row++) {
			for (int col = 0; col < cases[row].length - 1; col++) {
				if (cases[row][col].getPawn() != null && cases[row][col].getPawn().getType() == Type.PLAYER)
					return cases[row][col];
			}
		}

		return null;
	}

	public static void main(String[] args) {
		Level test = new Level(false);
		Board board = new Board(test);

		test.loadLevel("level1", board);

		System.out.println(test);

		Case[][] cases = test.getCaseArray();

		GameController gameController = new GameController(board);

		while (!board.getLevel().isFinished()) {
			Case playerCase = selectPlayerCase(cases);

			Scanner scanner = new Scanner(System.in);
			System.out.println("Direction ?");
			String input = scanner.next();

			if (playerCase != null) {
				switch (input) {
					case "UP":
						gameController.move(playerCase.getPawn(), Direction.UP);
						break;
					case "RIGHT":
						gameController.move(playerCase.getPawn(), Direction.RIGHT);
						break;
					case "DOWN":
						gameController.move(playerCase.getPawn(), Direction.DOWN);
						break;
					case "LEFT":
						gameController.move(playerCase.getPawn(), Direction.LEFT);
						break;
				}
			}

			System.out.println(test);
		}
	}
}
