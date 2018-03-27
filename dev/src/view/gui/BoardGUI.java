package view.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.Board;
import model.Case;
import model.Observer;

/**
 * BoardGUI's class
 * <p>
 * The class BoardGUI is used to print a gui's version of the actual Board.
 * </p>
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-21
 */

public class BoardGUI implements Observer {

    private Board board;

    private MainGUI mainGUI;

    private int shiftX;

    private int shiftY;

    public BoardGUI(Board board, MainGUI mainGUI){
        this.board = board;
        this.mainGUI = mainGUI;
        this.shiftX = 20;
        this.shiftY = 20;
    }

    public Board getBoard(){
        return board;
    }

    @Override
    public void update(Object source) {

        Canvas canvas = new Canvas( 800, 600 );
        mainGUI.groupe.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int cpt1 = 0;
        int cpt2 = 0;

        for (Case[] row : this.getBoard().getLevel().getCaseArray()){
            for (Case actualCase : row){

                if (actualCase.getPawn() == null){
                    switch (actualCase.getState()){
                        case TARGET:
                            Image target = new Image(getClass().getResource("/resources/images/target.png").toString());
                            gc.drawImage(target, cpt1* shiftX, cpt2* shiftY);
                            break;
                        case EMPTY:
                            Image empty = new Image(getClass().getResource("/resources/images/empty.png").toString());
                            gc.drawImage(empty, cpt1* shiftX, cpt2* shiftY);
                            break;
                        case WALL:
                            Image wall = new Image(getClass().getResource("/resources/images/wall.png").toString());
                            gc.drawImage(wall, cpt1* shiftX, cpt2* shiftY);
                            break;
                    }
                }
                else{
                    switch (actualCase.getPawn().getType()){
                        case CRATE:
                            Image box = new Image(getClass().getResource("/resources/images/box.png").toString());
                            gc.drawImage(box, cpt1* shiftX, cpt2* shiftY);
                            break;
                        case PLAYER:
                            Image player = new Image(getClass().getResource("/resources/images/player.png").toString());
                            gc.drawImage(player, cpt1* shiftX, cpt2* shiftY);
                            break;
                        }
                    }
                    cpt1 ++;
                }
                cpt2 ++;
            cpt1 = 0;
            }
        }
    }
