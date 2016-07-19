package inaer.client;

import inaer.client.calculator.Controller;
import inaer.client.calculator.Model;
import inaer.client.calculator.View;
import inaer.shared.CalcDataSimple;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calc implements EntryPoint {
	View calcView;
	Controller calcCtrl;
	Model calcModel;
	CalcDataView dataView;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		//Create the calculator objects
		calcModel = new Model();
		calcCtrl = new Controller(calcModel);
		calcView = new View(calcCtrl, RootPanel.get("calcContainer"));
		
		calcModel.addObserver(calcView);
		
		dataView = new CalcDataView();
		
		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Calculator data dump");
		//dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		//final Label textToServerLabel = new Label();
		//final HTML serverResponseLabel = new HTML();
		
		VerticalPanel dialogVPanel = new VerticalPanel();		
		dialogVPanel.addStyleName("dialogVPanel");
		
		//dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		//dialogVPanel.add(textToServerLabel);
		
		//dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		//dialogVPanel.add(serverResponseLabel);		
		
		
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
		
		dialogVPanel.add(dataView);
		
		ScrollPanel panel = new ScrollPanel();
		panel.add(dialogVPanel);
		panel.setWidth("800px");
		panel.setHeight("600px");
		dialogBox.setWidget(panel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();				
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {				
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {					
				}
			}

		}

		// Add a handler to send the name to the server
		@SuppressWarnings("unused")
		MyHandler handler = new MyHandler();
		//sendButton.addClickHandler(handler);
		//nameField.addKeyUpHandler(handler);
		
		//Button for data dump
		TextButton showDataButton = new TextButton();
		RootPanel.get("showDataContainer").add(showDataButton);
		showDataButton.setText("Show Datastore");
		showDataButton.addSelectHandler(new SelectHandler() {
			public void onSelect(SelectEvent event) {
				System.out.println("ShowData clicked");
				calcCtrl.getService().getData(new AsyncCallback<List<CalcDataSimple>> () {
					public void onFailure(Throwable caught) {						
					}
					public void onSuccess(List<CalcDataSimple> result) {
						for (CalcDataSimple dataObj : result) {
							dataView.addData(dataObj);
						    //System.out.println(dataObj.toString());
						}
						dialogBox.center();
					}
				});
			}
		});
	}
}
