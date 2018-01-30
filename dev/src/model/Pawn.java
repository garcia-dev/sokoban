package model;

/**
 * Pawn's class
 *
 * @author GARCIA Romain, DE OLIVEIRA Dylan, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */

public class Pawn {
    private Type type;
    private Case coord;

    public Pawn(Type type, Case coord) {
        this.type = type;
        this.coord = coord;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Case getCoord() {
        return coord;
    }

    public void setCoord(Case coord) {
        this.coord = coord;
    }

}


