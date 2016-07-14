package inaer.client.calculator;

import java.util.*;

interface ModelObserver {
	public void onModelChange();
}

/**
 * Model for the Calculator component.
 * @author Samuel Ors
 */
public class Model {
	private float accumulator;
	private String currentValue;
	private Controller.ECalcCmds currentOperator;
	private List<ModelObserver> observers = new ArrayList<ModelObserver>();
	
	public Model() {
		accumulator = 0;
		currentValue = "0";
	}
	
	protected void notifyObjservers() {
		for (ModelObserver o : observers)
            o.onModelChange();
	}
	public void addObserver(ModelObserver toAdd) {
		observers.add(toAdd);
    }

	public float getAccumulator() {
		return accumulator;
	}

	public void setAccumulator(float accumulator) {
		this.accumulator = accumulator;
		notifyObjservers();
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void clearCurrentValue() {
		setCurrentValue("0");
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
		notifyObjservers();
	}

	public Controller.ECalcCmds getCurrentOperator() {
		return currentOperator;
	}

	public void setCurrentOperator(Controller.ECalcCmds currentOperator) {
		this.currentOperator = currentOperator;
		notifyObjservers();
	}
}
