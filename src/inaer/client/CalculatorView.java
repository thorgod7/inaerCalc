package inaer.client;

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueBoxBase.TextAlignment;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * CalcButton
 * Extends TextButton to be able to send commands to the controller when pressed.
 * @author Samuel Ors
 */
class CalcButton extends TextButton implements SelectHandler {	
	private CalculatorCtrl ctrl;
	private CalculatorCtrl.ECalcCmds cmd = CalculatorCtrl.ECalcCmds.none;
	
	public CalcButton() {
		super();
		this.addSelectHandler(this);				
	}
	public CalcButton(String text) {
		super(text);
		this.addSelectHandler(this);
	}
	public CalcButton(String text, CalculatorCtrl ctrl, CalculatorCtrl.ECalcCmds cmd) {
		super(text);
		this.ctrl = ctrl;
		this.cmd = cmd;
		this.addSelectHandler(this);		
	}	
	@Override
	public void onSelect(SelectEvent event) {
		ctrl.processCmd(cmd);
	}
}

/**
 * CalculatorView 
 * View for the Calculator component.
 * @author Samuel Ors
 */
public class CalculatorView implements ModelObserver {
	private CalculatorCtrl ctrl;
	private TextBox editArea;
 
	private void buildUI(Panel placeHolder) {				
		final int BTN_MARGIN = 2;
		final int BTN_WIDTH = 48 + BTN_MARGIN*2;
		final int BTN_HEIGHT = 32 + BTN_MARGIN*2;
		Margins margins = new Margins(BTN_MARGIN, BTN_MARGIN, BTN_MARGIN, BTN_MARGIN);
		
		//Main panel for the calculator UI
		DecoratorPanel panel = new DecoratorPanel();
		placeHolder.add(panel);
		//RootPanel.get("calcContainer").add(panel);
		
		//Main vertical for placing rows
		VerticalLayoutContainer vl = new VerticalLayoutContainer();
		panel.add(vl);
		
		VerticalLayoutData vld = new VerticalLayoutData(BTN_WIDTH * 5, BTN_HEIGHT);
		HorizontalLayoutData hld = new HorizontalLayoutData(BTN_WIDTH, BTN_HEIGHT, margins);
		
		HorizontalLayoutContainer hl;		
		editArea = new TextBox();
		editArea.setAlignment(TextAlignment.RIGHT);		

		//Row 1
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(editArea, new HorizontalLayoutData(BTN_WIDTH*3, BTN_HEIGHT, margins));
		hl.add(new CalcButton("C", ctrl,  CalculatorCtrl.ECalcCmds.clear), hld);	
		hl.add(new CalcButton("CE", ctrl, CalculatorCtrl.ECalcCmds.clearCurr), hld);
		
		//Row 2
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("7", ctrl, CalculatorCtrl.ECalcCmds.v7), hld);	
		hl.add(new CalcButton("8", ctrl, CalculatorCtrl.ECalcCmds.v8), hld);
		hl.add(new CalcButton("9", ctrl, CalculatorCtrl.ECalcCmds.v9), hld);
		hl.add(new CalcButton("+/-", ctrl, CalculatorCtrl.ECalcCmds.sign), hld);
		hl.add(new CalcButton("%", ctrl, CalculatorCtrl.ECalcCmds.percent), hld);

		//Row 3
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("4", ctrl, CalculatorCtrl.ECalcCmds.v4), hld);	
		hl.add(new CalcButton("5", ctrl, CalculatorCtrl.ECalcCmds.v5), hld);
		hl.add(new CalcButton("6", ctrl, CalculatorCtrl.ECalcCmds.v6), hld);
		hl.add(new CalcButton("+", ctrl, CalculatorCtrl.ECalcCmds.add), hld);
		hl.add(new CalcButton("-", ctrl, CalculatorCtrl.ECalcCmds.substract), hld);
		
		//Row 4
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("1", ctrl, CalculatorCtrl.ECalcCmds.v1), hld);	
		hl.add(new CalcButton("2", ctrl, CalculatorCtrl.ECalcCmds.v2), hld);
		hl.add(new CalcButton("3", ctrl, CalculatorCtrl.ECalcCmds.v3), hld);
		hl.add(new CalcButton("*", ctrl, CalculatorCtrl.ECalcCmds.multiply), hld);
		hl.add(new CalcButton("/", ctrl, CalculatorCtrl.ECalcCmds.divide), hld);

		//Row 5
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("0", ctrl, CalculatorCtrl.ECalcCmds.v0), hld);	
		hl.add(new CalcButton(".", ctrl, CalculatorCtrl.ECalcCmds.dot), hld);
		hl.add(new CalcButton("=", ctrl, CalculatorCtrl.ECalcCmds.equal), 
			new HorizontalLayoutData(BTN_WIDTH*3 - BTN_MARGIN, BTN_HEIGHT, new Margins(0, 0, 0, BTN_WIDTH*2 + BTN_MARGIN)));
	}
	
	CalculatorView(CalculatorCtrl ctrl, Panel placeHolder) {
		this.ctrl = ctrl;
		buildUI(placeHolder);
		refreshView();
	}
	protected void refreshView() {
		String value = ctrl.getCurrentValue();
		editArea.setText(value);
		/*float fValue = Float.parseFloat(value);
		if( editArea != null ) {
			if( fValue % 1 == 0)
				editArea.setText(Integer.toString((int)fValue));
			else
				editArea.setText(Float.toString(fValue));
		}*/
	}
	//ModelObserver
	@Override
	public void onModelChange() {
		refreshView();
	}
}
