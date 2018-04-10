package utility;

/**
 * Observable's interface which is part of the MVC pattern.
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */
interface Observable {
	/**
	 * Adds an Observer to the inherited object.
	 *
	 * @param observer observer to add to the observers List
	 * @see Observer
	 */
	void addObserver(Observer observer);

	/**
	 * Removes an Observer to the inherited object.
	 *
	 * @param observer observer to remove from the observers List
	 * @see Observer
	 */
	void removeObserver(Observer observer);

	/**
	 * Notify all the observers to update themselves.
	 **/
	void notifyObservers();
}
