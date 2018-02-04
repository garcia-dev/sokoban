package view;

/**
 * Main's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */

import model.Level;

public class Main {
	public static void main(String[] args){
		Level test = new Level(false);
		test.loadLevel("resources/levels/level1.sok");

		System.out.println(test);
	}
}
