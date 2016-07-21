package inaer.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.DataProxy;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

import inaer.shared.CalcDataSimple;

public class CalcDataView implements IsWidget, EntryPoint {
  protected static final int HEIGHT = 320;
  protected static final int WIDTH = 240;

  private class DataPagedResult extends ArrayList<CalcDataSimple> implements PagingLoadResult<CalcDataSimple> {
    private static final long serialVersionUID = 1L;
    private int offset = 0;

    @Override
    public List<CalcDataSimple> getData() {
      List<CalcDataSimple>  result = subList(getOffset(), size());
      return result;
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
      // Not implemented
    }
  }

  private static final CalcDataProperties props = GWT.create(CalcDataProperties.class);

  private ContentPanel panel;
  private DataPagedResult dataList = new DataPagedResult();
  private CalcDataPageLoader loader;

  public void clearData() {
    dataList.clear();
  }

  public void addData(CalcDataSimple data) {
    dataList.add(data);
  }

  @Override
  public Widget asWidget() {
    if (panel == null) {
      final DataProxy<PagingLoadConfig, PagingLoadResult<CalcDataSimple>> dataPagingLoader = new DataProxy<PagingLoadConfig, PagingLoadResult<CalcDataSimple>>() {
        @Override
        public void load(PagingLoadConfig loadConfig, Callback<PagingLoadResult<CalcDataSimple>, Throwable> callback) {
          int offset = loadConfig.getOffset();
          dataList.setOffset(offset);
          callback.onSuccess(dataList);
        }
      };

      ColumnConfig<CalcDataSimple, String> date = new ColumnConfig<CalcDataSimple, String>(props.timestamp(), 160, "Date");
      ColumnConfig<CalcDataSimple, Double> input = new ColumnConfig<CalcDataSimple, Double>(props.input(), 60, "Input");
      ColumnConfig<CalcDataSimple, String> output = new ColumnConfig<CalcDataSimple, String>(props.output(), 60, "Output");

      List<ColumnConfig<CalcDataSimple, ?>> columns = new ArrayList<ColumnConfig<CalcDataSimple, ?>>();
      columns.add(date);
      columns.add(input);
      columns.add(output);

      ColumnModel<CalcDataSimple> cm = new ColumnModel<CalcDataSimple>(columns);

      ListStore<CalcDataSimple> store = new ListStore<CalcDataSimple>(props.key());

      loader = new CalcDataPageLoader(dataPagingLoader);
      loader.setRemoteSort(true);
      loader.setLimit(5);
      loader.addLoadHandler(
          new LoadResultListStoreBinding<PagingLoadConfig, CalcDataSimple, PagingLoadResult<CalcDataSimple>>(store));

      final Grid<CalcDataSimple> grid = new Grid<CalcDataSimple>(store, cm);

      grid.setLoadMask(true);
      grid.setLoader(loader);
      grid.setAllowTextSelection(true);
      grid.setHideHeaders(false);
      grid.getView().setStripeRows(true);
      grid.getView().setColumnLines(true);
      grid.setBorders(false);
      grid.setWidth(340);

      VerticalLayoutContainer vl = new VerticalLayoutContainer();
      vl.add(grid, new VerticalLayoutData(1, 1));

      panel = new ContentPanel();
      panel.setVisible(false);
      panel.setHeading("Datastore contents");
      panel.add(vl);
      panel.setWidth("340px");
      panel.setHeight("200px");
    }
    return panel;
  }
  
  public void show() {
    loader.load();
    panel.show();
  }

  public void hide() {
    panel.hide();
  }

  @Override
  public void onModuleLoad() {
    // State manager, initialize the state options
//    StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));
//    Container cont = new SimpleContainer();
//    cont.setWidth(WIDTH);
//    cont.setHeight(HEIGHT);
//    cont.add(panel);
  }
}