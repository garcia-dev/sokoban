package view;

/**
 * Main's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-20
 * @see model.Board
 * @see controller.GameController
 */

import controller.GameController;
import model.*;

import java.util.Scanner;


public class Main implements Observer {
	private Board board;

	private Main(Board board) {
		this.board = board;
	}

	@Override
	public void update(Object source) {
		System.out.println(board.getLevel());
		System.out.println(board.getLevel().getScore().getStepCount());
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.home"));
		Main main = new Main(new Board());

		main.board.setLevel(LevelLoader.loadFile("level1", main.board));
		main.board.addObserver(main);

		GameController gameController = new GameController(main.board);

		System.out.println(main.board.getLevel());

		while (!main.board.getLevel().isFinished()){
			Scanner scanner = new Scanner(System.in);
			System.out.println("Direction :");

			switch (scanner.next()){
				case "UP":
					gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.UP);
					break;
				case "RIGHT":
					gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.RIGHT);
					break;
				case "DOWN":
					gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.DOWN);
					break;
				case "LEFT":
					gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.LEFT);
					break;
				default:
					System.out.println("Not a direction");
					break;
			}
		}
	}
}
