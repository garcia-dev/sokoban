package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * LevelLoader's class
 * <p>
 * The class LevelLoader loads a file with a ".sok" extension and translate it into a Level's caseArray.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-02-04
 * @see Level
 * @see Case
 * @see Pawn
 */

public class LevelLoader {
	public static Case[][] loadFile(String fileName, Board board) {
		String workingDirectory = System.getProperty("user.dir");

		if (System.getProperty("os.name").toLowerCase().contains("win"))
			workingDirectory += File.separator + "dev";

		String absoluteFilePath = workingDirectory + File.separator + "resources" + File.separator + "levels" + File.separator + fileName + ".sok";

		System.out.println(absoluteFilePath);

		List<String> lines = new ArrayList<>();

		try (Stream<String> stream = Files.lines(Paths.get(absoluteFilePath))) {
			lines = stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Case[][] cases = new Case[lines.size()][];

		for (int row = 0; row < lines.size(); row++) {
			cases[row] = new Case[lines.get(row).length()];

			for (int col = 0; col < lines.get(row).length(); col++) {
				switch (lines.get(row).charAt(col)) {
					case ' ': // if the char represents an empty case
						cases[row][col] = new Case(State.EMPTY, new int[]{row, col}, board);
						break;
					case '#': // if the char represents a wall
						cases[row][col] = new Case(State.WALL, new int[]{row, col}, board);
						break;
					case '.': // if the char represents a target case
						cases[row][col] = new Case(State.TARGET, new int[]{row, col}, board);
						break;
					case '@': // if the char represents a player
						System.out.println(row + "-" + col);
						Case playerCase = new Case(State.EMPTY, new int[]{row, col}, board);
						Pawn playerPawn = new Pawn(Type.PLAYER, playerCase, board);
						playerCase.addPawn(playerPawn);
						cases[row][col] = playerCase;
						break;
					case '$': // if the char represents a crate
						Case crateCase = new Case(State.EMPTY, new int[]{row, col}, board);
						Pawn cratePawn = new Pawn(Type.CRATE, crateCase, board);
						crateCase.addPawn(cratePawn);
						cases[row][col] = crateCase;
						break;
				}
			}
		}

		return cases;
	}
}
