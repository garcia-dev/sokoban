package model;

public class Plateau implements Observer{

    private Board board;

    public Plateau(Board board){
        this.board = board;
    }

    public Board getBoard(){
        return board;
    }

    @Override
    public void update(Object source) {
        for (Case[] row : this.getBoard().getLevel().getCaseArray()){
            for (Case actualCase : row){

                if (actualCase.getPawn() == null){
                    switch (actualCase.getState()){
                        case TARGET:
                            break;
                        case EMPTY:
                            break;
                        case WALL:
                            System.out.println("WALL");
                            break;
                    }
                }
                else{
                    switch (actualCase.getPawn().getType()){
                        case CRATE:
                            System.out.println("CRATE");
                            break;
                        case PLAYER:
                            System.out.println("PLAYER");
                            break;
                        }
                    }
                }
            }
        }
    }
