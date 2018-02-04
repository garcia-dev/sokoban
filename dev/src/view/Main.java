package view;

import model.Level;

public class Main {
	public static void main(String[] args){
		Level test = new Level(false);
		test.loadLevel("resources/levels/level1.sok");

		System.out.println(test);
	}
}
