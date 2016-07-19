package inaer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.GridStateHandler;
import com.sencha.gxt.state.client.StateManager;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.Container;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.grid.CellSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.selection.CellSelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.CellSelectionChangedEvent.CellSelectionChangedHandler;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import inaer.shared.CalcDataSimple;

public class CalcDataView implements IsWidget, EntryPoint {
  protected static final int MAX_HEIGHT = 600;
  protected static final int MAX_WIDTH = 800;
  protected static final int MIN_HEIGHT = 320;
  protected static final int MIN_WIDTH = 480;

  private class DataPagedResult extends ArrayList<CalcDataSimple> implements PagingLoadResult<CalcDataSimple> {
	private static final long serialVersionUID = 1L;
	private int offset = 0;
	@Override
	public List<CalcDataSimple> getData() {
		return this;
	}
	@Override
	public int getOffset() {
		return offset;
	}
	@Override
	public void setOffset(int offset) {
		offset = 0;
	}
	@Override
	public int getTotalLength() {
		return size();
	}
	@Override
	public void setTotalLength(int totalLength) {
		//Not implemented
	}	  
  }
  private static final CalcDataProperties props = GWT.create(CalcDataProperties.class);

  private ContentPanel panel;
  private DataPagedResult dataList = new DataPagedResult();
        
  public void clearData() {
    dataList.clear();
  }
  public void addData(CalcDataSimple data) {
    dataList.add(data);
  } 
  
  @Override
  public Widget asWidget() {
  if (panel == null) {
		  final DataProxy<PagingLoadConfig, PagingLoadResult<CalcDataSimple>> dataPagingLoader = 
			new DataProxy<PagingLoadConfig, PagingLoadResult<CalcDataSimple>>() {
			  @Override
			  public void load(PagingLoadConfig loadConfig, Callback<PagingLoadResult<CalcDataSimple>, Throwable> callback) {
				  int offset = loadConfig.getOffset();
				  dataList.setOffset(offset);
				  callback.onSuccess(dataList);				
			  }
		  };
    	
	  ColumnConfig<CalcDataSimple, String> date = new ColumnConfig<CalcDataSimple, String>(props.timestamp(), 30, "Date");
	  ColumnConfig<CalcDataSimple, Double> input = new ColumnConfig<CalcDataSimple, Double>(props.input(), 20, "Input");
      ColumnConfig<CalcDataSimple, String> output= new ColumnConfig<CalcDataSimple, String>(props.output(), 40, "Output");
//      
//      ColumnConfig<Stock, Double> changeCol = new ColumnConfig<Stock, Double>(props.change(), 75, "Change");
//      ColumnConfig<Stock, Date> lastTransCol = new ColumnConfig<Stock, Date>(props.lastTrans(), 100, "Last Updated");

//      final NumberFormat number = NumberFormat.getFormat("0.00");
//      changeCol.setCell(new AbstractCell<Double>() {
//        @Override
//        public void render(Context context, Double value, SafeHtmlBuilder sb) {
//          String style = "style='color: " + (value < 0 ? "red" : "green") + "'";
//          String v = number.format(value);
//          sb.appendHtmlConstant("<span " + style + ">" + v + "</span>");
//        }
//      });

      //timestamp.setCell(new DateCell(DateTimeFormat.getFormat("MM/dd/yyyy")));

      List<ColumnConfig<CalcDataSimple, ?>> columns = new ArrayList<ColumnConfig<CalcDataSimple, ?>>();
      columns.add(date);
      columns.add(input);
      columns.add(output);

      ColumnModel<CalcDataSimple> cm = new ColumnModel<CalcDataSimple>(columns);

      ListStore<CalcDataSimple> store = new ListStore<CalcDataSimple>(props.key());
      store.addAll(dataList);
      
	  /*final PagingLoader<PagingLoadConfig, PagingLoadResult<CalcDataSimple>> gridLoader = 
    		  new PagingLoader<PagingLoadConfig, PagingLoadResult<CalcDataSimple>>(dataPagingLoader);
      gridLoader.setRemoteSort(true);*/
      
      final CalcDataPageLoader loader = new CalcDataPageLoader(dataPagingLoader);
      	loader.setRemoteSort(true);
      	loader.addLoadHandler(new LoadResultListStoreBinding<PagingLoadConfig, CalcDataSimple, PagingLoadResult<CalcDataSimple>>(store));

      final Grid<CalcDataSimple> grid = new Grid<CalcDataSimple>(store, cm) {
        @Override
        protected void onAfterFirstAttach() {
          super.onAfterFirstAttach();
          // After the Grid has been attached to DOM load the data
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
              loader.load();
            }
          });
        }
      };
      
      grid.setLoadMask(true);
      grid.setLoader(loader);
      grid.setAllowTextSelection(true);
      grid.getView().setAutoExpandColumn(date);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);
      grid.setBorders(false);
      grid.setColumnReordering(true);

      // Stage manager, turn on state management
      grid.setStateful(true);
      grid.setStateId("gridExample");

      // Stage manager, load previous state
      GridStateHandler<CalcDataSimple> state = new GridStateHandler<CalcDataSimple>(grid);
      state.loadState();

      SimpleComboBox<String> typeCombo = new SimpleComboBox<String>(new StringLabelProvider<String>());
      typeCombo.setTriggerAction(TriggerAction.ALL);
      typeCombo.setEditable(false);
      typeCombo.setWidth(100);
      typeCombo.add("Row");
      typeCombo.add("Cell");
      typeCombo.setValue("Row");
      // we want to change selection model on select, not value change which fires on blur
      typeCombo.addSelectionHandler(new SelectionHandler<String>() {
        @Override
        public void onSelection(SelectionEvent<String> event) {
          boolean cell = event.getSelectedItem().equals("Cell");
          if (cell) {
            CellSelectionModel<CalcDataSimple> c = new CellSelectionModel<CalcDataSimple>();
            c.addCellSelectionChangedHandler(new CellSelectionChangedHandler<CalcDataSimple>() {
              @Override
              public void onCellSelectionChanged(CellSelectionChangedEvent<CalcDataSimple> event) {
              }
            });
            grid.setSelectionModel(c);
          } else {
            grid.setSelectionModel(new GridSelectionModel<CalcDataSimple>());
          }
        }
      });
      typeCombo.addValueChangeHandler(new ValueChangeHandler<String>() {
        @Override
        public void onValueChange(ValueChangeEvent<String> event) {
        }
      });

      ToolBar toolBar = new ToolBar();
      toolBar.setEnableOverflow(false);
      //toolBar.add(new LabelToolItem("Selection Mode: "));
      //toolBar.add(typeCombo);

      VerticalLayoutContainer con = new VerticalLayoutContainer();
      con.add(toolBar, new VerticalLayoutData(1, -1));
      con.add(grid, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setHeading("Basic Grid");
      panel.add(con);
      panel.setWidth("600px");
      panel.setHeight("600px");
    }

    return panel;
  }

  @Override
  public void onModuleLoad() {
    // State manager, initialize the state options
    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));
    Container cont = new SimpleContainer();
    cont.setWidth(MIN_WIDTH);
    cont.setHeight(MIN_HEIGHT);
    cont.add(panel);
//    new ExampleContainer(this)
//        .setMaxHeight(MAX_HEIGHT)
//        .setMaxWidth(MAX_WIDTH)
//        .setMinHeight(MIN_HEIGHT)
//        .setMinWidth(MIN_WIDTH)
//        .doStandalone();
  }
}