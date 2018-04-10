package view.console;

import controller.GameController;
import model.general.Board;
import model.general.Direction;
import model.general.LevelLoader;
import model.solver.Solver;
import utility.Observer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;


/**
 * Main's class
 * <p>
 * The Main class is the console version executable class.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 * @see Board
 */
public class Main implements Observer {
	Board board;

	/**
	 * Main's constructor initializing the board.
	 *
	 * @param board the board to implements into the Main
	 */
	private Main(Board board) {
		this.board = board;
	}

	/**
	 * Main method of the console version.
	 *
	 * @param args arguments given when calling execution
	 */
	public static void main(String[] args) {
		Main main = new Main(new Board());

		int numberOfLevels = 0;
		try {
			numberOfLevels = Objects.requireNonNull(new File(ClassLoader.getSystemResource("resources/levels").toURI()).listFiles()).length;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		Scanner scanner = new Scanner(System.in);

		System.out.print("Which level do you want to do ? (");

		for (int levelNumber = 1; levelNumber <= numberOfLevels; levelNumber++) {
			System.out.print(levelNumber);
			if (levelNumber != numberOfLevels)
				System.out.print(" or ");
		}
		System.out.println(")");

		int selectedLevel;
		do {
			selectedLevel = scanner.nextInt();
		} while (selectedLevel > numberOfLevels && selectedLevel <= 0);

		main.board.setLevel(LevelLoader.loadFile(selectedLevel, main.board));
		main.board.addObserver(main);

		GameController gameController = new GameController(main.board);

		System.out.println(main.board.getLevel());

		String AImode = "";
		System.out.println("Do you want the AI to do complete the level ? (yes/no)");

		do {
			AImode = scanner.next();
		} while (!AImode.equals("yes") && !AImode.equals("no"));

		if (AImode.equals("no")) {
			while (!gameController.isFinished()) {
				System.out.println("Direction : (z vers le haut/q vers la gauche/s vers le bas/d vers la droite)");

				switch (scanner.next()) {
					case "z":
						gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.UP);
						break;
					case "d":
						gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.RIGHT);
						break;
					case "s":
						gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.DOWN);
						break;
					case "q":
						gameController.move(main.board.getLevel().getPlayerCase().getPawn(), Direction.LEFT);
						break;
					default:
						System.out.println("Not a direction");
						break;
				}
			}
		} else {
			Solver solver = new Solver(main.board);
			solver.solveBoard(gameController);
		}

		System.out.println("-----------!! CONGRATULATIONS !!-----------");
		System.out.println("You ended this level in " + main.board.getLevel().getScore().getDuration().toSeconds() + " seconds and " + main.board.getLevel().getScore().getStepCount() + " moves.");
	}

	@Override
	public void update() {
		System.out.println(board.getLevel());
	}
}
