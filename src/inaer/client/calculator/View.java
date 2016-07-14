package inaer.client.calculator;

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
	private Controller ctrl;
	private Controller.ECalcCmds cmd = Controller.ECalcCmds.none;
	
	public CalcButton() {
		super();
		this.addSelectHandler(this);				
	}
	public CalcButton(String text) {
		super(text);
		this.addSelectHandler(this);
	}
	public CalcButton(String text, Controller ctrl, Controller.ECalcCmds cmd) {
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
public class View implements ModelObserver {
	private Controller ctrl;
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
		hl.add(new CalcButton("C", ctrl,  Controller.ECalcCmds.clear), hld);	
		hl.add(new CalcButton("CE", ctrl, Controller.ECalcCmds.clearCurr), hld);
		
		//Row 2
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("7", ctrl, Controller.ECalcCmds.v7), hld);	
		hl.add(new CalcButton("8", ctrl, Controller.ECalcCmds.v8), hld);
		hl.add(new CalcButton("9", ctrl, Controller.ECalcCmds.v9), hld);
		hl.add(new CalcButton("+/-", ctrl, Controller.ECalcCmds.sign), hld);
		hl.add(new CalcButton("%", ctrl, Controller.ECalcCmds.percent), hld);

		//Row 3
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("4", ctrl, Controller.ECalcCmds.v4), hld);	
		hl.add(new CalcButton("5", ctrl, Controller.ECalcCmds.v5), hld);
		hl.add(new CalcButton("6", ctrl, Controller.ECalcCmds.v6), hld);
		hl.add(new CalcButton("+", ctrl, Controller.ECalcCmds.add), hld);
		hl.add(new CalcButton("-", ctrl, Controller.ECalcCmds.substract), hld);
		
		//Row 4
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("1", ctrl, Controller.ECalcCmds.v1), hld);	
		hl.add(new CalcButton("2", ctrl, Controller.ECalcCmds.v2), hld);
		hl.add(new CalcButton("3", ctrl, Controller.ECalcCmds.v3), hld);
		hl.add(new CalcButton("*", ctrl, Controller.ECalcCmds.multiply), hld);
		hl.add(new CalcButton("/", ctrl, Controller.ECalcCmds.divide), hld);

		//Row 5
		hl = new HorizontalLayoutContainer();
		vl.add(hl, vld);
		hl.add(new CalcButton("0", ctrl, Controller.ECalcCmds.v0), hld);	
		hl.add(new CalcButton(".", ctrl, Controller.ECalcCmds.dot), hld);
		hl.add(new CalcButton("=", ctrl, Controller.ECalcCmds.equal), 
			new HorizontalLayoutData(BTN_WIDTH*3 - BTN_MARGIN, BTN_HEIGHT, new Margins(0, 0, 0, BTN_WIDTH*2 + BTN_MARGIN)));
	}
	
	public View(Controller ctrl, Panel placeHolder) {
		this.ctrl = ctrl;
		buildUI(placeHolder);
		refreshView();
	}
	protected void refreshView() {
		String value = ctrl.getCurrentValue();
		if(value == "")
			editArea.setText("0");
		else
			editArea.setText(value);
	}
	//ModelObserver
	@Override
	public void onModelChange() {
		refreshView();
	}
}
