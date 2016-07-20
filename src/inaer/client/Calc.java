package inaer.client;

import inaer.client.calculator.Controller;
import inaer.client.calculator.Model;
import inaer.client.calculator.View;
import inaer.shared.CalcDataSimple;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
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
    // Create the calculator objects
    calcModel = new Model();
    calcCtrl = new Controller(calcModel);
    calcView = new View(calcCtrl, RootPanel.get("calcContainer"));

    calcModel.addObserver(calcView);

    dataView = new CalcDataView();
    
    // Button to show/hide data dump.
    TextButton showDataButton = new TextButton();
    RootPanel.get("showDataContainer").add(showDataButton);
    showDataButton.setText("Show Datastore");
    showDataButton.addSelectHandler(new SelectHandler() {
      public void onSelect(SelectEvent event) {
        if(dataView.asWidget().isVisible() == false) {
          calcCtrl.getService().getData(new AsyncCallback<List<CalcDataSimple>>() {
            public void onFailure(Throwable caught) {
            }
  
            public void onSuccess(List<CalcDataSimple> result) {
              dataView.clearData();
              for (CalcDataSimple dataObj : result) {
                dataView.addData(dataObj);
              }
              dataView.show();
            }
          });
        }
        else
          dataView.hide();
      }
    });    
    RootPanel.get("showDataContainer").add(dataView.asWidget());
  }
}
