package inaer.client.calculator;

import com.google.gwt.i18n.client.NumberFormat;

/**
 * Controller for the Calculator component.
 * @author Samuel Ors
 *
 */
public class Controller {
	public enum ECalcCmds {
		none, v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, dot, clear, clearCurr, sign, percent, add, substract, multiply, divide, equal
	}
	
	private Model model;
	private ECalcCmds lastCmd = ECalcCmds.none;
	
	public Controller(Model model) {
		this.model = model;
	}			
	protected void updateValue(ECalcCmds cmd) {
		String value = model.getCurrentValue();
		switch(lastCmd){
			case add:
			case substract:
			case multiply:
			case divide:
			case equal:
				value="";
			default:
				break; 
		}
		switch(cmd) {
			default:
			case none: break;
			case v0: value += '0'; break;
			case v1: value += '1'; break;
			case v2: value += '2'; break;
			case v3: value += '3'; break;
			case v4: value += '4'; break;
			case v5: value += '5'; break;
			case v6: value += '6'; break;
			case v7: value += '7'; break;
			case v8: value += '8'; break;
			case v9: value += '9'; break;
			case dot:
				if(value == "")
					value = "0.";
				else
					value += '.';				
				break;
		}
		//Try to convert to double, and if possible, update the model.
		try {			
			Double.parseDouble(value);
			model.setCurrentValue(value);
		}
		catch(Exception e) {			
		}		
	}
	protected void changeSign() {
		String value = model.getCurrentValue();
		if(value.charAt(0)=='-')
			value = value.substring(1);
		else
			value = '-' + value;		
		model.setCurrentValue(value);
	}
	protected void processOperator(ECalcCmds op) {
		model.setCurrentOperator(op);
		String value = model.getCurrentValue();
		try {
			model.setAccumulator(Double.parseDouble(value));
		}
		catch(Exception e) {
			model.setAccumulator(0);
			model.setCurrentOperator(ECalcCmds.none);
			model.clearCurrentValue();
		}
	}
	protected String getTrimmedValue(double fValue) {		
		NumberFormat fmt = NumberFormat.getDecimalFormat();		
		return fmt.format(fValue);	
	}
	protected void processPercent() {
		double a = model.getAccumulator();
		double b;
		try {
			b = Double.parseDouble(model.getCurrentValue());
			switch(model.getCurrentOperator()) {
				case add: 
					a = a*(100 + b)/100; 
					break;
				case substract:
					a = a*(100 - b)/100; 
					break;
				case multiply:
					a = a*(b/100);					
					break;
				case divide:
					a /= b;
					break;
				default:
					break;
			}
			model.setAccumulator(0);
			model.setCurrentOperator(ECalcCmds.none);
			model.setCurrentValue(getTrimmedValue(a));
			lastCmd = ECalcCmds.none;
		}
		catch(Exception e) {
		}
	}
	protected void processRes() {
		double a = model.getAccumulator();
		double b;
		try {
			b = Double.parseDouble(model.getCurrentValue());
			switch(model.getCurrentOperator()) {
				case add: 
					a += b; 
					break;
				case substract:
					a -= b; 
					break;
				case multiply:
					a *= b;
					break;
				case divide:
					a /= b;
					break;
				default:
					break;
			}
			model.setAccumulator(0);
			model.setCurrentOperator(ECalcCmds.none);
			model.setCurrentValue(getTrimmedValue(a));
			lastCmd = ECalcCmds.none;
		}
		catch(Exception e) {
		}
	}
	public void processCmd(ECalcCmds cmd) {		
		switch(cmd) {
			default:
			case none: break;
			case v0: 
			case v1: 
			case v2: 
			case v3: 
			case v4: 
			case v5: 
			case v6: 
			case v7: 
			case v8: 
			case v9: 
			case dot:
				updateValue(cmd);
				break;
			case clear:
				model.setAccumulator(0);
				model.setCurrentOperator(ECalcCmds.none);				
				model.clearCurrentValue();
				lastCmd = ECalcCmds.none;
				break;
			case clearCurr: 
				model.clearCurrentValue();
				break;
			case sign:
				changeSign();
				break;
			case add:
				processOperator(ECalcCmds.add);
				break;
			case substract: 
				processOperator(ECalcCmds.substract);
				break;
			case multiply:
				processOperator(ECalcCmds.multiply);
				break;
			case divide:
				processOperator(ECalcCmds.divide);
				break;
			case percent:
				processPercent();
				break;
			case equal:
				processRes();
				break;
		}
		lastCmd = cmd;
	}
	public String getCurrentValue() {
		return model.getCurrentValue();
	}
}
