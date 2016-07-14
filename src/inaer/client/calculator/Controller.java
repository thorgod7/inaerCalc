package inaer.client.calculator;

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
	
	public Controller(Model model) {
		this.model = model;
	}			
	protected void updateValue(ECalcCmds cmd) {
		String value = model.getCurrentValue();
		if(value == "0")
			value = "";
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
			case dot: value += '.'; break;
		}
		//Try to convert to float, and if possible, update the model.
		try {
			@SuppressWarnings("unused")
			float fValue = Float.parseFloat(value);
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
	public void processRes() {
		
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
				break;
			case clearCurr: 
				model.clearCurrentValue();
				break;
			case sign:
				changeSign();
				break;
			case percent:
				model.setCurrentOperator(ECalcCmds.percent);
				break;
			case add:
				model.setCurrentOperator(ECalcCmds.add);
				break;
			case substract: 
				model.setCurrentOperator(ECalcCmds.substract);
				break;
			case multiply:
				model.setCurrentOperator(ECalcCmds.multiply);
				break;
			case divide:
				model.setCurrentOperator(ECalcCmds.divide);
				break;
			case equal:
				processRes();
				break;
		}
	}
	public String getCurrentValue() {
		return model.getCurrentValue();
	}
}
