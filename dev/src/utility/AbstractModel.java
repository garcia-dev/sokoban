package utility;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractModel's static class which is part of the MVC pattern.
 *
 * @author DE OLIVEIRA Dylan, GARCIA Romain, NGUYEN Michaël, VINCIGUERRA Antoine
 * @version 2018-01-30
 */
public abstract class AbstractModel implements Observable {
	private final List<Observer> observers = new ArrayList<>();

	/**
	 * @see Observable#addObserver(Observer)
	 */
	public void addObserver(Observer observer){
		observers.add(observer);
	}

	/**
	 * @see Observable#removeObserver(Observer)
	 */
	public void removeObserver(Observer observer){
		observers.remove(observer);
	}

	/**
	 * @see Observable#notifyObservers()
	 **/
	public void notifyObservers() {
		for (Observer observer : observers)
			observer.update();
 	}
}