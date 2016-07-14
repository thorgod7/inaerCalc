package inaer.client;

import java.util.*;

interface ModelObserver {
	public void onModelChange();
}

/**
 * Model for the Calculator component.
 * @author Samuel Ors
 */
public class CalculatorModel {
	private float accumulator;
	private String currentValue;
	private CalculatorCtrl.ECalcCmds currentOperator;
	private List<ModelObserver> observers = new ArrayList<ModelObserver>();
	
	CalculatorModel() {
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

	public CalculatorCtrl.ECalcCmds getCurrentOperator() {
		return currentOperator;
	}

	public void setCurrentOperator(CalculatorCtrl.ECalcCmds currentOperator) {
		this.currentOperator = currentOperator;
		notifyObjservers();
	}
}
