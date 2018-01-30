package model;

/**
 * Case's class
 *
 * @see State
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */

public class Case {
    private State state = State.WALL;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
