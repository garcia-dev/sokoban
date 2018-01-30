package model;

/**
 * Case's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 * @see State
 */

public class Case {
    private State state;
    private Pawn pawn;

    public Case(State state, Pawn pawn) {
        this.state = state;
        this.pawn = pawn;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

}
