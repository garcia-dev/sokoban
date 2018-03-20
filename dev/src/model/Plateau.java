package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.ihm;

import java.io.File;
import java.net.URL;

public class Plateau implements Observer{

    private Board board;

    private ihm ihm;

    private int decalageX;

    private int decalageY;

    public Plateau(Board board, ihm ihm){
        this.board = board;
        this.ihm = ihm;
        this.decalageX = 20;
        this.decalageY = 20;
    }

    private view.ihm getIhm() {
        return ihm;
    }


    public Board getBoard(){
        return board;
    }

    @Override
    public void update(Object source) {

        Canvas canvas = new Canvas( 800, 600 );
        this.getIhm().groupe.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int cpt1 = 0;
        int cpt2 = 0;

        for (Case[] row : this.getBoard().getLevel().getCaseArray()){
            for (Case actualCase : row){

                if (actualCase.getPawn() == null){
                    switch (actualCase.getState()){
                        case TARGET:
                            Image target = new Image(getClass().getResource("target.png").toString());
                            gc.drawImage(target, cpt1*decalageX, cpt2*decalageY);
                            break;
                        case EMPTY:
                            Image empty = new Image(getClass().getResource("empty.png").toString());
                            gc.drawImage(empty, cpt1*decalageX, cpt2*decalageY);
                            break;
                        case WALL:
                            Image wall = new Image(getClass().getResource("wall.png").toString());
                            gc.drawImage(wall, cpt1*decalageX, cpt2*decalageY);
                            break;
                    }
                }
                else{
                    switch (actualCase.getPawn().getType()){
                        case CRATE:
                            Image box = new Image(getClass().getResource("box.png").toString());
                            gc.drawImage(box, cpt1*decalageX, cpt2*decalageY);
                            break;
                        case PLAYER:
                            Image player = new Image(getClass().getResource("player.png").toString());
                            gc.drawImage(player, cpt1*decalageX, cpt2*decalageY);
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
