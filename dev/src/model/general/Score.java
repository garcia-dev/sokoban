package model.general;

import utility.AbstractModel;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 * Score's class
 * <p>
 *     The class Score stores all the informations about the actual game.
 * </p>
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-03-28
 */
public class Score extends AbstractModel {
    private int stepCount;
    private Duration duration;

	/**
	 * Score's constructor that initialize the Score.
	 */
	Score(){
        stepCount = 0;
    }

	/**
	 * Getter of the number of moves.
	 *
	 * @return the number of moves
	 */
    public int getStepCount() {
        return stepCount;
    }

	/**
	 * Getter of the Level's duration.
	 *
	 * @return the Level's duration
	 */
    public Duration getDuration(){
    	return duration;
    }


	/**
	 * Setter of the Level's duration.
	 *
	 * @param startDate the date at the moment of the Level starts
	 * @param endDate the date at the moment of the Level ends
	 */
    public void setDuration(LocalDateTime startDate, LocalDateTime endDate){
    	duration = Duration.between(startDate, endDate);
    }

	/**
	 * Method that increment the step count.
	 */
	void addStepCount(){
		stepCount++;
		notifyObservers();
	}
}
