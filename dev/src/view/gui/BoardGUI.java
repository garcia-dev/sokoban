package view.gui;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.general.Board;
import model.general.Case;
import utility.Observer;

/**
 * BoardGUI's class
 * <p>
 * The class BoardGUI is used to print a gui's version of the actual Board.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-04-10
 */

public class BoardGUI extends Node implements Observer {

    private Board board;

    private MainGUI mainGUI;

    private int centerShiftX;

    private int centerShiftY;

    Canvas canvas;

    public GraphicsContext gc;

    public BoardGUI(Board board, MainGUI mainGUI, int width, int height){
        this.board = board;
        this.mainGUI = mainGUI;
        this.centerShiftX = 0;
        this.centerShiftY = 0;
        this.canvas = new Canvas( width, height);
        this.gc = canvas.getGraphicsContext2D();
    }

    public void middle(Board board){
        int temp = 0;
        int finalTemp = 0;
        int nbRow = 0;
        for (Case[] row : board.getLevel().getCaseArray()){
            nbRow++;
            for (Case actualCase : row){
                temp++;
            }
            if (temp > finalTemp){
                finalTemp = temp;
            }
            temp = 0;
        }
        this.centerShiftX = (800 - (finalTemp*20))/2;
        this.centerShiftY = (600 - (nbRow*20))/2;
    }

    public Board getBoard(){
        return board;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void update() {

        int cpt1 = 0;
        int cpt2 = 0;

        for (Case[] row : this.getBoard().getLevel().getCaseArray()){
            for (Case actualCase : row){

                if (actualCase.getPawn() == null){
                    switch (actualCase.getState()){
                        case TARGET:
                            Image target = new Image(getClass().getResource("/resources/images/target.png").toString());
                            gc.drawImage(target, cpt1* 20 + centerShiftX, cpt2* 20 + centerShiftY);
                            break;
                        case EMPTY:
                            Image empty = new Image(getClass().getResource("/resources/images/empty.png").toString());
                            gc.drawImage(empty, cpt1* 20 + centerShiftX, cpt2* 20 + centerShiftY);
                            break;
                        case WALL:
                            Image wall = new Image(getClass().getResource("/resources/images/wall.png").toString());
                            gc.drawImage(wall, cpt1* 20 + centerShiftX, cpt2* 20 + centerShiftY);
                            break;
                    }
                }
                else{
                    switch (actualCase.getPawn().getType()){
                        case CRATE:
                            Image box = new Image(getClass().getResource("/resources/images/box.png").toString());
                            gc.drawImage(box, cpt1* 20 + centerShiftX, cpt2* 20 + centerShiftY);
                            break;
                        case PLAYER:
                            Image player = new Image(getClass().getResource("/resources/images/player.png").toString());
                            gc.drawImage(player, cpt1* 20 + centerShiftX, cpt2* 20 + centerShiftY);
                            break;
                        }
                    }
                    cpt1 ++;
                }
                cpt2 ++;
            cpt1 = 0;
            }
        }

	@Override
	public Node getStyleableNode() {
		return null;
	}
}
