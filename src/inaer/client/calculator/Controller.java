package inaer.client.calculator;

//import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

import inaer.client.CalcService;
import inaer.client.CalcServiceAsync;

/**
 * Controller for the Calculator component.
 * @author Samuel Ors
 *
 */
public class Controller {

	public enum ECalcCmds {
		none, v0, v1, v2, v3, v4, v5, v6, v7, v8, v9, dot, clear, clearCurr, sign, percent, add, substract, multiply, divide, equal, bin
	}
	
	private Model model;
	private ECalcCmds lastCmd = ECalcCmds.none;
	/**
	 * Create a remote service proxy to talk to the server-side Calc service.
	 */
	private final CalcServiceAsync calcService = GWT.create(CalcService.class);

	public Controller(Model model) {
		this.model = model;		
	}			
	protected void updateValue(ECalcCmds cmd) {
		switch(lastCmd) {
			case add:
			case substract:
			case multiply:
			case divide:
			case equal:
			case percent:
				model.clearCurrentValue();
				break;
			default:
				break;
		}
		String value = model.getCurrentValue();
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
		//Try to convert to double, and if possible, update the model.
		try {			
			value = getTrimmedStringValue(value);
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
	protected String getStringValue(double fValue) {		
		NumberFormat fmt = NumberFormat.getDecimalFormat();		
		return fmt.format(fValue);	
	}
	protected String getTrimmedStringValue(String value) {
		int sInd=0;
		for(int i=0; i<value.length()-1; i++) {
			if(value.charAt(i)=='0' && value.charAt(i+1)!='.') {
				sInd = i+1;			
				break;
			}
		}
		String res = value.substring(sInd);
		return res;
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
			model.setCurrentValue(getStringValue(a));
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
			model.setCurrentValue(getStringValue(a));
			lastCmd = ECalcCmds.none;
		}
		catch(Exception e) {
		}
	}
	protected void processToBin() {
		long lValue;
		try {
			lValue = Long.parseLong(model.getCurrentValue());
		}
		catch(Exception e) {
			model.setCurrentValue("Invalid integer");
			return;
		}
		calcService.toBin(lValue, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				model.setCurrentValue("Error on RPC");
				model.setAccumulator(0);
				model.setCurrentOperator(ECalcCmds.none);
			}

			public void onSuccess(String result) {
				model.setCurrentValue(result);
				model.setAccumulator(0);
				model.setCurrentOperator(ECalcCmds.none);
			}
		});	
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
			case bin:
				processToBin();
				break;
		}
		lastCmd = cmd;
	}
	public String getCurrentValue() {
		return model.getCurrentValue();
	}
}
